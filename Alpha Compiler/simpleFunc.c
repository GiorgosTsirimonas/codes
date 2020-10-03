#include "ex.h"
#include <assert.h>
#define TOTALSIZE 1024

extern int error;
extern unsigned temp_counter;
extern scopes;
quad *quads = NULL;
unsigned total_quads = 0;
unsigned curr_quad = 0;
int loopcounter = 0;


// scopeSpaceCounter:
//      gia global in panta 1
//      ayksanetai kata 1 se eisodo se formal arguments (kai kena) 'h se sunarthsh
//      meiwnetai kata 2 se eksodo apo synarthsh
unsigned programVarOffset = 0;
unsigned functionLocalOffset = 0;
unsigned formalArgOffset = 0;
unsigned scopeSpaceCounter = 1;

unsigned temp_counter = 0;

expr* lvalue_expr(SymbolTableEntry *symbol)
{
	expr *tmp_e;
	
	tmp_e = malloc(sizeof(expr));
	memset(tmp_e, 0, sizeof(expr));
	tmp_e->next = NULL;
	//tmp_e->truelist = NULL;
	//tmp_e->falselist = NULL;
	
	tmp_e->sym = symbol;
	
	switch(symbol->type) {
		case GLOBAL:
			tmp_e->type = VAR_E;
			break;
		case LOCAL:
			tmp_e->type = VAR_E;
			break;
		case FORMAL:
			tmp_e->type = VAR_E;
			break;
		case USERFUNC:
			tmp_e->type = USERFUNC_E;
			break;
		case LIBFUNC:
			tmp_e->type = LIBFUNC_E;
	}
	
	return tmp_e;
}

expr* new_expr(enum expr_t type)
{
	expr *tmp_e;
	
	tmp_e = malloc(sizeof(expr));
	memset(tmp_e, 0, sizeof(expr));
	tmp_e->next = NULL;	
	tmp_e->type = type;
	
	return tmp_e;
}

expr* new_nil_expr(){
	expr *tmp_e;
	tmp_e = new_expr(NIL_E);
	
	return tmp_e;
}

expr* new_expr_const_string(char *const_string)
{
	expr *tmp_e;
	
	tmp_e = new_expr(CONSTSTRING_E);
	tmp_e->strConst = strdup(const_string);
	return tmp_e;
}

expr* new_expr_const_num(double number)
{
	expr *tmp_e;
	
	tmp_e = new_expr(CONSTNUM_E);
	tmp_e->numConst = number;
	
	return tmp_e;
}

expr* new_true_expr(){
	expr *tmp_e;
	
	tmp_e = new_expr(CONSTBOOL_E);
	tmp_e->boolConst = 1;
	
	return tmp_e;
}

expr* new_false_expr(){
	expr *tmp_e;
	
	tmp_e = new_expr(CONSTBOOL_E);
	tmp_e->boolConst = 0;
	
	return tmp_e;
}

expr* change_expr_type_true(expr* temp){
	temp->type = CONSTBOOL_E;
	temp->boolConst = 1;
	return temp;
}

expr* change_expr_type_false(expr* temp){
	temp->type = CONSTBOOL_E;
	temp->boolConst = 0;
	return temp;
}



void emit(enum iopcode op, expr *result, expr *arg1, expr *arg2, long int label ,int line){
	if(curr_quad == total_quads)
		expand_quads_array();
	
	quads[curr_quad].op = op;
	quads[curr_quad].result = result;
	quads[curr_quad].arg1 = arg1;
	quads[curr_quad].arg2 = arg2;
	quads[curr_quad].label = label;
	quads[curr_quad].line = line;
	
	
	curr_quad = getNextQuad();
}

void expand_quads_array(void)
{
	quads = realloc(quads, (TOTALSIZE * sizeof(quad))
				+ (total_quads * sizeof(quad)));
	
	if(!quads) {
		puts("allocation error");
		exit(EXIT_FAILURE);
	}
	
	total_quads += TOTALSIZE;
}

SymbolTableEntry* lookup(char *onoma){
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1; 

     while(temp!=NULL){
                if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                    if(!strcmp(temp->value.varVal->name ,onoma)){
                        return temp;
                    }       
                }
                else{
                    if(!strcmp(temp->value.funcVal->name ,onoma)){
                        return temp;
                    }  
                }
              
                temp1 = temp->next_same_scope;
                while(temp1!=NULL){
                    if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                        if(!strcmp(temp1->value.varVal->name ,onoma)){
                            return temp1;
                        }
                    }
                    else{
                        if(!strcmp(temp1->value.funcVal->name ,onoma)){
                            return temp1;
                        }
                    }
                    
                temp1 = temp1->next_same_scope;
                }
                temp = temp->next; 
    }
    return NULL;
}

SymbolTableEntry* lookupInScope(int scope,char *onoma){
    
     SymbolTableEntry *temp = Hashtable[0];

     while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == scope ){
               break;
            }
        }else {
            if (temp->value.funcVal->scope == scope){
                break;
            }
        }
        temp = temp->next;
     }

     while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (!strcmp(temp->value.varVal->name , onoma )){
            //printf("found it!\n");
               return temp;
            }
        }else {
            if (!strcmp(temp->value.funcVal->name ,onoma )){
              //  printf("found it!\n");
                return temp;
            }
        }
        temp = temp->next_same_scope;
     }
     //printf("didn't find it!\n");
     return NULL;
 
}

unsigned getNextQuad(){
	return curr_quad+1;
}

void print_quads(void){
	
	int i;
	for(i = 0; i < curr_quad; i++){
		
		printf("%u: ", i+1);
		switch (quads[i].op){
			case _ASSIGN:
				printf("\tASSIGN ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				break;
			case _ADD: 
				printf("\tADD ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _SUB: 
				printf("\tSUB ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _MUL:
				printf("\tMUL ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _DIV: 
				printf("\tDIV ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _MOD: 
				printf("\tMOD ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _AND: 
				printf("\tAND ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _OR:
				printf("\tOR ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _NOT:
				printf("\tNOT ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				break;
			case _IF_EQ:
				printf("\tIF_EQ ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				printf("%d",(int)quads[i].label);
				break;
			case _IF_NOTEQ:
				printf("\tIF_NOTEQ ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				printf("%d",(int)quads[i].label);
				break;
			case _IF_LESSEQ:
				printf("\tIF_LESSEQ ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				printf("%d",(int)quads[i].label);
				break;
			case _IF_GREATEREQ:
				printf("\tIF_GREATEREQ ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				printf("%d",(int)quads[i].label);
				break;
			case _IF_LESS:
				printf("\tIF_LESS ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				printf("%d",(int)quads[i].label);
				break;
			case _IF_GREATER:
				printf("\tIF_GREATER ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				printf("%d",(int)quads[i].label);
				break;
			case _JUMP:
				printf("\tJUMP ");
				printf("%d",(int)quads[i].label);
				break;
			case _CALL:
				printf("\tCALL ");
				print_expr_name(quads[i].result);
				break;
			case _PARAM:
				printf("\tPARAM ");
				print_expr_name(quads[i].result);
				break;
			case _RETURN:
				printf("\tRETURN ");
				print_expr_name(quads[i].arg1);
				break;
			case _GETRETVAL:
				printf("\tGETRETVAL ");
				print_expr_name(quads[i].result);
				break;
			case _FUNCSTART:
				printf("\tFUNCSTART ");
				print_expr_name(quads[i].result);
				break;
			case _FUNCEND:
				printf("\tFUNCEND ");
				print_expr_name(quads[i].result);
				break;
			case _TABLECREATE:
				printf("\tTABLECREATE ");
				print_expr_name(quads[i].result);
				break;
			case _TABLEGETELEM:
				printf("\tTABLEGETELEM ");
				print_expr_name(quads[i].result);
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				break;
			case _TABLESETELEM:
				printf("\tTABLESETELEM ");
				print_expr_name(quads[i].arg1);
				print_expr_name(quads[i].arg2);
				print_expr_name(quads[i].result);
				break;
			default :
				assert(0);

		}
		printf("\n");
	}
}

void print_expr_name(expr* arg){
	if(arg->type == VAR_E || arg->type == TABLEITEM_E || arg->type == ARITHM_E || arg->type == BOOL_E || arg->type == ASSIGN_E || arg->type == NEWTABLE_E){
		printf("%s ",arg->sym->value.varVal->name);
	}else if(arg->type == USERFUNC_E || arg->type == LIBFUNC_E){
		printf("%s ",arg->sym->value.funcVal->name);
	}else if(arg->type == CONSTNUM_E){

		if((int)arg->numConst == arg->numConst){
			printf("%d ", (int)arg->numConst);
		}else{
			printf("%f", arg->numConst);
		}
	}else if(arg->type == CONSTBOOL_E){
		if(arg->boolConst){
			printf("TRUE");
		}else{
			printf("FALSE");
		}
	}else if(arg->type == CONSTSTRING_E){
		printf("%s ",arg->strConst);
	}else if(arg->type == NIL_E){
		printf("NIL ");
	}else{
		printf("%d",arg->type);
		assert(0);
	}
}

enum scopespace_t currscopespace(){
	if(scopeSpaceCounter == 1){
		return PROGRAMVAR;
	}else if(scopeSpaceCounter % 2 == 0){
		return FORMALARG;
	}else{
		return FUNCLOCAL;
	}
}

unsigned currscopeoffset(){
	switch (currscopespace()){
		case PROGRAMVAR:	return programVarOffset;
		case FUNCLOCAL:		return functionLocalOffset;
		case FORMALARG:		return formalArgOffset;
		default: assert(0);
	}
}

void inccurrscopeoffset(){
	switch(currscopespace()){
		case PROGRAMVAR:	++programVarOffset; break;
		case FUNCLOCAL:		++functionLocalOffset; break;
		case FORMALARG:		++formalArgOffset; break;
		default: assert(0);
	}
}

// 		scopeSpaceCounter:
//      gia global in panta 1
//      ayksanetai kata 1 se eisodo se formal arguments (kai kena) 'h se sunarthsh
//      meiwnetai kata 2 se eksodo apo synarthsh

void enterscopespace(){
	++scopeSpaceCounter;
}

void exitscopespace(){
	assert(scopeSpaceCounter>1);
	--scopeSpaceCounter;
}

char* newtempname(){
	char* tempString = (char*)malloc(sizeof(char)*100);
	char* tempName = (char*)malloc(sizeof(char)*100);

	sprintf(tempString, "%d", temp_counter);
	strcpy(tempName, "_t");
	strcat(tempName, tempString);
	temp_counter++;

	return tempName;

	/*char tempName[10];
	sprintf(tempName, "_t%d", temp_counter);
	temp_counter++;
	return tempName;*/
}

void resettemp(){
	temp_counter = 0;
}

//epistrefei 'h idi diathesimh metavlhth me newtempname 'h mia nea kryfh sto paron scope
SymbolTableEntry* newtemp(){
	char* name = newtempname();

	SymbolTableEntry* sym;
	sym = lookupInScope(scopes, name);

	if(sym==NULL){
		if(scopes==0){
			sym = InsertHas(1,GLOBAL,name,scopes,yylineno);
		}else{
			sym = InsertHas(1,LOCAL,name,scopes,yylineno);
		}
		sym->space = currscopespace();
		sym->offset = currscopeoffset();
		inccurrscopeoffset();

		return sym;
	}
	else{
		return sym;
	}
}

//new! gia sunarthseis

void resetformalargoffset(){
	formalArgOffset = 0;
}

void resetfunctionlocaloffset(){
	functionLocalOffset = 0;
}

void restorecurrscopeoffset(unsigned n){
	switch (currscopespace()){
		case PROGRAMVAR: programVarOffset = n; break;
		case FUNCLOCAL:  functionLocalOffset = n; break;
		case FORMALARG:  formalArgOffset = n; break;
		default: assert(0);
	}
}

//new! dialeksh 10 sel 29

expr* emit_iftableitem(expr *e){
	if(e->type != TABLEITEM_E){
		return e;
	}else{
		expr* result = new_expr(VAR_E);
		result -> sym = newtemp();
		emit(_TABLEGETELEM,result,e,e->index,-1,yylineno);
		return result;
	}
}

expr* member_item(expr* lvalue,char* name){
	lvalue = emit_iftableitem(lvalue);
	expr* item = new_expr(TABLEITEM_E);
	item -> sym = lvalue -> sym;
	item -> index = new_expr_const_string(name);
	return item;
}

unsigned nextquadlabel(){
	return curr_quad;
}

void patchlabel(unsigned quadNo, unsigned label){
	assert(quadNo < curr_quad);
	quads[quadNo].label = label;
}

expr* change_to_bool(expr* expression){

	if(expression){
		if((expression->type == ARITHM_E) || (expression->type == CONSTNUM_E)){
			if(expression->numConst == 0){
				printf("changed int 0 to bool\n");
				return change_expr_type_false(expression);
			}else{
				printf("changed int 1 to bool\n");
				return change_expr_type_true(expression);
			}
		}else if((expression->type == TABLEITEM_E) || (expression->type == LIBFUNC_E) || (expression->type == USERFUNC_E)){
			return change_expr_type_true(expression);
		}else if(expression->type == NIL_E){
			return change_expr_type_false(expression);
		}else if(expression->type == CONSTSTRING_E){
			if(!strcmp(expression->strConst,"")){
				return change_expr_type_false(expression); 
			}else{
				return change_expr_type_true(expression);
			}
		}else if(expression->type==VAR_E){
				return expression;
		}		
	}else{
		printf("null expression in change_to_bool\n");
		return expression;	
	}
	
}

intLists* make_int_list(){
	intLists* temp3;
	temp3 = malloc(sizeof(intLists));
	if(!temp3){
		printf("error malloc\n");
	}
	temp3->break_list = NULL;
	temp3->continue_list = NULL;
	temp3->return_list = NULL;
	temp3->quad_for_else = 0;

	return temp3;
}

qnode* merge_int_lists(qnode* list1,qnode* list2){

	qnode* temp;

	if(list1 == NULL){
		return list2;
	}else if(list2 == NULL){
		return list1;
	}

	temp = list1;
	while(temp->next != NULL){
		temp = temp->next;
	}

	temp->next = list2;

	return list1;

}


void push_counter_stack(int loop_c, loopstacknode* top){
	
	loopstacknode* new = (loopstacknode*)malloc(sizeof(loopstacknode));

	new->counter = loop_c;
	new->next = NULL;

	if(top->next == NULL){
		top->next = new;
		printf("inserted!!!!!\n");
	}
	else{
		loopstacknode* cur = top;
		while(cur->next != NULL){
			cur = cur->next;
		}

		cur->next = new;
	}
}

int pop_counter_stack(loopstacknode* top){
	int c;
	
	loopstacknode* temp = top->next;
	loopstacknode* t;
	
	if(temp->next == NULL){
		c = temp->counter;
		temp->next = NULL;
		return c;
	}
	else{
		while(temp->next != NULL){
			printf("in while\n");
			t = temp;
			temp = temp->next;
		}
		printf("after while!!!!\n");
		c = t->counter;
		t->next=NULL;
		return c;
	}	
	//free(t->next);
}


void push_flocal_stack(int flocal,flocalstacknode* top_2){
	
	flocalstacknode* new = (flocalstacknode*)malloc(sizeof(flocalstacknode));

	new->counter = flocal;
	new->next = NULL;

	if(top_2->next == NULL){
		top_2->next = new;
	}
	else{
		flocalstacknode* cur = top_2;
		while(cur->next != NULL){
			cur = cur->next;
		}

		cur->next = new;
	}
}

int pop_flocal_stack(flocalstacknode* top_2){
	int c;
	
	flocalstacknode* temp = top_2->next;
	flocalstacknode* t;


	if(temp->next == NULL){
		c = temp->counter;
		temp->next = NULL;
		return c;
	}else{		
		while(temp->next != NULL){
			t = temp;
			temp = temp->next;
		}
		c = t->counter;
		t->next=NULL;
		return c;
	}	
	//free(t->next);
}

expr* make_call(expr* lvalue, expr* elist){
	expr* func = emit_iftableitem(lvalue);
	int i = 0;
	expr* temp = elist;
	while(temp!=NULL){
		i++;
		temp = temp->next;
	}
	printf("i = %d\n",i);
	int j;
	int k = 1;
	for(j = i; j>0; j--){
		expr* temp2 = elist;
		while(k<j){
			temp2 = temp2->next;
			k++;
		}
		emit(_PARAM,temp2,NULL,NULL,-1,yylineno);
		printf("emitted param\n");
		k=1;
	}
	emit(_CALL, func, NULL, NULL, -1, yylineno);
	expr* result = new_expr(VAR_E);
	result->sym = newtemp();
	printf("emitted getretval\n");
	emit(_GETRETVAL,result, NULL, NULL, -1, yylineno);
	return result;
}

expr* elist_add(expr* e, expr* n){
	if(e==NULL){
		return n;
	}

	expr* temp = e;
	while(temp->next!=NULL){
		temp = temp->next;
	}

	temp->next = n;
	return e;
}

expr* elist_add_beginning(expr* e, expr* n){
	n->next = e;
	return n;
}
