
import java.util.Collection;
import java.util.HashMap;

/**
 * Table des symboles
 */
public class TableDesSymboles {
    // Table des symboles, a chaque symbole est associé une clef unique correspondant a $ + le numero du symbole dans la table
    private HashMap<String, Symbole> tableDesSymboles;

    public TableDesSymboles(){
        this.tableDesSymboles = new HashMap<String, Symbole>();
    }

    // Ajoute un symbole à la table des symboles
    public void ajouterSymbole(Symbole symbole){
        this.tableDesSymboles.put("$"+tableDesSymboles.size(), symbole);
    }

    // Retourne le symbole correspondant a la fois au nom et au scope
    // Retourne null si le symbole n'existe pas
    public Symbole getSymbole(String nom, String scope){
        for (String clef : this.tableDesSymboles.keySet()) {
            Symbole symbole = this.tableDesSymboles.get(clef);
            if (symbole.getScope() != null) {
                if(symbole.getNom().equals(nom) && symbole.getScope().equals(scope)){
                    return symbole;
                }
            }
        }
        return null;
    }

    public Symbole getSymbole(String nom){
        for (String clef : this.tableDesSymboles.keySet()) {
            Symbole symbole = this.tableDesSymboles.get(clef);
            if(symbole.getNom().equals(nom)){
                return symbole;
            }
        }
        return null;
    }

    public Symbole getSymbole(String nom, String scope, String categorie){
        for (String clef : this.tableDesSymboles.keySet()) {
            Symbole symbole = this.tableDesSymboles.get(clef);
            if(symbole.getNom().equals(nom) && symbole.getCategorie().equals(categorie) && symbole.getScope().equals(scope)){
                return symbole;
            }
        }
        return null;
    }

    public Collection<Symbole> getSymboles(){
        return this.tableDesSymboles.values();
    }

    // Retourne vrai si le symbole existe dans la table des symboles (meme nom + scope)
    public boolean existeSymbole(String nom, String scope){
        for (String clef : this.tableDesSymboles.keySet()) {
            Symbole symbole = this.tableDesSymboles.get(clef);
            if(symbole.getNom().equals(nom) && symbole.getScope().equals(scope)){
                return true;
            }
        }
        return false;
    }

    // Affiche la table des symboles
    public void afficher(){
        for (String nom : this.tableDesSymboles.keySet()) {
            Symbole symbole = this.tableDesSymboles.get(nom);
            if(symbole.getCategorie().equals("fonction")) {
                System.out.println(afficherFonction(symbole));
            } else {
                System.out.println(afficherVariable(symbole));
            }
        }
    }
    // Renvoie l affichage pour les fonctions de la table des symboles
    public String afficherFonction(Symbole s){
        if(s.getNom() == "main") {
            return "nom : " + s.getNom() + "; type : " + s.getType() + "; categorie : " + s.getCategorie() + ";";
        } else {
            return "nom : " + s.getNom() + "; type : " + s.getType() + "; categorie : " + s.getCategorie() + "; nbparam : " + s.getNbParam() + "; nbloc : " + s.getNbBloc() + ";";
        }
    }
    // Renvoie l affichage pour les variables, constantes et parametres de la table des symboles
    public String afficherVariable(Symbole s) {
        if (s.getValeur() == "" && s.getScope() == "global") {
            return "nom : " + s.getNom() + "; type : " + s.getType() + "; categorie : " + s.getCategorie() + ";";
        } else if (s.getValeur() != "" && s.getScope() == "global") {
            return "nom : " + s.getNom() + "; type : " + s.getType() + "; categorie : " + s.getCategorie() + "; valeur : " + s.getValeur() + ";";
        } else {
            return "nom : " + s.getNom() + "; type : " + s.getType() + "; categorie : " + s.getCategorie() + "; valeur : " + s.getValeur() + "; rang : " + s.getRang() + "; scope : " + s.getScope() + ";";
        }
    }
}
