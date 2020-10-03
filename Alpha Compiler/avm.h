
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <math.h>
#define AVM_STACKSIZE 4096
#define AVM_WIPEOUT(m) memset(&(m),0,sizeof(m))
#define AVM_TABLE_HASHSIZE 256
#define AVM_STACKENV_SIZE 4
#define AVM_ENDING_PC codeSize
#define AVM_MAX_INSTRUCTIONS (unsigned) _NOP_V

#define execute_add execute_arithmetic
#define execute_sub execute_arithmetic
#define execute_mul execute_arithmetic
#define execute_div execute_arithmetic
#define execute_mod execute_arithmetic

#define AVM_NUMACTUALS_OFFSET +4
#define AVM_SAVEDPC_OFFSET +3
#define AVM_SAVEDTOP_OFFSET +2
#define AVM_SAVEDTOPSP_OFFSET +1

typedef void (*libfunc_t)(void);

enum vmopcode {
	_ASSIGN_V,	
	_ADD_V,		
	_SUB_V,
	_MUL_V,		
	_DIV_V,		
	_MOD_V,
	_AND_V,		
	_OR_V,
	_NOT_V,		
	_JUMP_EQ_V,		
	_JUMP_NOTEQ_V,
	_JUMP_LESSEQ_V,	
	_JUMP_GREATEREQ_V,	
	_JUMP_LESS_V,
	_JUMP_GREATER_V, 
	_JUMP_V, 
	_CALL_V, 
	_PUSHARG_V,	
	_RETURN_V, 
	_GETRETVAL_V,
	_FUNCSTART_V,	
	_FUNCEND_V,	
	_TABLECREATE_V,
	_TABLEGETELEM_V,	
	_TABLESETELEM_V, 
	_NOP_V
	
};

enum vmarg_t {
	LABEL_A = 0,
	GLOBAL_A = 1,
	FORMAL_A = 2,
	LOCAL_A = 3,
	NUMBER_A = 4,
	STRING_A = 5,
	BOOL_A = 6,
	NIL_A = 7,
	USERFUNC_A = 8,
	LIBFUNC_A = 9,
	RETVAL_A = 10
};

typedef struct vmarg {
	enum vmarg_t type;
	unsigned val;
	const char* name;
}vmarg;

typedef struct instruction{
	enum vmopcode opcode;
	vmarg result;
	vmarg arg1;
	vmarg arg2;
	unsigned srcLine;
}instruction;

enum avm_memcell_t{
	NUMBER_M = 0,
	STRING_M = 1,
	BOOL_M = 2,
	TABLE_M = 3,
	USERFUNC_M = 4,
	LIBFUNC_M = 5,
	NIL_M = 6,
	UNDEF_M = 7
};

typedef struct avm_memcell{
	enum avm_memcell_t type;
	union{
		double numVal;
		char* strVal;
		unsigned char boolVal;
		struct avm_table* tableVal;
		unsigned funcVal;
		char* libfuncVal;
	} data;
}avm_memcell;

typedef struct avm_table_bucket{
	avm_memcell key;
	avm_memcell value;
	struct avm_table_bucket* next;
}avm_table_bucket;

typedef struct avm_table{
	unsigned refCounter;
	struct avm_table_bucket* strIndexed[AVM_TABLE_HASHSIZE];
	struct avm_table_bucket* numIndexed[AVM_TABLE_HASHSIZE];
	unsigned total;
}avm_table;

typedef struct userfunc{
	unsigned address;
	unsigned localSize;
	char* id;
}userfunc;

typedef struct libfunc{
	char* id;
	libfunc_t addr;
}libfunc;

void memclear_string(avm_memcell *m);
void memclear_table(avm_memcell *m);
void avm_tableincrefcounter(avm_table* t);
void avm_tabledecrefcounter(avm_table* t);
void execute_assign (instruction*);
void load_from_binary(FILE* fp);
void execute_add (instruction*);
void execute_sub (instruction*);
void execute_mul (instruction*);
void execute_div (instruction*);
void execute_mod (instruction*);

void execute_and (instruction*);
void execute_or (instruction*);
void execute_not (instruction*);

void execute_pusharg(instruction*);
void execute_funcenter(instruction*);
void execute_funcexit(instruction*);
void execute_newtable(instruction*);
void execute_tablegetelem(instruction*);
void execute_tablesetelem(instruction*);

void execute_nop(instruction *instr);

double consts_getnumber(unsigned index);
char* consts_getstring(unsigned index);
char* libfuncs_getused(unsigned index);
static void avm_initstack(void);

void avm_memcellclear(avm_memcell* m);
void avm_error(char* format);
void avm_warning(char* format);

avm_memcell* avm_translate_operand(vmarg* arg, avm_memcell* reg);
void execute_cycle();
void execute_assign (instruction *instr);
void avm_assign(avm_memcell* lv, avm_memcell* rv);
void avm_dec_top(void);
void avm_push_envvalue(unsigned val);
void avm_callsaveenviroment();

double add_impl(double x, double y);
double sub_impl(double x, double y);
double mul_impl(double x, double y);
double div_impl(double x, double y);
double mod_impl(double x, double y);
void execute_arithmetic(instruction* instr);
void execute_nop(instruction *instr);



avm_table* avm_tablenew(void);
void avm_tabledestroy(avm_table* t);
avm_memcell* avm_tablegetelem(avm_memcell* key);
void avm_tablesetelem(avm_memcell* key, avm_memcell* value);

void avm_tableincrefcounter(avm_table* t);
void avm_tabledecrefcounter(avm_table* t);
void avm_tablebucketsinit(avm_table_bucket** p);
void avm_tablebucketsdestroy(avm_table_bucket** p);


void print_instructions(void);
void print_vmarg_name(vmarg arg);

void addInstr(int i,int op,int type_result, unsigned val_result, int type_arg1 ,
				unsigned val_arg1 ,int type_arg2 , unsigned val_arg2,unsigned srcLine);

unsigned avm_get_envvalue(unsigned i);
void execute_funcexit(instruction* unused);
void avm_calllibfunc(char* id);
unsigned avm_totalactuals(void);
avm_memcell* avm_getactual(unsigned i);
void libfunc_print(void);
libfunc_t avm_getlibraryfunc(char* id);
void avm_registerlibfunc(char* id, libfunc_t addr);
void avm_initialize();
char* number_tostring(avm_memcell* m);
char* string_tostring(avm_memcell* m);
char* bool_tostring(avm_memcell* m);
char* nil_tostring(avm_memcell*);
char* undef_tostring(avm_memcell*);
char* avm_tostring(avm_memcell* m);
void execute_call(instruction* instr);
void execute_and(instruction* instr);
void execute_or(instruction* instr);
void execute_not(instruction* instr);
void execute_jeq(instruction* instr);
void execute_jne(instruction* instr);
void execute_jle(instruction* instr);
void execute_jge(instruction* instr);
void execute_jlt(instruction* instr);
void execute_jgt(instruction* instr);
void execute_pusharg(instruction* instr);
void execute_funcenter(instruction* instr);
void execute_newtable(instruction* instr);
void execute_tablegetelem(instruction* instr);
void execute_tablesetelem(instruction* instr);
void execute_jump(instruction* instr);
void execute_returnf(instruction* instr);
void execute_getret(instruction* instr);
void libfunc_typeof(void);
void libfunc_sqrt(void);
void libfunc_cos(void);
void libfunc_sin(void);
char jle_impl(double arg1, double arg2);
char jlt_impl(double arg1, double arg2);
char jge_impl(double arg1, double arg2);
char jgt_impl(double arg1, double arg2);
char number_tobool(avm_memcell *m);
char string_tobool(avm_memcell *m);