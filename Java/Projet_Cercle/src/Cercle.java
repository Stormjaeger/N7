import java.awt.Color;

/** Cercle modélise un cercle dans un plan équipé d'un
 * repère cartésien.
 *
 * @author  Maxime LAURENT <maxime.laurent@enseeiht.fr>
 */

public class Cercle implements Mesurable2D {
    /**
     * Valeur de pi.
     */
    public static final double PI = Math.PI;
     /**
     * Centre du cercle.
     */
    private Point centre;
     /**
     * Rayon du cercle.
     */
    private double rayon;
     /**
     * Couleur du cercle.
     */
    private Color couleur;
    /**Construire un cercle à partir de son centre et de son rayon(réel).
     * @param centre
     * @param rayon
     */
    public Cercle(Point centre, double rayon) {
        assert (rayon > 0.0);
        assert (centre != null);
        this.centre = new Point(centre.getX(), centre.getY());
        this.rayon = rayon;
        this.couleur = Color.blue;
    }



    /**Construire un cercle à partir de 2 points opposés,
     * le cercle créer sera de couleur bleu.
     * @param p1
     * @param p2
     */
    public Cercle(Point p1, Point p2) {
        this(p1, p2, Color.blue);
    }

    /**Construire un cercle à partir de 2 points opposés et de sa couleur.
     * @param p1
     * @param p2
     * @param newcolor
     */
    public Cercle(Point p1, Point p2, Color newcolor) {
        assert (p1 != null);
        assert (p2 != null);
        assert ((p1.getX() != p2.getX()) || (p1.getY() != p2.getY()));
        assert (newcolor != null);
        this.centre = new Point((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
        this.rayon = p1.distance(p2) / 2;
        this.couleur = newcolor;
    }

    /**Créer un cercle à partir de son centre et d'un point de sa circonférence.
     * @param centre
     * @param circonf
     * @return le nouveau cercle
     */
    public static Cercle creerCercle(Point centre, Point circonf) {
        assert (centre != null);
        assert (circonf != null);
        assert ((centre.getX() != circonf.getX()) || (centre.getY() != circonf.getY()));
        Point c = new Point(centre.getX(), centre.getY());
        double r = centre.distance(circonf);
        Cercle mycercle = new Cercle(c, r);

        return mycercle;

    }


    /** Obtenir le périmètre du cercle.
     * @return le périmètre du cercle
     */
    public double perimetre() {
        return this.rayon * 2 * PI;
    }

    /** Obtenir l'aire du cercle.
     * @return l'aire du cercle
     */
    public double aire() {
        return Math.pow(this.rayon, 2.0) * PI;
    }
    /**Obtenir le centre du cercle.
     * @return centre du cercle
     */
    public Point getCentre() {
        Point mypoint = new Point(this.centre.getX(), this.centre.getY());
        return mypoint;
    }

    /**Obtenir le rayon du cercle.
     * @return rayon du cercle
     */
    public double getRayon() {
        return this.rayon;
    }

    /** Obtenir le diamètre du cercle.
     * @return diamètre du cercle
     */
    public double getDiametre() {
        return this.rayon * 2.0;
    }

    /**Obtenir la couleur du cercle.
     * @return couleur du cercle
     */
    public Color getCouleur() {
        return this.couleur;
    }

    /** Traslater le cercle en translatant son centre.
     * @param dx déplacement suivant l'axe des X
     * @param dy déplacement suivant l'axe des Y
     */
    public void translater(double dx, double dy) {
        this.centre.translater(dx, dy);
    }

    /** Changer la couleur du cercle.
     * @param newcolor nouvelle couleur du cercle
     */
    public void setCouleur(Color newcolor) {
        assert (newcolor != null);
        this.couleur = newcolor;
    }

    /** Changer le rayon du cercle.
     * @param newrayon nouveau rayon du cercle
     */
    public void setRayon(double newrayon) {
        assert (newrayon > 0.0);
        this.rayon = newrayon;
    }

    /** Changer le diamètre du cercle.
     * @param newdiametre nouveau diamètre du cercle
     */
    public void setDiametre(double newdiametre) {
        assert (newdiametre > 0.0);
        this.rayon = newdiametre / 2;
    }

    /** Appartenance d'un point à this qui est un cercle.
     * @param autre point dont on test l'appartenance
     * @return appartenance du point à this
     */
    public boolean contient(Point autre) {
        assert (autre != null);
        double d = this.centre.distance(autre);
        return (d <= this.rayon);
    }

    @Override
    public String toString() {
        return "C" + this.rayon + "@" + this.centre.toString();
    }

    /**Afficher le cercle. */
    public void afficher() {
        System.out.print(this);
    }
}



