package allumettes;
import java.util.Random;

public class StrategieNaif implements Strategie {

    public int getPrise(Jeu jeu, Joueur joueur) {
        int prise = 0;
        Random randomNumbers = new Random();

        if (jeu.getNombreAllumettes() == 1) {
            prise = 1;
        } else if (jeu.getNombreAllumettes() == 2) {
            prise = randomNumbers.nextInt(2) + 1;
        } else {
            prise = randomNumbers.nextInt(Jeu.PRISE_MAX) + 1;
        }
        return prise;
    }

}
