package main.java.evilpops.pyathome_2_x86.grammar_listener.ctx_processors.function_def_domain;


import main.java.evilpops.pyathome_2_x86.assembly_generator.AssemblyGenerator;
import main.java.evilpops.pyathome_2_x86.assembly_generator.IAssemblyGenerator;
import main.java.evilpops.pyathome_2_x86.compilation_info_tracker.CompilationInfoTracker;
import main.java.evilpops.pyathome_2_x86.grammar.grammar_classes.PyAtHomeParser;
import main.java.evilpops.pyathome_2_x86.grammar_listener.utils.AssemblySymbolProcessor;
import main.java.evilpops.pyathome_2_x86.grammar_listener.utils.TypesContextProcessor;
import main.java.evilpops.pyathome_2_x86.log_handler.exceptions.CompilationError;
import main.java.evilpops.pyathome_2_x86.symbol_table.ISymTabController;
import main.java.evilpops.pyathome_2_x86.symbol_table.SymTabController;
import main.java.evilpops.pyathome_2_x86.symbol_table.enums.DataType;

public class ParamDefValCtxProcessor {
    private static final IAssemblyGenerator assemblyGenerator = AssemblyGenerator.getInstance();
    private static final ISymTabController symTabController = SymTabController.getInstance();
    private static final CompilationInfoTracker compilationInfoTracker = CompilationInfoTracker.getInstance();

    public static void processOnEnter() {
        compilationInfoTracker.getDefParamsLblTracker().incLblCount();
        int lblNum = compilationInfoTracker.getDefParamsLblTracker().getLblCounter();
        assemblyGenerator.genNonCondJmpToDefParamCondStart(lblNum);
        assemblyGenerator.genDefParamNumExpLabel(lblNum);
    }

    public static void processOnExit(PyAtHomeParser.ParamDefValContext ctx) {
        if (ctx.COMMA() != null)
            return;

        int numExpRef = ctx.complexExpression().getRefToSymTab();
        DataType numExpDataType = symTabController.getDataType(numExpRef);

        if (
                ctx.varType() != null
                        && !numExpDataType.equals(
                        TypesContextProcessor.convertTypingCtxToDataType(
                                ctx.varType().types()
                        )
                )
        ) {
            throw new CompilationError("Explicit and implicit parameter types must be the same!");
        }

        boolean is64bit = !numExpDataType.equals(DataType.FLOAT);

        int perDataTypeParamOrdinality = is64bit
                ? compilationInfoTracker.getCurrFuncTracker().incAndGetNonFloatParamCnt()
                : compilationInfoTracker.getCurrFuncTracker().incAndGetFloatParamCnt();

        int paramRef = symTabController.addParameter(
                numExpDataType,
                symTabController.getFuncScope(
                        compilationInfoTracker.getCurrFuncTracker().getFuncRef()
                ),
                compilationInfoTracker.getBlockScopeTracker().getScope(),
                ctx.ID().getText(),
                compilationInfoTracker.getCurrFuncTracker().getFuncRef(),
                true,
                compilationInfoTracker.getCurrFuncTracker().getTotalParamCnt(),
                perDataTypeParamOrdinality
        );

        int paramAsVarRef = symTabController.transferParamToVar(
                paramRef,
                compilationInfoTracker.getCurrFuncTracker().incAndGetVarCounter(),
                compilationInfoTracker.getFunctionScopeTracker().getScope(),
                compilationInfoTracker.getBlockScopeTracker().getScope()
        );

        genAfterNumExpCode(paramAsVarRef, numExpRef, is64bit);
        genParamDefConditionCode(paramRef, paramAsVarRef);
    }

    private static void genAfterNumExpCode(int paramAsVarRef, int numExpRef, boolean is64bit) {
        assemblyGenerator.genStackPointerDec(1);
        if (
                (symTabController.checkIfIsLiteral(numExpRef) && !is64bit)
                        || symTabController.checkIfIsVar(numExpRef)
        ) {
            int regRef = symTabController.takeRegister(symTabController.getDataType(numExpRef));
            assemblyGenerator.genMoveSymbolToReg(
                    AssemblySymbolProcessor.createAssemblySymbol(numExpRef),
                    symTabController.getRegName(regRef),
                    is64bit

            );
            assemblyGenerator.genMoveSymbolToSymbol(
                    AssemblySymbolProcessor.createAssemblySymbol(regRef),
                    AssemblySymbolProcessor.createAssemblySymbol(paramAsVarRef),
                    is64bit
            );
            symTabController.freeIfIsRegister(regRef);
        } else {
            assemblyGenerator.genMoveSymbolToSymbol(
                    AssemblySymbolProcessor.createAssemblySymbol(numExpRef),
                    AssemblySymbolProcessor.createAssemblySymbol(paramAsVarRef),
                    is64bit
            );
        }
        symTabController.freeIfIsRegister(numExpRef);

        assemblyGenerator.genNonCondJmpToDefParamCondEnd(
                compilationInfoTracker.getDefParamsLblTracker().getLblCounter()
        );

    }

    private static void genParamDefConditionCode(int paramRef, int paramAsVarRef) {
        int lblNum = compilationInfoTracker.getDefParamsLblTracker().getLblCounter();
        assemblyGenerator.genDefParamCondStartLabel(lblNum);

        int paramRegRef = symTabController.takeParamReg(
                symTabController.getPerDataTypeParamOrdinality(paramRef),
                symTabController.getParamDataType(paramRef)
        );

        if (symTabController.checkIfParamDataTypeIsFloat(paramRef))
            genParamDefConditionWhen128bit(paramRegRef, paramAsVarRef);
        else
            genParamDefConditionWhen64bit(paramRegRef, paramAsVarRef);

        assemblyGenerator.genDefParamCondEndLabel(lblNum);
    }

    private static void genParamDefConditionWhen64bit(int paramRegRef, int paramAsVarRef) {
        assemblyGenerator.genCmpToUndefined64bit(
                AssemblySymbolProcessor.createAssemblySymbol(paramRegRef)
        );
        assemblyGenerator.genJmpIfEqToDefParamNumExpStart(
                compilationInfoTracker.getDefParamsLblTracker().getLblCounter()
        );
        assemblyGenerator.genStackPointerDec(1);
        assemblyGenerator.genMoveSymbolToSymbol(
                AssemblySymbolProcessor.createAssemblySymbol(paramRegRef),
                AssemblySymbolProcessor.createAssemblySymbol(paramAsVarRef),
                true
        );
    }

    private static void genParamDefConditionWhen128bit(int paramRegRef, int paramAsVarRef) {
        int undefinedAddressRegRef = symTabController.takeRegister(DataType.INTEGER);
        int undefinedAddressRefRefAs128bit = symTabController.takeRegister(DataType.FLOAT);

        assemblyGenerator.genMovUndefinedToReg(symTabController.getRegName(undefinedAddressRegRef));
        assemblyGenerator.genIntToFloatConversion(
                AssemblySymbolProcessor.createAssemblySymbol(undefinedAddressRegRef),
                AssemblySymbolProcessor.createAssemblySymbol(undefinedAddressRefRefAs128bit)
        );
        symTabController.freeIfIsRegister(undefinedAddressRegRef);

        assemblyGenerator.genCmpSymbolToSymbol(
                AssemblySymbolProcessor.createAssemblySymbol(paramRegRef),
                AssemblySymbolProcessor.createAssemblySymbol(undefinedAddressRefRefAs128bit),
                false
        );
        symTabController.freeIfIsRegister(undefinedAddressRefRefAs128bit);

        assemblyGenerator.genJmpIfEqToDefParamNumExpStart(
                compilationInfoTracker.getDefParamsLblTracker().getLblCounter()
        );
        assemblyGenerator.genStackPointerDec(1);
        assemblyGenerator.genMoveSymbolToSymbol(
                AssemblySymbolProcessor.createAssemblySymbol(paramRegRef),
                AssemblySymbolProcessor.createAssemblySymbol(paramAsVarRef),
                false
        );
    }
}
