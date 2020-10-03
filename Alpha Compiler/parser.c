/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.0.4"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* Copy the first part of user declarations.  */
#line 1 "parser2.y" /* yacc.c:339  */

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

#line 79 "parser.c" /* yacc.c:339  */

# ifndef YY_NULLPTR
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULLPTR nullptr
#  else
#   define YY_NULLPTR 0
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* In a future release of Bison, this section will be replaced
   by #include "parser.h".  */
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
#line 15 "parser2.y" /* yacc.c:355  */

	char *string;
	double double_const_num;
	int int_const_num;
	struct expr* expr;
	struct SymbolTableEntry* sym;
	struct int_Lists* int_lists;

#line 224 "parser.c" /* yacc.c:355  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void* top, void* top_2);

#endif /* !YY_YY_PARSER_H_INCLUDED  */

/* Copy the second part of user declarations.  */

#line 241 "parser.c" /* yacc.c:358  */

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE
# if (defined __GNUC__                                               \
      && (2 < __GNUC__ || (__GNUC__ == 2 && 96 <= __GNUC_MINOR__)))  \
     || defined __SUNPRO_C && 0x5110 <= __SUNPRO_C
#  define YY_ATTRIBUTE(Spec) __attribute__(Spec)
# else
#  define YY_ATTRIBUTE(Spec) /* empty */
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# define YY_ATTRIBUTE_PURE   YY_ATTRIBUTE ((__pure__))
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# define YY_ATTRIBUTE_UNUSED YY_ATTRIBUTE ((__unused__))
#endif

#if !defined _Noreturn \
     && (!defined __STDC_VERSION__ || __STDC_VERSION__ < 201112)
# if defined _MSC_VER && 1200 <= _MSC_VER
#  define _Noreturn __declspec (noreturn)
# else
#  define _Noreturn YY_ATTRIBUTE ((__noreturn__))
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN \
    _Pragma ("GCC diagnostic push") \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")\
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif


#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYSIZE_T yynewbytes;                                            \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / sizeof (*yyptr);                          \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, (Count) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYSIZE_T yyi;                         \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  69
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   447

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  49
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  52
/* YYNRULES -- Number of rules.  */
#define YYNRULES  111
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  193

/* YYTRANSLATE[YYX] -- Symbol number corresponding to YYX as returned
   by yylex, with out-of-bounds checking.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   303

#define YYTRANSLATE(YYX)                                                \
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, without out-of-bounds checking.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,    91,    91,    92,    95,    99,   100,   101,   102,   109,
     123,   137,   138,   139,   142,   143,   154,   165,   176,   186,
     197,   211,   225,   239,   253,   267,   276,   300,   326,   329,
     332,   348,   355,   378,   404,   429,   455,   458,   458,   484,
     485,   486,   487,   492,   495,   604,   628,   637,   640,   644,
     652,   656,   665,   669,   685,   693,   694,   697,   707,   718,
     724,   730,   734,   751,   768,   774,   778,   781,   787,   793,
     796,   796,   805,   807,   810,   862,   861,   872,   880,   886,
     890,   909,   910,   911,   912,   913,   914,   917,   974,   974,
    1030,  1033,  1042,  1053,  1061,  1070,  1092,  1097,  1113,  1115,
    1117,  1119,  1119,  1119,  1119,  1119,  1119,  1122,  1122,  1126,
    1126,  1126
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "IF", "ELSE", "WHILE", "FOR", "FUNCTION",
  "RETURN", "BREAK", "CONTINUE", "LOCALL", "TRUES", "FALSES", "NIL", "LC",
  "RC", "SEMICOLON", "COMMA", "COLON_COLON", "COLON", "STRINGS",
  "INTCONST", "REALCONST", "IDENTIFIER", "ASSIGN", "OR", "AND", "EQUAL",
  "NOT_EQUAL", "GREATER", "GTOE", "LESS", "LTOE", "PLUS", "MINUS", "STAR",
  "DIVISION", "MODULO", "NOT", "PLUS_PLUS", "MINUS_MINUS", "UMINUS", "DOT",
  "DOT_DOT", "LB", "RB", "LP", "RP", "$accept", "program", "stmt", "expr",
  "term", "assignexpr", "$@1", "primary", "lvalue", "member", "call",
  "callsuffix", "normcall", "methodcall", "elist", "objectdef", "indexed",
  "indexed_next_expr", "indexedelem", "stmt_in_block", "block", "$@2",
  "funcname", "funcprefix", "funcargs", "$@3", "funcbody",
  "funcblockstart", "funcblockend", "funcdef", "const", "idlist", "$@4",
  "ifstmt", "ifpre", "elsepre", "whilestmt", "whilestart", "whilecond",
  "loopstmt", "loopstart", "loopend", "forstmt", "$@5", "$@6", "$@7",
  "$@8", "$@9", "returnstmt", "$@10", "$@11", "$@12", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303
};
# endif

#define YYPACT_NINF -81

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-81)))

#define YYTABLE_NINF -108

#define yytable_value_is_error(Yytable_value) \
  (!!((Yytable_value) == (-108)))

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int16 yypact[] =
{
     135,   -32,   -81,   -81,    -6,    -8,     8,    10,     9,   -81,
     -81,   -81,   -81,   -81,    18,   -81,   -81,   -81,   -81,   172,
     172,    25,    25,    74,     0,    46,   135,   285,   -81,   -81,
     -81,   193,   -81,    79,   -81,   -81,     1,   -81,   -81,   -81,
     135,   -81,     5,   -81,   -81,   172,    19,   -81,   -81,    51,
     172,   -81,   -81,   -81,   135,   -81,   -81,   -81,    67,   -15,
      79,   -15,   172,   375,   -12,    45,    61,   194,    54,   -81,
     -81,   -81,   172,   172,   172,   172,   172,   172,   172,   172,
     172,   172,   172,   172,   172,   -81,   -81,    70,    93,   172,
     172,    98,   -81,   -81,   -81,    94,   172,   172,   -81,   -81,
     121,   172,   -81,   217,   -81,   -81,   375,   135,   111,    81,
     362,   172,   -81,   -81,   115,   -81,   -81,    85,   387,   398,
     409,   409,   167,   167,   167,   167,    63,    63,   -81,   -81,
     -81,   -81,    87,   320,   -10,   172,   -81,   341,     2,   107,
     120,   -81,   135,   240,   -81,   135,   -81,   172,   122,   -81,
     -81,    85,   172,   375,    61,   172,   172,   -81,   -81,   375,
     -81,   -81,   -81,    33,   -81,   -81,   -81,   -81,   -81,    66,
     -81,   263,   -81,    55,    62,   -81,   -81,   -81,   -81,   -81,
     -81,   -81,   -81,   127,   172,   -81,   307,   -81,   172,    72,
     -81,   135,   -81
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       3,     0,    96,   101,    73,   109,     0,     0,     0,    85,
      86,    84,    70,    13,     0,    83,    81,    82,    44,     0,
       0,     0,     0,    61,     0,     0,     3,     0,    28,    14,
      36,    39,    47,    40,    41,    11,     0,    12,    43,     5,
       0,     6,     0,     7,     8,     0,     0,    72,    74,     0,
       0,     9,    10,    45,    69,    46,    30,    31,     0,    32,
       0,    34,     0,    59,     0,     0,    66,     0,     0,     1,
       2,     4,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    33,    35,     0,     0,     0,
      61,     0,    53,    55,    56,     0,     0,    61,    75,    78,
      91,     0,    99,     0,   102,   108,   110,    69,     0,     0,
       0,     0,    62,    63,     0,    64,    29,    42,    27,    26,
      24,    25,    20,    21,    22,    23,    15,    16,    17,    18,
      19,    48,     0,     0,     0,     0,    50,     0,     0,    90,
       0,    94,     0,     0,    95,     0,    93,    61,     0,    68,
      71,     0,     0,    60,    66,    61,    61,    49,    57,    38,
      51,    52,    87,     0,    77,    79,    92,    97,   100,     0,
     111,     0,    65,     0,     0,    88,    76,    80,    98,   103,
      67,    54,    58,     0,     0,    89,     0,   104,    61,     0,
     105,     0,   106
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
     -81,   129,   -38,   -19,   -81,   -81,   -81,   -81,    84,   -81,
      90,   -81,   -81,   -81,   -80,   -81,   -81,     6,    47,    56,
      22,   -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,   -21,
     -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,
     -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,   -81,
     -81,   -81
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,    25,    26,    27,    28,    29,    91,    30,    31,    32,
      33,    92,    93,    94,    64,    34,    65,   115,    66,   108,
      35,    54,    48,    36,    99,   139,   165,   140,   177,    37,
      38,   163,   183,    39,    40,   142,    41,    42,   102,   144,
     145,   178,    43,    46,   147,   184,   188,   191,    44,    49,
      50,   148
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int16 yytable[] =
{
      56,    57,   100,    68,    63,    67,   111,     4,   111,  -107,
     134,     8,     9,    10,    11,    45,   107,   138,    47,    14,
     111,    15,    16,    17,    18,    51,   103,    52,    87,    88,
      89,   106,    90,    53,   112,    19,     8,   109,   158,    20,
      21,    22,    55,   110,    14,    23,    69,    24,    98,    18,
     161,   175,   101,   118,   119,   120,   121,   122,   123,   124,
     125,   126,   127,   128,   129,   130,   104,   169,   105,   107,
     133,    63,    58,   111,     4,   173,   174,   137,    63,   114,
     111,   176,   143,   179,   111,     8,     9,    10,    11,    62,
     111,   113,   153,    14,   131,    15,    16,    17,    18,    82,
      83,    84,   117,   181,   166,    59,    61,   168,   189,    19,
     182,    60,    60,    20,    21,    22,   159,   132,   136,    23,
     190,    24,    95,   135,    96,   141,    97,   150,    63,   151,
      62,   162,   155,   171,   156,    12,    63,    63,     1,   170,
       2,     3,     4,     5,     6,     7,     8,     9,    10,    11,
      12,   185,    13,   192,    14,    70,    15,    16,    17,    18,
     172,   154,   164,   149,     0,   186,     0,     0,     0,    63,
      19,     0,     0,     0,    20,    21,    22,     0,     0,     0,
      23,     0,    24,     8,     9,    10,    11,     0,     0,     0,
       0,    14,     0,    15,    16,    17,    18,  -108,  -108,  -108,
    -108,    80,    81,    82,    83,    84,     0,    19,     0,     0,
       0,    20,    21,    22,     0,     0,     0,    23,   -37,    24,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    86,     0,    87,    88,    89,     0,
      90,     0,   116,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   146,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,   180,
       0,     0,     0,     0,     0,     0,     0,     0,   167,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    71,     0,     0,     0,     0,     0,     0,     0,
       0,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,   187,     0,     0,     0,     0,     0,
       0,     0,     0,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,     0,
       0,     0,     0,     0,     0,     0,   157,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
       0,     0,   152,     0,     0,     0,     0,   160,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      84,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,  -108,  -108,    76,
      77,    78,    79,    80,    81,    82,    83,    84
};

static const yytype_int16 yycheck[] =
{
      19,    20,    40,    24,    23,    24,    18,     7,    18,    17,
      90,    11,    12,    13,    14,    47,    54,    97,    24,    19,
      18,    21,    22,    23,    24,    17,    45,    17,    43,    44,
      45,    50,    47,    24,    46,    35,    11,    58,    48,    39,
      40,    41,    24,    62,    19,    45,     0,    47,    47,    24,
      48,    18,    47,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    47,   147,    17,   107,
      89,    90,    47,    18,     7,   155,   156,    96,    97,    18,
      18,    48,   101,    17,    18,    11,    12,    13,    14,    15,
      18,    46,   111,    19,    24,    21,    22,    23,    24,    36,
      37,    38,    48,    48,   142,    21,    22,   145,   188,    35,
      48,    21,    22,    39,    40,    41,   135,    24,    24,    45,
      48,    47,    43,    25,    45,     4,    47,    16,   147,    48,
      15,    24,    47,   152,    47,    15,   155,   156,     3,    17,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    24,    17,   191,    19,    26,    21,    22,    23,    24,
     154,   114,   140,   107,    -1,   184,    -1,    -1,    -1,   188,
      35,    -1,    -1,    -1,    39,    40,    41,    -1,    -1,    -1,
      45,    -1,    47,    11,    12,    13,    14,    -1,    -1,    -1,
      -1,    19,    -1,    21,    22,    23,    24,    30,    31,    32,
      33,    34,    35,    36,    37,    38,    -1,    35,    -1,    -1,
      -1,    39,    40,    41,    -1,    -1,    -1,    45,    25,    47,
      26,    27,    28,    29,    30,    31,    32,    33,    34,    35,
      36,    37,    38,    40,    41,    -1,    43,    44,    45,    -1,
      47,    -1,    48,    26,    27,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    38,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    48,    26,    27,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    38,    16,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    48,    26,
      27,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    38,    17,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    17,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    26,    27,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    38,    26,    27,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    38,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    46,    26,    27,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      -1,    -1,    20,    -1,    -1,    -1,    -1,    46,    26,    27,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      38,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    27,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    38,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    38
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,     3,     5,     6,     7,     8,     9,    10,    11,    12,
      13,    14,    15,    17,    19,    21,    22,    23,    24,    35,
      39,    40,    41,    45,    47,    50,    51,    52,    53,    54,
      56,    57,    58,    59,    64,    69,    72,    78,    79,    82,
      83,    85,    86,    91,    97,    47,    92,    24,    71,    98,
      99,    17,    17,    24,    70,    24,    52,    52,    47,    57,
      59,    57,    15,    52,    63,    65,    67,    52,    78,     0,
      50,    17,    26,    27,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    38,    40,    41,    43,    44,    45,
      47,    55,    60,    61,    62,    43,    45,    47,    47,    73,
      51,    47,    87,    52,    47,    17,    52,    51,    68,    78,
      52,    18,    46,    46,    18,    66,    48,    48,    52,    52,
      52,    52,    52,    52,    52,    52,    52,    52,    52,    52,
      52,    24,    24,    52,    63,    25,    24,    52,    63,    74,
      76,     4,    84,    52,    88,    89,    48,    93,   100,    68,
      16,    48,    20,    52,    67,    47,    47,    46,    48,    52,
      46,    48,    24,    80,    69,    75,    51,    48,    51,    63,
      17,    52,    66,    63,    63,    18,    48,    77,    90,    17,
      16,    48,    48,    81,    94,    24,    52,    17,    95,    63,
      48,    96,    51
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    49,    50,    50,    51,    51,    51,    51,    51,    51,
      51,    51,    51,    51,    52,    52,    52,    52,    52,    52,
      52,    52,    52,    52,    52,    52,    52,    52,    52,    53,
      53,    53,    53,    53,    53,    53,    53,    55,    54,    56,
      56,    56,    56,    56,    57,    57,    57,    57,    58,    58,
      58,    58,    59,    59,    59,    60,    60,    61,    62,    63,
      63,    63,    64,    64,    65,    66,    66,    67,    68,    68,
      70,    69,    71,    71,    72,    74,    73,    75,    76,    77,
      78,    79,    79,    79,    79,    79,    79,    80,    81,    80,
      80,    82,    82,    83,    84,    85,    86,    87,    88,    89,
      90,    92,    93,    94,    95,    96,    91,    98,    97,    99,
     100,    97
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     2,     0,     2,     1,     1,     1,     1,     2,
       2,     1,     1,     1,     1,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     1,     3,
       2,     2,     2,     2,     2,     2,     1,     0,     4,     1,
       1,     1,     3,     1,     1,     2,     2,     1,     3,     4,
       3,     4,     4,     2,     6,     1,     1,     3,     5,     1,
       3,     0,     3,     3,     2,     3,     0,     5,     2,     0,
       0,     4,     1,     0,     2,     0,     4,     1,     0,     0,
       5,     1,     1,     1,     1,     1,     1,     1,     0,     4,
       0,     2,     4,     4,     1,     3,     1,     3,     3,     0,
       0,     0,     0,     0,     0,     0,    14,     0,     3,     0,
       0,     5
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                  \
do                                                              \
  if (yychar == YYEMPTY)                                        \
    {                                                           \
      yychar = (Token);                                         \
      yylval = (Value);                                         \
      YYPOPSTACK (yylen);                                       \
      yystate = *yyssp;                                         \
      goto yybackup;                                            \
    }                                                           \
  else                                                          \
    {                                                           \
      yyerror (top, top_2, YY_("syntax error: cannot back up")); \
      YYERROR;                                                  \
    }                                                           \
while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value, top, top_2); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*----------------------------------------.
| Print this symbol's value on YYOUTPUT.  |
`----------------------------------------*/

static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep, void* top, void* top_2)
{
  FILE *yyo = yyoutput;
  YYUSE (yyo);
  YYUSE (top);
  YYUSE (top_2);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# endif
  YYUSE (yytype);
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep, void* top, void* top_2)
{
  YYFPRINTF (yyoutput, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep, top, top_2);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yytype_int16 *yyssp, YYSTYPE *yyvsp, int yyrule, void* top, void* top_2)
{
  unsigned long int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[yyssp[yyi + 1 - yynrhs]],
                       &(yyvsp[(yyi + 1) - (yynrhs)])
                                              , top, top_2);
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule, top, top_2); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
static YYSIZE_T
yystrlen (const char *yystr)
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            /* Fall through.  */
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYSIZE_T yysize1 = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (! (yysize <= yysize1
                         && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                    return 2;
                  yysize = yysize1;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    YYSIZE_T yysize1 = yysize + yystrlen (yyformat);
    if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
      return 2;
    yysize = yysize1;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep, void* top, void* top_2)
{
  YYUSE (yyvaluep);
  YYUSE (top);
  YYUSE (top_2);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void* top, void* top_2)
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        YYSTYPE *yyvs1 = yyvs;
        yytype_int16 *yyss1 = yyss;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * sizeof (*yyssp),
                    &yyvs1, yysize * sizeof (*yyvsp),
                    &yystacksize);

        yyss = yyss1;
        yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yytype_int16 *yyss1 = yyss;
        union yyalloc *yyptr =
          (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
                  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 3:
#line 92 "parser2.y" /* yacc.c:1646  */
    {}
#line 1520 "parser.c" /* yacc.c:1646  */
    break;

  case 4:
#line 95 "parser2.y" /* yacc.c:1646  */
    {
					resettemp();
					(yyval.int_lists) = make_int_list();
					}
#line 1529 "parser.c" /* yacc.c:1646  */
    break;

  case 5:
#line 99 "parser2.y" /* yacc.c:1646  */
    {resettemp();(yyval.int_lists) = (yyvsp[0].int_lists);}
#line 1535 "parser.c" /* yacc.c:1646  */
    break;

  case 6:
#line 100 "parser2.y" /* yacc.c:1646  */
    {resettemp();(yyval.int_lists) = make_int_list();}
#line 1541 "parser.c" /* yacc.c:1646  */
    break;

  case 7:
#line 101 "parser2.y" /* yacc.c:1646  */
    {resettemp();(yyval.int_lists) = make_int_list();}
#line 1547 "parser.c" /* yacc.c:1646  */
    break;

  case 8:
#line 102 "parser2.y" /* yacc.c:1646  */
    {
						resettemp();
						if(scopes==0){
							printf("Error: RETURN in scope 0\n");
						}
						(yyval.int_lists) = make_int_list();
					}
#line 1559 "parser.c" /* yacc.c:1646  */
    break;

  case 9:
#line 109 "parser2.y" /* yacc.c:1646  */
    {
					resettemp();
					if(scopes==0){
						printf("Error: BREAK in scope 0\n");
					}
					(yyval.int_lists) = make_int_list();

					qnode* temp;
					temp = malloc(sizeof(qnode));
					temp->next = NULL;
					temp->index = nextquadlabel();
					(yyval.int_lists)->break_list = temp;
					emit(_JUMP,NULL,NULL,NULL,-1,yylineno);
		}
#line 1578 "parser.c" /* yacc.c:1646  */
    break;

  case 10:
#line 123 "parser2.y" /* yacc.c:1646  */
    {
					resettemp();
					if(scopes==0){
						printf("Error: CONTINUE in scope 0\n");
					}
					(yyval.int_lists) = make_int_list();

					qnode* temp;
					temp = malloc(sizeof(qnode));
					temp->next = NULL;
					temp->index = nextquadlabel();
					(yyval.int_lists)->continue_list = temp;
					emit(_JUMP,NULL,NULL,NULL,-1,yylineno);
		}
#line 1597 "parser.c" /* yacc.c:1646  */
    break;

  case 11:
#line 137 "parser2.y" /* yacc.c:1646  */
    {resettemp();(yyval.int_lists) = (yyvsp[0].int_lists);}
#line 1603 "parser.c" /* yacc.c:1646  */
    break;

  case 12:
#line 138 "parser2.y" /* yacc.c:1646  */
    {resettemp();}
#line 1609 "parser.c" /* yacc.c:1646  */
    break;

  case 13:
#line 139 "parser2.y" /* yacc.c:1646  */
    {resettemp();}
#line 1615 "parser.c" /* yacc.c:1646  */
    break;

  case 14:
#line 142 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 1621 "parser.c" /* yacc.c:1646  */
    break;

  case 15:
#line 143 "parser2.y" /* yacc.c:1646  */
    {

				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
				   ((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
					printf("Error on line %u invalid use of expression in PLUS operator\n" , yylineno);
				}
				(yyval.expr) = new_expr(ARITHM_E);
				(yyval.expr)->sym = newtemp();
				emit(_ADD,(yyval.expr),(yyvsp[-2].expr),(yyvsp[0].expr),-1,yylineno);
				
		}
#line 1637 "parser.c" /* yacc.c:1646  */
    break;

  case 16:
#line 154 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in MINUS operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(ARITHM_E);
					(yyval.expr)->sym = newtemp();
					emit(_SUB,(yyval.expr),(yyvsp[-2].expr),(yyvsp[0].expr),-1,yylineno);
				}
					
		}
#line 1653 "parser.c" /* yacc.c:1646  */
    break;

  case 17:
#line 165 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in STAR operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(ARITHM_E);
					(yyval.expr)->sym = newtemp();
					emit(_MUL,(yyval.expr),(yyvsp[-2].expr),(yyvsp[0].expr),-1,yylineno);
				}

		}
#line 1669 "parser.c" /* yacc.c:1646  */
    break;

  case 18:
#line 176 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in DIVISION operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(ARITHM_E);
					(yyval.expr)->sym = newtemp();
					emit(_DIV,(yyval.expr),(yyvsp[-2].expr),(yyvsp[0].expr),-1,yylineno);
				}
		}
#line 1684 "parser.c" /* yacc.c:1646  */
    break;

  case 19:
#line 186 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in MODULO operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(ARITHM_E);
					(yyval.expr)->sym = newtemp();
					emit(_MOD,(yyval.expr),(yyvsp[-2].expr),(yyvsp[0].expr),-1,yylineno);
				}

		}
#line 1700 "parser.c" /* yacc.c:1646  */
    break;

  case 20:
#line 197 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in GREATER operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(BOOL_E);
					(yyval.expr)->sym = newtemp();
					emit(_IF_GREATER,NULL,(yyvsp[-2].expr),(yyvsp[0].expr),(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,(yyval.expr),new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,(yyval.expr),new_true_expr(),NULL,-1,yylineno);
				}

		}
#line 1719 "parser.c" /* yacc.c:1646  */
    break;

  case 21:
#line 211 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in GTOE operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(BOOL_E);
					(yyval.expr)->sym = newtemp();
					emit(_IF_GREATEREQ,NULL,(yyvsp[-2].expr),(yyvsp[0].expr),(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,(yyval.expr),new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,(yyval.expr),new_true_expr(),NULL,-1,yylineno);
				}

		}
#line 1738 "parser.c" /* yacc.c:1646  */
    break;

  case 22:
#line 225 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in LESS operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(BOOL_E);
					(yyval.expr)->sym = newtemp();
					emit(_IF_LESS, NULL,(yyvsp[-2].expr),(yyvsp[0].expr),(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,(yyval.expr),new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,(yyval.expr),new_true_expr(),NULL,-1,yylineno);
				}

		}
#line 1757 "parser.c" /* yacc.c:1646  */
    break;

  case 23:
#line 239 "parser2.y" /* yacc.c:1646  */
    {
				if(((yyvsp[-2].expr)->type == USERFUNC_E) || ((yyvsp[-2].expr)->type == LIBFUNC_E) || ((yyvsp[-2].expr)->type == BOOL_E) || ((yyvsp[-2].expr)->type == NEWTABLE_E) || ((yyvsp[-2].expr)->type == CONSTBOOL_E) || ((yyvsp[-2].expr)->type == CONSTSTRING_E) || ((yyvsp[-2].expr)->type == NIL_E) || 
			   		((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || (((yyvsp[0].expr)->type == NIL_E)) ){
				printf("Error on line %u invalid use of expression in LTOE operator\n" , yylineno);
				}else{
					(yyval.expr) = new_expr(BOOL_E);
					(yyval.expr)->sym = newtemp();
					emit(_IF_LESSEQ , NULL , (yyvsp[-2].expr) , (yyvsp[0].expr) ,(int) getNextQuad() + 3,yylineno);
					emit(_ASSIGN,(yyval.expr),new_false_expr(),NULL,-1,yylineno);
					emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
					emit(_ASSIGN,(yyval.expr),new_true_expr(),NULL,-1,yylineno);
				}

		}
#line 1776 "parser.c" /* yacc.c:1646  */
    break;

  case 24:
#line 253 "parser2.y" /* yacc.c:1646  */
    {

			(yyval.expr) = new_expr(BOOL_E);
			(yyval.expr)->sym = newtemp();
			emit(_IF_EQ , NULL , (yyvsp[-2].expr) , (yyvsp[0].expr) ,(int) getNextQuad() + 3,yylineno);
			emit(_ASSIGN,(yyval.expr),new_false_expr(),NULL,-1,yylineno);
			emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
			emit(_ASSIGN,(yyval.expr),new_true_expr(),NULL,-1,yylineno);

			//truelist kai falselist
			//me h xwris backpath?


		}
#line 1795 "parser.c" /* yacc.c:1646  */
    break;

  case 25:
#line 267 "parser2.y" /* yacc.c:1646  */
    {
			//truelist kai falselist
			(yyval.expr) = new_expr(BOOL_E);
			(yyval.expr)->sym = newtemp();
			emit(_IF_NOTEQ , NULL , (yyvsp[-2].expr) , (yyvsp[0].expr) ,(int) nextquadlabel() + 3,yylineno);
			emit(_ASSIGN,(yyval.expr),new_false_expr(),NULL,-1,yylineno);
			emit(_JUMP , NULL ,NULL , NULL , (int) getNextQuad()+2,yylineno);
			emit(_ASSIGN,(yyval.expr),new_true_expr(),NULL,-1,yylineno);		
		}
#line 1809 "parser.c" /* yacc.c:1646  */
    break;

  case 26:
#line 276 "parser2.y" /* yacc.c:1646  */
    {
			//truelist kai falselist
			if(((yyvsp[-2].expr)->type != BOOL_E)) {
				expr* temp = (yyvsp[-2].expr);
				expr* temp2 = (yyvsp[0].expr);
				printf("in outer if AND\n");
				if(((yyvsp[0].expr)->type != CONSTBOOL_E)) {
					printf("in inner if AND\n");
					temp2 = change_to_bool(temp2);
				}
				temp = change_to_bool(temp);
				(yyval.expr) = new_expr(BOOL_E);
				(yyval.expr)->sym = newtemp();
				emit(_AND , (yyval.expr) , temp , temp2 , -1 ,yylineno);

			}else{
				printf("in else AND\n");
				(yyval.expr) = new_expr(BOOL_E);
				(yyval.expr)->sym = newtemp();
				emit(_AND , (yyval.expr) , (yyvsp[-2].expr) , (yyvsp[0].expr) , -1 ,yylineno);
			}
			
			
		}
#line 1838 "parser.c" /* yacc.c:1646  */
    break;

  case 27:
#line 300 "parser2.y" /* yacc.c:1646  */
    {
			//truelist kai falselist

			if(((yyvsp[-2].expr)->type != BOOL_E)) {
				expr* temp = (yyvsp[-2].expr);
				expr* temp2 = (yyvsp[0].expr);
				printf("in outer if OR\n");
				if(((yyvsp[0].expr)->type != CONSTBOOL_E)) {
					printf("in inner if OR\n");
					temp2 = change_to_bool(temp2);
				}
				temp = change_to_bool(temp);
				(yyval.expr) = new_expr(BOOL_E);
				(yyval.expr)->sym = newtemp();
				emit(_OR , (yyval.expr) , temp , temp2 , -1 ,yylineno);

			}else{
				printf("in else OR\n");
				(yyval.expr) = new_expr(BOOL_E);
				(yyval.expr)->sym = newtemp();
				emit(_OR , (yyval.expr) , (yyvsp[-2].expr) , (yyvsp[0].expr) , -1 ,yylineno);
			}

			
		}
#line 1868 "parser.c" /* yacc.c:1646  */
    break;

  case 28:
#line 326 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 1874 "parser.c" /* yacc.c:1646  */
    break;

  case 29:
#line 329 "parser2.y" /* yacc.c:1646  */
    { 
			(yyval.expr) = (yyvsp[-1].expr); 
		}
#line 1882 "parser.c" /* yacc.c:1646  */
    break;

  case 30:
#line 332 "parser2.y" /* yacc.c:1646  */
    {
			if(((yyvsp[0].expr)->type == USERFUNC_E) || ((yyvsp[0].expr)->type == LIBFUNC_E) || ((yyvsp[0].expr)->type == BOOL_E) || ((yyvsp[0].expr)->type == NEWTABLE_E) || ((yyvsp[0].expr)->type == CONSTBOOL_E) || ((yyvsp[0].expr)->type == CONSTSTRING_E) || ((yyvsp[0].expr)->type == NIL_E)){
				printf("Error on line %u invalid use of expression in \"MINUS expr\"\n" , yylineno);
			}else{
			/*
				$$ = new_expr(ARITHM_E);
				$$->sym = newtemp();
				emit(_UMINUS,$$,$2,NULL,-1,yylineno);

			*/
			expr* e = new_expr_const_num(-1);
			(yyval.expr) = new_expr(ARITHM_E);
			(yyval.expr)->sym = newtemp();
			emit(_MUL,(yyval.expr),(yyvsp[0].expr),e,-1,yylineno);
			}
		}
#line 1903 "parser.c" /* yacc.c:1646  */
    break;

  case 31:
#line 348 "parser2.y" /* yacc.c:1646  */
    {
			//truelist kai falselist
			(yyval.expr) = new_expr(BOOL_E);
			(yyval.expr)->sym = newtemp();
			emit(_NOT,(yyval.expr),(yyvsp[0].expr),NULL,-1,yylineno);

		}
#line 1915 "parser.c" /* yacc.c:1646  */
    break;

  case 32:
#line 355 "parser2.y" /* yacc.c:1646  */
    {

			if(isfunc==1){
				printf("error on line %d! you can't change the value of a function\n",yylineno);
			}
			
			if(!(yyvsp[0].expr)){printf("error: in \"PLUS_PLUS lvalue\" empty lvalue\n");}
			else{
				if((yyvsp[0].expr)->type == TABLEITEM_E){
					(yyval.expr) = emit_iftableitem((yyvsp[0].expr));
					expr* temp = new_expr_const_num(1);
					emit(_ADD, (yyval.expr), (yyval.expr), temp, -1 , yylineno);
					emit(_TABLESETELEM,(yyval.expr), (yyvsp[0].expr), (yyvsp[0].expr)->index, -1, yylineno);

				}else{
					(yyval.expr) = new_expr_const_num(1);
					emit(_ADD,(yyvsp[0].expr),(yyvsp[0].expr),(yyval.expr),-1,yylineno);
					(yyval.expr) = new_expr(ARITHM_E);
					(yyval.expr)->sym = newtemp();
					emit(_ASSIGN,(yyval.expr),(yyvsp[0].expr),NULL,-1,yylineno);
				}
			}
		}
#line 1943 "parser.c" /* yacc.c:1646  */
    break;

  case 33:
#line 379 "parser2.y" /* yacc.c:1646  */
    {

			if(isfunc==1){
				printf("error on line %d! you can't change the value of a function\n",yylineno);
			}

			if(!(yyvsp[-1].expr)){printf("error in: \"lvalue PLUS_PLUS\" empty lvalue\n");}
			else{
			(yyval.expr) = new_expr(VAR_E);
			(yyval.expr) -> sym = newtemp();

			if((yyvsp[-1].expr) -> type == TABLEITEM_E){
				expr* value = emit_iftableitem((yyvsp[-1].expr));
				emit(_ASSIGN, (yyval.expr), value, NULL, -1, yylineno);
				expr* temp = new_expr_const_num(1);
				emit(_ADD, value, value, temp, -1, yylineno);
				emit(_TABLESETELEM, value, (yyvsp[-1].expr), (yyvsp[-1].expr) -> index, -1, yylineno);
			}else{
				emit(_ASSIGN,(yyval.expr),(yyvsp[-1].expr),NULL,-1,yylineno);
				(yyval.expr) = new_expr_const_num(1); 
				emit(_ADD,(yyvsp[-1].expr),(yyvsp[-1].expr),(yyval.expr),-1,yylineno);
			}
			}
			
		}
#line 1973 "parser.c" /* yacc.c:1646  */
    break;

  case 34:
#line 405 "parser2.y" /* yacc.c:1646  */
    {

			if(isfunc==1){
				printf("error on line %d! you can't change the value of a function\n",yylineno);
			}

			if(!(yyvsp[0].expr)){printf("error: in \"MINUS_MINUS lvalue\" empty lvalue\n");}
			else{

				if((yyvsp[0].expr)->type == TABLEITEM_E){
					(yyval.expr) = emit_iftableitem((yyvsp[0].expr));
					expr* temp = new_expr_const_num(1);
					emit(_SUB, (yyval.expr), (yyval.expr), temp, -1 , yylineno);
					emit(_TABLESETELEM,(yyval.expr), (yyvsp[0].expr), (yyvsp[0].expr)->index, -1, yylineno);
				}else{	
				(yyval.expr) = new_expr_const_num(1);
				emit(_SUB,(yyvsp[0].expr),(yyvsp[0].expr),(yyval.expr),-1,yylineno);
				(yyval.expr) = new_expr(ARITHM_E);
				(yyval.expr)->sym = newtemp();
				emit(_ASSIGN,(yyval.expr),(yyvsp[0].expr),NULL,-1,yylineno);
				}
			}

		}
#line 2002 "parser.c" /* yacc.c:1646  */
    break;

  case 35:
#line 430 "parser2.y" /* yacc.c:1646  */
    {

			if(isfunc==1){
					printf("error on line %d! you can't change the value of a function\n",yylineno);
			}
			
			if(!(yyvsp[-1].expr)){printf("error: in \"lvalue MINUS_MINUS\" empty lvalue\n");}
			else{
			(yyval.expr) = new_expr(VAR_E);
			(yyval.expr) -> sym = newtemp();

			if((yyvsp[-1].expr) -> type == TABLEITEM_E){
				expr* value = emit_iftableitem((yyvsp[-1].expr));
				emit(_ASSIGN, (yyval.expr), value, NULL, -1, yylineno);
				expr* temp = new_expr_const_num(1);
				emit(_SUB, value, value, temp, -1, yylineno);
				emit(_TABLESETELEM, value, (yyvsp[-1].expr), (yyvsp[-1].expr) -> index, -1, yylineno);
			}else{
				emit(_ASSIGN,(yyval.expr),(yyvsp[-1].expr),NULL,-1,yylineno);
				(yyval.expr) = new_expr_const_num(1); 
				emit(_SUB,(yyvsp[-1].expr),(yyvsp[-1].expr),(yyval.expr),-1,yylineno);
			}
			}

		}
#line 2032 "parser.c" /* yacc.c:1646  */
    break;

  case 36:
#line 455 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2038 "parser.c" /* yacc.c:1646  */
    break;

  case 37:
#line 458 "parser2.y" /* yacc.c:1646  */
    {
						if(isfunc==1){
							printf("error on line %d! you can't assign a function\n",yylineno);
						}
					}
#line 2048 "parser.c" /* yacc.c:1646  */
    break;

  case 38:
#line 462 "parser2.y" /* yacc.c:1646  */
    {

						if(!(yyvsp[-3].expr)){printf("error \n");}
						else{
							if((yyvsp[-3].expr)->type == TABLEITEM_E){
								emit(_TABLESETELEM, (yyvsp[0].expr), (yyvsp[-3].expr), (yyvsp[-3].expr)->index, -1, yylineno);
								(yyval.expr) = emit_iftableitem((yyvsp[-3].expr));
								(yyval.expr) -> type = ASSIGN_E;
							}
							else{
								emit(_ASSIGN,(yyvsp[-3].expr),(yyvsp[0].expr),NULL, -1,yylineno);
								(yyval.expr) = new_expr(ASSIGN_E);
								(yyval.expr)->sym = newtemp();
								emit(_ASSIGN,(yyval.expr),(yyvsp[-3].expr),NULL, -1,yylineno);
							}

						}

						
					}
#line 2073 "parser.c" /* yacc.c:1646  */
    break;

  case 39:
#line 484 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = emit_iftableitem((yyvsp[0].expr));}
#line 2079 "parser.c" /* yacc.c:1646  */
    break;

  case 40:
#line 485 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2085 "parser.c" /* yacc.c:1646  */
    break;

  case 41:
#line 486 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2091 "parser.c" /* yacc.c:1646  */
    break;

  case 42:
#line 488 "parser2.y" /* yacc.c:1646  */
    { 
					(yyval.expr) = new_expr(LIBFUNC_E);
					(yyval.expr)->sym = (yyvsp[-1].sym);
				}
#line 2100 "parser.c" /* yacc.c:1646  */
    break;

  case 43:
#line 492 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2106 "parser.c" /* yacc.c:1646  */
    break;

  case 44:
#line 495 "parser2.y" /* yacc.c:1646  */
    {
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
							(yyval.expr) = lvalue_expr(symbol);
						}else{
							SymbolTableEntry *symbol = 	InsertHas(1,GLOBAL,yylval.string,0,yylineno);
							symbol->space = currscopespace();
							symbol->offset = currscopeoffset();
							(yyval.expr) = lvalue_expr(symbol);
						}
						inccurrscopeoffset();
					}else{
						//yparxei kapou
						if(i == 0){
							//anaferomaste se kati global ( libfunc , userfunc, global var )
							(yyval.expr) = lvalue_expr(temp);
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
										(yyval.expr) = lvalue_expr(temp);

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
										(yyval.expr) = lvalue_expr(temp);
									}
								}else{
									//anaferomai se kati pou den einai active ara mporw na kanw insert
									if(scopes!=0){
										SymbolTableEntry *symbol = InsertHas(1,LOCAL,yylval.string,scopes,yylineno);
										symbol->space = currscopespace();
										symbol->offset = currscopeoffset();
										(yyval.expr) = lvalue_expr(symbol);
										inccurrscopeoffset();

									}else{
										SymbolTableEntry *symbol = InsertHas(1,GLOBAL,yylval.string,0,yylineno);
										symbol->space = currscopespace();
										symbol->offset = currscopeoffset();
										(yyval.expr) = lvalue_expr(symbol);
										inccurrscopeoffset();
									}
								}
							}
						}


					}
				}else{
					SymbolTableEntry* lb = NULL;
					lb = lookupInScope(0,yylval.string);
					(yyval.expr) = lvalue_expr(lb);
					//vrhka libfunc me to idio onoma
					//h ektypwsh tou la8ous ginetai sthn synarthsh checklibraryfunction
				}

			}
#line 2218 "parser.c" /* yacc.c:1646  */
    break;

  case 45:
#line 604 "parser2.y" /* yacc.c:1646  */
    { 
				//prepei na ginei to idio me to identifier
				int j = checkLibraryFunction(yylval.string);

				if(j==1 && (!lookupInScope(scopes,yylval.string))){
					if(scopes==0){
						SymbolTableEntry *symbol = InsertHas(1,GLOBAL,yylval.string,0,yylineno);
						symbol->space = currscopespace();
						symbol->offset = currscopeoffset();
						(yyval.expr) = lvalue_expr(symbol);
					}
					else{
						SymbolTableEntry *symbol = InsertHas(1,LOCAL,yylval.string,scopes,yylineno);
						symbol->space = currscopespace();
						symbol->offset = currscopeoffset();
						(yyval.expr) = lvalue_expr(symbol);
					}
					inccurrscopeoffset();
				}else{
					printf("already declared\n");
					(yyval.expr) = NULL;
				}
			}
#line 2246 "parser.c" /* yacc.c:1646  */
    break;

  case 46:
#line 628 "parser2.y" /* yacc.c:1646  */
    {
				if(Lookup(0, yylval.string)==0){
					printf("error! undeclared global symbol %s (line %d)\n", yylval.string, yylineno);
				}
				(yyval.expr) = NULL;

			}
#line 2258 "parser.c" /* yacc.c:1646  */
    break;

  case 47:
#line 637 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2264 "parser.c" /* yacc.c:1646  */
    break;

  case 48:
#line 641 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.expr) = member_item((yyvsp[-2].expr),(yyvsp[0].string));
			}
#line 2272 "parser.c" /* yacc.c:1646  */
    break;

  case 49:
#line 645 "parser2.y" /* yacc.c:1646  */
    {
				(yyvsp[-3].expr) = emit_iftableitem((yyvsp[-3].expr));
				(yyval.expr) = new_expr(TABLEITEM_E);
				(yyval.expr) -> sym = (yyvsp[-3].expr) -> sym;
				(yyval.expr) -> index = (yyvsp[-1].expr);

			}
#line 2284 "parser.c" /* yacc.c:1646  */
    break;

  case 50:
#line 653 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.expr) = member_item((yyvsp[-2].expr),(yyvsp[0].string));
			}
#line 2292 "parser.c" /* yacc.c:1646  */
    break;

  case 51:
#line 657 "parser2.y" /* yacc.c:1646  */
    {
				(yyvsp[-3].expr) = emit_iftableitem((yyvsp[-3].expr));
				(yyval.expr) = new_expr(TABLEITEM_E);
				(yyval.expr) -> sym = (yyvsp[-3].expr) -> sym;
				(yyval.expr) -> index = (yyvsp[-1].expr);
			}
#line 2303 "parser.c" /* yacc.c:1646  */
    break;

  case 52:
#line 666 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.expr) = make_call((yyvsp[-3].expr),(yyvsp[-1].expr));
			}
#line 2311 "parser.c" /* yacc.c:1646  */
    break;

  case 53:
#line 670 "parser2.y" /* yacc.c:1646  */
    {
				expr* temp;
				if((yyvsp[0].expr)->boolConst==1){
					expr* self = (yyvsp[-1].expr);
					(yyvsp[-1].expr) = emit_iftableitem(member_item(self,(yyvsp[0].expr)->strConst));
					
					temp = elist_add_beginning((yyvsp[0].expr)->next,self);
					(yyval.expr) = make_call((yyvsp[-1].expr),temp); 
				}
				else{
					(yyval.expr) = make_call((yyvsp[-1].expr),(yyvsp[0].expr)->next); 
				}
				

			}
#line 2331 "parser.c" /* yacc.c:1646  */
    break;

  case 54:
#line 686 "parser2.y" /* yacc.c:1646  */
    {
				expr* func = new_expr(LIBFUNC_E);
				func->sym = (yyvsp[-4].sym);
				(yyval.expr) = make_call(func,(yyvsp[-1].expr));
			}
#line 2341 "parser.c" /* yacc.c:1646  */
    break;

  case 55:
#line 693 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2347 "parser.c" /* yacc.c:1646  */
    break;

  case 56:
#line 694 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = (yyvsp[0].expr);}
#line 2353 "parser.c" /* yacc.c:1646  */
    break;

  case 57:
#line 698 "parser2.y" /* yacc.c:1646  */
    {
				expr* norm = new_expr(VAR_E);
				norm->next = (yyvsp[-1].expr);
				norm->boolConst = 0;
				norm->strConst = NULL;
				(yyval.expr) = norm;
			}
#line 2365 "parser.c" /* yacc.c:1646  */
    break;

  case 58:
#line 708 "parser2.y" /* yacc.c:1646  */
    {
				expr* method = new_expr(VAR_E);
				method->next = (yyvsp[-1].expr);
				method->boolConst = 1;
				method->strConst = (yyvsp[-3].string);
				(yyval.expr) = method;

			}
#line 2378 "parser.c" /* yacc.c:1646  */
    break;

  case 59:
#line 719 "parser2.y" /* yacc.c:1646  */
    {	
			expr* temp = (yyvsp[0].expr);
			temp->next=NULL;
			(yyval.expr) = temp;
		}
#line 2388 "parser.c" /* yacc.c:1646  */
    break;

  case 60:
#line 725 "parser2.y" /* yacc.c:1646  */
    {
				expr* temp = (yyvsp[0].expr);
				temp->next=NULL;
				(yyval.expr) = elist_add((yyvsp[-2].expr),temp);
		}
#line 2398 "parser.c" /* yacc.c:1646  */
    break;

  case 61:
#line 730 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = NULL;}
#line 2404 "parser.c" /* yacc.c:1646  */
    break;

  case 62:
#line 735 "parser2.y" /* yacc.c:1646  */
    {

				expr* t = new_expr(NEWTABLE_E);
				t -> sym = newtemp();
				emit(_TABLECREATE, t, NULL, NULL, -1, yylineno);
				int i = 0;
				expr* temp = (yyvsp[-1].expr);
				while(temp!=NULL){
					expr* temp2 = new_expr_const_num(i);
					emit(_TABLESETELEM,temp,t,temp2,-1,yylineno);
					temp = temp -> next;
					i++;
				}
				(yyval.expr) = t;

			}
#line 2425 "parser.c" /* yacc.c:1646  */
    break;

  case 63:
#line 752 "parser2.y" /* yacc.c:1646  */
    {
				expr* t = new_expr(NEWTABLE_E);
				t -> sym = newtemp();
				emit(_TABLECREATE, t, NULL, NULL, -1, yylineno);
				expr* temp = (yyvsp[-1].expr);

				while(temp!=NULL){
					emit(_TABLESETELEM,temp->index,t,temp,-1,yylineno);
					temp = temp -> next;
				}
				
				(yyval.expr) = t;

			}
#line 2444 "parser.c" /* yacc.c:1646  */
    break;

  case 64:
#line 769 "parser2.y" /* yacc.c:1646  */
    {	
				(yyval.expr) = (yyvsp[-1].expr); (yyval.expr)->next = (yyvsp[0].expr);
			}
#line 2452 "parser.c" /* yacc.c:1646  */
    break;

  case 65:
#line 775 "parser2.y" /* yacc.c:1646  */
    {
						(yyval.expr) = (yyvsp[-1].expr); (yyval.expr)->next = (yyvsp[0].expr);
					}
#line 2460 "parser.c" /* yacc.c:1646  */
    break;

  case 66:
#line 778 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = NULL;}
#line 2466 "parser.c" /* yacc.c:1646  */
    break;

  case 67:
#line 782 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.expr) = (yyvsp[-3].expr); (yyval.expr)->index = (yyvsp[-1].expr);	
			}
#line 2474 "parser.c" /* yacc.c:1646  */
    break;

  case 68:
#line 787 "parser2.y" /* yacc.c:1646  */
    {printf("block\n");
				
					(yyvsp[0].int_lists)->break_list = merge_int_lists((yyvsp[-1].int_lists)->break_list,(yyvsp[0].int_lists)->break_list);
					(yyvsp[0].int_lists)->continue_list = merge_int_lists((yyvsp[-1].int_lists)->continue_list,(yyvsp[0].int_lists)->continue_list);
					(yyval.int_lists) = (yyvsp[0].int_lists);
				}
#line 2485 "parser.c" /* yacc.c:1646  */
    break;

  case 69:
#line 793 "parser2.y" /* yacc.c:1646  */
    {printf("block empty rule \n"); (yyval.int_lists) = make_int_list();}
#line 2491 "parser.c" /* yacc.c:1646  */
    break;

  case 70:
#line 796 "parser2.y" /* yacc.c:1646  */
    {scopes++;}
#line 2497 "parser.c" /* yacc.c:1646  */
    break;

  case 71:
#line 796 "parser2.y" /* yacc.c:1646  */
    {

			(yyval.int_lists) = (yyvsp[-1].int_lists);

			lookupAndHide(scopes);
			scopes--;}
#line 2508 "parser.c" /* yacc.c:1646  */
    break;

  case 72:
#line 806 "parser2.y" /* yacc.c:1646  */
    {	(yyval.string) = (yyvsp[0].string); }
#line 2514 "parser.c" /* yacc.c:1646  */
    break;

  case 73:
#line 807 "parser2.y" /* yacc.c:1646  */
    {(yyval.string) = NULL;}
#line 2520 "parser.c" /* yacc.c:1646  */
    break;

  case 74:
#line 811 "parser2.y" /* yacc.c:1646  */
    {
				if((yyvsp[0].string)==NULL){
					
					//char newFuncName[20];
					//sprintf(newFuncName,"_f%u", functionNumber);
					char* gname = functionGenerateName();
                	(yyval.sym) = InsertHas(1,USERFUNC,gname,scopes,yylineno);
                	(yyval.sym)->space = currscopespace();
					(yyval.sym)->offset = currscopeoffset();
					inccurrscopeoffset();
				}else{
					int j = checkLibraryFunction((yyvsp[0].string));
					if(j==1 && (!lookupInScope(scopes,(yyvsp[0].string)))){
						(yyval.sym) = InsertHas(1,USERFUNC,(yyvsp[0].string),scopes,yylineno);
						(yyval.sym)->space = currscopespace();
						(yyval.sym)->offset = currscopeoffset();
						inccurrscopeoffset();
					}
					else if (j==1 && lookupInScope(scopes,(yyvsp[0].string))){
						if(!lookupIfFunc((yyvsp[0].string)) && (!lookupIfForm((yyvsp[0].string)))){
							(yyval.sym) = InsertHas(1,USERFUNC,(yyvsp[0].string),scopes,yylineno);
							(yyval.sym)->space = currscopespace();
							(yyval.sym)->offset = currscopeoffset();
							inccurrscopeoffset();
						}
						else{
							printf("Error on line %d\n",yylineno);
						}
					}
					else{
						printf("error with function %s on line%d\n", (yyvsp[0].string), yylineno);
					}
				}
				
				

				
				(yyval.sym)->value.funcVal->iaddress = nextquadlabel();
				expr* temp = lvalue_expr((yyval.sym));
				emit(_FUNCSTART, temp, NULL, NULL, nextquadlabel(), yylineno);
				
				//push sto stack twn synarthsewn pou einai energes  gia na kanw 3e-push meta ka8ws aytes kleinoun
		
				push_flocal_stack(functionLocalOffset,top_2);

				//enterscopespace();
				resetformalargoffset();
			}
#line 2573 "parser.c" /* yacc.c:1646  */
    break;

  case 75:
#line 862 "parser2.y" /* yacc.c:1646  */
    {scopes++; enterscopespace();}
#line 2579 "parser.c" /* yacc.c:1646  */
    break;

  case 76:
#line 864 "parser2.y" /* yacc.c:1646  */
    {
				scopes--; 
				enterscopespace(); 
				resetfunctionlocaloffset();

			}
#line 2590 "parser.c" /* yacc.c:1646  */
    break;

  case 77:
#line 873 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.int_lists) = (yyvsp[0].int_lists);
				exitscopespace();
				//prepei na apothikeusw kai sto funcbody ton arithmo ton locals!
			}
#line 2600 "parser.c" /* yacc.c:1646  */
    break;

  case 78:
#line 880 "parser2.y" /* yacc.c:1646  */
    {
		
		push_counter_stack(loopcounter,top);
		loopcounter = 1;
}
#line 2610 "parser.c" /* yacc.c:1646  */
    break;

  case 79:
#line 886 "parser2.y" /* yacc.c:1646  */
    {
		loopcounter = pop_counter_stack(top);
}
#line 2618 "parser.c" /* yacc.c:1646  */
    break;

  case 80:
#line 891 "parser2.y" /* yacc.c:1646  */
    {
				//totallocals in function
				(yyvsp[-4].sym)->value.funcVal->total_locals = functionLocalOffset;
				exitscopespace();
				

				//pop
				functionLocalOffset = pop_flocal_stack(top_2);
				(yyval.sym) = (yyvsp[-4].sym);

				expr* e = new_expr(LIBFUNC_E);
				e->sym = (yyval.sym);

				//expr* temp = lvalue_expr($$);
				emit(_FUNCEND,e, NULL, NULL,-1,yylineno);
			}
#line 2639 "parser.c" /* yacc.c:1646  */
    break;

  case 81:
#line 909 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = new_expr_const_num((yyvsp[0].int_const_num));}
#line 2645 "parser.c" /* yacc.c:1646  */
    break;

  case 82:
#line 910 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = new_expr_const_num((yyvsp[0].double_const_num));}
#line 2651 "parser.c" /* yacc.c:1646  */
    break;

  case 83:
#line 911 "parser2.y" /* yacc.c:1646  */
    {  (yyval.expr) = new_expr_const_string((yyvsp[0].string)); }
#line 2657 "parser.c" /* yacc.c:1646  */
    break;

  case 84:
#line 912 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = new_nil_expr();}
#line 2663 "parser.c" /* yacc.c:1646  */
    break;

  case 85:
#line 913 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = new_true_expr();}
#line 2669 "parser.c" /* yacc.c:1646  */
    break;

  case 86:
#line 914 "parser2.y" /* yacc.c:1646  */
    {(yyval.expr) = new_false_expr();}
#line 2675 "parser.c" /* yacc.c:1646  */
    break;

  case 87:
#line 917 "parser2.y" /* yacc.c:1646  */
    {printf("IDENTIFIER");
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
#line 2736 "parser.c" /* yacc.c:1646  */
    break;

  case 88:
#line 974 "parser2.y" /* yacc.c:1646  */
    {printf("COMMA ");}
#line 2742 "parser.c" /* yacc.c:1646  */
    break;

  case 89:
#line 974 "parser2.y" /* yacc.c:1646  */
    {printf("IDENTIFIER ");
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
#line 2802 "parser.c" /* yacc.c:1646  */
    break;

  case 90:
#line 1030 "parser2.y" /* yacc.c:1646  */
    {}
#line 2808 "parser.c" /* yacc.c:1646  */
    break;

  case 91:
#line 1034 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.int_lists) = make_int_list();
				(yyval.int_lists)->quad_for_else = getNextQuad();
				patchlabel((yyvsp[-1].int_const_num),(yyval.int_lists)->quad_for_else);
				(yyval.int_lists)->break_list = merge_int_lists((yyvsp[0].int_lists)->break_list,(yyval.int_lists)->break_list);
				(yyval.int_lists)->continue_list = merge_int_lists((yyvsp[0].int_lists)->continue_list,(yyval.int_lists)->continue_list);

			}
#line 2821 "parser.c" /* yacc.c:1646  */
    break;

  case 92:
#line 1043 "parser2.y" /* yacc.c:1646  */
    {
				patchlabel((yyvsp[-1].int_const_num),getNextQuad());
				(yyvsp[0].int_lists)->quad_for_else = (yyvsp[-1].int_const_num)+2;
				(yyval.int_lists) = (yyvsp[0].int_lists);
				patchlabel((yyvsp[-3].int_const_num),(yyvsp[0].int_lists)->quad_for_else);
				(yyval.int_lists)->break_list = merge_int_lists((yyvsp[-2].int_lists)->break_list,(yyval.int_lists)->break_list);
				(yyval.int_lists)->continue_list = merge_int_lists((yyvsp[-2].int_lists)->continue_list,(yyval.int_lists)->continue_list);
			}
#line 2834 "parser.c" /* yacc.c:1646  */
    break;

  case 93:
#line 1054 "parser2.y" /* yacc.c:1646  */
    {
			emit(_IF_EQ,NULL,(yyvsp[-1].expr),new_true_expr(),(long int) getNextQuad()+2,yylineno);
			(yyval.int_const_num) = nextquadlabel();
			emit(_JUMP,NULL,NULL,NULL,-1,yylineno);
		}
#line 2844 "parser.c" /* yacc.c:1646  */
    break;

  case 94:
#line 1062 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.int_const_num) = nextquadlabel();
				emit(_JUMP,NULL,NULL,NULL,-1,yylineno);

			}
#line 2854 "parser.c" /* yacc.c:1646  */
    break;

  case 95:
#line 1070 "parser2.y" /* yacc.c:1646  */
    {
				
				qnode* temp;
				qnode* temp2;
				emit(_JUMP, NULL, NULL, NULL, (yyvsp[-2].int_const_num) ,yylineno);
				patchlabel((yyvsp[-1].int_const_num),nextquadlabel());

				temp = (yyvsp[0].int_lists)->break_list;
				while(temp){
					patchlabel(temp->index,nextquadlabel());
					temp = temp->next;
				}

				temp2 = (yyvsp[0].int_lists)->continue_list;
				while(temp2){
					patchlabel(temp2->index,(yyvsp[-2].int_const_num));
					temp2 = temp2->next;
				}

			}
#line 2879 "parser.c" /* yacc.c:1646  */
    break;

  case 96:
#line 1092 "parser2.y" /* yacc.c:1646  */
    {
				(yyval.int_const_num) = nextquadlabel();
			}
#line 2887 "parser.c" /* yacc.c:1646  */
    break;

  case 97:
#line 1097 "parser2.y" /* yacc.c:1646  */
    {

				(yyvsp[-1].expr) = new_expr(BOOL_E);
				(yyvsp[-1].expr)->sym = newtemp();

				emit(_ASSIGN,(yyvsp[-1].expr),new_true_expr(),NULL,-1,yylineno);
				emit(_JUMP,NULL,NULL,NULL,nextquadlabel()+2,yylineno);
				emit(_ASSIGN,(yyvsp[-1].expr),new_false_expr(),NULL,-1, yylineno);

				emit(_IF_EQ,NULL,(yyvsp[-1].expr),new_true_expr(),nextquadlabel()+2,yylineno);
				(yyval.int_const_num) = nextquadlabel();
				emit(_JUMP,NULL,NULL,NULL,-1,yylineno);

			}
#line 2906 "parser.c" /* yacc.c:1646  */
    break;

  case 98:
#line 1113 "parser2.y" /* yacc.c:1646  */
    {(yyval.int_lists) = (yyvsp[-1].int_lists);}
#line 2912 "parser.c" /* yacc.c:1646  */
    break;

  case 99:
#line 1115 "parser2.y" /* yacc.c:1646  */
    {++loopcounter;}
#line 2918 "parser.c" /* yacc.c:1646  */
    break;

  case 100:
#line 1117 "parser2.y" /* yacc.c:1646  */
    {--loopcounter;}
#line 2924 "parser.c" /* yacc.c:1646  */
    break;

  case 101:
#line 1119 "parser2.y" /* yacc.c:1646  */
    {printf("FOR\n");}
#line 2930 "parser.c" /* yacc.c:1646  */
    break;

  case 102:
#line 1119 "parser2.y" /* yacc.c:1646  */
    {printf("LP\n");}
#line 2936 "parser.c" /* yacc.c:1646  */
    break;

  case 103:
#line 1119 "parser2.y" /* yacc.c:1646  */
    {printf("SEMICOLON\n");}
#line 2942 "parser.c" /* yacc.c:1646  */
    break;

  case 104:
#line 1119 "parser2.y" /* yacc.c:1646  */
    {printf("SEMICOLON\n");}
#line 2948 "parser.c" /* yacc.c:1646  */
    break;

  case 105:
#line 1119 "parser2.y" /* yacc.c:1646  */
    {printf("RP\n");}
#line 2954 "parser.c" /* yacc.c:1646  */
    break;

  case 107:
#line 1122 "parser2.y" /* yacc.c:1646  */
    {if(!isfunc) printf("Error: return cannot be called here , there is no function \n");}
#line 2960 "parser.c" /* yacc.c:1646  */
    break;

  case 108:
#line 1122 "parser2.y" /* yacc.c:1646  */
    {
				expr* temp3 = new_expr(NIL_E);
				emit(_RETURN,NULL,temp3,NULL,-1,yylineno);
			}
#line 2969 "parser.c" /* yacc.c:1646  */
    break;

  case 109:
#line 1126 "parser2.y" /* yacc.c:1646  */
    {if(!isfunc) printf("Error: return cannot be called here , there is no function \n");}
#line 2975 "parser.c" /* yacc.c:1646  */
    break;

  case 110:
#line 1126 "parser2.y" /* yacc.c:1646  */
    { 
				expr* temp4 = new_expr(BOOL_E);
				temp4->sym = newtemp();

				emit(_ASSIGN, temp4,new_true_expr(),NULL,-1,yylineno);
				emit(_JUMP,NULL,NULL,NULL,nextquadlabel()+2,yylineno);
				emit(_ASSIGN,temp4,new_false_expr(),NULL,-1,yylineno);

				(yyvsp[0].expr) = temp4;
				emit(_RETURN,NULL,(yyvsp[0].expr),NULL,-1,yylineno);

			}
#line 2992 "parser.c" /* yacc.c:1646  */
    break;


#line 2996 "parser.c" /* yacc.c:1646  */
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (top, top_2, YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (top, top_2, yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval, top, top_2);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp, top, top_2);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (top, top_2, YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval, top, top_2);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[*yyssp], yyvsp, top, top_2);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 1141 "parser2.y" /* yacc.c:1906  */


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
