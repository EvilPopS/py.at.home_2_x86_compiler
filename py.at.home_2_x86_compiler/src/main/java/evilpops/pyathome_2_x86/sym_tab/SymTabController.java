package main.java.evilpops.pyathome_2_x86.sym_tab;

import main.java.evilpops.pyathome_2_x86.assembly_gen.enums.AssemblyRegister;
import main.java.evilpops.pyathome_2_x86.sym_tab.enums.DataType;
import main.java.evilpops.pyathome_2_x86.sym_tab.enums.TabType;
import main.java.evilpops.pyathome_2_x86.sym_tab.exceptions.TabTypeEnumNotInSyncWithTabClassesException;
import main.java.evilpops.pyathome_2_x86.sym_tab.tabs.*;
import main.java.evilpops.pyathome_2_x86.sym_tab.tabs.row_struct.ConcreteRowArchetype;
import main.java.evilpops.pyathome_2_x86.sym_tab.tabs.row_struct.MainTabRow;

public class SymTabController implements ISymTabController {
    protected static ISymTabController symTabBean = null;
    protected final MainTab mainTab;
    protected final VariableTab variableTab;
    protected final LiteralTab literalTab;
    protected final RegisterTab registerTab;



    private SymTabController() {
        this.mainTab = new MainTab();
        this.variableTab = new VariableTab();
        this.literalTab = new LiteralTab();
        this.registerTab = new RegisterTab();
    }

    public static ISymTabController getInstance() {
        if (symTabBean == null)
            symTabBean = new SymTabController();
        return symTabBean;
    }

    @Override
    public int addVariable(DataType dataType, String name, int ordinality) {
        int rowRef = this.mainTab.getNextFreeRowInd();
        this.variableTab.add(rowRef, dataType, name, ordinality);
        this.mainTab.addVariable(this.variableTab.getLastRowInd());
        return rowRef;
    }

    @Override
    public int addLiteral(String value, DataType dataType) {
        int rowRef = this.mainTab.getNextFreeRowInd();
        this.literalTab.add(rowRef, dataType, value);
        this.mainTab.addLiteral(this.literalTab.getLastRowInd());
        return rowRef;
    }

    @Override
    public int addLiteralFloat(String value, DataType dataType, int dataLabelCounter) {
        int rowRef = this.mainTab.getNextFreeRowInd();
        this.literalTab.addFloat(rowRef, value, dataLabelCounter);
        this.mainTab.addLiteral(this.literalTab.getLastRowInd());
        return rowRef;
    }

    @Override
    public int addLiteralString(String value, DataType dataType, int dataLabelCounter) {
        int rowRef = this.mainTab.getNextFreeRowInd();
        this.literalTab.addString(rowRef, value, dataLabelCounter);
        this.mainTab.addLiteral(this.literalTab.getLastRowInd());
        return rowRef;
    }

    @Override
    public int takeRegister(DataType dataType) {
        int rowRef = this.mainTab.getNextFreeRowInd();
        this.mainTab.addRegister(this.registerTab.takeGenPurposeReg(rowRef, dataType));
        return rowRef;
    }

    @Override
    public int getVarRefByName(String name) {
        return this.variableTab.getByName(name).getForeignId();
    }

    @Override
    public DataType getDataType(int ind) {
        MainTabRow rowData = this.mainTab.get(ind);
        return getConcreteTableByTabType(rowData.getRefTabType())
                .getDataType(rowData.getForeignId());
    }

    @Override
    public String getLiteralValue(int ind) {
        return this.literalTab.getValue(getForeignId(ind));
    }

    @Override
    public AssemblyRegister getRegName(int ind) {
        return this.registerTab.getRegisterName(getForeignId(ind));
    }

    @Override
    public int getVarOrdinality(int ind) {
        return this.variableTab.getOrdinality(getForeignId(ind));
    }

    @Override
    public int getDataLabelCounter(int ind) {
        return this.literalTab.getDataLabelCounter(getForeignId(ind));
    }

    @Override
    public void setDataType(int ind, DataType dataType) {
        MainTabRow rowData = this.mainTab.get(ind);
        getConcreteTableByTabType(rowData.getRefTabType())
                .setDataType(rowData.getForeignId(), dataType);
    }

    @Override
    public boolean checkIfIsReg(int ind) {
        return this.checkIfIsOfGivenTabType(ind, TabType.REGISTER);
    }

    @Override
    public boolean checkIfIsLiteral(int ind) {
        return this.checkIfIsOfGivenTabType(ind, TabType.LITERAL);
    }

    @Override
    public boolean checkIfIsVar(int ind) {
        return this.checkIfIsOfGivenTabType(ind, TabType.VARIABLE);
    }

    @Override
    public boolean checkIfDataTypeIsFloat(int ind) {
        return this.getDataType(ind).equals(DataType.FLOAT);
    }

    @Override
    public boolean checkIfDataTypeIsString(int ind) {
        return this.getDataType(ind).equals(DataType.STRING);
    }

    @Override
    public boolean checkIfDataTypeIsBoolean(int ind) {
        return this.getDataType(ind) == DataType.BOOLEAN;
    }

    @Override
    public void freeIfIsRegister(int ind) {
        if (this.checkIfIsReg(ind))
            this.registerTab.freeRegister(this.mainTab.get(ind).getForeignId());
    }

    private ConcreteTableArchetype<? extends ConcreteRowArchetype> getConcreteTableByTabType(TabType tabType) {
        return switch (tabType) {
            case VARIABLE -> this.variableTab;
            case LITERAL -> this.literalTab;
            case REGISTER -> this.registerTab;
            default -> throw new TabTypeEnumNotInSyncWithTabClassesException();
        };
    }

    private boolean checkIfIsOfGivenTabType(int ind, TabType tabType) {
        return this.mainTab.get(ind).getRefTabType().equals(tabType);
    }

    private int getForeignId(int ind) {
        return this.mainTab.get(ind).getForeignId();
    }
}
