package allumettes;


public class Joueur {

    private String nom;
    private Strategie strategie;


    public Joueur(String str, Strategie strategie) {
        this.nom = str;
        this.strategie = strategie;
    }



    /** Obtenir le nom du joueur.
     * @return nom du joueur
     */
    public String getNom() {
        return this.nom;
    }

    /** Obtenir le nombre d'allumettes prises par le joueur.
     * @param jeu
     * @return le nombre d'allumettes prises
     */
    public int getPrise(Jeu jeu) throws CoupInvalideException {
        return this.strategie.getPrise(jeu, this);
    }

    /**Permet de changer la stratégie du joueur.
     * @param newStrategie nouvelle stratégie du joueur
     */
    public void setStrategie(Strategie newStrategie) {
        this.strategie = newStrategie;
    }

}
