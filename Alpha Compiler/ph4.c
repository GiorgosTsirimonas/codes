#include "ex.h"
#include "ph4.h"
#include <assert.h>
#define TOTALSIZEINSTR 1024
#define TOTALSIZENUM 1024
#define TOTALSIZESTR 1024
#define TOTALSIZELIBFUNC 1024
#define TOTALSIZEUSERFUNC 1024

instruction* instructions = NULL;
unsigned total_instructions = 0;
unsigned curr_instruction = 0;

extern quad* quads;
extern unsigned programVarOffset;
typedef void (*generator_func_t)(quad*);


void emit_i(enum vmopcode op, vmarg *result, vmarg *arg1, vmarg *arg2, int line){
	if(curr_instruction == total_instructions)
		expand_instructions_array();
	
	instructions[curr_instruction].opcode = op;
	instructions[curr_instruction].result = result;
	instructions[curr_instruction].arg1 = arg1;
	instructions[curr_instruction].arg2 = arg2;
	instructions[curr_instruction].srcLine = line;
	
	curr_instruction = getNextInstruction();
}

unsigned getNextInstruction(){
	return curr_instruction+1;
}

void expand_instructions_array(void)
{
	instructions = realloc(instructions, (TOTALSIZEINSTR * sizeof(instruction))
				+ (total_instructions * sizeof(instruction)));
	
	if(!instructions) {
		puts("allocation error");
		exit(EXIT_FAILURE);
	}
	
	total_instructions += TOTALSIZEINSTR;
}

unsigned currprocessedquad(){
	return processedQuad;
}

void expand_numConsts_array(void)
{
	numConsts = realloc(numConsts, (TOTALSIZENUM * sizeof(double))
				+ (totalNumConsts * sizeof(double)));
	
	if(!numConsts) {
		puts("allocation error");
		exit(EXIT_FAILURE);
	}
	
	totalNumConsts += TOTALSIZENUM;
}

void expand_stringConsts_array(void)
{
	stringConsts = realloc(stringConsts, (TOTALSIZESTR * sizeof(char*))
				+ (totalStringConsts * sizeof(char*)));
	
	if(!stringConsts) {
		puts("allocation error");
		exit(EXIT_FAILURE);
	}
	
	totalStringConsts += TOTALSIZESTR;
}

void expand_libFuncs_array(void)
{
	namedLibfuncs = realloc(namedLibfuncs, (TOTALSIZELIBFUNC * sizeof(char*))
				+ (totalNamedLibfuncs * sizeof(char*)));
	
	if(!namedLibfuncs) {
		puts("allocation error");
		exit(EXIT_FAILURE);
	}
	
	totalNamedLibfuncs += TOTALSIZELIBFUNC;
}

void expand_userFuncs_array(void)
{
	userFuncs = realloc(userFuncs, (TOTALSIZEUSERFUNC * sizeof(userfunc))
				+ (totalUserFuncs * sizeof(userfunc)));
	
	if(!userFuncs) {
		puts("allocation error");
		exit(EXIT_FAILURE);
	}
	
	totalUserFuncs += TOTALSIZEUSERFUNC;
}

void make_operand(expr *e, vmarg* arg){
	switch (e->type){
		case VAR_E: 
		case TABLEITEM_E:
		case ARITHM_E:
		case BOOL_E:
		case NEWTABLE_E: 
		case ASSIGN_E: {

			assert(e->sym);
			arg->val = e->sym->offset;
			if(e->type == VAR_E || TABLEITEM_E){
				arg->name = e->sym->value.varVal->name;
			}

			switch(e->sym->space){
				case PROGRAMVAR: arg->type = GLOBAL_A; break;
				case FUNCLOCAL:	arg->type = LOCAL_A; break;
				case FORMALARG:	arg->type = FORMAL_A; break;
				default: assert(0);
			}
			break;
		}

		case CONSTBOOL_E:{
			arg->val = e->boolConst;
			arg->type = BOOL_A;
			break;
		}

		case CONSTSTRING_E:{
			arg->val = consts_newstring(e->strConst);
			arg->type = STRING_A;
			break;
		}

		case CONSTNUM_E:{
			arg->val = consts_newnumber(e->numConst);
			arg->type = NUMBER_A;

			break;
		}

		case NIL_E: arg->type = NIL_A; break;


		/* gia tis synarthseis mporoume na ehoume 
		ws timh tou operand apeutheias th dieuthinsh ths
		synarthshs ston teliko kwdika 'h ena index ston 
		pinaka opou katahwroume kathe synarthsh tou hrhsth
		*/
		case USERFUNC_E:{
			arg->type = USERFUNC_A;
			//arg->val = e->taddress; //
			arg->val = userfuncs_newfunc(e->sym);
			arg->name = e->sym->value.funcVal->name;
			break;
		}

		case LIBFUNC_E:{
			arg->type = LIBFUNC_A;
			arg->val = libfuncs_newused(e->sym->value.funcVal->name);
			arg->name = e->sym->value.funcVal->name;
			break;
		}
		default: assert(0);

	}
}

unsigned consts_newstring(char* s){
	if(currStringConst == totalStringConsts)
		expand_stringConsts_array();

	stringConsts[currStringConst] = strdup(s);
	return currStringConst++;
}

unsigned consts_newnumber(double n){
	if(currNumConst == totalNumConsts)
		expand_numConsts_array();

	numConsts[currNumConst] = n;
	return currNumConst++;
}

unsigned libfuncs_newused(const char* s){
	if(currNamedLibfunc == totalNamedLibfuncs)
		expand_libFuncs_array();

	namedLibfuncs[currNamedLibfunc] = strdup(s);
	return currNamedLibfunc++;
}

unsigned userfuncs_newfunc(SymbolTableEntry* sym){
	if(currUserFunc == totalUserFuncs)
		expand_userFuncs_array();

	userFuncs[currUserFunc].address = sym->value.funcVal->iaddress;// nomizw thelei taddress
	userFuncs[currUserFunc].localSize = sym->value.funcVal->total_locals;
	userFuncs[currUserFunc].id = strdup(sym->value.funcVal->name);
	return currUserFunc++;
}


/*	helper functions to produce common arguments for
generated instruction like 1, 0 , true, false and function
return values
*/

void make_numberoperand(vmarg* arg, double val){
	arg->val = consts_newnumber(val);
	arg->type = NUMBER_A;
}

void make_booloperand(vmarg* arg, unsigned val){
	arg->val = val;
	arg->type = BOOL_A;
}

void make_retvaloperand(vmarg* arg){
	arg->type = RETVAL_A;
}

void add_incomplete_jump(unsigned instrNo, unsigned iaddress){
	
	incomplete_jump* ij = (incomplete_jump*)malloc(sizeof(incomplete_jump));
	ij->instrNo = instrNo;
	ij->iaddress = iaddress;
	ij->next = ij_head;
	ij_head = ij;
}

void patch_incomplete_jumps(){
	
	incomplete_jump* cur = ij_head;

	while(cur!=NULL){
		
		if(cur->iaddress == nextquadlabel()){
			
			instructions[cur->instrNo].result->val = curr_instruction;
		}
		else{
			
			instructions[cur->instrNo].result->val = cur->iaddress;
		}
		cur = cur->next;
	}
}


void generate_simple(enum vmopcode op,quad* quad){
	
	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	if(quad->arg1){
		make_operand(quad->arg1, arg1);
	}
	if(quad->arg2){
		make_operand(quad->arg2, arg2);
	}
	if(quad->result){
		make_operand(quad->result, result);
	}

	quad->taddress = getNextInstruction();
	
	emit_i(op,result,arg1,arg2,quad->line);
}

void generate_ADD(quad* quad){generate_simple(_ADD_V,quad);}

void generate_SUB(quad* quad){generate_simple(_SUB_V,quad);}

void generate_MUL(quad* quad){generate_simple(_MUL_V,quad);}

void generate_DIV(quad* quad){generate_simple(_DIV_V,quad);}

void generate_MOD(quad* quad){generate_simple(_MOD_V,quad);}

void generate_NEWTABLE(quad* quad){generate_simple(_TABLECREATE_V,quad);}

void generate_TABLEGETELEM(quad* quad){generate_simple(_TABLEGETELEM_V,quad);}

void generate_TABLESETELEM(quad* quad){generate_simple(_TABLESETELEM_V,quad);}

void generate_ASSIGN(quad* quad){generate_simple(_ASSIGN_V,quad);}

void generate_NOP(){
	emit_i(_NOP_V, NULL, NULL, NULL, 0);
}

void generate_relational(enum vmopcode op,quad* quad){
	
	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));
	
	if(quad->arg1){
		make_operand(quad->arg1, arg1);
	}
	if(quad->arg2){
		make_operand(quad->arg2, arg2);
	}

	result->type = LABEL_A;
	//printf("quad->label : %d,currprocessedquad : %d\n",quad->label,currprocessedquad());
	if(quad->label<currprocessedquad()){
		result->val = quads[quad->label].taddress;
	}
	else{
		add_incomplete_jump(curr_instruction,quad->label);
	}
	
	quad->taddress = curr_instruction;
	emit_i(op,result,arg1,arg2,quad->line); 

}

void generate_JUMP(quad* quad){generate_relational(_JUMP_V,quad);}

void generate_IF_EQ(quad* quad){generate_relational(_JUMP_EQ_V,quad);}

void generate_IF_NOTEQ(quad* quad){generate_relational(_JUMP_NOTEQ_V,quad);}

void generate_IF_GREATER(quad* quad){generate_relational(_JUMP_GREATER_V,quad);}

void generate_IF_GREATEREQ(quad* quad){generate_relational(_JUMP_GREATEREQ_V,quad);}

void generate_IF_LESS(quad* quad){generate_relational(_JUMP_LESS_V,quad);}

void generate_IF_LESSEQ(quad* quad){generate_relational(_JUMP_LESSEQ_V,quad);}

void reset_operand(vmarg* arg){
	arg = NULL;
}

void generate_AND(quad* quad){
	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	quad->taddress = getNextInstruction();
	
	make_operand(quad->arg1,arg1);
	make_booloperand(arg2,0);
	result->type = LABEL_A;
	result->val = getNextInstruction() + 4;
	emit_i(_JUMP_EQ_V,result,arg1,arg2,quad->line);

	vmarg* arg1_3 = (vmarg*)malloc(sizeof(vmarg));
	make_operand(quad->arg2,arg1_3);
	result->val = getNextInstruction() + 3;
	emit_i(_JUMP_EQ_V,result,arg1_3,arg2,quad->line);

	vmarg* result3 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg1_4 = (vmarg*)malloc(sizeof(vmarg));
	make_booloperand(arg1_4,1);
	reset_operand(arg2);
	make_operand(quad->result, result3);
    emit_i(_ASSIGN_V,result3,arg1_4,arg2,quad->line);

    vmarg* result4 = (vmarg*)malloc(sizeof(vmarg));
	reset_operand(arg1);
	reset_operand(arg2);
	result4->type = LABEL_A;
	result4->val = getNextInstruction() + 2;
    emit_i(_JUMP_V,result4,arg1,arg2,quad->line);

    //vmarg* result5 = (vmarg*)malloc(sizeof(vmarg));
    vmarg* arg1_2 = (vmarg*)malloc(sizeof(vmarg));
	make_booloperand(arg1_2,0);
	reset_operand(arg2);
	make_operand(quad->result, result3);
	emit_i(_ASSIGN_V,result3,arg1_2,arg2,quad->line);
	
}

void generate_OR(quad* quad){
	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	quad->taddress = getNextInstruction();
	
	make_operand(quad->arg1,arg1);
	make_booloperand(arg2,1);
	result->type = LABEL_A;
	result->val = getNextInstruction() + 4;
	emit_i(_JUMP_EQ_V,result,arg1,arg2,quad->line);

	vmarg* arg1_3 = (vmarg*)malloc(sizeof(vmarg));
	make_operand(quad->arg2,arg1_3);
	result->val = getNextInstruction() + 3;
	emit_i(_JUMP_EQ_V,result,arg1_3,arg2,quad->line);

	vmarg* result3 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg1_4 = (vmarg*)malloc(sizeof(vmarg));
	make_booloperand(arg1_4,0);
	reset_operand(arg2);
	make_operand(quad->result, result3);
    emit_i(_ASSIGN_V,result3,arg1_4,arg2,quad->line);

    vmarg* result4 = (vmarg*)malloc(sizeof(vmarg));
	reset_operand(arg1);
	reset_operand(arg2);
	result4->type = LABEL_A;
	result4->val = getNextInstruction() + 2;
    emit_i(_JUMP_V,result4,arg1,arg2,quad->line);

    //vmarg* result5 = (vmarg*)malloc(sizeof(vmarg));
    vmarg* arg1_2 = (vmarg*)malloc(sizeof(vmarg));
	make_booloperand(arg1_2,1);
	reset_operand(arg2);
	make_operand(quad->result, result3);
	emit_i(_ASSIGN_V,result3,arg1_2,arg2,quad->line);
	
}

void generate_NOT(quad* quad){
	
	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	quad->taddress = getNextInstruction();
	
	make_operand(quad->arg1,arg1);
	make_booloperand(arg2,0);
	result->type = LABEL_A;
	result->val = getNextInstruction() + 3;
	emit_i(_JUMP_EQ_V,result,arg1,arg2,quad->line);

	vmarg* arg1_2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result2 = (vmarg*)malloc(sizeof(vmarg));
	make_booloperand(arg1_2,0);
	reset_operand(arg2);
	make_operand(quad->result, result2);
	emit_i(_ASSIGN_V,result2,arg1_2,arg2,quad->line);

	vmarg* result3 = (vmarg*)malloc(sizeof(vmarg));
	reset_operand(arg1);
	reset_operand(arg2);
	result3->type = LABEL_A;
	result3->val = getNextInstruction() + 2;
	emit_i(_JUMP_V,result3,arg1,arg2,quad->line);

	vmarg* arg1_3 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result4 = (vmarg*)malloc(sizeof(vmarg));
	make_booloperand(arg1_3, 1);
	reset_operand(arg2);
	make_operand(quad->result, result4);
	emit_i(_ASSIGN_V,result4,arg1_3,arg2,quad->line);
}


void generate_PARAM(quad* quad){

	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));

	quad->taddress = getNextInstruction();
	make_operand(quad->result,arg1);
	emit_i(_PUSHARG_V,NULL,arg1,NULL,quad->line);

}

void generate_CALL(quad* quad){

	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	
	quad->taddress = getNextInstruction();
	make_operand(quad->result,arg1);
	emit_i(_CALL_V,NULL,arg1,NULL,quad->line);
}

void generate_GETRETVAL(quad* quad){

	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	quad->taddress = getNextInstruction();
	make_operand(quad->result,result);
	make_retvaloperand(arg1);
	emit_i(_ASSIGN_V,result,arg1,NULL,quad->line);
}

void generate_FUNCSTART(quad* quad){

	vmarg* result = (vmarg*)malloc(sizeof(vmarg));
	

	SymbolTableEntry* f = quad->result->sym;

	f->value.funcVal->iaddress = getNextInstruction();

	quad->taddress = getNextInstruction();

	//nmzw dn hreiazetai userfunctions.add(f->id,f->taddress,f->totallocals);
	userfuncs_newfunc(f);

	//push(funcstack, f);

	make_operand(quad->result, result);
	
	emit_i(_FUNCSTART_V,result,NULL,NULL,quad->line);

}

void generate_FUNCEND(quad* quad){
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	//f = pop(funcstack);
	//backpatch(f.returnList, getNextInstruction());

	quad->taddress = getNextInstruction();
	make_operand(quad->result, result);
	emit_i(_FUNCEND_V,result,NULL,NULL,quad->line);
}

void generate_RETURN(quad* quad){

	vmarg* arg1 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* arg2 = (vmarg*)malloc(sizeof(vmarg));
	vmarg* result = (vmarg*)malloc(sizeof(vmarg));

	quad->taddress = getNextInstruction();
	make_retvaloperand(result);
	make_operand(quad->arg1,arg1);
	emit_i(_ASSIGN_V,result,arg1,NULL,quad->line);

	//f = top(funcstack);
	//append(f.returnList,getNextInstruction());

	reset_operand(arg1);
	reset_operand(arg2);
	result->type = LABEL_A;
	emit_i(_JUMP_V,result,arg1,arg2,quad->line);
}


void generate(){
	unsigned i;
	for(i = 0; i<nextquadlabel(); i++){
		processedQuad = i;
		(*generators[quads[i].op])(quads+i);
	}
	patch_incomplete_jumps();
	//create_tcode_textfile();
	create_tcode_binary();
	generate_NOP();
}

void print_instructions(void){
	
	int i;

	for(i = 0; i < curr_instruction; i++){
		
		printf("%u: ", i+1);
		switch (instructions[i].opcode){
			case _ASSIGN_V:
				printf("ASSIGN_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _ADD_V: 
				printf("ADD_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _SUB_V: 
				printf("SUB_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _MUL_V:
				printf("MUL_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _DIV_V: 
				printf("DIV_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _MOD_V: 
				printf("MOD_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_EQ_V:
				printf("JMP_EQ_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_NOTEQ_V:
				printf("JMP_NOTEQ\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_LESSEQ_V:
				printf("IF_LESSEQ\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_GREATEREQ_V:
				printf("IF_GREATEREQ\t");
			print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_LESS_V:
				printf("IF_LESS\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_GREATER_V:
				printf("IF_GREATER\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _JUMP_V:
				printf("JUMP_V\t");
				//printf("instructions[%d]",i);
				print_vmarg_name(instructions[i].result);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _CALL_V:
				printf("CALL_V\t");
				print_vmarg_name(instructions[i].arg1);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _PUSHARG_V:
				printf("PUSHARG_V\t");
				print_vmarg_name(instructions[i].arg1);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _RETURN_V:
				printf("RETURN_V\t");
				print_vmarg_name(instructions[i].arg1);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _GETRETVAL_V:
				printf("GETRETVAL_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _FUNCSTART_V:
				printf("FUNCSTART_V\t");
				//print_vmarg_name(instructions[i].result);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _FUNCEND_V:
				printf("FUNCEND_V\t");
				print_vmarg_name(instructions[i].result);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _TABLECREATE_V:
				printf("TABLECREATE_V\t");
				print_vmarg_name(instructions[i].result);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _TABLEGETELEM_V:
				printf("TABLEGETELEM_V\t");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _TABLESETELEM_V:
				printf("TABLESETELEM_V\t");
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				print_vmarg_name(instructions[i].result);
				printf("\t%d",instructions[i].srcLine);
				break;
			case _NOP_V:
				printf("NOP_V\t");
			break;
			default :
				assert(0);

		}
		printf("\n");
	}
}

void print_vmarg_name(vmarg* arg){
	
	if(arg->type == GLOBAL_A || arg->type == FORMAL_A || arg->type == LOCAL_A || arg->type == BOOL_A){
		printf("%s[%u]\t",arg->name, arg->val);
		//printf("[%u]\t", arg->val);
	}
	else if(arg->type == LABEL_A || arg->type == RETVAL_A){
		printf("%u\t",arg->val);
	}
	else if(arg->type == NUMBER_A){
		if((int)numConsts[arg->val] == numConsts[arg->val]){
			printf("%d\t",(int)numConsts[arg->val]);
		}
		else{
			printf("%f\t",numConsts[arg->val]);
		}	
	}
	else if(arg->type == STRING_A){
		printf("%s\t",stringConsts[arg->val]);
	}
	else if(arg->type == LIBFUNC_A){
		printf("%s\t",namedLibfuncs[arg->val]);
	}
	else if(arg->type == USERFUNC_A){
		printf("%s\t",userFuncs[arg->val]);
	}
	else{
		printf("%d",arg->type);
		assert(0);
	}
}



void create_tcode_textfile(){
	FILE* fp;
	int magic_number;
	int tmp_number;

	if((fp = fopen("alpha.txt", "w")) == NULL) {
		printf("cannot open file\n");
		exit(EXIT_FAILURE);
	}

	magic_number = 123456789;
	fprintf(fp,"%u\n",magic_number);
	fprintf(fp,"%u\n",programVarOffset);

	//eisagwgh twn pinakwn me ta numbers
	fprintf(fp,"%u\n",currNumConst);

	for(tmp_number = 0; tmp_number < currNumConst; tmp_number++){
		fprintf(fp,"%f\n",numConsts[tmp_number]);
	}

	fprintf(fp,"%u\n",currNamedLibfunc);
	for(tmp_number = 0; tmp_number < currNamedLibfunc; tmp_number++){
		int k = strlen(namedLibfuncs[tmp_number]);
		fprintf(fp,"%d",k);
		fprintf(fp,"%s\n",namedLibfuncs[tmp_number]);
	}

	instructions_array_to_txt(fp);

}

void instructions_array_to_txt(FILE* fp){
	int i;
	for(i = 0; i < curr_instruction; i++){
		
		fprintf(fp,"%u:\t", i+1);
		switch (instructions[i].opcode){
			case _ASSIGN_V:
				fprintf(fp,"ASSIGN_V\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				fprintf(fp, "\n");
				break;
			case _ADD_V: 
				fprintf(fp,"ADD_V\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				fprintf(fp, "\n");
				break;
			case _SUB_V: 
				fprintf(fp,"SUB_V\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				fprintf(fp, "\n");
				break;
			case _MUL_V:
				fprintf(fp,"MUL_V\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				fprintf(fp, "\n");
				break;
			case _DIV_V: 
				fprintf(fp,"DIV_V\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				fprintf(fp, "\n");
				break;
			case _MOD_V: 
				fprintf(fp,"MOD_V\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				fprintf(fp, "\n");
				break;
			case _JUMP_EQ_V:
				fprintf(fp,"JMP_EQ_V\t");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _JUMP_NOTEQ_V:
				fprintf(fp,"JMP_NOTEQ\t");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _JUMP_LESSEQ_V:
				fprintf(fp,"IF_LESSEQ\t");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _JUMP_GREATEREQ_V:
				fprintf(fp,"IF_GREATEREQ\t");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _JUMP_LESS_V:
				fprintf(fp,"IF_LESS\t\t");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _JUMP_GREATER_V:
				fprintf(fp,"IF_GREATER\t");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				print_vmarg_name_to_text(fp,instructions[i].arg2);
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _JUMP_V:
				fprintf(fp,"JUMP_V\t\t");
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _CALL_V:
				fprintf(fp,"\tCALL ");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				fprintf(fp, "\n");
				break;
			case _PUSHARG_V:
				fprintf(fp,"\tPUSHARG ");
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				fprintf(fp, "\n");
				break;
			case _RETURN_V:
				printf("\tRETURN ");
				print_vmarg_name(instructions[i].arg1);
				break;
			case _GETRETVAL_V:
				fprintf(fp,"\tGETRETVAL ");
				print_vmarg_name_to_text(fp,instructions[i].result);
				print_vmarg_name_to_text(fp,instructions[i].arg1);
				fprintf(fp, "\n");
				break;
			case _FUNCSTART_V:
				fprintf(fp,"\tFUNCSTART ");
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _FUNCEND_V:
				fprintf(fp,"\tFUNCEND ");
				print_vmarg_name_to_text(fp,instructions[i].result);
				fprintf(fp, "\n");
				break;
			case _TABLECREATE_V:
				printf("\tTABLECREATE ");
				print_vmarg_name(instructions[i].result);
				break;
			case _TABLEGETELEM_V:
				printf("\tTABLEGETELEM ");
				print_vmarg_name(instructions[i].result);
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				break;
			case _TABLESETELEM_V:
				printf("\tTABLESETELEM ");
				print_vmarg_name(instructions[i].arg1);
				print_vmarg_name(instructions[i].arg2);
				print_vmarg_name(instructions[i].result);
				break;
			default :
				assert(0);

		}
		printf("\n");
	}	
}

void print_vmarg_name_to_text(FILE* fp,vmarg* arg){
	
	if(arg->type == GLOBAL_A || arg->type == FORMAL_A || arg->type == LOCAL_A || arg->type == BOOL_A){
		fprintf(fp,"%s[%u]\t",arg->name, arg->val);
	}
	else if(arg->type == LABEL_A || arg->type == RETVAL_A){
		fprintf(fp,"%u\t",arg->val);
	}
	else if(arg->type == NUMBER_A){
		if((int)numConsts[arg->val] == numConsts[arg->val]){
			fprintf(fp,"%d\t",(int)numConsts[arg->val]);
		}
		else{
			fprintf(fp,"%f\t",numConsts[arg->val]);
		}	
	}
	else if(arg->type == STRING_A){
		fprintf(fp,"%s\t",stringConsts[arg->val]);
	}
	else if(arg->type == NIL_A){
		fprintf(fp, "NILL" );
	}
	else if(arg->type == LIBFUNC_A){
		fprintf(fp,"%s\t",namedLibfuncs[arg->val]);
	}
	else if(arg->type == USERFUNC_A){
		fprintf(fp,"%s\t",userFuncs[arg->val]);
	}
	else{
		fprintf(fp,"%d",arg->type);
		assert(0);
	}
}



void create_tcode_binary(){
	FILE* fp;
	int magic_number;
	int tmp_number;
	int tmp_inst;
	int no_expr = -1;
	unsigned no_value = 0;

	if((fp = fopen("binary.txt", "w")) == NULL) {
		printf("cannot open file\n");
		exit(EXIT_FAILURE);
	}

	magic_number = 123456789;
	fwrite(&magic_number, sizeof(unsigned), 1, fp);
	fwrite(&programVarOffset, sizeof(unsigned), 1, fp);

	//eisagwgh twn pinakwn me ta numbers
	fwrite(&currNumConst,sizeof(unsigned),1,fp);
	for(tmp_number=0; tmp_number<currNumConst;tmp_number++){
		fwrite(&numConsts[tmp_number],sizeof(double),1,fp);
	}

	fwrite(&currNamedLibfunc,sizeof(unsigned),1,fp);
	for(tmp_number=0; tmp_number<currNamedLibfunc;tmp_number++){
		unsigned s = strlen(namedLibfuncs[tmp_number]);
		fwrite(&s,sizeof(unsigned),1,fp);
		fwrite(namedLibfuncs[tmp_number],s,1,fp);
		//printf("%s\n",namedLibfuncs[tmp_number]);
	}

	fwrite(&currStringConst,sizeof(unsigned),1,fp);
	
	for(tmp_number=0; tmp_number<currStringConst;tmp_number++){
		unsigned s2 = strlen(stringConsts[tmp_number]);
		//printf("grafw tosa : %d\n\n\n",(int)s);
		fwrite(&s2,sizeof(unsigned),1,fp);
		fwrite(stringConsts[tmp_number],s2,1,fp);
		//printf("%s\n",namedLibfuncs[tmp_number]);
	}

	fwrite(&curr_instruction,sizeof(unsigned),1,fp);
	
	for(tmp_inst =0;tmp_inst<curr_instruction;tmp_inst++){

		fwrite(&(instructions[tmp_inst].opcode),sizeof(int),1,fp);

		switch (instructions[tmp_inst].opcode){
			
			case _ASSIGN_V:
			case _GETRETVAL_V:{
				fwrite(&(instructions[tmp_inst].result->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].result->val),sizeof(unsigned),1,fp);

				fwrite(&(instructions[tmp_inst].arg1->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].arg1->val),sizeof(unsigned),1,fp); 
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
				fwrite(&(instructions[tmp_inst].result->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].result->val),sizeof(unsigned),1,fp);

				fwrite(&(instructions[tmp_inst].arg1->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].arg1->val),sizeof(unsigned),1,fp); 

				fwrite(&(instructions[tmp_inst].arg2->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].arg2->val),sizeof(unsigned),1,fp); 
				break;
			}

			case _CALL_V:
			case _PUSHARG_V:
			case _RETURN_V:{
				fwrite(&(instructions[tmp_inst].arg1->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].arg1->val),sizeof(unsigned),1,fp);
				break;
			}

			case _JUMP_V:
			case _FUNCSTART_V:
			case _FUNCEND_V:
			case _TABLECREATE_V:{
				fwrite(&(instructions[tmp_inst].result->type),sizeof(int),1,fp); 
				fwrite(&(instructions[tmp_inst].result->val),sizeof(unsigned),1,fp);
				break;
			}
			default :
				assert(0);
		}

		fwrite(&(instructions[tmp_inst].srcLine),sizeof(unsigned),1,fp); 
	}
}


//extern void generate_ADD(quad* quad);
//+++

/*
	TO DO!

userfunctions.add(f->id,f->taddress,f->totallocals);
push(funcstack, f);

f = pop(funcstack);
backpatch(f.returnList, getNextInstruction());

f = top(funcstack);
	append(f.returnList,getNextInstruction());

*/


