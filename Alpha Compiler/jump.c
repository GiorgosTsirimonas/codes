#include "avm.h"

extern unsigned pc;
extern avm_memcell bx,cx;
extern char executionFinished;
extern char* typeStrings[8];

typedef char (*tobool_func_t)(avm_memcell*);
typedef char (*cmp_func_t)(double,double);

cmp_func_t cmp_functs[] = {
	jle_impl,
	jge_impl,
	jlt_impl,
	jgt_impl
};

tobool_func_t toBoolFuncs[] = {
	number_tobool,
	string_tobool
	//bool_tobool,
	//table_tostring,
	//userfunc_tostring,
	//libfunc_tobool,
	//nil_tobool,
	//undef_tobool
};

void execute_relational(instruction* instr){

	avm_memcell* rv1 = avm_translate_operand(&instr->arg1,&bx);
	avm_memcell* rv2 = avm_translate_operand(&instr->arg2,&cx);
	char result;
	assert(instr->result.type == LABEL_A); 

	if(rv1->type != NUMBER_M || rv2->type != NUMBER_M){
		avm_error("not numbers in execute_relationalal");
		executionFinished = 1;
	}
	else{
		cmp_func_t op = cmp_functs[instr->opcode - _JUMP_LESSEQ_V]; 
		result = (*op)(rv1->data.numVal, rv2->data.numVal);
		if(result){
			printf("operation result = %d\n",result);
			execute_jump(instr);
		}else{
			printf("operation result = %d\n",result);
		}
	}
}

char jle_impl(double arg1, double arg2) {return arg1 <= arg2;}
char jlt_impl(double arg1, double arg2) {return arg1 < arg2;}
char jge_impl(double arg1, double arg2) {return arg1 >= arg2;}
char jgt_impl(double arg1, double arg2) {return arg1 > arg2;}

void execute_jump(instruction *instr){
	assert(instr->result.type == LABEL_A);

	pc = instr->result.val-1;
}

void execute_jgt(instruction *instr) {execute_relational(instr);}

void execute_jlt(instruction *instr) {execute_relational(instr);}

void execute_jge(instruction *instr) {execute_relational(instr);}

void execute_jle(instruction *instr) {execute_relational(instr);}

char avm_tobool(avm_memcell* m){
	assert(m->type >= NUMBER_M && m->type < UNDEF_M);
	return (*toBoolFuncs[m->type]) (m);
}

void execute_jeq(instruction *instr){
	avm_memcell* rv1 = avm_translate_operand(&instr->arg1,&bx);
	avm_memcell* rv2 = avm_translate_operand(&instr->arg2,&cx);
	char result;

	assert(instr->result.type == LABEL_A);

	if(rv1->type != NUMBER_M || rv2->type != NUMBER_M){
		avm_error("not numbers in execute_jeq");
		executionFinished = 1;
	}
	else{
		
		result = (rv1->data.numVal == rv2->data.numVal);
		if(result){
			printf("operation result = %d\n",result);
			execute_jump(instr);
		}else{
			printf("operation result = %d\n",result);
		}
	}

}
void execute_jne(instruction *instr){

}
char number_tobool(avm_memcell *m){

}
char string_tobool(avm_memcell *m){

}