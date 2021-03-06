
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.Compiler2;
import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;


%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	

%}

Character = [^\r\n\'\\]
Scalar = ["*"]

%cup
%line
%column

%xstate COMMENT
%xstate COMMENT2
%xstate CHAR

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext());}
"break"   	{ return new_symbol(sym.BREAK, yytext());}
"class"   	{ return new_symbol(sym.CLASS, yytext());}
"enum"   	{ return new_symbol(sym.ENUM, yytext());}
"else"   	{ return new_symbol(sym.ELSE, yytext());}
"const"   	{ return new_symbol(sym.CONST, yytext());}
"if"   		{ return new_symbol(sym.IF, yytext());}
"switch"   	{ return new_symbol(sym.SWITCH, yytext());}
"do"   		{ return new_symbol(sym.DO, yytext());}
"while"   	{ return new_symbol(sym.WHILE, yytext());}
"new"   	{ return new_symbol(sym.NEW, yytext());}
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read"   	{ return new_symbol(sym.READ, yytext());}
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"extends"   { return new_symbol(sym.EXTENDS, yytext());}
"continue"  { return new_symbol(sym.CONTINUE, yytext());}
"case"   	{ return new_symbol(sym.CASE, yytext());}
"true"		{ return new_symbol(sym.TRUE, yytext());}
"false"		{ return new_symbol(sym.FALSE, yytext());}

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"=="		{ return new_symbol(sym.ISEQUAL, yytext()); }
"!="		{ return new_symbol(sym.NOTEQUAL, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
">="		{ return new_symbol(sym.GREATER_OR_EQUAL, yytext()); }
"<"			{ return new_symbol(sym.LESS, yytext()); }
"<="		{ return new_symbol(sym.LESS_OR_EQUAL, yytext()); }
"&&"		{ return new_symbol(sym.DAND, yytext()); }
"||"		{ return new_symbol(sym.DOR, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"["			{ return new_symbol(sym.LBRACKET, yytext()); }
"]"			{ return new_symbol(sym.RBRACKET, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"?"			{ return new_symbol(sym.QUESTION_MARK, yytext()); }
":"			{ return new_symbol(sym.COLON, yytext()); }
"\"*\""     { return new_symbol(sym.SCALAR, yytext()); }
    

"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }


[0][0-9]+ { System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline+1) + ", koloni " + yycolumn + " : Ne moze 0 kao pocetna cifra");  Compiler2.addError( yyline+1, "("+yytext()+")"+"Ne moze 0 kao pocetna cifra" ,0);}
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([0-3])[a-z|A-Z|0-9|_]* { System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline+1) + ", koloni " + yycolumn + " : Neispravan string, mora poceti slovom"); Compiler2.addError( yyline+1, "("+yytext()+")"+"Neispravan string, mora poceti slovom" ,0);}
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

''                        { System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline+1) + ", koloni " + yycolumn + " : Mora da postoji jedan karakter u slucaju char literala"); Compiler2.addError( yyline+1, "("+yytext()+")"+"Mora da postoji jedan karakter u slucaju char literala" ,0);}
\'                        { yybegin(CHAR); }

<CHAR> {
	
	{Character}\'                	{ yybegin(YYINITIAL); return new_symbol( sym.CHARCONST, new Character( yytext().charAt(0) ) ); }
    
    {Character}+\'     { yybegin(YYINITIAL); System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline+1) + ", koloni " + yycolumn + " : Ne moze vise od jednog karaktera u slucaju char literala"); Compiler2.addError( yyline+1, "("+yytext()+")"+ "Ne moze vise od jednog karaktera u slucaju char literala" ,0);}
    \\.                                { yybegin(YYINITIAL); System.err.println("Leksicka greska na liniji " + (yyline+1) + ", koloni " + yycolumn + " : Pogresna escape sekvenca \"" +yytext()+"\"" );Compiler2.addError( yyline+1, "("+yytext()+")"+ "Pogresna Escape sekvenca" ,0); }
    "\r\n"                { yybegin(YYINITIAL); System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline+1) + ", koloni " + yycolumn + " : Ne moze vise od jednog karaktera u slucaju char literala");  Compiler2.addError( yyline+1, "("+yytext()+")"+ "Ne moze vise od jednog karaktera u slucaju char literala" ,0);}
}

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+", i koloni "+(yycolumn) ); Compiler2.addError( yyline+1, "("+yytext()+")"+ "Nije prepoznat simbol" ,0); }









