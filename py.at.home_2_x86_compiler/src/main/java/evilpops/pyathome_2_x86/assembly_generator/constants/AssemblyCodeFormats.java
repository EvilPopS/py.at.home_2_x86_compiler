package main.java.evilpops.pyathome_2_x86.assembly_generator.constants;

@SuppressWarnings("ALL")
public class AssemblyCodeFormats {
    public static final String SECTION = ".section .%s\n";
    public static final String GLOBAL_MAIN = ".globl main\n";
    public static final String MAIN_LBL = "main:\n";
    public static final String MAIN_START_CODE = "\tpushq %rbp\n\tandq $-16, %rsp\n\tmovq %rsp, %rbp\n";
    public static final String PROGRAM_END_CODE = "main_end:\n\tmovq $60, %rax\n\txorq %rdi, %rdi\n\tsyscall\n\n";

    public static final String PUSH_INST = "\tpush%s %s\n";
    public static final String POP_INST = "\tpop%s %s\n";
    public static final String MOVE_INST = "\tmov%s %s, %s\n";
    public static final String ADD_INST = "\tadd%s %s, %s\n";
    public static final String SUB_INST = "\tsub%s %s, %s\n";
    public static final String MUL_INST = "\t%smul%s %s, %s\n";
    public static final String DIV_INST = "\tdivsd %s, %s\n";
    public static final String AND_INST = "\tandq %s, %s\n";
    public static final String OR_INST = "\torq %s, %s\n";
    public static final String CALL_INST = "\tcall %s\n";
    public static final String RET_INST = "\tret\n";
    public static final String BOOLINT_2_FLOAT_INST = "\tcvtsi2sd %s, %s\n";
    public static final String CMP_INT = "\tcmpq %s, %s\n";
    public static final String CMP_FLOAT = "\tcomisd %s, %s\n";

    public static final String JMP_INST = "\t%s %s\n";
    public static final String JMP_NON_COND = "jmp";
    public static final String JMP_EQ = "je";
    public static final String JMP_NOT_EQ = "jne";
    public static final String JMP_GREATER = "jg";
    public static final String JMP_ABOVE = "ja";
    public static final String JMP_GREATER_EQ = "jge";
    public static final String JMP_ABOVE_EQ = "jae";
    public static final String JMP_LESSER = "jl";
    public static final String JMP_BELOW = "jb";
    public static final String JMP_LESSER_EQ = "jle";
    public static final String JMP_BELOW_EQ = "jbe";

    public static final String INST_PREFIX = "i";
    public static final String INST_SUFFIX = "q";
    public static final String FLOAT_INST_SUFFIX = "sd";

    public static final String POS_8_BYTES = "8";
    public static final String NEG_8_BYTES = "-8";
    public static final String POS_16_BYTES = "16";
    public static final String NEG_16_BYTES = "-16";

    public static final String REG_ACCESS = "%%%s";
    public static final String MEM_ACCESS = "%s(%s)";
    public static final String LITERAL_W_DOLLAR = "$%s";

    public static final String DATA_SEC_FLOAT_REF = "FLOAT_VAL%s";
    public static final String DATA_SEC_STRING_REF = "STRING_VAL%s";
    public static final String FLOAT_LIT_DATA_SEC = "\t" + DATA_SEC_FLOAT_REF + ": .double %s\n";
    public static final String STRING_LIT_DATA_SEC = "\t" + DATA_SEC_STRING_REF + ": .string \"%s\"\n";

    public static final String FLOAT_ZERO = "FLOAT_ZERO";
    public static final String NONE_LIT = "NONE";
    public static final String UNDEFINED_LIT = "UNDEFINED";
    public static final String PRINT_INT_OR_BOOL = "PRINT_INT_OR_BOOL_FORMAT";
    public static final String PRINT_FLOAT = "PRINT_FLOAT";
    public static final String PRINT_STRING = "PRINT_STRING_FORMAT";

    public static final String FLOAT_ZERO_DATA_SEC = "\t" + FLOAT_ZERO + ": .double 0.0\n";
    public static final String NONE_LIT_DATA_SEC = "\t" + NONE_LIT + ": .asciz \"None\"\n";
    public static final String UNDEFINED_LIT_DATA_SEC = "\t" + UNDEFINED_LIT + ": .asciz \"Undefined\"\n";
    public static final String PRINT_INT_OR_BOOL_DATA_SEC = "\t" + PRINT_INT_OR_BOOL + ": .asciz \"%d\\n\"\n";
    public static final String PRINT_FLOAT_DATA_SEC = "\t" + PRINT_FLOAT + ": .asciz \"%.3f\\n\"\n";
    public static final String PRINT_STRING_DATA_SEC = "\t" + PRINT_STRING + ": .asciz \"%s\\n\"\n";
    public static final String DATA_SECTION_INIT =
            NONE_LIT_DATA_SEC +
                    FLOAT_ZERO_DATA_SEC +
                    UNDEFINED_LIT_DATA_SEC +
                    PRINT_INT_OR_BOOL_DATA_SEC +
                    PRINT_FLOAT_DATA_SEC +
                    PRINT_STRING_DATA_SEC;

    public static final String LBL_FORMAT = "%s:\n";
    public static final String LBL_CMP_TRUE = "L_CMP_TRUE_%s";
    public static final String LBL_CMP_END = "L_CMP_END_%s";

    public static final String LBL_FUNC_NAME = "L_FUNC_%s";
    public static final String LBL_FUNC_END = "L_FUNC_%s_END";

    public static final String LBL_DEF_PARAM_COND = "L_DEF_PARAM_COND_%s";
    public static final String LBL_DEF_PARAM_COND_END = "L_DEF_PARAM_COND_%s_END";
    public static final String LBL_DEF_PARAM_NUM_EXP = "L_DEF_PARAM_NUM_EXP_%s";

    public static final String LBL_WHILE_ELSE_STAT_END = "L_WHILE_ELSE_STAT_END_%s";
    public static final String LBL_WHILE_CONDITION_START = "L_WHILE_CONDITION_START_%s";
    public static final String LBL_WHILE_ON_FIRST_TIME_ENTER = "L_WHILE_ON_FIRST_TIME_ENTER_%s";
    public static final String LBL_WHILE_STAT_PART_END = "L_WHILE_STAT_PART_END_%s";
    public static final String LBL_WHILE_BLOCK_START = "L_WHILE_BLOCK_START_%s";
    public static final String LBL_WHILE_BLOCK_END = "L_WHILE_BLOCK_END_%s";
    public static final String LBL_WHILE_LOOP_START = "L_WHILE_LOOP_START_%s";
    public static final String LBL_WHILE_LOOP_END = "L_WHILE_LOOP_END_%s";

    public static final String LBL_IF_ELIF_ELSE_STAT_END = "L_IF_ELIF_ELSE_STAT_END_%s";
    public static final String LBL_IF_STAT_START = "L_IF_STAT_START_%s";
    public static final String LBL_IF_STAT_END = "L_IF_STAT_END_%s";
    public static final String LBL_ELIF_STAT_START = "L_ELIF_STAT_START_%s_%s";
    public static final String LBL_ELIF_STAT_END = "L_ELIF_STAT_END_%s_%s";

    public static final String BUILT_IN_CONCAT_STRINGS_LBL = "BUILT_IN_STRING_CONCAT";
    public static final String BUILT_IN_STRINGS_MUL_LBL = "BUILT_IN_STRING_MUL";
    public static final String BUILT_IN_STRING_CMP_LBL = "BUILT_IN_STRING_CMP";
    public static final String BUILT_IN_STRING_TO_BOOL_LBL = "BUILT_IN_STRING_TO_BOOL";
    public static final String BUILT_IN_PRINT_LBL = "printf@plt";

    public static final String FUNC_START = "\tpushq %rbp\n\tmovq %rsp, %rbp\n\n";
    public static final String FUNC_END =
            "\tmovq %rbp, %rsp\n" +
                    "\tpopq %rbp\n" +
                    "\tret\n\n";

    public static final String CONCAT_STRINGS_BUILTIN =
            String.format(LBL_FUNC_NAME, BUILT_IN_CONCAT_STRINGS_LBL) +
                    ":\n" +
                    FUNC_START +
                    "\tsubq $32, %rsp\n" +
                    "\tmovq %rdi, -16(%rbp)\n" +
                    "\tmovq %rsi, -24(%rbp)\n\n" +
                    "\tmovq -16(%rbp), %rdi\t\n" +
                    "\tcall strlen@plt\n" +
                    "\tmovq %rax, %r10\n\n" +
                    "\tmovq -24(%rbp), %rdi\t\n" +
                    "\tcall strlen@plt\n" +
                    "\taddq %rax, %r10\n\n" +
                    "\taddq $1, %r10\n\n" +
                    "\tmovq %r10, %rdi\n" +
                    "\tcall malloc@plt\n" +
                    "\tmovq %rax, -32(%rbp)\t\n\n" +
                    "\tmovq -32(%rbp), %rdi\n" +
                    "\tmovq -16(%rbp), %rsi\n" +
                    "\tcall strcpy@plt\n\n" +
                    "\tmovq -32(%rbp), %rdi\n" +
                    "\tmovq -24(%rbp), %rsi\n" +
                    "\tcall strcat@plt\n\n" +
                    "\tmovq -32(%rbp), %rax\n\n" +
                    "\taddq $32, %rsp\n" +
                    FUNC_END;

    public static final String STRINGS_MUL_BUILTIN =
            String.format(LBL_FUNC_NAME, BUILT_IN_STRINGS_MUL_LBL) +
                    ":\n" +
                    FUNC_START +
                    "\tsubq $32, %rsp\n" +
                    "\tmovq %rdi, -16(%rbp)\n" +
                    "\tmovq %rsi, -24(%rbp)\n\n" +
                    "\tmovq -16(%rbp), %rdi\t\n" +
                    "\tcall strlen@plt\n" +
                    "\tmovq %rax, %r10\n\n" +
                    "\tcmpq $0, -24(%rbp) \n" +
                    "\tjle SM_EMPTY_RES\t\n" +
                    "\tcmpq $0, %r10 \n" +
                    "\tje SM_EMPTY_RES\t\n\n" +
                    "\timulq -24(%rbp), %r10\n\n" +
                    "\taddq $1, %r10\n\n" +
                    "\tmovq %r10, %rdi\n" +
                    "\tcall malloc@plt\n" +
                    "\tmovq %rax, -32(%rbp)\n\n" +
                    "SM_CAT_ITER_START:\n" +
                    "\tcmpq $0, -24(%rbp)\t\n" +
                    "\tje SM_END\n\n" +
                    "\tmovq -32(%rbp), %rdi\n" +
                    "\tmovq -16(%rbp), %rsi\n" +
                    "\tcall strcat@plt\n\n" +
                    "\tsubq $1, -24(%rbp)\n" +
                    "\tjmp SM_CAT_ITER_START\n\n" +
                    "SM_EMPTY_RES:\n" +
                    "\tmovq $0, %rdi\n" +
                    "\tcall malloc@plt\n" +
                    "\tmovq %rax, -32(%rbp)\n" +
                    "\tjmp SM_END\t\n\n" +
                    "SM_END:\n" +
                    "\tmovq -32(%rbp), %rax\n\n" +
                    "\taddq $32, %rsp\n" +
                    FUNC_END;

    public static final String STRINGS_CMP_BUILTIN =
            String.format(LBL_FUNC_NAME, BUILT_IN_STRING_CMP_LBL) +
                    ":\n" +
                    FUNC_START +
                    "\tcall strcmp@plt\n" +
                    "\tcdqe\n\n" +
                    FUNC_END;

    public static final String STRING_TO_BOOL_BUILTIN =
            String.format(LBL_FUNC_NAME, BUILT_IN_STRING_TO_BOOL_LBL) +
                    ":\n" +
                    FUNC_START +
                    "\tcall strlen@plt\n" +
                    "\tmovq %rax, %r10\n" +
                    "\tcmp $0, %r10\n" +
                    "\tje STB_EMPTY_STRING\n" +
                    "\tmovq $1, %rax\n" +
                    "\tjmp STB_END\n\n" +
                    "STB_EMPTY_STRING:\n" +
                    "\tmovq $0, %rax\n\n" +
                    "STB_END:\t\n" +
                    FUNC_END;
}
