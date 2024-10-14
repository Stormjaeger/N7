package allumettes;

public class Proxy implements Jeu {

    private Jeu jeu;

    public Proxy(Jeu jeu) {
        this.jeu = jeu;
    }

    public int getNombreAllumettes() {
        return this.jeu.getNombreAllumettes();
    }

    public void retirer(int allumettesRetirees) throws OperationInterditeException {
        throw new OperationInterditeException("détection d'une triche");
    }

    public String toString() {
        return this.jeu.toString();
    }
}
