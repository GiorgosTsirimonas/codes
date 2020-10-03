#include "avm.h"
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
instruction *instructions = NULL;

unsigned total_instr;
unsigned programVarOffset;
avm_memcell stack[AVM_STACKSIZE];

unsigned char executionFinished = 0;
unsigned pc = 0;
unsigned currLine = 0;
unsigned codeSize = 0;
instruction* code = (instruction*) 0;
unsigned totalActuals = 0;

static void avm_initstack(void){
	unsigned i;
	for(i = 0; i<AVM_STACKSIZE; ++i){
		AVM_WIPEOUT(stack[i]);
		stack[i].type = UNDEF_M;
	}
}

avm_memcell ax,bx,cx;
avm_memcell retval;
unsigned top,topsp;

typedef void (*memclear_func_t)(avm_memcell*);
typedef void (*execute_func_t)(instruction*);



struct libfunc libfuncs[5];
typedef char* (*tostring_func_t)(avm_memcell*);

unsigned lcounter = 0;


avm_memcell* avm_translate_operand(vmarg* arg, avm_memcell* reg){
	
	switch(arg->type){
		case GLOBAL_A: return &stack[AVM_STACKSIZE-1-arg->val];
		
		case LOCAL_A: return &stack[topsp-arg->val];
		
		case FORMAL_A: return &stack[topsp+AVM_STACKENV_SIZE+1+arg->val];

		case RETVAL_A: return &retval;

		case NUMBER_A: {
			reg->type = NUMBER_M;
			reg->data.numVal = consts_getnumber(arg->val);
			return reg;
		}

		case STRING_A: {
			reg->type = STRING_M;
			reg->data.strVal = strdup(consts_getstring(arg->val));
			return reg;
		}

		case BOOL_A: {
			reg->type = BOOL_M;
			reg->data.boolVal = arg->val;
			return reg;
		}

		case NIL_A: reg->type = NIL_M; return reg;

		case USERFUNC_A: {
			reg->type = USERFUNC_M;
			reg->data.funcVal = arg->val;
			return reg;
		}

		case LIBFUNC_A: {
			reg->type = LIBFUNC_M;
			reg->data.libfuncVal = libfuncs_getused(arg->val);
			return reg;
		}

		default: assert(0);

	}
}

//na mpoun se hwrista arheia
void execute_nop(instruction *instr){
	executionFinished = 1;
}

execute_func_t executeFuncs[]={
	execute_assign,
	execute_add,
	execute_sub,
	execute_mul,
	execute_div,
	execute_mod,
	execute_and,
	execute_or,
	execute_not,
	execute_jeq,
	execute_jne,
	execute_jle,
	execute_jge,
	execute_jlt,
	execute_jgt,
	execute_jump,
	execute_call,
	execute_pusharg,
	execute_returnf,
	execute_getret,
	execute_funcenter,
	execute_funcexit,
	execute_newtable,
	execute_tablegetelem,
	execute_tablesetelem,
	execute_nop
};

void execute_cycle(){
	while(1){
		if(executionFinished){
			return;
		}
		else{
			if(pc == AVM_ENDING_PC){
				executionFinished = 1;
				return;
			}
			else{
				assert(pc<AVM_ENDING_PC);
				instruction* instr = code + pc;
				assert(instr->opcode >= 0 && instr->opcode <= AVM_MAX_INSTRUCTIONS);
				if(instr->srcLine){
					currLine = instr->srcLine;
				}
				unsigned oldPC = pc;
				(*executeFuncs[instr->opcode])(instr);
				if(pc==oldPC){
					++pc;
				}
			}
		}	
	}
	
}

memclear_func_t memclearFuncs[] = {
	0, //number
	memclear_string,
	0, //bool
	memclear_table,
	0, //userfunc
	0, //libfunc
	0, //nil
	0 //undef
};

void avm_memcellclear(avm_memcell* m){
	if(m->type != UNDEF_M){
		memclear_func_t f = memclearFuncs[m->type];
		if(f){
			(*f)(m);
		}
		m->type = UNDEF_M;
	}
}

 void execute_pusharg(instruction* instr){
	avm_memcell* arg = avm_translate_operand(&instr->arg1, &ax);
	assert(arg);
	//this is actually stack[top] = arg, but we
	//have to use avm_assign

	avm_assign(&stack[top],arg);
	
	++totalActuals;
	avm_dec_top();
}

void execute_assign (instruction *instr){
	avm_memcell* lv = avm_translate_operand(&instr->result, (avm_memcell*) 0);
	avm_memcell* rv = avm_translate_operand(&instr->arg1, &ax);
	assert(lv && (&stack[AVM_STACKSIZE-1] >= lv && lv > &stack[top] || lv == &retval));
	assert(rv); //similar assertion tests

	avm_assign(lv,rv);
	
}

//maybe needs something extra
void avm_assign(avm_memcell* lv, avm_memcell* rv){
	if(lv == rv){
		return;
	}

	if(lv->type == TABLE_M && rv->type == TABLE_M &&
		lv->data.tableVal == rv->data.tableVal){
		return;
	}

	if(rv->type == UNDEF_M){
		printf("on line %u, ",currLine+1);
		avm_warning("assigning from 'undef' content!");
	}

	avm_memcellclear(lv);

	memcpy(lv,rv,sizeof(avm_memcell));

	if(lv->type == STRING_M){
		lv->data.strVal = strdup(rv->data.strVal);
	}
	else if(lv->type == NUMBER_M){
		lv->data.numVal = rv->data.numVal;
	}
	else if(lv->type == BOOL_M){
		lv->data.boolVal = rv->data.boolVal;
	}
	else if(lv->type == TABLE_M){
		avm_tableincrefcounter(lv->data.tableVal);
	}
}

void avm_dec_top(void){
	if(!top){
		avm_error("stack overflow");
		executionFinished = 1;
	}
	else{
		--top;
	}
}

void avm_push_envvalue(unsigned val){
	stack[top].type = NUMBER_M;
	stack[top].data.numVal = val;
	avm_dec_top();
}

void avm_callsaveenviroment(){
	avm_push_envvalue(totalActuals);
	avm_push_envvalue(pc+1);
	avm_push_envvalue(top + totalActuals + 2);
	avm_push_envvalue(topsp);
}


typedef double (*arithmetic_func_t)(double x, double y);

double add_impl(double x, double y){return x+y; }

double sub_impl(double x, double y){return x-y; }

double mul_impl(double x, double y){return x*y; }

double div_impl(double x, double y){
    if(y==0){
        avm_error("you cannot divide with 0!\n");
    }
    return x/y;
}

double mod_impl(double x, double y){
    if(y==0){
        avm_error("you cannot divide with 0!\n");
    }
    return ((unsigned)x) % ((unsigned)y); 
}

//dispatcher just for arithmetic funcs
arithmetic_func_t arithmeticFuncs[] = {
	add_impl,
	sub_impl,
	mul_impl,
	div_impl,
	mod_impl
};

void execute_arithmetic(instruction* instr){
	avm_memcell* lv = avm_translate_operand(&instr->result, (avm_memcell*) 0 );
	avm_memcell* rv1 = avm_translate_operand(&instr->arg1,&ax);
	avm_memcell* rv2 = avm_translate_operand(&instr->arg2,&bx);

	assert(lv && (&stack[AVM_STACKSIZE-1] >= lv && lv > &stack[top] || lv == &retval ));
	assert(rv1 && rv2); 

	if(rv1->type != NUMBER_M || rv2->type != NUMBER_M){
		printf("on line %u, ",instr->srcLine+1);
		avm_error("not a number in arithmetic");
		executionFinished = 1;
	}
	else{
		arithmetic_func_t op = arithmeticFuncs[instr->opcode - _ADD_V]; 
		avm_memcellclear(lv);
		lv->type = NUMBER_M;
		lv->data.numVal = (*op)(rv1->data.numVal, rv2->data.numVal);
	}
}


void memclear_string(avm_memcell *m){
	assert(m->data.strVal);
	free(m->data.strVal);
}


void memclear_table(avm_memcell *m){
    assert(m->data.tableVal);
    avm_tabledecrefcounter(m->data.tableVal);
}

void avm_tableincrefcounter(avm_table* t){
    t->refCounter++;
}

//automatic garbage collection for tables when reference counter gets zero
void avm_tabledecrefcounter(avm_table* t){
    assert(t->refCounter>0);
    if(!--t->refCounter){
        avm_tabledestroy(t);
    }
}

void avm_tablebucketsinit(avm_table_bucket** p){
    unsigned  i;
    for(i = 0; i<AVM_TABLE_HASHSIZE; ++i){
        p[i] = (avm_table_bucket*) 0;
    }
}

//the reference counter is initially zero
avm_table* avm_tablenew(void){
    avm_table* t = (avm_table*) malloc(sizeof(avm_table));
    AVM_WIPEOUT(*t);
    
    t->refCounter = t->total = 0;
    avm_tablebucketsinit(t->numIndexed);
    avm_tablebucketsinit(t->strIndexed);
    
    return t;
}

//when a cell is cleared, it has to destroy all dynamic data content or reset its reference to a table
//void avm_memcellclear(avm_memcell* m);

void avm_tablebucketsdestroy(avm_table_bucket** p){
    unsigned i;
    avm_table_bucket* b;
    for(i = 0; i < AVM_TABLE_HASHSIZE; ++i, ++p){
        for(b = *p; b;){
            avm_table_bucket* del = b;
            b = b->next;
            avm_memcellclear(&del->key);
            avm_memcellclear(&del->value);
            free(del);
        }
        p[i] = (avm_table_bucket*) 0;
    }
}

void avm_tabledestroy (avm_table *t){
    avm_tablebucketsdestroy(t->strIndexed);
    avm_tablebucketsdestroy(t->numIndexed);
    free(t);
}

void avm_error(char* format){
	printf("Runtime error '%s'\n",format);
	executionFinished = 1;
}

void avm_warning(char* format){
	printf("Runtime warning '%s'\n",format);
}

double consts_getnumber(unsigned index){
	return numConsts[index];
}

char* consts_getstring(unsigned index){
	return stringConsts[index];
}

char* libfuncs_getused(unsigned index){
	return namedLibfuncs[index];
}






void execute_call(instruction* instr){
	avm_memcell* func = avm_translate_operand(&instr->arg1, &ax);
	assert(func);
	avm_callsaveenviroment();
	switch(func->type){
		case USERFUNC_M:{
			pc = func->data.funcVal;
			assert(pc < AVM_ENDING_PC);
			assert(code[pc].opcode == _FUNCSTART_V);
			break;
		}

		case STRING_M: avm_calllibfunc(func->data.strVal); break;
		case LIBFUNC_M: avm_calllibfunc(func->data.libfuncVal); break;

		default: {
			char* s = avm_tostring(func);
			printf("on line %u, ",instr->srcLine+1);
			avm_error("call: cannot bind to function");
			free(s);
			executionFinished = 1;

		}
	}
}

unsigned avm_get_envvalue(unsigned i){
	assert(stack[i].type == NUMBER_M);
	unsigned val = (unsigned) stack[i].data.numVal;
	assert(stack[i].data.numVal == ((double) val));
	return val;
}

void execute_funcexit(instruction* unused){
	unsigned oldTop = top;
	top = avm_get_envvalue(topsp + AVM_SAVEDTOP_OFFSET);
	pc = avm_get_envvalue(topsp + AVM_SAVEDPC_OFFSET);
	topsp = avm_get_envvalue(topsp + AVM_SAVEDTOPSP_OFFSET);

	while(++oldTop <= top){
		avm_memcellclear(&stack[oldTop]);
	}
}

void avm_calllibfunc(char* id){
	libfunc_t f = avm_getlibraryfunc(id);
	if(!f){
		printf("on line %u, ",currLine+1);
		avm_error("unsupported lib func called!");
		executionFinished = 1;
	}
	else{
		topsp = top;
		totalActuals = 0;
		(*f)();
		if(!executionFinished){
			execute_funcexit((instruction*)0);
		}
	}
}

unsigned avm_totalactuals(void){
	return avm_get_envvalue(topsp + AVM_NUMACTUALS_OFFSET);
}

avm_memcell* avm_getactual(unsigned i){
	assert(i<avm_totalactuals());
	return &stack[topsp + AVM_STACKENV_SIZE + 1 + i];
}

//implementation of library function print
void libfunc_print(void){
	unsigned n = avm_totalactuals();
	unsigned i;

	for(i = 0; i<n; ++i){
		char* s = avm_tostring(avm_getactual(i));
		printf("%s",s);
		free(s);
	}
}

char* typeStrings[]={
	"number",
	"string",
	"bool",
	"table",
	"userfunc",
	"libfunc",
	"nil",
	"undef"
};

//typeof synarthsh ylopoihshs

void libfunc_typeof(void){

	unsigned n = avm_totalactuals();

	if(n!=1){
		printf("on line %u, ",currLine+1);
		avm_error("argument expected in typeof");
	}
	else{
		avm_memcellclear(&retval); 
		retval.type = STRING_M;
		retval.data.strVal = strdup(typeStrings[avm_getactual(0)->type]);
	}
}

void libfunc_sqrt(void){
	unsigned n = avm_totalactuals();

	if(n!=1){
		printf("on line %u, ",currLine+1);
		avm_error("argument expected in sqrt");
	}
	else{
		avm_memcellclear(&retval); 
		retval.type = NUMBER_M;
		retval.data.numVal = sqrt(avm_getactual(0)->data.numVal);
	}
}

void libfunc_cos(void){
	unsigned n = avm_totalactuals();

	if(n!=1){
		printf("on line %u, ",currLine+1);
		avm_error("argument expected in cos");
	}
	else{
		avm_memcellclear(&retval); 
		retval.type = NUMBER_M;
		retval.data.numVal = cos(avm_getactual(0)->data.numVal);
	}
}

void libfunc_sin(void){
	unsigned n = avm_totalactuals();

	if(n!=1){
		printf("on line %u, ",currLine+1);
		avm_error("argument expected in sin");
	}
	else{
		avm_memcellclear(&retval); 
		retval.type = NUMBER_M;
		retval.data.numVal = sin(avm_getactual(0)->data.numVal);
	}
}

libfunc_t avm_getlibraryfunc(char* id){
	if(strcmp(id,libfuncs[0].id)==0){
		return libfuncs[0].addr;
	}
	if(strcmp(id,libfuncs[1].id)==0){
		return libfuncs[1].addr;
	}
	if(strcmp(id,libfuncs[2].id)==0){
		return libfuncs[2].addr;
	}
	if(strcmp(id,libfuncs[3].id)==0){
		return libfuncs[3].addr;
	}
	if(strcmp(id,libfuncs[4].id)==0){
		return libfuncs[4].addr;
	}

}


//with the following every library function is manually
//added in the VM library function resolution map

void avm_registerlibfunc(char* id,libfunc_t addr){
	libfuncs[lcounter].addr = addr;
	libfuncs[lcounter].id = id;
	lcounter++;
}

void avm_initialize(){
	avm_initstack();
	avm_registerlibfunc("print", libfunc_print);
	avm_registerlibfunc("typeof", libfunc_typeof);
	avm_registerlibfunc("sqrt", libfunc_sqrt);
	avm_registerlibfunc("cos", libfunc_cos);
	avm_registerlibfunc("sin", libfunc_sin);
}


char* number_tostring(avm_memcell* m){
	double number = m->data.numVal;
	char* output_string = malloc(sizeof(char)*100);

	if((int)number == number){
			sprintf(output_string,"%d",(int)number);
	}else{
			sprintf(output_string,"%.5f",number);
	}
	return strdup(output_string);
}

char* string_tostring(avm_memcell* m){
	char* input_string = m->data.strVal;
	char* output_string = malloc(sizeof(char)*(strlen(input_string)+1));
	output_string = strdup(input_string);

	return strdup(output_string);
}

char* bool_tostring(avm_memcell* m){
	unsigned char bVal = m->data.boolVal;
	char* output_string = malloc(sizeof(char)*10);

	if(bVal == 0){
		sprintf(output_string,"%s","false");
	}
	else{
		sprintf(output_string,"%s","true");
	}

	return strdup(output_string);

}

char* nil_tostring(avm_memcell* m){
	char* output_string = malloc(sizeof(char)*10);

	sprintf(output_string,"%s","potato");

	return strdup(output_string);
}

char* undef_tostring(avm_memcell* m){
	char* output_string = malloc(sizeof(char)*10);

	sprintf(output_string,"%s","undefined");
	
	return strdup(output_string);
}


//char* table_tostring(avm_memcell*);
//char* userfunc_tostring(avm_memcell*);
//char* livfunc_tostring(avm_memcell*);



tostring_func_t tostringFuncs[] = {
	number_tostring,
	string_tostring,
	bool_tostring,
	//table_tostring,
	//userfunc_tostring,
	//livfunc_tostring,
	nil_tostring,
	undef_tostring
};

char* avm_tostring(avm_memcell* m){
	assert(m->type >= 0 && m->type <= UNDEF_M);
	char* str;
	str = (*tostringFuncs[m->type])(m);
	return str;
}

void addInstr(int i,int op,int type_result, unsigned val_result, int type_arg1 ,
				unsigned val_arg1 ,int type_arg2 , unsigned val_arg2,unsigned srcLine){

	instructions[i].opcode = op;
	instructions[i].result.type = type_result;
	instructions[i].result.val = val_result;
	instructions[i].arg1.type = type_arg1;
	instructions[i].arg1.val = val_arg1;
	instructions[i].arg2.type = type_arg2;
	instructions[i].arg2.val = val_arg2;
	instructions[i].srcLine = srcLine;

}


void load_from_binary(FILE* fp){
	
	unsigned magic_number;
	unsigned total_d;
	unsigned total_libfuncs;
	unsigned total_strings;
	double number;
	int i;

	fread(&magic_number,sizeof(unsigned),1,fp);
	printf("magic number: %u\n",magic_number);
	if(magic_number != 123456789){
		printf("error with file input\n");
		exit(0);
	}

	fread(&programVarOffset,sizeof(unsigned),1,fp);
	printf("total globals: %u\n", programVarOffset);

	fread(&total_d,sizeof(unsigned),1,fp);
	printf("total numbers: %u\n", total_d);
	numConsts = (double*)malloc(sizeof(double)*total_d);
	for(i = 0;i<total_d;i++){
		fread(&number,sizeof(double),1,fp);
		numConsts[currNumConst++] = number;
		//printf("%f\n",numConsts[i]);
	}

	fread(&total_libfuncs,sizeof(unsigned),1,fp);
	printf("total library functions: %u\n", total_libfuncs);
	namedLibfuncs = malloc((sizeof(char*))*total_libfuncs);
	for(i = 0;i<total_libfuncs;i++){
		unsigned s;
		fread(&s,sizeof(unsigned),1,fp);
		//printf("%d\n",(int)s);
		namedLibfuncs[i] = malloc(s * (sizeof(char)));
		fread(namedLibfuncs[i],s,1,fp);
		//printf("%s\n",namedLibfuncs[i]);
	}

	fread(&total_strings,sizeof(unsigned),1,fp);
	printf("total strings: %u\n", total_strings);
	stringConsts = malloc((sizeof(char*))*total_strings);
	for(i = 0;i<total_strings;i++){
		unsigned s2;
		fread(&s2,sizeof(unsigned),1,fp);
		//printf("%d\n",(int)s);
		stringConsts[i] = malloc(s2 * (sizeof(char)));
		fread(stringConsts[i],s2,1,fp);
		//printf("%s\n",stringConsts[i]);
	}

	fread(&total_instr,sizeof(unsigned),1,fp);
	printf("total instructions: %u\n",total_instr);

	instructions = malloc(total_instr*sizeof(instruction));

	int op;
	int type_arg1, type_arg2, type_result;
	unsigned val_arg1, val_arg2, val_result;
	unsigned srcLine;

	for(i=0;i<total_instr;i++){
		
		fread(&op,sizeof(int),1,fp); 

		switch(op){
			
			case _ASSIGN_V:
			case _GETRETVAL_V:{
				fread(&type_result,sizeof(int),1,fp); 
				fread(&val_result,sizeof(unsigned),1,fp); 

				fread(&type_arg1,sizeof(int),1,fp); 
				fread(&val_arg1,sizeof(unsigned),1,fp);

				addInstr(i, op, type_result, val_result, type_arg1, val_arg1, -1 , 0 , srcLine);
 
 				break;
			}	
			
			case _ADD_V: 
			case _SUB_V:
			case _MUL_V:
			case _DIV_V:
			case _MOD_V:
			case _JUMP_EQ_V:
			case _JUMP_NOTEQ_V:
			case _JUMP_LESSEQ_V:
			case _JUMP_GREATEREQ_V:
			case _JUMP_LESS_V:
			case _JUMP_GREATER_V:
			case _TABLEGETELEM_V:
			case _TABLESETELEM_V:{
				fread(&type_result,sizeof(int),1,fp); 
				fread(&val_result,sizeof(unsigned),1,fp); 

				fread(&type_arg1,sizeof(int),1,fp); 
				fread(&val_arg1,sizeof(unsigned),1,fp);

				fread(&type_arg2,sizeof(int),1,fp); 
				fread(&val_arg2,sizeof(unsigned),1,fp);

				addInstr(i, op, type_result, val_result, type_arg1, val_arg1, type_arg2, val_arg2, srcLine);
				break;
			}

			case _CALL_V:
			case _PUSHARG_V:
			case _RETURN_V:{
				fread(&type_arg1,sizeof(int),1,fp); 
				fread(&val_arg1,sizeof(unsigned),1,fp);
				addInstr(i, op, -1, 0, type_arg1, val_arg1, -1, 0, srcLine);
				break;
			}

			case _JUMP_V:
			case _FUNCSTART_V:
			case _FUNCEND_V:
			case _TABLECREATE_V:{
				fread(&type_result,sizeof(int),1,fp); 
				fread(&val_result,sizeof(unsigned),1,fp); 
				addInstr(i, op, type_result, val_result, -1, 0, -1, 0, srcLine);
				break;
			}
			default :
				assert(0);
		}

		fread(&srcLine,sizeof(unsigned),1,fp); 

	}


}

void execute_and(instruction* instr){

}
void execute_or(instruction* instr){

}
void execute_not(instruction* instr){

}
void execute_funcenter(instruction* instr){

}
void execute_newtable(instruction* instr){

}
void execute_tablegetelem(instruction* instr){

}
void execute_tablesetelem(instruction* instr){

}
void execute_returnf(instruction* instr){

}
void execute_getret(instruction* instr){

}

int main(int argc, char **argv)
{
	FILE* fp;
	int x;
	if(argc != 2) {
		puts("wrong command-line arguments");
		exit(EXIT_FAILURE);
	}
	
	fp = fopen(argv[1],"r");
	load_from_binary(fp);

	printf("\n<--------------- OUTPUT --------------->\n\n");
	
	avm_initialize();

	code = instructions;
	codeSize = total_instr;
	top = AVM_STACKSIZE - programVarOffset - 1;
	topsp = 0;

	execute_cycle();
	printf("\n\n<------------ END OUTPUT ------------>\n");
	return 0;
}
