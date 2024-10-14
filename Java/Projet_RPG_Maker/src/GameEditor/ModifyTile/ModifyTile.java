package GameEditor.ModifyTile;

import javax.swing.*;

import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Map.Tile.TileModel;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;

public class ModifyTile extends JFrame implements ActionListener {
  private String url;
  private String nom;
  private Integer id;
  private boolean isWall;

  private String pathToProject;
  private Map<String, JTextField> textFields = new HashMap<>();
  private JCheckBox refWall;

  private ValidateListener listener;

  public String getUrl() {
    return this.url;
  }

  public String getNom() {
    return this.nom;
  }
  
  public Integer getId() {
	    return this.id;
	  }

  public boolean getIsWall() {
    return this.isWall;
  }

  public ModifyTile(String pathToProject, TileModel Tile, ValidateListener listener) {
	// On récupére les data de la tuile déjà existante
	super("Modifier une tuile");
	this.url = Tile.getSpritePath();
	this.nom = Tile.getName();
	this.isWall = Tile.getIsWall();
	this.id = Tile.getId();

    this.pathToProject = pathToProject;
    this.listener = listener;

    this.refWall = new JCheckBox();
    addWindowListener(new FermerFenetre());
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

    addRow("Chemin", new JTextField(this.getUrl()));

    addRow("La tuile est-elle un mur ?", new JCheckBox("",this.getIsWall()));
    addRow("nom", new JTextField(this.getNom()));
    JButton valider = new JButton("Valider");
    addButtons(valider);
    valider.addActionListener(this);
    
    JButton supprimer = new JButton("Supprimer");
    addButtons(supprimer);
    ModifyTile projet = this;
    supprimer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            DatabaseAdapter tileBase = new DatabaseAdapter(projet.pathToProject);
            tileBase.deleteTile(projet.getId());
            projet.listener.action();
        }
    });

    
    // JPanel panneau = new JPanel();

    setSize(600, 300);
    setVisible(true);
  }

  private class FermerFenetre extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
      dispose();
    }
  }

  private void addRow(String titre, JComponent... components) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

    JLabel label = new JLabel(titre);
    label.setLabelFor(components[0]);
    panel.add(label);

    for (JComponent component : components) {
      panel.add(Box.createHorizontalStrut(10));
      panel.add(component);

      if (component instanceof JTextField) {
        this.textFields.put(titre, (JTextField) component);
      } else if (component instanceof JCheckBox) {
        this.refWall = (JCheckBox) component;

      } else {
        System.out.println("Erreur sur le type de panel");
      }

    }
    this.add(panel);
  }

  private void addButtons(JButton... buttons) {
    FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);
    JPanel panel = new JPanel(flowLayout);
    for (JButton button : buttons) {
      panel.add(button);
    }
    this.add(panel);
  }

  @Override
  public void actionPerformed(ActionEvent event) {

    this.nom = textFields.get("nom").getText();
    this.url = textFields.get("Chemin").getText();
    this.isWall = refWall.isSelected();
    setVisible(false); // INFO : ca ca ne ferme pas la fenetre c chiant !

    checkParameter(this.nom);

    // Ajouter une tuile dans la base de donnée SQL
    DatabaseAdapter tileBase = new DatabaseAdapter(this.pathToProject);
    tileBase.updateTile(this.getId(),this.getNom(),this.getUrl() ,this.isWall);
    
    // TEST récupération des variables (à supprimer)
    System.out.println("test (à supprimer) :");
    System.out.println("url : " + url);
    System.out.println("nom : " + nom);
    System.out.println("est un mur : " + isWall);

    // Demander l'actualisation de l'affichage des tuiles
    listener.action();

  }

  private void checkParameter(String param) {
    if (param.length() < 1 || param == null) {
      throw new NameException();
    }
  }
}
