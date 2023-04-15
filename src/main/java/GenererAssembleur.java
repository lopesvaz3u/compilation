import fr.ul.miage.arbre.*;

import java.util.Arrays;

/**
 * Classe permettant de générer le code assembleur
 */
public class GenererAssembleur {

    // table des symboles associee a l'arbre
    private TableDesSymboles tableDesSymboles;
    // code assembleur genere
    private String code = "| Code genere par le compilateur \n";

    public GenererAssembleur(Prog arbre, TableDesSymboles tableDesSymboles) {
        this.tableDesSymboles = tableDesSymboles;
        genererProgramme(arbre);
    }

    /**
     * @param p : programme général de l'arbre abstrait
     * genère le code assembleur du programme
     */
    public void genererProgramme(Prog p) {
        this.code += ".include beta.uasm\n" +
                ".include intio.uasm\n" +
                ".options tty\n" +
                "\n" +
                "    CMOVE(pile,SP)\n" +
                "    BR(debut)\n";
            for (Symbole s : this.tableDesSymboles.getSymboles()) {
                if (s.getCategorie().equals("global")) {
                    if (s.getValeur().equals("")) {
                        this.code += s.getNom() + ": LONG(0)\n";
                    }else{
                        this.code += s.getNom() + ": LONG(" + s.getValeur() + ")\n";
                    }
                }
            }

        for (Noeud n : p.getFils()) {
            String[] nom = n.getLabel().split("/");
            if (n instanceof Fonction && nom[1].equals("main")) {
                this.genererFonction((Fonction) n);
                this.code += "\ndebut:\n";
                this.code += "CALL(" + nom[1] + ")\n";
            } else if (n instanceof Fonction) {
                this.genererFonction((Fonction) n);
            } else {
                this.genererInstruction(n);
            }
        }
        this.code += "HALT()\n" + "pile:";
    }

    /**
     * @param f : fonction de l'arbre abstrait
     * genère le code assembleur de la fonction
     */
    public void genererFonction(Fonction f) {
        String[] nomFonction = f.getLabel().split("/");
        this.code += "\n"+ nomFonction[1] + ":\n";
        this.code += "PUSH (LP)\n" + "PUSH (BP)\n" + "MOVE (SP, BP)\n";
        Symbole fonction = this.tableDesSymboles.getSymbole(f.getValeur().toString());
        this.code += "ALLOCATE (" + fonction.getNbBloc() + ")\n";
        for (Noeud n : f.getFils()) {
            genererInstruction(n);
        }
        this.code += "DEALLOCATE (" + fonction.getNbBloc() + ")\n" + "POP (BP)\n" + "POP (LP)\n";
        this.code += "RTN()\n";
//        if (!f.getValeur().toString().equals("main")) {
//            this.code +=  "RTN()\n";
//        }
    }

    /**
     * @param n : noeud de l'arbre abstrait
     * appel une generation d'instruction en fonction du type de noeud
     */
    public void genererInstruction(Noeud n) {
        if (n instanceof Affectation) {
            this.genererAffectation((Affectation) n);
        } else if (n instanceof Bloc) {
            this.genererBloc((Bloc) n);
        } else if (n instanceof Ecrire) {
            this.genererEcrire((Ecrire) n);
        } else if (n instanceof Retour) {
            this.genererRetour((Retour) n);
        } else if (n instanceof TantQue) {
            this.genererTantQue((TantQue) n);
        } else if (n instanceof Si) {
            this.genererSi((Si) n);
        }else if (n instanceof Appel) {
            this.genererAppel((Appel) n);
        } else {
            this.genererExpressionBool(n);
        }
    }

    /**
     * @param n : Noeud de l'arbre abstrait
     * appel une méthode de génération d'une condition en fonction du type de noeud
     */
    public void genererExpressionBool(Noeud n){
        if (n instanceof Inferieur) {
            this.genererInferieur((Inferieur) n);
        } else if (n instanceof Superieur) {
            this.genererSuperieur((Superieur) n);
        } else if (n instanceof Egal) {
            this.genererEgal((Egal) n);
        } else if (n instanceof Different) {
            this.genererDifferent((Different) n);
        } else if (n instanceof InferieurEgal) {
            this.genererInferieurEgal((InferieurEgal) n);
        } else if (n instanceof SuperieurEgal) {
            this.genererSuperieurEgal((SuperieurEgal) n);
        }
    }

    /**
     * @param n : Noeud de l'arbre abstrait
     * appel une méthode de génération d'expression en fonction du type de noeud
     */
    public void genererExpression(Noeud n) {
        if (n instanceof Const) {
            this.genererConst((Const) n);
        } else if (n instanceof Idf) {
            this.genererIdf((Idf) n);
        } else if (n instanceof Plus) {
            this.genererAddition((Plus) n);
        } else if (n instanceof Moins) {
            this.genererSoustraction((Moins) n);
        } else if (n instanceof Multiplication) {
            this.genererMultiplication((Multiplication) n);
        } else if (n instanceof Division) {
            this.genererDivision((Division) n);
        } else if (n instanceof Lire){
            this.genererLecture();
        }
    }

    /**
     * @param b : bloc de l'arbre abstrait
     * genère le code assembleur de l'expression du bloc
     */
    public void genererBloc(Bloc b) {
        for (Noeud n : b.getFils()) {
            genererInstruction(n);
        }
    }

    /**
     * @param c : constante de l'arbre abstrait
     * genère le code assembleur de la constante
     */
    public void genererConst(Const c) {

        this.code += "CMOVE(" + c.getValeur() + ", r0)\n" + "PUSH (r0)\n";
    }

    /**
     * @param i : idf de l'arbre abstrait
     * genère le code assembleur de l'idf
     */
    public void genererIdf(Idf i) {
        if (this.tableDesSymboles.getSymbole(i.getValeur().toString(), "global") != null) {
            this.code += "LD(" + i.getValeur() + ", r0)\n" + "PUSH (r0)\n";
        } else {
            if (this.tableDesSymboles.getSymbole(i.getValeur().toString(), "f") != null || this.tableDesSymboles.getSymbole(i.getValeur().toString(), "param") != null) {
                this.code += "GETFRAME(r0, " + this.genererOffSet(this.tableDesSymboles.getSymbole(i.getValeur().toString(), "f")) + ")\n" + "PUSH (r0)\n";
            }
        }
    }

    /**
     * @param a : affectation de l'arbre abstrait
     * genère le code assembleur d'une affectation
     */
    public void genererAffectation(Affectation a) {
        genererExpression(a.getFilsDroit());
        String[] nom = a.getFilsGauche().getLabel().split("/");
        this.code += "POP (r0)\n";
        if (this.tableDesSymboles.getSymbole(nom[1], "global") != null) {
            this.code += "ST(r0, " + nom[1] + ")\n";
        } else {
            if (this.tableDesSymboles.getSymbole(nom[1], "f") != null) {
                this.code += "PUTFRAME(r0, " + this.genererOffSet(this.tableDesSymboles.getSymbole(nom[1], "f")) + ")\n";
            }
        }
        if (this.tableDesSymboles.getSymbole(a.getFilsGauche().getLabel(), "f" ,"param") != null) {
            this.code += "PUTFRAME(r0, " + this.genererOffSet(this.tableDesSymboles.getSymbole(nom[1], "f" , "param")) + ")\n";
        }
    }

    /**
     * genère le code assembleur de la lecture
     */
    public void genererLecture() {
        this.code += "RDINT()\n" + "PUSH (r0)\n";
    }

    /**
     * @param e : noeud ecrire de l'arbre abstrait
     * genère le code assembleur pour l'écrire
     */
    public void genererEcrire(Ecrire e) {
        genererExpression(e.getLeFils());
        this.code += "POP (r0)\n" + "WRINT()\n";
    }

    /**
     * @param a : noeud appel de l'arbre abstrait
     * genère le code assembleur pour l'appel
     */
    public void genererAppel(Appel a) {
        int p = 0;
        String[] nom = a.getLabel().split("/");
        if (this.tableDesSymboles.getSymbole(nom[1]).getNom() != null){
            p = 1;
        }
        for (Noeud n : a.getFils()) {
            genererExpression(n);
        }
        this.code += "CALL(" + nom[1] + ")\n";
        this.code += "DEALLOCATE(" + p + ")\n";
    }

    /**
     * @param r : noeud retour de l'arbre abstrait
     * genère le code assembleur pour le retour
     */
    public void genererRetour(Retour r) {
        genererExpression(r.getLeFils());
        String[] nom = r.getLabel().split("/");
        if (this.tableDesSymboles.getSymbole(nom[1]).getScope()!= null){
            this.code += "POP (r0)\n" + "PUTFRAME(r0,"+ this.genererOffSet(this.tableDesSymboles.getSymbole(nom[1], "f")) +")\n" + "BR(" + nom[1] + ")\n";
        } else {
            this.code += "POP (r0)\n" + "PUTFRAME(r0,"+ this.genererOffSet(this.tableDesSymboles.getSymbole(nom[1])) +")\n" + "BR(" + nom[1] + ")\n";
        }
    }

    /**
     * @param s : noeud si de l'arbre abstrait
     * genère le code assembleur pour le si
     */
    public void genererSi(Si s) {
        String[] nom = s.getLabel().split("/");

        this.code += "si_"+nom[1]+":\n";
        genererExpressionBool(s.getCondition());
        this.code += "POP (r0)\n" + "BF(R0, sinon_"+nom[1]+")\n";
        this.code += "alors_"+nom[1]+":\n";
        genererBloc(s.getBlocAlors());
        this.code += "BR(fsi_"+nom[1]+")\n";
        this.code += "sinon_"+nom[1]+":\n";
        genererBloc(s.getBlocSinon());
        this.code += "fsi_"+nom[1]+":\n";
    }

    /**
     * @param tq : noeud tant que de l'arbre abstrait
     * genère le code assembleur pour le tant que
     */
    public void genererTantQue(TantQue tq) {
        String[] nom = tq.getLabel().split("/");
        this.code += "tantque_"+nom[1]+":\n";
        genererExpressionBool(tq.getFils().get(0));
        this.code += "POP (R0)\n" + "BF(R0, ftantque_"+nom[1]+")\n";
        genererBloc((Bloc) tq.getFils().get(1));
        this.code += "BR(tantque_"+nom[1]+")\n";
        this.code += "ftantque_"+nom[1]+":\n";

    }

    /**
     * @param a : noeud addition de l'arbre abstrait
     * genère le code assembleur pour l'addition
     */
    public void genererAddition(Plus a) {
        genererExpression(a.getFilsGauche());
        genererExpression(a.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "ADD (r1, r2, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param m : noeud soustraction de l'arbre abstrait
     * genère le code assembleur pour la soustraction
     */
    public void genererSoustraction(Moins m) {
        genererExpression(m.getFilsGauche());
        genererExpression(m.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "SUB (r1, r2, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param m : noeud multiplication de l'arbre abstrait
     * genère le code assembleur pour la multiplication
     */
    public void genererMultiplication(Multiplication m) {
        genererExpression(m.getFilsGauche());
        genererExpression(m.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "MUL (r1, r2, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param d : noeud division de l'arbre abstrait
     * genère le code assembleur pour la division
     */
    public void genererDivision(Division d) {
        genererExpression(d.getFilsGauche());
        genererExpression(d.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "DIV (r1, r2, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param item : symbole de la table des symboles
     * @return l'offset du symbole en fonction de son scope
     */
    public int genererOffSet(Symbole item) {
        if (item.getScope() != null){
            if (item.getScope().equals("local") || item.getScope().equals("f")) {
                return 1+item.getRang()*4;
            }
            if (item.getScope().equals("param")){
                return (-1-item.getNbParam()+ item.getRang())*4;
            }
        }
        return 0;
    }

    /**
     * @param i : noeud inferieur de l'arbre abstrait
     * genère le code assembleur pour l'inferieur
     */
    public void genererInferieur(Inferieur i) {
        genererExpression(i.getFilsGauche());
        genererExpression(i.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "CMPLT (r2, r1, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param s : noeud superieur de l'arbre abstrait
     * genère le code assembleur pour le supérieur
     */
    public void genererSuperieur(Superieur s) {
        genererExpression(s.getFilsGauche());
        genererExpression(s.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "CMPLT (r1, r2, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param e : noeud egal de l'arbre abstrait
     * genère le code assembleur pour l'égalité
     */
    public void genererEgal(Egal e) {
        genererExpression(e.getFilsGauche());
        genererExpression(e.getFilsDroit());
        this.code += "POP (r2)\n" + "POP (r1)\n" + "CMPEQ (r1, r2, r3)\n" + "PUSH (r3)\n";
    }

    /**
     * @param n : noeud superieur ou égal de l'arbre abstrait
     * genère le code assembleur pour sur superieur ou egal
     */
    private void genererSuperieurEgal(SuperieurEgal n) {
        genererExpression(n.getFilsGauche());
        genererExpression(n.getFilsDroit());
        this.code += "POP (r0)\n" + "POP (r1)\n" + "CMPLE (r1, r0)\n" + "JLE (r1, r0, " + n.getLabel() + ")\n";
    }

    /**
     * @param n : noeud inferieur ou égal de l'arbre abstrait
     * genère le code assembleur pour sur inferieur ou egal
     */
    private void genererInferieurEgal(InferieurEgal n) {
        genererExpression(n.getFilsGauche());
        genererExpression(n.getFilsDroit());
        this.code += "POP (r0)\n" + "POP (r1)\n" + "CMPLE (r0, r1)\n" + "JLE (r0, r1, " + n.getLabel() + ")\n";
    }

    /**
     * @param n : noeud différent de l'arbre abstrait
     * genère le code assembleur pour sur différent
     */
    private void genererDifferent(Different n) {
        genererExpression(n.getFilsGauche());
        genererExpression(n.getFilsDroit());
        this.code += "POP (r0)\n" + "POP (r1)\n" + "CMPEQ (r0, r1)\n" + "JNE (r0, r1, " + n.getLabel() + ")\n";
    }

    /**
     * @return le code assembleur généré
     */
    public String getCode(){
        return this.code;
    }
}
