

import generated.fr.ul.miage.expression.Yylex;

import java.io.FileReader;

public class MainLEX {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: parser <nom de fichier>");
            System.exit(1);
        }
        try{
            Yylex scanner = new Yylex(new FileReader(args[0]));
            //ParserCup parser = new ParserCup(scanner);
            //parser.parse();
        }catch (Exception e){
            System.err.println("Erreur : \n" + e.getMessage());
        }
        System.out.println("Terminé !");
    }
}