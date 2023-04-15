
import fr.ul.miage.arbre.*;

import java.sql.SQLOutput;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        exemple1();
        exemple2();
        exemple3();
        exemple4();
        exemple5();
        exemple6();
        exemple7();
        exemple8();
    }
    public static void exemple1(){
        // test #1 --------------------------------
        System.out.println("Test #1");
        //TDS
        TableDesSymboles tds1 = new TableDesSymboles();
        // Symboles
        Symbole main = new Symbole("main", "void", 0, 0);
        tds1.ajouterSymbole(main);
        tds1.afficher();
        // Arbre abstrait
        Prog prog1 = new Prog();
        Noeud main1 = new Fonction("main");
        prog1.ajouterUnFils(main1);
        TxtAfficheur.afficher(prog1);
        // Generation du code
        GenererAssembleur genAss = new GenererAssembleur(prog1, tds1);
        GenerateurFichier genFichier = new GenerateurFichier(genAss.getCode(), "exemple1");
        genFichier.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple2(){
        // test #2 --------------------------------
        System.out.println("Test #2");
        //TDS
        TableDesSymboles tds2 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain2 = new Symbole("main", "void", 0, 0);
        Symbole idf1 = new Symbole("i", "int", "10", 0, "global", "global");
        Symbole idf2 = new Symbole("j", "int", "20", 1, "global", "global");
        Symbole idf3 = new Symbole("k", "int", "", 2, "global", "global");
        Symbole idf4 = new Symbole("l", "int", "", 3, "global", "global");
        tds2.ajouterSymbole(idf1);
        tds2.ajouterSymbole(idf2);
        tds2.ajouterSymbole(idf3);
        tds2.ajouterSymbole(idf4);
        tds2.ajouterSymbole(symboleMain2);
        tds2.afficher();
        // Arbre abstrait
        Prog prog2 = new Prog();
        Noeud main2 = new Fonction("main");
        prog2.ajouterUnFils(main2);
        TxtAfficheur.afficher(prog2);
        // Generation du code
        GenererAssembleur genAss2 = new GenererAssembleur(prog2, tds2);
        GenerateurFichier genFichier2 = new GenerateurFichier(genAss2.getCode(), "exemple2");
        genFichier2.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple3(){
        // test #3 --------------------------------
        System.out.println("Test #3");
        //TDS
        TableDesSymboles tds3 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain3 = new Symbole("main", "void", 0, 0);
        Symbole idf5 = new Symbole("i", "int", "10", 0, "global", "global");
        Symbole idf6 = new Symbole("j", "int", "20", 1, "global", "global");
        Symbole idf7 = new Symbole("k", "int", "", 2, "global", "global");
        Symbole idf8 = new Symbole("l", "int", "", 3, "global", "global");
        tds3.ajouterSymbole(idf5);
        tds3.ajouterSymbole(idf6);
        tds3.ajouterSymbole(idf7);
        tds3.ajouterSymbole(idf8);
        tds3.ajouterSymbole(symboleMain3);
        tds3.afficher();

        // Arbre abstrait
        Noeud f3 = new Fonction("main");
        Prog prog3 = new Prog();

        Const c1 = new Const(2);
        Idf i1 = new Idf("k");
        Affectation a1 = new Affectation();
        a1.setFilsGauche(i1);
        a1.setFilsDroit(c1);
        f3.ajouterUnFils(a1);

        Idf i2 = new Idf("j");
        Const c2 = new Const(3);
        Multiplication m1 = new Multiplication();
        m1.setFilsGauche(i2);
        m1.setFilsDroit(c2);

        Idf i3 = new Idf("i");
        Plus p1 = new Plus();
        p1.setFilsGauche(i3);
        p1.setFilsDroit(m1);

        Idf i4 = new Idf("l");
        Affectation a2 = new Affectation();
        a2.setFilsGauche(i4);
        a2.setFilsDroit(p1);
        f3.ajouterUnFils(a2);

        prog3.ajouterUnFils(f3);
        TxtAfficheur.afficher(prog3);
        // Generation du code
        GenererAssembleur genAss3 = new GenererAssembleur(prog3, tds3);
        GenerateurFichier genFichier3 = new GenerateurFichier(genAss3.getCode(), "exemple3");
        genFichier3.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple4(){
        // test #4 --------------------------------
        System.out.println("Test #4");
        //TDS
        TableDesSymboles tds4 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain4 = new Symbole("main", "void", 0, 0);
        Symbole idf9 = new Symbole("i", "int", "", 0, "global", "global");
        Symbole idf10 = new Symbole("j", "int", "20", 1, "global", "global");
        tds4.ajouterSymbole(idf9);
        tds4.ajouterSymbole(idf10);
        tds4.ajouterSymbole(symboleMain4);
        tds4.afficher();

        // Arbre abstrait
        Idf idff = new Idf("i");
        Lire lire4 = new Lire();
        Affectation aff4 = new Affectation();
        aff4.setFilsGauche(idff);
        aff4.setFilsDroit(lire4);

        Idf idfff = new Idf("j");
        Plus plus4 = new Plus();
        plus4.setFilsGauche(idff);
        plus4.setFilsDroit(idfff);
        Ecrire ecrire4 = new Ecrire();
        ecrire4.setLeFils(plus4);

        Noeud f4 = new Fonction("main");
        Prog prog4 = new Prog();
        prog4.ajouterUnFils(f4);
        f4.ajouterUnFils(aff4);
        f4.ajouterUnFils(ecrire4);

        TxtAfficheur.afficher(prog4);

        // Generation du code
        GenererAssembleur genAss4 = new GenererAssembleur(prog4, tds4);
        GenerateurFichier genFichier4 = new GenerateurFichier(genAss4.getCode(), "exemple4");
        genFichier4.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple5(){
        // test #5 --------------------------------
        System.out.println("Test #5");
        //TDS
        TableDesSymboles tds5 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain5 = new Symbole("main", "void", 0, 0);
        Symbole idf12 = new Symbole("i", "int", "", 0, "global", "global");
        tds5.ajouterSymbole(idf12);
        tds5.ajouterSymbole(symboleMain5);
        tds5.afficher();

        // Arbre abstrait
        Idf idf14 = new Idf("i");
        Lire lire = new Lire();
        Affectation aff = new Affectation();
        aff.setFilsGauche(idf14);
        aff.setFilsDroit(lire);
        //----------------------
        Const const1 = new Const(10);
        Superieur sup1 = new Superieur();
        sup1.setFilsGauche(idf14);
        sup1.setFilsDroit(const1);
        //----------------------
        Const c3 = new Const(1);
        Const c4 = new Const(2);
        Ecrire ecrire = new Ecrire();
        ecrire.setLeFils(c3);
        Ecrire ecrire2 = new Ecrire();
        ecrire2.setLeFils(c4);
        Bloc bloc1 = new Bloc();
        bloc1.ajouterUnFils(ecrire);
        Bloc bloc2 = new Bloc();
        bloc2.ajouterUnFils(ecrire2);
        //----------------------
        Si si1 = new Si(1);
        si1.setCondition(sup1);
        si1.setBlocAlors(bloc1);
        si1.setBlocSinon(bloc2);
        //----------------------
        Noeud f5 = new Fonction("main");
        Prog prog5 = new Prog();
        prog5.ajouterUnFils(f5);
        f5.ajouterUnFils(aff);
        f5.ajouterUnFils(si1);
        TxtAfficheur.afficher(prog5);

        // Generation du code
        GenererAssembleur genAss5 = new GenererAssembleur(prog5, tds5);
        GenerateurFichier genFichier5 = new GenerateurFichier(genAss5.getCode(), "exemple5");
        genFichier5.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple6(){
        // test #6 --------------------------------
        System.out.println("Test #6");
        //TDS
        TableDesSymboles tds6 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain6 = new Symbole("main", "void", 0, 0);
        Symbole idf15 = new Symbole("i", "int", "", 0, "global", "global");
        Symbole idf16 = new Symbole("h", "int", "5", 1, "global", "global");
        tds6.ajouterSymbole(idf15);
        tds6.ajouterSymbole(idf16);
        tds6.ajouterSymbole(symboleMain6);
        tds6.afficher();

        // Arbre abstrait
        Idf idf20 = new Idf("i");
        Idf idf21 = new Idf("h");
        Inferieur inf11 = new Inferieur();
        inf11.setFilsGauche(idf20);
        inf11.setFilsDroit(idf21);
        //----------------------
        Idf idf22 = new Idf("i");
        Const const11 = new Const(0);
        Affectation aff22 = new Affectation();
        aff22.setFilsGauche(idf22);
        aff22.setFilsDroit(const11);
        //----------------------
        Const c5 = new Const(1);
        Idf idf17 = new Idf("i");
        Plus plus11 = new Plus();
        plus11.setFilsDroit(c5);
        plus11.setFilsGauche(idf17);
        Idf idf18 = new Idf("i");
        Affectation aff11 = new Affectation();
        aff11.setFilsGauche(idf18);
        aff11.setFilsDroit(plus11);
        Idf idf19 = new Idf("i");
        Ecrire ecrire11 = new Ecrire();
        ecrire11.setLeFils(idf19);
        Bloc bloc11 = new Bloc();
        bloc11.ajouterUnFils(ecrire11);
        bloc11.ajouterUnFils(aff11);

        //----------------------
        TantQue tq1 = new TantQue(1);
        tq1.setCondition(inf11);
        tq1.setBloc(bloc11);
        //----------------------

        Prog prog6 = new Prog();
        Noeud main11 = new Fonction("main");
        prog6.ajouterUnFils(main11);
        main11.ajouterUnFils(aff22);
        main11.ajouterUnFils(tq1);
        TxtAfficheur.afficher(prog6);

        // Generation du code
        GenererAssembleur genAss6 = new GenererAssembleur(prog6, tds6);
        GenerateurFichier genFichier6 = new GenerateurFichier(genAss6.getCode(), "exemple6");
        genFichier6.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple7(){
        // test #7 --------------------------------
        System.out.println("Test #7");
        //TDS
        TableDesSymboles tds7 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain7 = new Symbole("main", "void", 0, 0);
        Symbole idf23 = new Symbole("a", "int", "10", 0, "global", "global");
        Symbole fct1 = new Symbole("f", "void", 2, 1);
        Symbole idf24 = new Symbole("i", "int", "", 0, "f", "param");
        Symbole idf25 = new Symbole("x", "int", "", 0, "f", "local");
        Symbole idf26 = new Symbole("y", "int", "", 1, "f", "local");
        tds7.ajouterSymbole(idf23);
        tds7.ajouterSymbole(idf24);
        tds7.ajouterSymbole(idf25);
        tds7.ajouterSymbole(idf26);
        tds7.ajouterSymbole(fct1);
        tds7.ajouterSymbole(symboleMain7);
        tds7.afficher();

        // Arbre abstrait
        Idf idf777 = new Idf("x");
        Const const333 = new Const(1);
        Affectation aff333 = new Affectation();
        aff333.setFilsGauche(idf777);
        aff333.setFilsDroit(const333);
        //----------------------
        Idf idf666 = new Idf("y");
        Const const222 = new Const(1);
        Affectation aff222 = new Affectation();
        aff222.setFilsGauche(idf666);
        aff222.setFilsDroit(const222);
        //----------------------
        Idf idf222 = new Idf("y");
        Idf idf333 = new Idf("x");
        Plus plus222 = new Plus();
        plus222.setFilsGauche(idf333);
        plus222.setFilsDroit(idf222);
        Idf idf444 = new Idf("i");
        Plus plus333 = new Plus();
        plus333.setFilsGauche(idf444);
        plus333.setFilsDroit(plus222);
        Idf idf555 = new Idf("a");
        Affectation aff111 = new Affectation();
        aff111.setFilsGauche(idf555);
        aff111.setFilsDroit(plus333);
        //----------------------
        Idf idf111 = new Idf("a");
        Ecrire ecrire111 = new Ecrire();
        ecrire111.setLeFils(idf111);
        Const c111 = new Const(3);
        Appel appel111 = new Appel("f");
        appel111.ajouterUnFils(c111);
        Noeud f111 = new Fonction("main");
        f111.ajouterUnFils(appel111);
        f111.ajouterUnFils(ecrire111);
        //----------------------
        Noeud f222 = new Fonction("f");
        f222.ajouterUnFils(aff333);
        f222.ajouterUnFils(aff222);
        f222.ajouterUnFils(aff111);

        Prog prog7 = new Prog();
        prog7.ajouterUnFils(f222);
        prog7.ajouterUnFils(f111);
        TxtAfficheur.afficher(prog7);

        // Generation du code
        GenererAssembleur genAss7 = new GenererAssembleur(prog7, tds7);
        GenerateurFichier genFichier7 = new GenerateurFichier(genAss7.getCode(), "exemple7");
        genFichier7.genererFichier();
        System.out.println("----------------------\n");
    }
    public static void exemple8() {
        // test #8 --------------------------------
        System.out.println("Test #8");
        //TDS
        TableDesSymboles tds8 = new TableDesSymboles();
        // Symboles
        Symbole symboleMain8 = new Symbole("main", "void", 0, 0);
        Symbole symbole27 = new Symbole("a", "int", "10", 0, "global", "global");
        Symbole fct2 = new Symbole("f", "void", 1, 2);
        Symbole idf28 = new Symbole("x", "int", "", 0, "f", "local");
        Symbole idf29 = new Symbole("i", "int", "", 0, "f", "param");
        Symbole idf30 = new Symbole("j", "int", "", 1, "f", "param");
        tds8.ajouterSymbole(symbole27);
        tds8.ajouterSymbole(idf28);
        tds8.ajouterSymbole(idf29);
        tds8.ajouterSymbole(idf30);
        tds8.ajouterSymbole(fct2);
        tds8.ajouterSymbole(symboleMain8);
        tds8.afficher();

        // Arbre abstrait
        Idf idf1111 = new Idf("a");
        Ecrire ecrire1111 = new Ecrire();
        ecrire1111.setLeFils(idf1111);

        Const c1111 = new Const(2);
        Const c2222 = new Const(1);
        Appel appel1111 = new Appel("f");
        appel1111.ajouterUnFils(c1111);
        appel1111.ajouterUnFils(c2222);

        Idf idf2222 = new Idf("a");
        Affectation aff1111 = new Affectation();
        aff1111.setFilsGauche(idf2222);
        aff1111.setFilsDroit(appel1111);

        Noeud f1111 = new Fonction("main");
        f1111.ajouterUnFils(ecrire1111);
        f1111.ajouterUnFils(aff1111);
        //----------------------
        Const c3333 = new Const(10);
        Idf idf3333 = new Idf("x");
        Plus plus1111 = new Plus();
        plus1111.setFilsGauche(c3333);
        plus1111.setFilsDroit(idf3333);
        Retour retour1111 = new Retour("f");
        retour1111.setLeFils(plus1111);

        Idf idf4444 = new Idf("j");
        Idf idf5555 = new Idf("i");
        Plus plus2222 = new Plus();
        plus2222.setFilsGauche(idf4444);
        plus2222.setFilsDroit(idf5555);
        Idf idf6666 = new Idf("x");
        Affectation aff2222 = new Affectation();
        aff2222.setFilsGauche(idf6666);
        aff2222.setFilsDroit(plus2222);
        Noeud f2222 = new Fonction("f");
        f2222.ajouterUnFils(aff2222);
        f2222.ajouterUnFils(retour1111);

        Prog prog8 = new Prog();
        prog8.ajouterUnFils(f1111);
        prog8.ajouterUnFils(f2222);
        TxtAfficheur.afficher(prog8);

        // Generation du code
        GenererAssembleur genAss8 = new GenererAssembleur(prog8, tds8);
        GenerateurFichier genFichier8 = new GenerateurFichier(genAss8.getCode(), "exemple8");
        genFichier8.genererFichier();
        System.out.println("----------------------\n");
    }
}