package allumettes;
import org.junit.*;
import static org.junit.Assert.*;


public class TestStrategieRapide {

    private Jeu jeu1;
    private Joueur joueur1;
    @Before 
    public void initialize(){
        this.jeu1 = new JeuReel(13);
        this.joueur1 = new Joueur("testeur",new StrategieRapide());
    }

    @Test
    public void testStrategieRapide() {
        try {
            assertEquals(this.joueur1.getPrise(this.jeu1), 3);
            this.jeu1.retirer(2);
            assertEquals(this.joueur1.getPrise(this.jeu1), 3);
            this.jeu1.retirer(3);
            this.jeu1.retirer(3);
            this.jeu1.retirer(3);
            assertEquals(this.joueur1.getPrise(this.jeu1), 2);
            this.jeu1.retirer(1);
            assertEquals(this.joueur1.getPrise(this.jeu1), 1);

        } catch (CoupInvalideException e) {
            System.out.println("erreur test");
        }
    }
    
}
