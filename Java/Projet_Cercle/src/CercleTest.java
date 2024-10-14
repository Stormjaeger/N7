import org.junit.*;
import static org.junit.Assert.*;
import java.awt.Color;

/** Classe de test de la classe Cercle
 * @author Maxime LAURENT
 *
 */

public class CercleTest{

    public static final double EPSILON = 1e-4; // précision pour la comparaison entre réels

    // cercles de références pour les tests
    private Cercle C1;
    private Cercle C2;
    private Cercle C3;

    @Before
    public void setUp(){
        //Construire les cercles
        C1 = new Cercle(new Point(7,7), new Point(5,5));
        C2 = new Cercle(new Point(4, 2), new Point(4, -2), Color.green);
        C3 = Cercle.creerCercle(new Point(0,0), new Point(-1,0));

    }


    /** Vérifier si deux points ont mêmes coordonnées.
	  * @param p1 le premier point
	  * @param p2 le deuxième point
	  */
	static void memesCoordonnees(String message, Point p1, Point p2) {
		assertEquals(message + " (x)", p1.getX(), p2.getX(), EPSILON);
		assertEquals(message + " (y)", p1.getY(), p2.getY(), EPSILON);
	}

    @Test
    public void testerE12(){
        memesCoordonnees("E12 : Centre de C1 incorrect",new Point(6,6),C1.getCentre());
        assertEquals("E12 : Rayon de C1 incorrect", Math.sqrt(2.0), C1.getRayon(), EPSILON);
        assertEquals(Color.blue, C1.getCouleur());
    }

    @Test
    public void testerE13(){
        memesCoordonnees("E13 : Centre de C2 incorrect",new Point(4,0),C2.getCentre());
        assertEquals("E13 : Rayon de C1 incorrect", 2.0, C2.getRayon(), EPSILON);
        assertEquals(Color.green, C2.getCouleur());
    }

    @Test
    public void testerE14(){
        memesCoordonnees("E14 : Centre de C3 incorrect",new Point(0,0),C3.getCentre());
        assertEquals("E14 : Rayon de C3 incorrect", 1.0, C3.getRayon(), EPSILON);
        assertEquals(Color.blue, C3.getCouleur());
    }
}