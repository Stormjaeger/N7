package allumettes;

public class StrategieExpert implements Strategie {

    public int getPrise(Jeu jeu, Joueur joueur) {
        final int modulo = jeu.PRISE_MAX + 1;
        return Math.max(1, (jeu.getNombreAllumettes() - 1) % modulo);
    }
}
