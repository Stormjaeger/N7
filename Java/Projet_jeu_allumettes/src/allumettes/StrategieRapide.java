package allumettes;

public class StrategieRapide implements  Strategie {

    public int getPrise(Jeu jeu, Joueur joueur) {
        int prise = 0;

        if (jeu.getNombreAllumettes() < Jeu.PRISE_MAX) {
            prise = jeu.getNombreAllumettes();
        } else {
            prise = Jeu.PRISE_MAX;
        }
        return prise;
    }
}
