package rs.ac.bg.etf.pp1;
import java_cup.runtime.Symbol;

%%

%{
	//ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type){
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	//ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value){
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	
%}



%cup
%line
%column

%xstate COMMENTSTATE

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " {}
"\b" {}
"\t" {}
"\r\n" {}
"\f" {}

"program" { return new_symbol(sym.PROG, yytext()); }
"break" { return new_symbol(sym.BREAK, yytext()); }
"class" { return new_symbol(sym.CLASS, yytext()); }
"if" { return new_symbol(sym.IF, yytext()); }
"else" { return new_symbol(sym.ELSE, yytext()); }
"const" { return new_symbol(sym.CONST, yytext()); }
"while" { return new_symbol(sym.WHILE, yytext()); }
"new" { return new_symbol(sym.NEW, yytext()); }
"print" { return new_symbol(sym.PRINT, yytext()); }
"read" { return new_symbol(sym.READ, yytext()); }
"return" { return new_symbol(sym.RETURN, yytext()); }
"void" { return new_symbol(sym.VOID, yytext()); }
"extends" { return new_symbol(sym.EXTENDS, yytext()); }
"continue" { return new_symbol(sym.CONTINUE, yytext()); }
"map" { return new_symbol(sym.MAP, yytext()); }
"static" { return new_symbol(sym.STATIC, yytext()); }

"//" {yybegin(COMMENTSTATE); }

<COMMENTSTATE> . {yybegin(COMMENTSTATE); }
<COMMENTSTATE> "\r\n" {yybegin(YYINITIAL); }


"*" { return new_symbol(sym.STAR, yytext()); }
"/" { return new_symbol(sym.SLASH, yytext()); }
"%" { return new_symbol(sym.PERCENT, yytext()); }
"=>" { return new_symbol(sym.ARROW, yytext()); }
"==" { return new_symbol(sym.EQUAL, yytext()); }
"!=" { return new_symbol(sym.NOTEQUAL, yytext()); }
">=" { return new_symbol(sym.GREATEROREQUAL, yytext()); }
"<=" { return new_symbol(sym.LESSOREQUAL, yytext()); }
">" { return new_symbol(sym.GREATER, yytext()); }
"<" { return new_symbol(sym.LESS, yytext()); }
"&&" { return new_symbol(sym.AND, yytext()); }
"||" { return new_symbol(sym.OR, yytext()); }
"=" { return new_symbol(sym.ASSIGNMENT, yytext()); }
"++" { return new_symbol(sym.INCREMENT, yytext()); }
"--" { return new_symbol(sym.DECREMENT, yytext()); }
";" { return new_symbol(sym.SEMICOLON, yytext()); }
":" { return new_symbol(sym.COLON, yytext()); }
"," { return new_symbol(sym.COMMA, yytext()); }
"." { return new_symbol(sym.DOT, yytext()); } //proveri za ovo da li sme ovako da tacka ne pokupi sve
"(" { return new_symbol(sym.LEFTPARENTHESIS, yytext()); }
")" { return new_symbol(sym.RIGHTPARENTHESIS, yytext()); }
"[" { return new_symbol(sym.LEFTSQUAREBRACKET, yytext()); }
"]" { return new_symbol(sym.RIGHTSQUAREBRACKET, yytext()); }
"{" { return new_symbol(sym.LEFTCURLYBRACKET, yytext()); }
"}" { return new_symbol(sym.RIGHTCURLYBRACKET, yytext()); }
"+" { return new_symbol(sym.PLUS, yytext()); }
"-" { return new_symbol(sym.MINUS, yytext()); }

'.' { return new_symbol(sym.CHAR, new Character(yytext().charAt(1))); }
//ipak ne treba string samo char \"([^\"]*)\" { return new_symbol(sym.CHAR, new Character(yytext().charAt(1))); }
"true"|"false" { return new_symbol (sym.BOOL, new Boolean(yytext())); } //proveriti za ovoo!!!
[0-9]+ { return new_symbol(sym.NUM, new Integer(yytext())); }
([a-zA-Z])[a-zA-Z0-9_]* { return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline + 1)); }




