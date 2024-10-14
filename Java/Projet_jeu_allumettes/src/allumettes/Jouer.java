package allumettes;
import java.util.ArrayList; // import the ArrayList class

/** Lance une partie des 13 allumettes en fonction des arguments fournis
 * sur la ligne de commande.
 * @author	Xavier Crégut
 * @version	$Revision: 1.5 $
 */
public class Jouer {

	static final int ALLUMETTES_INIT = 13;

	/** Lancer une partie. En argument sont donnés les deux joueurs sous
	 * la forme nom@stratégie.
	 * @param args la description des deux joueurs
	 */
	public static void main(String[] args) {
		try {
			verifierNombreArguments(args);
			ArrayList<Joueur> tabJoueur = new ArrayList<Joueur>();

			String[] infoJoueur;
			String[] arguments;
			arguments = new String[2];
			boolean confiant = false;

			if (args[0].equals("-confiant")) {
				confiant = true;
				arguments[0] = args[1];
				arguments[1] = args[2];
			} else {
				arguments = args;
			}
			for (int i = 0; i < arguments.length; i++) {
				infoJoueur = arguments[i].split("@");
				if (infoJoueur.length != 2) {
					throw new ConfigurationException("Longueur infoJoueur anormale");
				}
				String nom = infoJoueur[0];
				if (nom.length() == 0) throw new ConfigurationException("nom du Joueur vide");

				Strategie strat;
				switch (infoJoueur[1]) {

					case "humain":
						strat = new StrategieHumain();
						break;
					case "naif":
						strat = new StrategieNaif();
						break;
					case "expert":
						strat = new StrategieExpert();
						break;
					case "rapide":
						strat = new StrategieRapide();
						break;
					case "tricheur":
						strat = new StrategieTriche();
						break;

					default:
						throw new ConfigurationException("Stratégie non conforme");

				}
				tabJoueur.add(new Joueur(nom, strat));
		}

		JeuReel jeu = new JeuReel(Jouer.ALLUMETTES_INIT);
		Proxy jeuProxy = new Proxy(jeu);

		Arbitre arbitre = new Arbitre(tabJoueur.get(0), tabJoueur.get(1));
		if (confiant) {
			arbitre.arbitrer(jeu);
		} else {
			arbitre.arbitrer(jeu, jeuProxy);

		}


		} catch (ConfigurationException | ArrayIndexOutOfBoundsException e) {
			System.out.println();
			System.out.println("Erreur : " + e.getMessage());
			afficherUsage();
			System.exit(1);
		}
	}

	private static void verifierNombreArguments(String[] args) {
		final int nbJoueurs = 2;
		if (args.length < nbJoueurs) {
			throw new ConfigurationException("Trop peu d'arguments : "
					+ args.length);
		}
		if (args.length > nbJoueurs + 1) {
			throw new ConfigurationException("Trop d'arguments : "
					+ args.length);
		}
	}

	/** Afficher des indications sur la manière d'exécuter cette classe. */
	public static void afficherUsage() {
		System.out.println("\n" + "Usage :"
				+ "\n\t" + "java allumettes.Jouer joueur1 joueur2"
				+ "\n\t\t" + "joueur est de la forme nom@stratégie"
				+ "\n\t\t" + "strategie = naif | rapide | expert | humain | tricheur"
				+ "\n"
				+ "\n\t" + "Exemple :"
				+ "\n\t" + "	java allumettes.Jouer Xavier@humain "
					   + "Ordinateur@naif"
				+ "\n"
				);
	}

}
