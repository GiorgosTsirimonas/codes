#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ex.h"

typedef void (*generator_func_t)(quad*);

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
	vmarg* result;
	vmarg* arg1;
	vmarg* arg2;
	unsigned srcLine;
}instruction;

typedef struct userfunc{
	unsigned address;
	unsigned localSize;
	char* id;
}userfunc;

//pinakes gia statheres
double* numConsts =NULL;
unsigned totalNumConsts = 0;
char** stringConsts = NULL;
unsigned totalStringConsts = 0;
char** namedLibfuncs = NULL;
unsigned totalNamedLibfuncs = 0;
userfunc* userFuncs = NULL;
unsigned totalUserFuncs = 0;
unsigned currNumConst = 0;
unsigned currStringConst = 0;
unsigned currNamedLibfunc = 0;
unsigned currUserFunc = 0;

unsigned processedQuad = 0;

typedef struct incomplete_jump{
	unsigned instrNo; //the jump instruction number
	unsigned iaddress; //the i code jump-target address
	struct incomplete_jump* next; //a trivial linked list
}incomplete_jump;


incomplete_jump* ij_head = (incomplete_jump*) 0;
unsigned ij_total = 0;

//pinakes statherwn timvn kai synarthsevn
unsigned consts_newstring(char* s);
unsigned consts_newnumber(double n);
unsigned libfuncs_newused(const char* s);
unsigned userfuncs_newfunc(SymbolTableEntry* sym);

void emit_i(enum vmopcode op, vmarg *result, vmarg *arg1, vmarg *arg2, int line);
unsigned getNextInstruction();
void expand_instructions_array(void);
unsigned currprocessedquad();
void make_operand(expr *e, vmarg* arg);
void make_numberoperand(vmarg* arg, double val);
void make_booloperand(vmarg* arg, unsigned val);
void make_retvaloperand(vmarg* arg);
void add_incomplete_jump(unsigned instrNo, unsigned iaddress);
void patch_incomplete_jumps();
void generate_simple(enum vmopcode op,quad* quad);
void generate_ADD(quad* quad);
void generate_SUB(quad* quad);
void generate_MUL(quad* quad);
void generate_DIV(quad* quad);
void generate_MOD(quad* quad);
void generate_NEWTABLE(quad* quad);
void generate_TABLEGETELEM(quad* quad);
void generate_TABLESETELEM(quad* quad);
void generate_ASSIGN(quad* quad);
void generate_NOP();
void generate_relational(enum vmopcode op,quad* quad);
void generate_JUMP(quad* quad);
void generate_IF_EQ(quad* quad);
void generate_IF_NOTEQ(quad* quad);
void generate_IF_GREATER(quad* quad);
void generate_IF_GREATEREQ(quad* quad);
void generate_IF_LESS(quad* quad);
void generate_IF_LESSEQ(quad* quad);
void generate_NOT(quad* quad);
void generate_OR(quad* quad);
void generate_AND(quad* quad);
void generate_PARAM(quad* quad);
void generate_CALL(quad* quad);
void generate_GETRETVAL(quad* quad);

void generate_FUNCSTART(quad* quad);
void generate_FUNCEND(quad* quad);
void generate_RETURN(quad* quad);

void generate();

void reset_operand(vmarg* arg);

//ORDER OF PRESENCE HAS TO BE EQUAL TO THE ENUMERATED CONSTANT VALUE
//OF THE RESPECTIVE I-CODE INSTRUCTION
generator_func_t generators[] = {
	generate_ASSIGN,
	generate_ADD,
	generate_SUB,
	generate_MUL,
	generate_DIV,
	generate_MOD,
	generate_AND,
	generate_OR,
	generate_NOT,
	generate_IF_EQ,
	generate_IF_NOTEQ,
	generate_IF_LESSEQ,
	generate_IF_GREATEREQ,
	generate_IF_LESS,
	generate_IF_GREATER,
	generate_JUMP,
	generate_CALL,
	generate_PARAM,
	generate_RETURN,
	generate_GETRETVAL,
	generate_FUNCSTART,
	generate_FUNCEND,
	generate_NEWTABLE,
	generate_TABLEGETELEM,
	generate_TABLESETELEM,
	generate_NOP
};

void print_instructions(void);
void print_vmarg_name(vmarg* arg);

void create_tcode_textfile(void);
void create_tcode_binary(void);

void instructions_array_to_txt(FILE* fp);
void print_vmarg_name_to_text(FILE* fp,vmarg* arg);







