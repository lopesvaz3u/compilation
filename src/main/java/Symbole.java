/**
 * Classe Symbole
 */
public class Symbole {

    // Nom du symbole
    private String nom;
    // Type : int, void
    private String type;
    // Valeur du symbole
    private String valeur;
    // Categorie : variable, constante, fonction, parametre
    private String categorie;
    // Scope : global, local a une fonction (f, g, main, ...)
    private String scope;
    // Rang du parametre ou de la variable locale sur la ligne
    private int rang;
    // Niveau d imbrication du bloc
    private int nbBloc;
    // Nombre de parametres pour les fonctions
    private int nbParam;

    // Constructeur pour les fonctions
    public Symbole(String nom, String type, int nbBloc, int nbParam) {
        this.nom = nom;
        this.type = type;
        this.categorie = "fonction";
        this.nbBloc = nbBloc;
        this.nbParam = nbParam;
    }

    // Constructeur pour les variables, constantes et parametres
    // si la valeur est inconnue mettre 0
    public Symbole(String nom, String type, String valeur, int rang, String scope, String categorie){
        this.nom = nom;
        this.type = type;
        this.valeur = valeur;
        this.rang = rang;
        this.scope = scope;
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }


    public String getType() {
        return type;
    }


    public String getValeur() {
        return valeur;
    }


    public String getCategorie() {
        return categorie;
    }


    public String getScope() {
        return scope;
    }


    public int getRang() {
        return rang;
    }


    public int getNbBloc() {
        return nbBloc;
    }


    public int getNbParam() {
        return nbParam;
    }

}
