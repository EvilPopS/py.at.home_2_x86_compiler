package main.java.evilpops.pyathome_2_x86.grammar_listener.ctx_processors.simple_statements_domain;

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
import main.java.evilpops.pyathome_2_x86.symbol_table.exceptions.VariableNotFoundException;

public class AssignStatementCtxProcessor {
    private static final IAssemblyGenerator assemblyGenerator = AssemblyGenerator.getInstance();
    private static final ISymTabController symTabController = SymTabController.getInstance();
    private static final CompilationInfoTracker compilationInfoTracker = CompilationInfoTracker.getInstance();

    public static void processOnExit(PyAtHomeParser.AssignStatementContext ctx) {
        String varName = ctx.ID().getText();
        int numExpRef = ctx.complexExpression().getRefToSymTab();
        DataType numExpDataType = symTabController.getDataType(numExpRef);

        DataType explicitType = (ctx.varType() != null)
                ? TypesContextProcessor.convertTypingCtxToDataType(ctx.varType().types())
                : DataType.UNKNOWN;

        if (!explicitType.equals(DataType.UNKNOWN) && !explicitType.equals(numExpDataType))
            throw new CompilationError("Expl and impl types not same.");

        int idRef;
        try {
            idRef = processFindVarInCurrFuncScope(varName, numExpDataType);
        } catch (VariableNotFoundException ignored) {
            idRef = processVarRefCreate(varName, numExpDataType);
        }

        processNumExpToVarAssignment(idRef, numExpRef, numExpDataType);
    }

    private static int processFindVarInCurrFuncScope(String varName, DataType numExpDataType) {
        int idRef = symTabController.getVarRefByNameInCurrentFuncScope(
                varName,
                compilationInfoTracker.getFunctionScopeTracker().getScope()
        );
        symTabController.setDataType(idRef, numExpDataType);
        return idRef;
    }

    private static int processVarRefCreate(String varName, DataType numExpDataType) {
        int idRef = symTabController.addVariable(
                numExpDataType,
                compilationInfoTracker.getFunctionScopeTracker().getScope(),
                compilationInfoTracker.getBlockScopeTracker().getScope(),
                varName,
                compilationInfoTracker.getCurrFuncTracker().incAndGetVarCounter()
        );
        assemblyGenerator.genStackPointerDec(1);
        return idRef;
    }

    private static void processNumExpToVarAssignment(int idRef, int numExpRef, DataType numExpDataType) {
        boolean is64bit = !numExpDataType.equals(DataType.FLOAT);
        if (
                (symTabController.checkIfIsLiteral(numExpRef) && !is64bit)
                        || symTabController.checkIfIsVar(numExpRef)
        ) {
            int oldNumExpRef = numExpRef;
            numExpRef = symTabController.takeRegister(numExpDataType);
            assemblyGenerator.genMoveSymbolToReg(
                    AssemblySymbolProcessor.createAssemblySymbol(oldNumExpRef),
                    symTabController.getRegName(numExpRef),
                    is64bit

            );
        }

        assemblyGenerator.genMoveSymbolToSymbol(
                AssemblySymbolProcessor.createAssemblySymbol(numExpRef),
                AssemblySymbolProcessor.createAssemblySymbol(idRef),
                is64bit
        );
        symTabController.freeIfIsRegister(numExpRef);
    }

}
