/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_PARSER_H_INCLUDED
# define YY_YY_PARSER_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    IF = 258,
    ELSE = 259,
    WHILE = 260,
    FOR = 261,
    FUNCTION = 262,
    RETURN = 263,
    BREAK = 264,
    CONTINUE = 265,
    LOCALL = 266,
    TRUES = 267,
    FALSES = 268,
    NIL = 269,
    LC = 270,
    RC = 271,
    SEMICOLON = 272,
    COMMA = 273,
    COLON_COLON = 274,
    COLON = 275,
    STRINGS = 276,
    INTCONST = 277,
    REALCONST = 278,
    IDENTIFIER = 279,
    ASSIGN = 280,
    OR = 281,
    AND = 282,
    EQUAL = 283,
    NOT_EQUAL = 284,
    GREATER = 285,
    GTOE = 286,
    LESS = 287,
    LTOE = 288,
    PLUS = 289,
    MINUS = 290,
    STAR = 291,
    DIVISION = 292,
    MODULO = 293,
    NOT = 294,
    PLUS_PLUS = 295,
    MINUS_MINUS = 296,
    UMINUS = 297,
    DOT = 298,
    DOT_DOT = 299,
    LB = 300,
    RB = 301,
    LP = 302,
    RP = 303
  };
#endif
/* Tokens.  */
#define IF 258
#define ELSE 259
#define WHILE 260
#define FOR 261
#define FUNCTION 262
#define RETURN 263
#define BREAK 264
#define CONTINUE 265
#define LOCALL 266
#define TRUES 267
#define FALSES 268
#define NIL 269
#define LC 270
#define RC 271
#define SEMICOLON 272
#define COMMA 273
#define COLON_COLON 274
#define COLON 275
#define STRINGS 276
#define INTCONST 277
#define REALCONST 278
#define IDENTIFIER 279
#define ASSIGN 280
#define OR 281
#define AND 282
#define EQUAL 283
#define NOT_EQUAL 284
#define GREATER 285
#define GTOE 286
#define LESS 287
#define LTOE 288
#define PLUS 289
#define MINUS 290
#define STAR 291
#define DIVISION 292
#define MODULO 293
#define NOT 294
#define PLUS_PLUS 295
#define MINUS_MINUS 296
#define UMINUS 297
#define DOT 298
#define DOT_DOT 299
#define LB 300
#define RB 301
#define LP 302
#define RP 303

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED

union YYSTYPE
{
#line 15 "parser2.y" /* yacc.c:1909  */

	char *string;
	double double_const_num;
	int int_const_num;
	struct expr* expr;
	struct SymbolTableEntry* sym;
	struct int_Lists* int_lists;

#line 159 "parser.h" /* yacc.c:1909  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void* top, void* top_2);

#endif /* !YY_YY_PARSER_H_INCLUDED  */
