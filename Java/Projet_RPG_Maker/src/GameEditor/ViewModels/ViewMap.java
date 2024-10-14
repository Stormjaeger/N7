package GameEditor.ViewModels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import GameEditor.Architecture.FileAdapter;
import GameEditor.Map.MapModel;
import GameEditor.Map.Tile.Coordinate;

import GameEditor.Architecture.FileAdapter;
import GameEditor.Map.MapModel;
import GameEditor.Map.Tile.Coordinate;
import GameEditor.Map.Tile.TileModel;
import GameEditor.Settings.SettingsView;
import GameEditor.Map.MapModel;
import GameEditor.Map.Tile.Coordinate;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.FileAdapter;

public class ViewMap implements ViewModel, MapListener {
    private MapModel map;
    private SettingsView settings;
    private JPanel panneauPrincipal; 

    public ViewMap(MapModel mapModel) {
        this.map = mapModel;
    }
    
    public ViewMap(MapModel mapModel, SettingsView settings) {
        this.map = mapModel;
        this.settings = settings;
    }
    
    public void display(JPanel main) {
        this.panneauPrincipal = main; 
        this.panneauPrincipal = main; 
        main.setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        
        SettingsView settings = this.settings;
        String temp_path = map.getProjectPath();
        String temp_map_path = map.getMapPath();
        FileAdapter adapter = new FileAdapter(map.getProjectPath());
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

                tileLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	// On enlève ce qu'il y avait avant dans le JPanel settings
                        settings.getPanel().removeAll();
                        // On le remet en mode settings
                        settings.display(settings.getPanel());
                        
                        
                        
                        Coordinate Spawn = adapter.getSpawn(temp_map_path);
                        JLabel spawn = new JLabel();
                        spawn.setText("Spawn : (" + Spawn.getX() + ";" + Spawn.getY() + ')');
                        spawn.setBorder(new EmptyBorder(15, 10, 15, 10));
                        
                        // On peut reprendrre là ou nous en étions
                    	JPanel panelTile = settings.getPanel();
                    	
                    	JButton setPortal = new JButton("Portail");
                    	setPortal.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                               //setPortal(map,coord);
                            }
                        });
                    	panelTile.add(setPortal);
                    	
                    	JButton setSpawn = new JButton("Définir comme nouveau Spawn ?");
                    	setSpawn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                adapter.setSpawn(temp_map_path, coord);
                            }
                        });
                    	
                    	
                        panelTile.add(spawn);
                    	
                    	
                        JLabel label = new JLabel("Coordonées" + "(" + coord.getX() + ";" + coord.getY() + ")");
                        panelTile.add(label);
                        panelTile.add(setSpawn);
                        panelTile.add(Box.createRigidArea(new Dimension(0, 20)));
                        //JFrame frameTile = new JFrame("Changer la tuile");
                        List<TileModel> tiles = map.dbAdapter.getAllTiles();
                        for (TileModel tile : tiles) {

                        	JLabel labelTileID;
                        	try {
                            BufferedImage tileSprite = ImageIO
                                    .read(new File(temp_path + "/assets/img/tiles/" + tile.getSpritePath()));
                            ImageIcon spriteImg = new ImageIcon(tileSprite);
                            JLabel newTileTest = new JLabel(tile.toString(),spriteImg, JLabel.CENTER);
                            labelTileID = newTileTest;
                            } catch (IOException e1) {
                            	
                            	ImageIcon spriteImg = new ImageIcon(temp_path + "/assets/img/tiles/default.png");
                            	JLabel newTileTest = new JLabel(tile.toString(),spriteImg, JLabel.CENTER);
                            	labelTileID = newTileTest;
                            }
                            
                            panelTile.add(labelTileID, BorderLayout.CENTER);
                            
                            // Changement de tuile
                            labelTileID.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    map.changeTile(map.getMapPath(), tile, coord);
                                    BufferedImage tileSprite;
                                    try {
                                        tileSprite = ImageIO
                                                .read(new File(map.getProjectPath() + "/assets/img/tiles/"
                                                        + tile.getSpritePath()));
                                        ImageIcon spriteImg = new ImageIcon(tileSprite);
                                        tileLabel.setIcon(spriteImg);
                                    } catch (IOException e1) {
                                        
                                    }
                                    panel.repaint();
                                }
                            });
                        }
                        panelTile.setLayout(new BoxLayout(panelTile, BoxLayout.Y_AXIS));                        
                        panelTile.revalidate();
                        panelTile.repaint();
                    }
                });
            } catch (IOException e) {
                
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

    public void update(MapModel mapModel){
        System.out.println("TTEST"); 
        this.panneauPrincipal.removeAll(); 
        this.map = mapModel; 
        this.display(this.panneauPrincipal);
        panneauPrincipal.revalidate(); // Rafraîchir le panneau pour refléter les modifications
        panneauPrincipal.repaint();
    }

    

    @Override
    public void action(MapModel map) {
        this.update(map); 
    }
}
