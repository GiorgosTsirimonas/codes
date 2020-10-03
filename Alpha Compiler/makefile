all:
	bison --yacc -v --defines --output=parser.c parser2.y
	flex --outfile=scanner.c ex.l
	gcc scanner.c parser.c functions.c simpleFunc.c ph4.c -o out
clean:
	rm out
	rm parser.c
	rm parser.h
	rm parser.output
	rm scanner.c
