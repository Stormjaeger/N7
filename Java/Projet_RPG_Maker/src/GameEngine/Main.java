package GameEngine;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GameEngine.Map.Tile.Coordinate;
import GameEngine.Map.Tile.TileModel;
import GameEngine.Game.GameModel;
import GameEngine.Game.GameView;
import GameEngine.Map.CharacterView;
import GameEngine.Map.MapModel;

public class Main {

    public static MapModel mapModel;
    public static GameView gameWindow;
    public static String projectPath;
    public static JFrame window;
    public static CharacterView playerView;
    public static String direction = "down";

    // Permet de choisir quand changer d'animation
    public static int spriteCounter = 0;
    public static int spriteNum = 1;

    public static void main(String[] args) {
        projectPath = "/home/alexandre/Année_1/TOB/Projet_Long/pl/pl/project_example";
        
        mapModel = new MapModel(projectPath, "/example_map_1/map_1.map");
        //affiche la map
        mapModel.load();

        //Création tuile joueur
        String joueur = "girl";
        TileModel playerTile = new TileModel(1, joueur +"_down_1.png", false, joueur);
        playerView = new CharacterView(playerTile, projectPath, joueur);
        playerView.getPlayerImage();

        JPanel mapPanel = new JPanel();
        GameModel gameModel = new GameModel(mapPanel, playerView, mapModel);

        gameWindow = new GameView(projectPath, gameModel, mapModel, mapPanel);
        gameModel.addViewer(gameWindow);
        //permet d'activer la boucle "active" du jeu qui repère les touches appuyées
        window = gameWindow.getWindow();
        window.add(gameModel);

        // modification manuelle de la taille de la fenêtre de jeu
        // parametres de maxime (448,485)

        // window.setSize(4480, 448);
        // window.setVisible(true);

        // génération automatique de la taille de la fenêtre.

        window.setVisible(true);
        
        //lancement de la boucle "active" 
        gameModel.startGameThread();
    }
}