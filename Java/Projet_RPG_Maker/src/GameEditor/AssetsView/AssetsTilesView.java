package GameEditor.AssetsView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import java.nio.file.attribute.BasicFileAttributes;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.util.List;

import GameEditor.AddMaps.AddMap;
import GameEditor.AddTiles.RunAddTile;
import GameEditor.ModifyTile.RunModifyTile;
import GameEditor.ViewModels.MapListener;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Map.Tile.TileModel;
import GameEditor.Map.ChoixMap;
import GameEditor.Editor.EditorView;
import GameEditor.Architecture.FileAdapter;

public class AssetsTilesView implements ActionListener, ValidateListener, AssetsView {
    private String pathToProject;
    private JPanel panelTile;
    private DatabaseAdapter tileBase;
    private JPanel mapPanel;
    private EditorView editor;
    private MapListener listener; 
    

    public AssetsTilesView(String path, EditorView editor, MapListener listener) {
        super();
        this.pathToProject = path;
        this.tileBase = new DatabaseAdapter(path);
        this.panelTile = new JPanel();
        this.editor = editor;
        this.listener = listener; 
        
        if (this.editor == null) {
        	System.out.println("wtf");
        }
    }

    public void display(JPanel mainPanel) {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new MatteBorder(-1, -1, -1, 1, Color.DARK_GRAY));
        Dimension dim = mainPanel.getSize();
        mainPanel.setPreferredSize(new Dimension(300, (int) dim.getHeight()));
        this.update();
        mainPanel.add(this.panelTile);
    }

    public void update() {
        this.panelTile.removeAll();
        
        List<TileModel> listTiles = this.tileBase.getAllTiles();	        
        this.panelTile.setLayout(new BoxLayout(this.panelTile, BoxLayout.Y_AXIS));
        this.panelTile.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        //
        // Choix des maps
        //
    	JComboBox<String> mapChoisie = new JComboBox<>();
        mapChoisie.setMaximumSize(new Dimension(200,30)); 
    	Path startPath = Paths.get(this.pathToProject + "/src/");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(startPath)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    String nomMap = entry.getFileName().toString().replace("example_", "");
                    mapChoisie.addItem(nomMap);
                }
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
		String path = this.pathToProject;

		mapChoisie.addActionListener(new ChoixMap(path, mapChoisie, this.listener)); 
        this.panelTile.add(mapChoisie);
        
        //
        // Ajout de map
        //
        JLabel addMap = new JLabel();
        addMap.setText("Ajouter une carte :");
        addMap.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.panelTile.add(addMap);

        JButton addMapButton = new JButton("+");
        addMapButton.addActionListener(new AddMap(path,this)); 
        this.panelTile.add(addMapButton);

        // Gestion des tuiles
        JLabel title = new JLabel();
        title.setText("Tuiles :");
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.panelTile.add(title);

        JButton button = new JButton("+");
        // button.setBorder(new EmptyBorder(0, 0, 10, 0));
        button.addActionListener(this);
        this.panelTile.add(button);
        
        //listing des tuiles existantes
        String temp_path = this.pathToProject;
        AssetsTilesView listener = this;
        for (TileModel tile : listTiles) {
        	JLabel newTile;
        	try {
            BufferedImage tileSprite = ImageIO
                    .read(new File(temp_path + "/assets/img/tiles/" + tile.getSpritePath()));
            ImageIcon spriteImg = new ImageIcon(tileSprite);
            JLabel newTileTest = new JLabel(tile.toString(),spriteImg, JLabel.CENTER);
            newTile = newTileTest;
            } catch (IOException e) {
            	
            	ImageIcon spriteImg = new ImageIcon(temp_path + "/assets/img/tiles/default.png");
            	JLabel newTileTest = new JLabel(tile.toString(),spriteImg, JLabel.CENTER);
            	newTile = newTileTest;
            }
            //newTile.setText(tile.toString());
            newTile.setBorder(new EmptyBorder(0, 10, 10, 0));
            
            newTile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	RunModifyTile runTile = new RunModifyTile(temp_path, listener);
                    runTile.display(tile);
                    }
                });
            
            this.panelTile.add(newTile);
            
        this.panelTile.revalidate();
        this.panelTile.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        RunAddTile runTile = new RunAddTile(this.pathToProject, this);
        runTile.display();

    }

    @Override
    public void action() {
        this.update();

    }

}