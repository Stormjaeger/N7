package GameEngine.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import GameEngine.Map.Tile.TileModel;
import GameEngine.Map.Tile.Coordinate;

public class MapView {
    private MapModel map;
    public JPanel main;
    public MapView(MapModel mapModel) {
        this.map = mapModel;
    }
    
    public void update(MapModel newMap) {
    	main.removeAll();
    	this.map = newMap;
    	display(main);
    }
    
    public void display(JPanel main) {
    	this.main = main;
        main.setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        
        for (Coordinate coord : this.map.getMap().keySet()) {
            try {
                TileModel tile = this.map.getMap().get(coord);

                BufferedImage tileSprite = ImageIO
                        .read(new File(this.map.getProjectPath() + "/assets/img/tiles/" + tile.getSpritePath()));
                ImageIcon spriteImg = new ImageIcon(tileSprite);

                JLabel tileLabel = new JLabel(spriteImg);
                tileLabel.setBorder(new LineBorder(Color.GRAY));

                tileLabel.setBounds(coord.getX() * tile.getSize(), coord.getY() * tile.getSize(), tile.getSize(),
                        tile.getSize());

                panel.add(tileLabel);
                Rectangle size = panel.getBounds();
                size.add(tileLabel.getBounds());
                panel.setBounds(size);

            } catch (IOException e) {
                System.out.println("Failed to load sprite !");
                e.printStackTrace();
            }
        }
        System.out.println(panel.getBounds());
        centerPanel.add(panel);
        /**
         * Cf Paul
         */
        centerPanel.setPreferredSize(
                new Dimension((int) panel.getBounds().getWidth(), (int) panel.getBounds().getHeight()));
        main.add(centerPanel, new GridBagConstraints());
    }
}

