package allumettes;

public class StrategieTriche implements Strategie {

    public int getPrise(Jeu jeu, Joueur joueur) {
        System.out.println("[Je triche...]");
        while (jeu.getNombreAllumettes() > 2) {
            try {
                jeu.retirer(1);
            } catch (CoupInvalideException e) {
                System.out.println("problème triche");
            }
        }
        System.out.println("[Allumettes restantes : " + jeu.getNombreAllumettes() + "]");
        return 1;
    }
}
