#ifndef HEADER_H
#define HEADER_H

//#define YY_DECL int alpha_yylex(void *yylval)
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

#define SIZE 100

extern int yylineno;
extern char* yytext;
extern FILE* yyin;

enum  SymbolTableType {
	GLOBAL, LOCAL, FORMAL,
	USERFUNC, LIBFUNC
};

enum scopespace_t{
    PROGRAMVAR,
    FORMALARG,
    FUNCLOCAL
};

enum iopcode {
	_ASSIGN,	
	_ADD,		
	_SUB,
	_MUL,		
	_DIV,		
	_MOD,
	_AND,		
	_OR,
	_NOT,		
	_IF_EQ,		
	_IF_NOTEQ,
	_IF_LESSEQ,	
	_IF_GREATEREQ,	
	_IF_LESS,
	_IF_GREATER,	
	_JUMP,		
	_CALL,
	_PARAM,		
	_RETURN,	
	_GETRETVAL,
	_FUNCSTART,	
	_FUNCEND,	
	_TABLECREATE,
	_TABLEGETELEM,	
	_TABLESETELEM
};

enum expr_t {
	VAR_E,
	TABLEITEM_E,
	USERFUNC_E,
	LIBFUNC_E,
	ARITHM_E,
	BOOL_E,
	ASSIGN_E,
	NEWTABLE_E,
	CONSTNUM_E,
	CONSTBOOL_E,
	CONSTSTRING_E,
	NIL_E
};

typedef struct Variable {
	const char *name;
	unsigned int scope;
	unsigned int line;
} Variable;

typedef struct Function {
	const char *name;
	//List of arguments
    unsigned int scope;
	unsigned int line;
	unsigned int iaddress;
	unsigned int total_locals;
} Function;

typedef struct SymbolTableEntry{
	int isActive;
	union{
		Variable *varVal;
        Function *funcVal;
	}value;
	enum SymbolTableType type;
	struct SymbolTableEntry *next;
	struct SymbolTableEntry *next_same_scope;
	enum scopespace_t space;
    unsigned offset;

}SymbolTableEntry;

typedef struct expr{
	enum expr_t	type;
	struct SymbolTableEntry*	sym;
	struct expr*	index;
	double 	numConst;
	char*	strConst;
	char boolConst;
	struct expr* next;
} expr;

typedef struct quad {
	enum iopcode op;
	expr *result;
	expr *arg1;
	expr *arg2;
	long int label;
	unsigned line;
	unsigned int taddress;
} quad;

typedef struct quad_nodes{
	unsigned index;
	struct quad_nodes* next;
}qnode;

typedef struct int_Lists{
	unsigned quad_for_else;
	qnode* break_list;
	qnode* continue_list;
	qnode* return_list;
}intLists;

typedef struct loop_stack_node {
	int counter;
	struct loop_stack_node* next;
} loopstacknode;

typedef struct flocal_stack_node {
	int counter;
	struct flocal_stack_node* next;
} flocalstacknode;


int yyerror(void*, void*, char* yaccProvidedMessage);
int yylex(void);


SymbolTableEntry *Hashtable[SIZE];
SymbolTableEntry *Scope0;
SymbolTableEntry *Scope1;
SymbolTableEntry *Scope2;
SymbolTableEntry *InsertHas(int isctive,enum SymbolTableType  typos,const char *name,unsigned int scope,unsigned int line);
int Lookup(int scope,char *onoma);
SymbolTableEntry Hide();
int checkLibraryFunction(char* name);
char* functionGenerateName();
int lookupIfForm (char * onoma);
int lookupIfFunc (char * onoma);
void printios();
void printlist();
int Hashkey(enum SymbolTableType  typos,int scope);
int search(int k,enum SymbolTableType  typos);
void NextScope(int k,SymbolTableEntry *a);
void insertLibraryFunctions();
void lookupAndHide(int scope);
int lookupIsActive (int scope,char *onoma);
int checkAccess(int scope);
int lookupIsActiveInScope(int scope,char *onoma);
int lookupIfFunc2(char *onoma);
int lookupIsActiveinScopeSameName(int scope,char *onoma);
int lookupIsActiveScopesSameName (int scope,char *onoma);

expr* lvalue_expr(SymbolTableEntry *symbol);
void emit(enum iopcode op, expr *result, expr *arg1, expr *arg2, long int label, int line);
expr* new_expr(enum expr_t type);
expr* new_expr_const_string(char *const_string);
expr* new_expr_const_num(double number);
expr* new_true_expr();
expr* new_false_expr();
expr* new_nil_expr();

void expand_quads_array(void);
SymbolTableEntry* lookup(char *onoma);
SymbolTableEntry* lookupInScope(int scope,char *onoma);
unsigned getNextQuad();
void print_quads(void);
void print_expr_name(expr* arg);
enum scopespace_t currscopespace();
unsigned currscopeoffset();
void inccurrscopeoffset();
void enterscopespace();
void exitscopespace();
char* newtempname();
void resettemp();
SymbolTableEntry* newtemp();
//new!
void resetformalargoffset();
void resetfunctionlocaloffset();
void restorecurrscopeoffset(unsigned n);
expr* emit_iftableitem(expr *e);
expr* member_item(expr* lvalue,char* name);

unsigned nextquadlabel();
void patchlabel(unsigned quadNo, unsigned label);


expr* change_expr_type_true(expr* temp);
expr* change_expr_type_false(expr* temp);
expr* change_to_bool(expr* expression);

intLists* make_int_list();
qnode* merge_int_lists(qnode* list1,qnode* list2);

void push_counter_stack(int loop_c, loopstacknode* top);
int pop_counter_stack(loopstacknode* top);
void push_flocal_stack(int flocal,flocalstacknode* top_2);
int pop_flocal_stack(flocalstacknode* top_2);

expr* make_call(expr* lvalue, expr* elist);
expr* elist_add(expr* e, expr* n);
expr* elist_add_beginning(expr* e, expr* n);

#endif /* HEADER_H */
