%{


#include "parser.h"
unsigned int pos = 0;
char *string;
unsigned int errorFound = 0;
unsigned int multiline_comments = 0;
unsigned int nested_comments = 0;
unsigned int count_comments = 0;
unsigned int line;
unsigned int line_comments = 0;
int i;
double d;
%}

%option yylineno 
%option noyywrap

/* KEYWORD */
IF "if"	
ELSE "else"	
WHILE "while"	
FOR "for"	
FUNCTION "function"	
RETURN "return"
BREAK "break"	
CONTINUE "continue"	
AND "and"	
NOT "not"	
OR "or"	
LOCALL "local"	
TRUES "true"	
FALSES "false"	
NIL "nil"	


/* OPERATOR */
EQUAL "=="	
NOT_EQUAL "!="	
PLUS_PLUS "++"	
MINUS_MINUS "--"	
ASSIGN "="	
PLUS "+"	
MINUS "-"	
STAR "*"	
DIVISION "/"	
MODULO "%"	
GTOE ">="	
LTOE "<="	
GREATER ">"	
LESS "<"	


/* PUNCTUATION */
LC "{"	
RC "}"	
LB "["	
RB "]"
LP "("	
RP ")"	
SEMICOLON ";"	
COMMA ","	
COLON_COLON "::"	
COLON ":"	
DOT_DOT ".."	
DOT "."	

id 			[a-zA-Z][a-zA-Z_0-9]*
digit	[0-9]
integer 		[-+]?(0[xX][0-9A-Fa-f]+)|([0-9]+)
real			[-+]?[0-9]*\.[0-9](e|E)?[0-9]*
string \"	
single_line_comment	("//".*"\n")
comment "/*"

%x com STRING SLASH

%%

{string}		{
				BEGIN(STRING);
			}

<STRING>\"		{
				BEGIN(INITIAL);
				string = realloc(string,(pos+1)*sizeof(char));
				string[pos] = '\0';
				pos = 0;
				//printf("%s",string);
				yylval.string = string;
				return STRINGS;
			}

<STRING>\\		{
				BEGIN(SLASH);
			}

<SLASH>t		{
				BEGIN(STRING);
				string = realloc(string, (pos + 1)*sizeof(char));
				string[pos] = '\t';
				++pos;
			}

<SLASH>n		{
				BEGIN(STRING);
				string = realloc(string, (pos + 1)*sizeof(char));
				string[pos] = '\n';
				++pos;
			}

<SLASH>\\		{
				BEGIN(STRING);
				string = realloc(string, (pos + 1) *sizeof(char));
				string[pos] = '\\';
				++pos;
			}

<SLASH>\"		{
				BEGIN(STRING);
				string = realloc(string, (pos + 1) *sizeof(char));
				string[pos] = '\"';
				++pos;
			}

<SLASH>.		{
				BEGIN(STRING);
				printf("Warning: Invalid %s line[%d]\n", yytext,yylineno);
			}

<STRING>.		{
				string = realloc(string,(pos+1)*sizeof(char));
				string[pos] = *yytext;
				++pos;
			}

<STRING><<EOF>>		{
				errorFound =1;
				printf("Error: unclosed string line[%d]\n",yylineno);
				return 0;
			}

{single_line_comment}	{line_comments++;}

{comment}	{ multiline_comments ++;
	count_comments ++;
	BEGIN(com); 
	}

<com>"*/"	{ 
	line = yylineno;
	count_comments--;
	if (count_comments==0) {
		BEGIN(INITIAL); 
	}}
<com>"/*"	{
	multiline_comments++;
	nested_comments++;
	count_comments ++;
	BEGIN(com); }
<com>\n 	{}
<com>. 		{}
<com><<EOF>>	{ 
	printf("error with miltiline comment on line: %d\n",line);
	exit(1);
	}


{IF}	{return IF;}
{ELSE}	{return ELSE;}
{WHILE}	{return WHILE;}
{FOR}	{return FOR;}
{FUNCTION}	{return FUNCTION;}
{RETURN}	{return RETURN;}
{BREAK}	{return BREAK;}
{CONTINUE}	{return CONTINUE;}
{AND}	{return AND;}
{NOT}	{return NOT;}
{OR}	{return OR;}
{LOCALL}	{return LOCALL;}
{TRUES}	{return TRUES;}
{FALSES}	{return FALSES;}
{NIL}	{return NIL;}



{EQUAL}	{return EQUAL;}
{NOT_EQUAL}	{return NOT_EQUAL;}
{PLUS_PLUS}	{return PLUS_PLUS;}
{MINUS_MINUS}	{return MINUS_MINUS;}
{ASSIGN}	{return ASSIGN;}
{PLUS}	{return PLUS;}
{MINUS}	{return MINUS;}
{STAR}	{return STAR;}
{DIVISION}	{return DIVISION;}
{MODULO}	{return MODULO;}
{GTOE}	{return GTOE;}
{LTOE}	{return LTOE;}
{GREATER}	{return GREATER;}
{LESS}	{return LESS;}


{digit}+	{
	yylval.int_const_num = atoi(yytext);
	return INTCONST;
}


{digit}+\.{digit}+ {
	yylval.double_const_num = atof(yytext);
	return REALCONST;
}


{id}	{yylval.string = strdup(yytext); return IDENTIFIER;}


{LC}	{return LC;}
{RC}	{return RC;}
{LB}	{return LB;}
{RB}	{return RB;}
{LP}	{return LP;}
{RP}	{return RP;}
{SEMICOLON}	{return SEMICOLON;}
{COMMA}	{return COMMA;}
{COLON_COLON}	{return COLON_COLON;}
{COLON}	{return COLON;}
{DOT_DOT}	{return DOT_DOT;}
{DOT}	{return DOT;}


\n|\t|\v|" " {}

%%
/*
int main(int argc[],char *argv[]){
	yyin = fopen("test.txt" , "r");
	yylex();
	return 0;
}
*/