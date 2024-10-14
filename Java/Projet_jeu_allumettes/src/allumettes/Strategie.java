package allumettes;

// Pas compris pourquoi on doit faire une interface et non une classe

public interface Strategie {

    /**Change la stratégie du joueur.
     * @param newStrategie
     */
    int getPrise(Jeu jeu, Joueur joueur);
}
