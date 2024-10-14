package GameEngine.Game;
import java.util.Map;
import GameEngine.Run;

import javax.swing.JPanel;

import GameEngine.Map.MapModel;
import GameEngine.Map.Tile.Coordinate;
import GameEngine.Map.Tile.TileModel;
import GameEngine.Main;
import GameEngine.Map.CharacterView;
import GameEngine.Architecture.FileAdapter;

public class GameModel extends JPanel implements Runnable{

    private JPanel panel;
    private CharacterView playerView;
    private MapModel map;
    private GameView viewer;
    // FPS
    int FPS = 10;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //Vitesse de déplacement du personnage
    int playerSpeed = 1;

    //Coordonnée de départ
    
    Coordinate startCoordinate;


    //position joueur
    Coordinate playerCoordinate;

    //Coordonnée future du joueur pour tester collision avec murs
    //Coordinate futurPlayerCoordinate;

    public GameModel(JPanel Panel, CharacterView playerView, MapModel map){
        this.panel = Panel;
        this.map = map;
        this.playerView = playerView;
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        FileAdapter adapter = new FileAdapter(this.map.getProjectPath());
        this.startCoordinate = adapter.getSpawn(this.map.getMapPath());
        this.playerCoordinate = new Coordinate(startCoordinate.getX(), startCoordinate.getY());
    }

    public KeyHandler getKeyHandler(){
        return(this.keyH);
    }


    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void addViewer(GameView viewer) {
    	this.viewer = viewer;
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; 
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        Main.gameWindow.display();
        playerView.display(this.panel, this.playerCoordinate);


        while (gameThread != null) {

            
            // 1 Update: update information such as character position
            update();
            playerView.changeTile();
            // 2 Draw : draw the screen with the update information
            playerView.refresh(panel, playerCoordinate);
            

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep( (long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void spriteCounterUpdate(){
        Main.spriteCounter++;
        if (Main.spriteCounter > 1){
            if (Main.spriteNum == 1){
                Main.spriteNum = 2;
            }
            else if (Main.spriteNum == 2){
                Main.spriteNum = 1;
            }
            Main.spriteCounter = 0;
        }
    }

    public boolean isWall(MapModel mapModel, Coordinate tileCoordinate){
        Map<Coordinate, TileModel> map = mapModel.getMap();
        TileModel tile = map.get(tileCoordinate);
        try {
        	return tile.getIsWall();
        } catch (NullPointerException e) {
        	return true;
        }
        
    }
    
    public boolean isPortal(MapModel mapModel, Coordinate playerCoordinate){
        Map<Coordinate, String> portal = mapModel.getPortal();
        try {
        	if (mapModel.getPortal().containsKey(playerCoordinate)) {
        	} else {
        		return false;
        	}
        	return true;
        } catch (NullPointerException e) {
        	return false;
        }
        
    }
    
    public String whatPortal(MapModel mapModel, Coordinate playercoordinate) {
    	return mapModel.getPortal().get(playercoordinate);
    }

    public void update() {
        Coordinate futurPlayerCoordinate = playerCoordinate;

        // met à jour la position du personnage sur la carte si la case d'arrivée n'est pas un mur
        if (keyH.upPressed == true) {
            futurPlayerCoordinate.updateY(-playerSpeed);
            if (!isWall(this.map, futurPlayerCoordinate)){
                playerCoordinate.updateY(-playerSpeed);
                Main.direction = "up";
                spriteCounterUpdate();
            }
            futurPlayerCoordinate.updateY(playerSpeed);
        }
        if (keyH.downPressed == true) {
            futurPlayerCoordinate.updateY(playerSpeed);
            if (!isWall(this.map, futurPlayerCoordinate)){
                playerCoordinate.updateY(playerSpeed);
                Main.direction = "down";
                spriteCounterUpdate();
            }
            futurPlayerCoordinate.updateY(-playerSpeed);
        }
        if (keyH.leftPressed == true) {
            futurPlayerCoordinate.updateX(-playerSpeed);
            if (!isWall(this.map, futurPlayerCoordinate)){
                playerCoordinate.updateX(-playerSpeed);
                Main.direction = "left";
                spriteCounterUpdate();
            }
            futurPlayerCoordinate.updateX(playerSpeed);
        }
        if (keyH.rightPressed == true) {
            futurPlayerCoordinate.updateX(playerSpeed);
            if (!isWall(this.map, futurPlayerCoordinate)){
                playerCoordinate.updateX(playerSpeed);
                Main.direction = "right";
                spriteCounterUpdate();
            }
            futurPlayerCoordinate.updateX(-playerSpeed);
            }
        if (isPortal(this.map, playerCoordinate)) {
        	System.out.println("test");
        	String newPath = whatPortal(this.map, playerCoordinate);
        	// set de la new map
        	MapModel newMap = new MapModel(this.map.getProjectPath(), newPath);     
        	this.map = newMap;
        	this.map.load();
            int nbr_largeur = 0;
            int nbr_hauteur = 0;
            
            for (Coordinate coord : this.map.getMap().keySet()) {
                if (coord.getX() > nbr_largeur) {
                    nbr_largeur= coord.getX();
                }
                if (coord.getY() > nbr_hauteur) {
                    nbr_hauteur = coord.getY();
                }
            }
            TileModel newtile = this.map.getMap().get(new Coordinate(0,0));
            System.out.println(this.map.getMap().get(new Coordinate(0,0)) == null);
            int largeur = (nbr_largeur+1) * newtile.getSize();
            int hauteur = (nbr_hauteur+1) * newtile.getSize();
            //this.panel.removeAll();
            // Change les coordonées du jouer
            playerCoordinate = this.map.getSpawn();
            playerView.changeTile();
            this.playerView.refresh(panel, playerCoordinate);
            
            /// Fonctionnel jusqu'ici
            
            //Main.window.dispose();
            try {
            	Main.window.setSize(largeur, hauteur);
            } catch (NullPointerException e){
            	Run.window.setSize(largeur, hauteur);
            }
            this.viewer.update(this.map);
        }
        
        
    }

}
 