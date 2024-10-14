package GameEditor.AddCharacters;
import javax.swing.*;

import GameEditor.AddTiles.NameException;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;

public class AddAttribut extends JFrame implements ActionListener {
    private int borneMin;
    private int borneMax;
    private String nom;
    private int valDefaut;
    private boolean mortAValMin;

  private String pathToProject;
  private Map<String, JTextField> textFields = new HashMap<>();
  private JCheckBox refWall;

  private ValidateListener listener;

  public AddAttribut(String pathToProject, ValidateListener listener) {

    super("Ajouter un attribut");

    this.pathToProject = pathToProject;
    this.listener = listener;
    this.nom = "Inconnu";
    this.borneMin = 0;
    this.borneMax = 0;
    this.valDefaut = 0;
    this.mortAValMin = false;
    this.refWall = new JCheckBox();

    addWindowListener(new FermerFenetre());
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

    addRow("BorneMin", new JTextField());
    addRow("BorneMax", new JTextField());
    addRow("Valeur par défaut", new JTextField());
    addRow("Le personnage meurt quand l'attribut atteint la valeur minimale ?", new JCheckBox());
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
    try {
      this.borneMin = Integer.parseInt(textFields.get("BorneMin").getText());
      this.borneMax = Integer.parseInt(textFields.get("BorneMax").getText());
      this.valDefaut = Integer.parseInt(textFields.get("Valeur par défaut").getText());
    } catch (NumberFormatException e) {
        JFrame exceptionFrame = new JFrame("Erreur");
        exceptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        exceptionFrame.setSize(600, 100); 
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Merci de renseigner un entier pour les bornes et valeur par défaut");
        panel.add(label);
        exceptionFrame.add(panel);
        exceptionFrame.setVisible(true);
    }

    this.mortAValMin = refWall.isSelected();
    setVisible(false);

    checkParameter(this.nom);

    // Ajouter une tuile dans la base de donnée SQL
    DatabaseAdapter tileBase = new DatabaseAdapter(this.pathToProject);
    tileBase.addAttribut(this.borneMin, this.borneMax, this.nom, this.valDefaut, this.mortAValMin);

    // Demander l'actualisation de l'affichage des tuiles
    listener.action();

  }

  private void checkParameter(String param) {
    if (param.length() < 1 || param == null) {
      throw new NameException();
    }
  }

  public static void main(String args[]) {
    //CHEMIN A MODIFIER
    new AddAttribut("/home/aao9259/Bureau/Annee_1/TOB/Projet long/pl/project_example", null); 
    
  }   

  
}
