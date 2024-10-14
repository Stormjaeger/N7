package GameEditor.AddTiles;

import javax.swing.*;

import GameEditor.Architecture.DatabaseAdapter;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;

public class AddTile extends JFrame implements ActionListener {
  private String url;
  private String nom;
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

  public boolean getIsWall() {
    return this.isWall;
  }

  public AddTile(String pathToProject, ValidateListener listener) {

    super("Ajouter une tuile");

    this.pathToProject = pathToProject;
    this.listener = listener;
    this.nom = "Inconnu";
    this.url = "Inconnu";
    this.isWall = false;
    this.refWall = new JCheckBox();

    addWindowListener(new FermerFenetre());
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

    addRow("url", new JTextField());

    addRow("La tuile est-elle un mur ?", new JCheckBox());
    addRow("nom", new JTextField());
    JButton valider = new JButton("Valider");
    addButtons(valider);
    valider.addActionListener(this);
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
    this.url = textFields.get("url").getText();
    this.isWall = refWall.isSelected();
    setVisible(false); 

    checkParameter(this.nom);

    // Ajouter une tuile dans la base de donnée SQL
    DatabaseAdapter tileBase = new DatabaseAdapter(this.pathToProject);
    tileBase.addTile(this.url, this.isWall, this.nom);

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
