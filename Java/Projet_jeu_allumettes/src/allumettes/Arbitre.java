package allumettes;

public class Arbitre {

    private Joueur joueur1;
    private Joueur joueur2;

    public Arbitre(Joueur j1, Joueur j2) {
        this.joueur2 = j2;
        this.joueur1 = j1;
    }
    private boolean faireJouerJoueur(Jeu jeu, Jeu jeuProxy, Joueur joueur) {
        int prise = 0;
        String str = "";
        try {
            prise = joueur.getPrise(jeuProxy);
            str = (prise > 1) ? " allumettes." : " allumette.";
            jeu.retirer(prise);
            System.out.println(joueur.getNom() + " prend " + prise + str);

            return true;
        } catch (CoupInvalideException e) {
            System.out.print(joueur.getNom() + " prend" + prise + str);
            System.out.println("");
            System.out.println("Impossible ! Nombre invalide : "
            + e.getCoup() + e.getProbleme());
            return false;
        }
    }

    public void arbitrer(Jeu jeu) {
        this.arbitrer(jeu, jeu);
    }


    public void arbitrer(Jeu jeu, Jeu jeuProxy) {
        boolean termine = false;
        Joueur joueurCourant = this.joueur1;
        boolean triche = false;
        boolean coupValide = false;

        while (!(termine)) {

            System.out.println(jeu.toString());
            try {
            coupValide = faireJouerJoueur(jeu, jeuProxy, joueurCourant);

            } catch (OperationInterditeException e) {
                triche = true;
                termine = true;
                System.out.println("Abandon de la partie car "
                + joueurCourant.getNom() + " triche !");
            }
            System.out.println("");

            if (coupValide) {
                if (joueurCourant.equals(this.joueur1)) {
                    joueurCourant = this.joueur2;
                } else {
                    joueurCourant = this.joueur1;
                }
            }

            if (jeu.getNombreAllumettes() <= 0) {
                termine = true;
            }
        }
        Joueur perdant = this.joueur1;
            if (joueurCourant.equals(this.joueur1)) {
                perdant = this.joueur2;
            }
        if (!triche) {
            System.out.println(perdant.getNom() + " perd !");
            System.out.println(joueurCourant.getNom() + " gagne !");
        }
    }
}
