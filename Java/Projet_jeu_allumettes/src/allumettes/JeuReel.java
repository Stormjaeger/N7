package allumettes;

public class JeuReel implements Jeu {

    private int nbAllumettes = Jouer.ALLUMETTES_INIT;

    public JeuReel(int nbAllumetteDepart) {
        this.nbAllumettes = nbAllumetteDepart;
    }

    public int getNombreAllumettes() {
        return this.nbAllumettes;
    }

    public void retirer(int allumettesRetirees) throws CoupInvalideException {
        int max = Math.min(Jeu.PRISE_MAX, this.getNombreAllumettes());

        if (allumettesRetirees < 1) {
            throw new CoupInvalideException(allumettesRetirees, " (< 1)");
        } else if (allumettesRetirees > max) {
            throw new CoupInvalideException(allumettesRetirees, (" (> " + max + ")"));
        } else {
            this.nbAllumettes -= allumettesRetirees;
        }
}

    public String toString() {
        return ("Allumettes restantes : " + this.nbAllumettes);
    }
}
