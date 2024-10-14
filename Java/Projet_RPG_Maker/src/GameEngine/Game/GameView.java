package GameEngine.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameEngine.Map.MapModel;
import GameEngine.Map.MapView;
import GameEngine.Map.Tile.Coordinate;
import GameEngine.Map.Tile.TileModel;

public class GameView {

    private JFrame window;
    private JPanel mapPanel;
    private JPanel mainPanel;
    private MapView mapView;
    private GameModel gameModel;

    public JFrame getWindow() {
        return this.window;
    }

    public JPanel getMapPanel() {
        return this.mapPanel;
    }

    public JPanel getMainPannel() {
        return this.mainPanel;
    }

    public GameView(String path, GameModel gameModel, MapModel map, JPanel mapPanel) {
        this.window = new JFrame("Ystoria - Game Maker");
        this.window.setResizable(false);
        int nbr_largeur = 0;
        int nbr_hauteur = 0;

        for (Coordinate coord : map.getMap().keySet()) {
            if (coord.getX() > nbr_largeur) {
                nbr_largeur = coord.getX();
            }
            if (coord.getY() > nbr_hauteur) {
                nbr_hauteur = coord.getY();
            }
        }
        TileModel tile = map.getMap().get(new Coordinate(0, 0));
        int largeur = (nbr_largeur) * tile.getSize();
        int hauteur = (nbr_hauteur) * tile.getSize();
        this.mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.window.getContentPane().setPreferredSize(new Dimension(largeur, hauteur));
        this.mapPanel = mapPanel;
        this.gameModel = gameModel;
        this.mapView = new MapView(map);
        this.mapPanel.addKeyListener(this.gameModel.getKeyHandler());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // EXIT_ON_CLOSE
        this.window.setUndecorated(true);
        this.window.setLocationRelativeTo(null);
        this.window.pack();
    }

    public void display() {
        this.mapView.display(mapPanel);
        this.mainPanel.add(this.mapPanel);
        this.window.add(mainPanel);
        this.window.setVisible(true);
    }

    public void update(MapModel map) {
        this.mainPanel.removeAll();
        this.mapView.update(map);
        this.mapPanel = this.mapView.main;
        this.mainPanel.add(this.mapPanel);
        this.window.add(mainPanel);
        this.window.setVisible(true);
    }

    public void close() {
        this.window.dispose();
    }
}
