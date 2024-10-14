package allumettes;
import java.util.Scanner;


public class StrategieHumain implements Strategie {

    private Scanner sc = new Scanner(System.in);

    public int getPrise(Jeu jeu, Joueur joueur) {

        boolean coupinvalide = true;
        int prise = 0;

        while (coupinvalide) {
            System.out.print(joueur.getNom() + ", combien d'allumettes ? ");
            String nbAllumettePrise = this.sc.next();

            if (nbAllumettePrise.equals("triche")) {
                try {
                    jeu.retirer(1);
                    System.out.println("[Une allumette en moins, plus que "
                    + jeu.getNombreAllumettes() + ". Chut !]");

                } catch (CoupInvalideException e) {
                    System.out.println("problème triche");
                }

            } else {
                try {
                    prise = Integer.valueOf(nbAllumettePrise);
                    coupinvalide = false;

                } catch (NumberFormatException e) {
                    System.out.println("Vous devez donner un entier.");
                }
            }

        }
        return prise;
    }
}
