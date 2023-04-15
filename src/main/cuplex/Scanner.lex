package generated.fr.ul.miage.expression;
import java_cup.runtime.Symbol;
import fr.ul.miage.arbre.Noeud;
import fr.ul.miage.expression.Sym;

%%
/* options */
%line
%public
%cupsym Sym
%cup

%{
	void erreur(){
		System.out.println("Caractère inattendu");
		System.exit(1);
	}
%}
/* macros */
SEP     =   [ \t]
NUM     =   [0-9]+
FIN     =   \r|\n|\r\n

%%
/* règles */
"+"		{ return new Symbol(Sym.ADD);}
"*"		{ return new Symbol(Sym.MUL);}
"("		{ return new Symbol(Sym.PO);}
")"		{ return new Symbol(Sym.PF);}
{NUM}	{ return new Symbol(Sym.NUM);}
{SEP}	{;}
{FIN}	{return new Symbol(Sym.EOF);}
.		{erreur();}
