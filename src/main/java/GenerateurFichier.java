import java.io.FileWriter;
import java.io.IOException;

public class GenerateurFichier {
    private String code;
    private String nomFichier;

    public GenerateurFichier(String c, String nomFichier) {
        this.code = c;
        this.nomFichier = nomFichier;
    }

    /**
     * Genere le fichier assembleur a partir du code assembleur fourni
     */
    public void genererFichier(){
        try {
            FileWriter fw = new FileWriter("codeAssembleurBeta/" + this.nomFichier + ".uasm");
            fw.write(this.code);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
