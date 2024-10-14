package GameEngine;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GameEngine.Game.GameModel;
import GameEngine.Game.GameView;
import GameEngine.Map.CharacterView;
import GameEngine.Map.MapModel;
import GameEngine.Map.Tile.Coordinate;
import GameEngine.Map.Tile.TileModel;

//
//
// Cette classe est littéralement la même que Main, mais prend un argument (map) qui est celui qui définira dans quel map
// le projet est lancé
//
//
public class Run {
    public static MapModel mapModel;
    public static GameView gameWindow;
    public static String projectPath;
    public static JFrame window;
    public static CharacterView playerView;
    public static String direction = "down";
    public MapModel mapEditor;

    //Permet de choisir quand changer d'animation
    public static int spriteCounter = 0;
    public static int spriteNum = 1;
    
    public Run(MapModel map) {
    	this.mapEditor = map;
    }

    public void running() {
        //projectPath = "/home/alexandre/Année_1/TOB/Projet_Long/pl/pl/project_example";
        projectPath = this.mapEditor.getProjectPath();
    	
        //mapModel = new MapModel(projectPath, "/example_map_3/map_3.map");
        mapModel = new MapModel(projectPath, this.mapEditor.getMapPath());
        
        //affiche la map
        mapModel.load();

        //Création tuile joueur
        TileModel playerTile = new TileModel(1, "boy_down_1.png", false, "playerTile");
        playerView = new CharacterView(playerTile, projectPath, "boy");
        playerView.getPlayerImage();
        
        JPanel mapPanel = new JPanel();
        GameModel gameModel = new GameModel(mapPanel,playerView, mapModel);
        
        gameWindow = new GameView(projectPath, gameModel, mapModel, mapPanel);
        
        Main.gameWindow = gameWindow;
        gameModel.addViewer(Main.gameWindow);
        //permet d'activer la boucle "active" du jeu qui repère les touches appuyées
        window = Main.gameWindow.getWindow();
        window.add(gameModel);
        
        //modification manuelle de la taille de la fenêtre de jeu
        // parametres de maxime (448,485)
        
        //window.setSize(4480, 448);
        //window.setVisible(true);
        
        //génération automatique de la taille de la fenêtre.
        int nbr_largeur = 0;
        int nbr_hauteur = 0;
        
        for (Coordinate coord : mapModel.getMap().keySet()) {
            if (coord.getX() > nbr_largeur) {
                nbr_largeur= coord.getX();
            }
            if (coord.getY() > nbr_hauteur) {
                nbr_hauteur = coord.getY();
            }
        }
        TileModel tile = mapModel.getMap().get(new Coordinate(0,0));
        int largeur = (nbr_largeur+1) * tile.getSize();
        int hauteur = (nbr_hauteur+1) * tile.getSize();
        System.out.print(tile.getSize());
        window.setSize(largeur, hauteur);
        window.setVisible(true);
        
        
        //lancement de la boucle "active" 
        gameModel.startGameThread();
    }
}
