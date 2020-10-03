%{
		#include "ex.h"
	extern scopes;
	extern functionNumber;
	extern isfunc;
	extern programVarOffset;
	extern functionLocalOffset;
	extern formalArgOffset;
	extern scopeSpaceCounter;
	extern curr_quad;
	extern loopcounter;
%}


%union{
	char *string;
	double double_const_num;
	int int_const_num;
	struct expr* expr;
	struct SymbolTableEntry* sym;
	struct int_Lists* int_lists;
}

%start program

%token IF ELSE WHILE FOR FUNCTION RETURN BREAK 
%token CONTINUE LOCALL TRUES FALSES NIL

%token LC RC SEMICOLON COMMA COLON_COLON COLON 

%token <string> STRINGS
%token <int_const_num> INTCONST
%token <double_const_num> REALCONST
%token <string> IDENTIFIER



%right ASSIGN
%left OR
%left AND
%nonassoc EQUAL NOT_EQUAL
%nonassoc GREATER GTOE LESS LTOE
%left PLUS MINUS
%left STAR DIVISION MODULO
%right NOT PLUS_PLUS MINUS_MINUS UMINUS
%left DOT DOT_DOT
%left LB RB
%left LP RP


%type	<expr>	lvalue;
%type	<expr>	member;
%type	<expr>	primary;
%type	<expr>	assignexpr;
%type	<expr>	call;
%type	<expr>	term;
%type	<expr>	objectdef;
%type	<expr>	const;
%type	<expr>	expr;

%type 	<sym>	funcdef;
%type 	<string>	funcname;
%type 	<sym> 	funcprefix;
%type 	<expr> 	elist;
%type 	<expr> 	indexed;
%type	<expr>	indexed_next_expr;
%type	<expr>	indexedelem;
%type	<expr>	methodcall;
%type	<expr>	normcall;
%type	<expr>	callsuffix;

%type 	<int_const_num> 	ifpre;
%type 	<int_const_num> 	elsepre;
%type 	<int_const_num> 	whilestart;
%type 	<int_const_num> 	whilecond;

%type	<int_lists>	stmt;
%type	<int_lists>	stmt_in_block;
%type	<int_lists>	block;
%type	<int_lists>	ifstmt;
%type	<int_lists>	loopstmt;

%type	<int_lists>	funcbody;

%parse-param {void* top};
%parse-param {void* top_2};

%%


program: 	stmt program	
		|		{}
		;

stmt: 	expr SEMICOLON {
					resettemp();
					$$ = make_int_list();
					}
		| ifstmt {resettemp();$$ = $1;}
		| whilestmt {resettemp();$$ = make_int_list();}
		| forstmt {resettemp();$$ = make_int_list();}
		| returnstmt {
						resettemp();
						if(scopes==0){
							printf("Error: RETURN in scope 0\n");
						}
						$$ = make_int_list();
					}
		| BREAK SEMICOLON {
					resettemp();
					if(scopes==0){
						printf("Error: BREAK in scope 0\n");
					}
					$$ = make_int_list();

					qnode* temp;
					temp = malloc(sizeof(qnode));
					temp->next = NULL;
					temp->index = nextquadlabel();
					$$->break_list = temp;
					emit(_JUMP,NULL,NULL,NULL,-1,yylineno);
		}
		| CONTINUE SEMICOLON {
					resettemp();
					if(scopes==0){
						printf("Error: CONTINUE in scope 0\n");
					}
					$$ = make_int_list();

					qnode* temp;
					temp = malloc(sizeof(qnode));
					temp->next = NULL;
					temp->index = nextquadlabel();
					$$->continue_list = temp;
					emit(_JUMP,NULL,NULL,NULL,-1,yylineno);
		} 
		| block {resettemp();$$ = $1;}
		| funcdef {resettemp();}
		| SEMICOLON  {resettemp();}
		;

expr:	assignexpr {$$ = $1;}
		| expr PLUS expr {

				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
				   ($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
					printf("Error on line %u invalid use of expression in PLUS operator\n" , yylineno);
				}
				$$ = new_expr(ARITHM_E);
				$$->sym = newtemp();
				emit(_ADD,$$,$1,$3,-1,yylineno);
				
		}
		| expr MINUS expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in MINUS operator\n" , yylineno);
				}else{
					$$ = new_expr(ARITHM_E);
					$$->sym = newtemp();
					emit(_SUB,$$,$1,$3,-1,yylineno);
				}
					
		}
		| expr STAR expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in STAR operator\n" , yylineno);
				}else{
					$$ = new_expr(ARITHM_E);
					$$->sym = newtemp();
					emit(_MUL,$$,$1,$3,-1,yylineno);
				}

		}
		| expr DIVISION expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in DIVISION operator\n" , yylineno);
				}else{
					$$ = new_expr(ARITHM_E);
					$$->sym = newtemp();
					emit(_DIV,$$,$1,$3,-1,yylineno);
				}
		}
		| expr MODULO expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in MODULO operator\n" , yylineno);
				}else{
					$$ = new_expr(ARITHM_E);
					$$->sym = newtemp();
					emit(_MOD,$$,$1,$3,-1,yylineno);
				}

		}
		| expr GREATER expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in GREATER operator\n" , yylineno);
				}else{
					$$ = new_expr(BOOL_E);
					$$->sym = newtemp();
					emit(_IF_GREATER,NULL,$1,$3,(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,$$,new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,$$,new_true_expr(),NULL,-1,yylineno);
				}

		}
		| expr GTOE expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in GTOE operator\n" , yylineno);
				}else{
					$$ = new_expr(BOOL_E);
					$$->sym = newtemp();
					emit(_IF_GREATEREQ,NULL,$1,$3,(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,$$,new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,$$,new_true_expr(),NULL,-1,yylineno);
				}

		}
		| expr LESS expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in LESS operator\n" , yylineno);
				}else{
					$$ = new_expr(BOOL_E);
					$$->sym = newtemp();
					emit(_IF_LESS, NULL,$1,$3,(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,$$,new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,$$,new_true_expr(),NULL,-1,yylineno);
				}

		}
		| expr LTOE expr {
				if(($1->type == USERFUNC_E) || ($1->type == LIBFUNC_E) || ($1->type == BOOL_E) || ($1->type == NEWTABLE_E) || ($1->type == CONSTBOOL_E) || ($1->type == CONSTSTRING_E) || ($1->type == NIL_E) || 
			   		($3->type == USERFUNC_E) || ($3->type == LIBFUNC_E) || ($3->type == BOOL_E) || ($3->type == NEWTABLE_E) || ($3->type == CONSTBOOL_E) || ($3->type == CONSTSTRING_E) || (($3->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in LTOE operator\n" , yylineno);
				}else{
					$$ = new_expr(BOOL_E);
					$$->sym = newtemp();
					emit(_IF_LESSEQ , NULL , $1 , $3 ,(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,$$,new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,$$,new_true_expr(),NULL,-1,yylineno);
				}

		}
		| expr EQUAL expr {

			$$ = new_expr(BOOL_E);
			$$->sym = newtemp();
			emit(_IF_EQ , NULL , $1 , $3 ,(int) getNextQuad() + 3,yylineno);
			emit(_ASSIGN,$$,new_false_expr(),NULL,-1,yylineno);
			emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
			emit(_ASSIGN,$$,new_true_expr(),NULL,-1,yylineno);

			//truelist kai falselist
			//me h xwris backpath?


		}
		| expr NOT_EQUAL expr {
			//truelist kai falselist
			$$ = new_expr(BOOL_E);
			$$->sym = newtemp();
			emit(_IF_NOTEQ , NULL , $1 , $3 ,(int) nextquadlabel() + 3,yylineno);
			emit(_ASSIGN,$$,new_false_expr(),NULL,-1,yylineno);
			emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
			emit(_ASSIGN,$$,new_true_expr(),NULL,-1,yylineno);		
		}
		| expr AND expr {
			//truelist kai falselist
			if(($1->type != BOOL_E)) {
				expr* temp = $1;
				expr* temp2 = $3;
				printf("in outer if AND\n");
				if(($3->type != CONSTBOOL_E)) {
					printf("in inner if AND\n");
					temp2 = change_to_bool(temp2);
				}
				temp = change_to_bool(temp);
				$$ = new_expr(BOOL_E);
				$$->sym = newtemp();
				emit(_AND , $$ , temp , temp2 , -1 ,yylineno);

			}else{
				printf("in else AND\n");
				$$ = new_expr(BOOL_E);
				$$->sym = newtemp();
				emit(_AND , $$ , $1 , $3 , -1 ,yylineno);
			}
			
			
		}
		| expr OR expr {
			//truelist kai falselist

			if(($1->type != BOOL_E)) {
				expr* temp = $1;
				expr* temp2 = $3;
				printf("in outer if OR\n");
				if(($3->type != CONSTBOOL_E)) {
					printf("in inner if OR\n");
					temp2 = change_to_bool(temp2);
				}
				temp = change_to_bool(temp);
				$$ = new_expr(BOOL_E);
				$$->sym = newtemp();
				emit(_OR , $$ , temp , temp2 , -1 ,yylineno);

			}else{
				printf("in else OR\n");
				$$ = new_expr(BOOL_E);
				$$->sym = newtemp();
				emit(_OR , $$ , $1 , $3 , -1 ,yylineno);
			}

			
		}

		| term {$$ = $1;}
		;

term:	LP expr RP { 
			$$ = $2; 
		} 
		| MINUS expr %prec UMINUS{
			if(($2->type == USERFUNC_E) || ($2->type == LIBFUNC_E) || ($2->type == BOOL_E) || ($2->type == NEWTABLE_E) || ($2->type == CONSTBOOL_E) || ($2->type == CONSTSTRING_E) || ($2->type == NIL_E)){
				printf("Error on line %u invalid use of expression in \"MINUS expr\"\n" , yylineno);
			}else{
			/*
				$$ = new_expr(ARITHM_E);
				$$->sym = newtemp();
				emit(_UMINUS,$$,$2,NULL,-1,yylineno);

			*/
			expr* e = new_expr_const_num(-1);
			$$ = new_expr(ARITHM_E);
			$$->sym = newtemp();
			emit(_MUL,$$,$2,e,-1,yylineno);
			}
		}
		| NOT expr{
			//truelist kai falselist
			$$ = new_expr(BOOL_E);
			$$->sym = newtemp();
			emit(_NOT,$$,$2,NULL,-1,yylineno);

		}
		| PLUS_PLUS lvalue {

			if(isfunc==1){
				printf("error on line %d! you can't change the value of a function\n",yylineno);
			}
			
			if(!$2){printf("error: in \"PLUS_PLUS lvalue\" empty lvalue\n");}
			else{
				if($2->type == TABLEITEM_E){
					$$ = emit_iftableitem($2);
					expr* temp = new_expr_const_num(1);
					emit(_ADD, $$, $$, temp, -1 , yylineno);
					emit(_TABLESETELEM,$$, $2, $2->index, -1, yylineno);

				}else{
					$$ = new_expr_const_num(1);
					emit(_ADD,$2,$2,$$,-1,yylineno);
					$$ = new_expr(ARITHM_E);
					$$->sym = newtemp();
					emit(_ASSIGN,$$,$2,NULL,-1,yylineno);
				}
			}
		}
		| lvalue PLUS_PLUS 
		{

			if(isfunc==1){
				printf("error on line %d! you can't change the value of a function\n",yylineno);
			}

			if(!$1){printf("error in: \"lvalue PLUS_PLUS\" empty lvalue\n");}
			else{
			$$ = new_expr(VAR_E);
			$$ -> sym = newtemp();

			if($1 -> type == TABLEITEM_E){
				expr* value = emit_iftableitem($1);
				emit(_ASSIGN, $$, value, NULL, -1, yylineno);
				expr* temp = new_expr_const_num(1);
				emit(_ADD, value, value, temp, -1, yylineno);
				emit(_TABLESETELEM, value, $1, $1 -> index, -1, yylineno);
			}else{
				emit(_ASSIGN,$$,$1,NULL,-1,yylineno);
				$$ = new_expr_const_num(1); 
				emit(_ADD,$1,$1,$$,-1,yylineno);
			}
			}
			
		}
		| MINUS_MINUS lvalue 
		{

			if(isfunc==1){
				printf("error on line %d! you can't change the value of a function\n",yylineno);
			}

			if(!$2){printf("error: in \"MINUS_MINUS lvalue\" empty lvalue\n");}
			else{

				if($2->type == TABLEITEM_E){
					$$ = emit_iftableitem($2);
					expr* temp = new_expr_const_num(1);
					emit(_SUB, $$, $$, temp, -1 , yylineno);
					emit(_TABLESETELEM,$$, $2, $2->index, -1, yylineno);
				}else{	
				$$ = new_expr_const_num(1);
				emit(_SUB,$2,$2,$$,-1,yylineno);
				$$ = new_expr(ARITHM_E);
				$$->sym = newtemp();
				emit(_ASSIGN,$$,$2,NULL,-1,yylineno);
				}
			}

		}
		| lvalue MINUS_MINUS 
		{

			if(isfunc==1){
					printf("error on line %d! you can't change the value of a function\n",yylineno);
			}
			
			if(!$1){printf("error: in \"lvalue MINUS_MINUS\" empty lvalue\n");}
			else{
			$$ = new_expr(VAR_E);
			$$ -> sym = newtemp();

			if($1 -> type == TABLEITEM_E){
				expr* value = emit_iftableitem($1);
				emit(_ASSIGN, $$, value, NULL, -1, yylineno);
				expr* temp = new_expr_const_num(1);
				emit(_SUB, value, value, temp, -1, yylineno);
				emit(_TABLESETELEM, value, $1, $1 -> index, -1, yylineno);
			}else{
				emit(_ASSIGN,$$,$1,NULL,-1,yylineno);
				$$ = new_expr_const_num(1); 
				emit(_SUB,$1,$1,$$,-1,yylineno);
			}
			}

		}
		| primary {$$ = $1;}
		;

assignexpr: lvalue {
						if(isfunc==1){
							printf("error on line %d! you can't assign a function\n",yylineno);
						}
					} ASSIGN expr {

						if(!$1){printf("error \n");}
						else{
							if($1->type == TABLEITEM_E){
								emit(_TABLESETELEM, $4, $1, $1->index, -1, yylineno);
								$$ = emit_iftableitem($1);
								$$ -> type = ASSIGN_E;
							}
							else{
								emit(_ASSIGN,$1,$4,NULL, -1,yylineno);
								$$ = new_expr(ASSIGN_E);
								$$->sym = newtemp();
								emit(_ASSIGN,$$,$1,NULL, -1,yylineno);
							}

						}

						
					} 
			;

primary:	lvalue {$$ = emit_iftableitem($1);}
			| call {$$ = $1;}
			| objectdef {$$ = $1;}
			| LP funcdef RP 	
				{ 
					$$ = new_expr(LIBFUNC_E);
					$$->sym = $2;
				}	
			| const {$$ = $1;}
			;

lvalue:		IDENTIFIER {
				int j = checkLibraryFunction(yylval.string);

				SymbolTableEntry* temp = NULL;
				isfunc = lookupIfFunc2(yylval.string);

				int i = scopes;
				while(i>-1){
					temp = lookupInScope(i,yylval.string);
					if(temp == NULL){
						i--;
					}else{
						break;  
					}
				}

				if(j == 1){
					if(i == -1){
						if(scopes!=0){
							SymbolTableEntry *symbol = InsertHas(1,LOCAL,yylval.string,scopes,yylineno);
							symbol->space = currscopespace();
							symbol->offset = currscopeoffset();
							$$ = lvalue_expr(symbol);
						}else{
							SymbolTableEntry *symbol = 	InsertHas(1,GLOBAL,yylval.string,0,yylineno);
							symbol->space = currscopespace();
							symbol->offset = currscopeoffset();
							$$ = lvalue_expr(symbol);
						}
						inccurrscopeoffset();
					}else{
						//yparxei kapou
						if(i == 0){
							//anaferomaste se kati global ( libfunc , userfunc, global var )
							$$ = lvalue_expr(temp);
						}else{
							//se kati oxi global
							if(lookupIfFunc(yylval.string) && (lookupIsActiveinScopeSameName(i,yylval.string) == 0)){
								// se kati oxi global to opoio einai function

								if(lookupIsActiveInScope(i,yylval.string)==1){
										//anaferomaste se mia func pou einai energh se scope i 
										printf("Error h synarthsh den exei kleisei , einai me acitve 1\n");
								}else if(lookupIsActiveInScope(i,yylval.string)==0){
									//mporoume na kalesoume ? th synarthsh arkei na koita3oume se poio scope einai
									//anaferomai se synarthsh pou den einai active

									if(i<=scopes){
										//printf("you can access this function\n");
										$$ = lvalue_expr(temp);

									}else{
										printf("error on line %d, you cannot access this variable\n",yylineno);
									}
								}
							}else {
								//vrhka ena variable me to idio onoma sto scope i

								if(lookupIsActiveinScopeSameName(i,yylval.string) == 1){
									//anferomai se ayth kai 8elw na dw an exw prosvash
									int k;
									int exwsynarthsh = 0;
									for(k=scopes; k>=i; k--){
										if(!checkAccess(k)){ //paremvaletai synarthsh
											printf("error! you cannot access %s on line %d \n",yylval.string, yylineno);
											exwsynarthsh = 1;
											break;
										}

									}
									if(exwsynarthsh){
										//anaferomai se metavlhth kai den exw prosvash
									}else{
										//anaferomai se metavlhth kai exw prosvash
										$$ = lvalue_expr(temp);
									}
								}else{
									//anaferomai se kati pou den einai active ara mporw na kanw insert
									if(scopes!=0){
										SymbolTableEntry *symbol = InsertHas(1,LOCAL,yylval.string,scopes,yylineno);
										symbol->space = currscopespace();
										symbol->offset = currscopeoffset();
										$$ = lvalue_expr(symbol);
										inccurrscopeoffset();

									}else{
										SymbolTableEntry *symbol = InsertHas(1,GLOBAL,yylval.string,0,yylineno);
										symbol->space = currscopespace();
										symbol->offset = currscopeoffset();
										$$ = lvalue_expr(symbol);
										inccurrscopeoffset();
									}
								}
							}
						}


					}
				}else{
					SymbolTableEntry* lb = NULL;
					lb = lookupInScope(0,yylval.string);
					$$ = lvalue_expr(lb);
					//vrhka libfunc me to idio onoma
					//h ektypwsh tou la8ous ginetai sthn synarthsh checklibraryfunction
				}

			}


			| LOCALL IDENTIFIER { 
				//prepei na ginei to idio me to identifier
				int j = checkLibraryFunction(yylval.string);

				if(j==1 && (!lookupInScope(scopes,yylval.string))){
					if(scopes==0){
						SymbolTableEntry *symbol = InsertHas(1,GLOBAL,yylval.string,0,yylineno);
						symbol->space = currscopespace();
						symbol->offset = currscopeoffset();
						$$ = lvalue_expr(symbol);
					}
					else{
						SymbolTableEntry *symbol = InsertHas(1,LOCAL,yylval.string,scopes,yylineno);
						symbol->space = currscopespace();
						symbol->offset = currscopeoffset();
						$$ = lvalue_expr(symbol);
					}
					inccurrscopeoffset();
				}else{
					printf("already declared\n");
					$$ = NULL;
				}
			}

			| COLON_COLON IDENTIFIER {
				if(Lookup(0, yylval.string)==0){
					printf("error! undeclared global symbol %s (line %d)\n", yylval.string, yylineno);
				}
				$$ = NULL;

			}


			| member {$$ = $1;}
			;

member:		lvalue DOT IDENTIFIER
			{
				$$ = member_item($1,$3);
			}
			| lvalue LB expr RB
			{
				$1 = emit_iftableitem($1);
				$$ = new_expr(TABLEITEM_E);
				$$ -> sym = $1 -> sym;
				$$ -> index = $3;

			}
			| call DOT IDENTIFIER
			{
				$$ = member_item($1,$3);
			}
			| call LB expr RB
			{
				$1 = emit_iftableitem($1);
				$$ = new_expr(TABLEITEM_E);
				$$ -> sym = $1 -> sym;
				$$ -> index = $3;
			}
			;

call:		call LP elist RP
			{
				$$ = make_call($1,$3);
			}
			| lvalue callsuffix
			{
				expr* temp;
				if($2->boolConst==1){
					expr* self = $1;
					$1 = emit_iftableitem(member_item(self,$2->strConst));
					
					temp = elist_add_beginning($2->next,self);
					$$ = make_call($1,temp); 
				}
				else{
					$$ = make_call($1,$2->next); 
				}
				

			}
			| LP funcdef RP LP elist RP
			{
				expr* func = new_expr(LIBFUNC_E);
				func->sym = $2;
				$$ = make_call(func,$5);
			}
			; 

callsuffix:	normcall {$$ = $1;}
			| methodcall {$$ = $1;}
			;

normcall:	LP elist RP
			{
				expr* norm = new_expr(VAR_E);
				norm->next = $2;
				norm->boolConst = 0;
				norm->strConst = NULL;
				$$ = norm;
			}
			;

methodcall:	DOT_DOT IDENTIFIER LP elist RP
			{
				expr* method = new_expr(VAR_E);
				method->next = $4;
				method->boolConst = 1;
				method->strConst = $2;
				$$ = method;

			}
			;

elist:	expr
		{	
			expr* temp = $1;
			temp->next=NULL;
			$$ = temp;
		}
		| elist COMMA expr
		{
				expr* temp = $3;
				temp->next=NULL;
				$$ = elist_add($1,temp);
		} 
		| {$$ = NULL;}
		;


objectdef: 	 LB elist RB
			{

				expr* t = new_expr(NEWTABLE_E);
				t -> sym = newtemp();
				emit(_TABLECREATE, t, NULL, NULL, -1, yylineno);
				int i = 0;
				expr* temp = $2;
				while(temp!=NULL){
					expr* temp2 = new_expr_const_num(i);
					emit(_TABLESETELEM,temp,t,temp2,-1,yylineno);
					temp = temp -> next;
					i++;
				}
				$$ = t;

			}
			| LB indexed RB 
			{
				expr* t = new_expr(NEWTABLE_E);
				t -> sym = newtemp();
				emit(_TABLECREATE, t, NULL, NULL, -1, yylineno);
				expr* temp = $2;

				while(temp!=NULL){
					emit(_TABLESETELEM,temp->index,t,temp,-1,yylineno);
					temp = temp -> next;
				}
				
				$$ = t;

			}
			; 

indexed:	indexedelem indexed_next_expr
			{	
				$$ = $1; $$->next = $2;
			}
			;

indexed_next_expr: 	COMMA indexedelem indexed_next_expr
					{
						$$ = $2; $$->next = $3;
					}
					|{$$ = NULL;}
					;

indexedelem: LC expr COLON expr RC
			{
				$$ = $2; $$->index = $4;	
			}
			;

stmt_in_block: 	stmt stmt_in_block {printf("block\n");
				
					$2->break_list = merge_int_lists($1->break_list,$2->break_list);
					$2->continue_list = merge_int_lists($1->continue_list,$2->continue_list);
					$$ = $2;
				}
				| {printf("block empty rule \n"); $$ = make_int_list();}
				;

block:	LC {scopes++;} stmt_in_block RC {

			$$ = $3;

			lookupAndHide(scopes);
			scopes--;}
		;
		
	
funcname:	IDENTIFIER 
			{	$$ = $1; }
			| {$$ = NULL;}
			;	 

funcprefix: FUNCTION funcname
			{
				if($2==NULL){
					
					//char newFuncName[20];
					//sprintf(newFuncName,"_f%u", functionNumber);
					char* gname = functionGenerateName();
                	$$ = InsertHas(1,USERFUNC,gname,scopes,yylineno);
                	$$->space = currscopespace();
					$$->offset = currscopeoffset();
					inccurrscopeoffset();
				}else{
					int j = checkLibraryFunction($2);
					if(j==1 && (!lookupInScope(scopes,$2))){
						$$ = InsertHas(1,USERFUNC,$2,scopes,yylineno);
						$$->space = currscopespace();
						$$->offset = currscopeoffset();
						inccurrscopeoffset();
					}
					else if (j==1 && lookupInScope(scopes,$2)){
						if(!lookupIfFunc($2) && (!lookupIfForm($2))){
							$$ = InsertHas(1,USERFUNC,$2,scopes,yylineno);
							$$->space = currscopespace();
							$$->offset = currscopeoffset();
							inccurrscopeoffset();
						}
						else{
							printf("Error on line %d\n",yylineno);
						}
					}
					else{
						printf("error with function %s on line%d\n", $2, yylineno);
					}
				}
				
				

				
				$$->value.funcVal->iaddress = nextquadlabel();
				expr* temp = lvalue_expr($$);
				emit(_FUNCSTART, temp, NULL, NULL, nextquadlabel(), yylineno);
				
				//push sto stack twn synarthsewn pou einai energes  gia na kanw 3e-push meta ka8ws aytes kleinoun
		
				push_flocal_stack(functionLocalOffset,top_2);

				//enterscopespace();
				resetformalargoffset();
			}
			;

funcargs: 	LP 
			{scopes++; enterscopespace();} 
			idlist RP 
			{
				scopes--; 
				enterscopespace(); 
				resetfunctionlocaloffset();

			}
			;

funcbody:	block 
			{
				$$ = $1;
				exitscopespace();
				//prepei na apothikeusw kai sto funcbody ton arithmo ton locals!
			}
			;

funcblockstart:	{
		
		push_counter_stack(loopcounter,top);
		loopcounter = 1;
}

funcblockend: {
		loopcounter = pop_counter_stack(top);
}

funcdef:	funcprefix funcargs funcblockstart funcbody funcblockend
			{
				//totallocals in function
				$1->value.funcVal->total_locals = functionLocalOffset;
				exitscopespace();
				

				//pop
				functionLocalOffset = pop_flocal_stack(top_2);
				$$ = $1;

				expr* e = new_expr(LIBFUNC_E);
				e->sym = $$;

				//expr* temp = lvalue_expr($$);
				emit(_FUNCEND,e, NULL, NULL,-1,yylineno);
			}
			;

const: INTCONST {$$ = new_expr_const_num($1);}
	   | REALCONST {$$ = new_expr_const_num($1);}
	   | STRINGS {  $$ = new_expr_const_string($1); }
	   | NIL {$$ = new_nil_expr();}
	   | TRUES {$$ = new_true_expr();}
	   | FALSES {$$ = new_false_expr();}
	   ;

idlist: IDENTIFIER {printf("IDENTIFIER");
			int j = checkLibraryFunction(yylval.string);
			isfunc = lookupIfFunc(yylval.string);

			int i = scopes;
			
			while(i>-1){
				if(!Lookup(i,yylval.string)){
					i--;
				}else{
					break;  
				}
			}

			if( j == 1 && i == -1){
				SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
				symbol->space = currscopespace();
				symbol->offset = currscopeoffset();
				inccurrscopeoffset();
			}else if(j == 1 && (lookupIsActiveInScope(i,yylval.string)) == 0){
				SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
				symbol->space = currscopespace();
				symbol->offset = currscopeoffset();
				inccurrscopeoffset();
			}else if(j == 1 && (lookupIsActiveInScope(i,yylval.string)) == -1){
				SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
				symbol->space = currscopespace();
				symbol->offset = currscopeoffset();
				inccurrscopeoffset();
			}else if(j==1 && (lookupIsActiveInScope(i,yylval.string)) == 1){
				
				if(i == 0){
				
					SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
					symbol->space = currscopespace();
					symbol->offset = currscopeoffset();
					inccurrscopeoffset();
				}else{
					printf("Error uparxei to formal %s sth line %d einai active alla oxi sto global\n",yylval.string,yylineno);
				}

				int k;

				if(i!=-1 && i!=0){
					for(k=scopes; k>=i; k--){
						if(!checkAccess(k)){
							printf("error! you cannot access %s on line %d \n",yylval.string, yylineno);
							break;
						}
					}
				}
			}

			
				
		}

		| idlist COMMA {printf("COMMA ");} IDENTIFIER {printf("IDENTIFIER ");
			int j = checkLibraryFunction(yylval.string);
			
			isfunc = lookupIfFunc(yylval.string);

			int i = scopes;
			
			while(i>-1){
				if(!Lookup(i,yylval.string)){
					i--;
				}else{
					break;  
				}
			}

			if( j == 1 && i == -1){
				SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
				symbol->space = currscopespace();
				symbol->offset = currscopeoffset();
				inccurrscopeoffset();
			}else if(j == 1 && (lookupIsActiveInScope(i,yylval.string)) == 0){
				SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
				symbol->space = currscopespace();
				symbol->offset = currscopeoffset();
				inccurrscopeoffset();
			}else if(j == 1 && (lookupIsActiveInScope(i,yylval.string)) == -1){
				SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
				symbol->space = currscopespace();
				symbol->offset = currscopeoffset();
				inccurrscopeoffset();
			}else if(j==1 && (lookupIsActiveInScope(i,yylval.string)) == 1){
			
				if(i == 0){
				
					SymbolTableEntry *symbol = InsertHas(1	,FORMAL,yylval.string,scopes,yylineno);
					symbol->space = currscopespace();
					symbol->offset = currscopeoffset();
					inccurrscopeoffset();
				}else{
					printf("Error uparxei to formal %s sth line %d einai active alla oxi sto global\n",yylval.string,yylineno);
				}

				int k;

				if(i!=-1 && i!=0){
					for(k=scopes; k>=i; k--){
						if(!checkAccess(k)){
							printf("error! you cannot access %s on line %d \n",yylval.string, yylineno);
							break;
						}
					}
				}
			}

		}

		| {}
		;

ifstmt:     ifpre stmt
			{
				$$ = make_int_list();
				$$->quad_for_else = getNextQuad();
				patchlabel($1,$$->quad_for_else);
				$$->break_list = merge_int_lists($2->break_list,$$->break_list);
				$$->continue_list = merge_int_lists($2->continue_list,$$->continue_list);

			}
			| ifpre stmt elsepre stmt 
			{
				patchlabel($3,getNextQuad());
				$4->quad_for_else = $3+2;
				$$ = $4;
				patchlabel($1,$4->quad_for_else);
				$$->break_list = merge_int_lists($2->break_list,$$->break_list);
				$$->continue_list = merge_int_lists($2->continue_list,$$->continue_list);
			}
            ;
    
ifpre:  IF LP expr RP 
		{
			emit(_IF_EQ,NULL,$3,new_true_expr(),(long int) getNextQuad()+2,yylineno);
			$$ = nextquadlabel();
			emit(_JUMP,NULL,NULL,NULL,-1,yylineno);
		}
        ;

elsepre:    ELSE 
			{
				$$ = nextquadlabel();
				emit(_JUMP,NULL,NULL,NULL,-1,yylineno);

			}



whilestmt:	whilestart whilecond loopstmt {
				
				qnode* temp;
				qnode* temp2;
				emit(_JUMP, NULL, NULL, NULL, $1 ,yylineno);
				patchlabel($2,nextquadlabel());

				temp = $3->break_list;
				while(temp){
					patchlabel(temp->index,nextquadlabel());
					temp = temp->next;
				}

				temp2 = $3->continue_list;
				while(temp2){
					patchlabel(temp2->index,$1);
					temp2 = temp2->next;
				}

			}
			;

whilestart:	WHILE {
				$$ = nextquadlabel();
			}
			;

whilecond:	LP expr RP {

				$2 = new_expr(BOOL_E);
				$2->sym = newtemp();

				emit(_ASSIGN,$2,new_true_expr(),NULL,-1,yylineno);
				emit(_JUMP,NULL,NULL,NULL,nextquadlabel()+2,yylineno);
				emit(_ASSIGN,$2,new_false_expr(),NULL,-1, yylineno);

				emit(_IF_EQ,NULL,$2,new_true_expr(),nextquadlabel()+2,yylineno);
				$$ = nextquadlabel();
				emit(_JUMP,NULL,NULL,NULL,-1,yylineno);

			}
			;

loopstmt:	loopstart stmt loopend {$$ = $2;}

loopstart:	{++loopcounter;}

loopend:	{--loopcounter;}

forstmt:	FOR{printf("FOR\n");} LP{printf("LP\n");} elist SEMICOLON{printf("SEMICOLON\n");} expr SEMICOLON{printf("SEMICOLON\n");} elist  RP{printf("RP\n");} stmt
			;

returnstmt:	RETURN {if(!isfunc) printf("Error: return cannot be called here , there is no function \n");} SEMICOLON{
				expr* temp3 = new_expr(NIL_E);
				emit(_RETURN,NULL,temp3,NULL,-1,yylineno);
			}
			|RETURN {if(!isfunc) printf("Error: return cannot be called here , there is no function \n");} expr { 
				expr* temp4 = new_expr(BOOL_E);
				temp4->sym = newtemp();

				emit(_ASSIGN, temp4,new_true_expr(),NULL,-1,yylineno);
				emit(_JUMP,NULL,NULL,NULL,nextquadlabel()+2,yylineno);
				emit(_ASSIGN,temp4,new_false_expr(),NULL,-1,yylineno);

				$3 = temp4;
				emit(_RETURN,NULL,$3,NULL,-1,yylineno);

			} SEMICOLON
			;

	
%%

int yyerror (void* top, void* top_2, char* yaccProvidedMessage){
	fprintf(stderr, "%s: at line %d, before token: %s\n", yaccProvidedMessage, yylineno, yytext);
	fprintf(stderr, "INPUT NOT VALIDE\n");
}



int main(int argc, char** argv){
	struct loop_stack_node* top;
	struct flocal_stack_node* top_2;
	if(argc>1){
	if(!(yyin=fopen(argv[1],"r"))){
		fprintf(stderr, "cannot read file %s", argv[1]);
		return 1;
	}
	}
	else
		yyin = stdin;
	insertLibraryFunctions();

	top = malloc(sizeof(struct loop_stack_node));
	top_2 = malloc(sizeof(struct flocal_stack_node));
	top->next = NULL;
	top_2->next = NULL;
	yyparse((void*) top, (void*) top_2);

	printios();
	printf("Quads: \n");
	//printf("currquad: %d\n",curr_quad);
	print_quads();
	generate();
	printf("\nInstructions: \n");
	print_instructions();
	return 0;
}