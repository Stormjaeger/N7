package GameEditor.AddCharacters;
import javax.swing.*;

import GameEditor.AddTiles.NameException;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

public class AddCharacter extends JFrame implements ActionListener{

  private String url;
  private String nom;
  private boolean isPlayable;

  private String pathToProject;
  private boolean premierValider; 
  private ValidateListener listener;
  private JFrame dialoguesFrame; 
  private Map<String, JTextField> textFields = new HashMap<>();
  private String dialogues;
  private JCheckBox refPlayable;
  private JComboBox<String> refCharacterType;
  private JComboBox<String> refZoneDetection;
  private String characterType;
  private Integer zoneDetection;
   

  public AddCharacter(String path, ValidateListener listener) {

    super("Ajouter un personnage");
    this.pathToProject = path; 
    this.listener = listener;
    this.premierValider = true;
    this.dialogues = "";  
    this.nom = "Inconnu";
    this.url = "Inconnu";
    this.isPlayable = false;
    this.characterType = "Non jouable";
    this.zoneDetection = 1; 
    this.refPlayable = new JCheckBox();

    addWindowListener(new FermerFenetre());
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
    
    // Création d'un tableau d'options pour le menu déroulant
    String[] options = {"Jouable", "Non jouable", "Monstre"};
        
    // Création du menu déroulant avec les options
    this.refCharacterType = new JComboBox<>(options);

    //Création des lignes 
    addRow(this, "url", new JTextField());
    addRow(this,"Type du personnage", this.refCharacterType);
    //addRow(this,"Le personnage est-il jouable ?", new JCheckBox());
    addRow(this, "nom", new JTextField());
    
    JButton valider = new JButton("Valider");
    addButtons(this, valider);
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

  private void addRow(JFrame frame, String titre, JComponent... components) {
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
      } else if (component instanceof JTextArea){

        
        JTextArea tmp2 = (JTextArea) component;
        JTextField tmp = new JTextField(tmp2.getText());
        System.out.println(tmp2.getText()); 
        this.textFields.put(titre, tmp);
      } 
      else if (component instanceof JCheckBox) {
        this.refPlayable = (JCheckBox) component;

      } else if (component instanceof JComboBox<?>){
        if (frame == this){
          this.refCharacterType = (JComboBox <String>) component;
        } else{
          this.refZoneDetection = (JComboBox <String>) component;
        } 
        
        
      } 
      else {
        System.out.println("Erreur sur le type de panel");
      }

    }
    frame.add(panel);
  }

  private void addButtons(JFrame frame, JButton... buttons) {
    FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);
    JPanel panel = new JPanel(flowLayout);
    for (JButton button : buttons) {
      panel.add(button);
    }
    frame.add(panel);
  }


  @Override
  public void actionPerformed(ActionEvent event) {

    //Si c'est le premier bouton Valider qui a été pressé (celui de la 1ère fenêtre)
    if (this.premierValider){

      this.nom = textFields.get("nom").getText();
      this.url = textFields.get("url").getText();
      this.characterType = (String) this.refCharacterType.getSelectedItem(); 

      setVisible(false);
      checkParameter(this.nom);

      // TEST récupération des variables (à supprimer)
      System.out.println("test (à supprimer) :");
      System.out.println("url : " + url);
      System.out.println("nom : " + nom);
      System.out.println("type : " + characterType);

      if (this.characterType.equals("Jouable")){
        

        // Ajouter un perso dans la base de donnée SQL
        DatabaseAdapter characterBase = new DatabaseAdapter(this.pathToProject);
        characterBase.addCharacter(this.url, this.characterType, this.nom, this.dialogues, "","");

        // Demander l'actualisation de l'affichage des personnages
        listener.action();
      } else  {

        //Créer une nouvelle fenêtre pour les dialogues (uniquement pour les PNJ et Monstres)
        this.premierValider = false; 
        this.dialoguesFrame = new JFrame("Dialogues");
        
        JPanel text = new JPanel();
        JLabel conseil = new JLabel("Veuillez séparer les répliques par des points virgules (;)"); 
        text.add(conseil); 

        this.dialoguesFrame.getContentPane().setLayout(new BoxLayout(dialoguesFrame.getContentPane(), BoxLayout.PAGE_AXIS));

        
        addRow(this.dialoguesFrame, "Dialogues", new JTextField(20));
        
        
        // Ajout du texte à la fenêtre
        this.dialoguesFrame.getContentPane().add(text);

        //Si le personnage non jouable est un monstre, paramétrer ses compétences
        if (this.characterType.equals("Monstre")){
          String[] options = {"1", "2", "3", "4"};
          this.refZoneDetection = new JComboBox<>(options);
          addRow(dialoguesFrame, "Zone de detection", this.refZoneDetection);
          

        } 
      
        JButton validerDialogues = new JButton("Valider");
        addButtons(dialoguesFrame, validerDialogues);
        
        this.dialoguesFrame.setSize(600,300);
        this.dialoguesFrame.setVisible(true);
        validerDialogues.addActionListener(this);
      } 

     } else{ // Si le bouton Valider de la fenêtre dialogue a été préssé 

      //Fermer la fenêtre
      this.dialoguesFrame.setVisible(false);

      //Recuperer les dialogues et autres paramètres
      this.dialogues = textFields.get("Dialogues").getText();
      this.zoneDetection = Integer.parseInt((String) this.refZoneDetection.getSelectedItem()); 
      
      //TEST
      System.out.println(this.dialogues);
      System.out.println(this.zoneDetection);

      //Enregistrer dans la base de données les dialogues
      DatabaseAdapter characterBase = new DatabaseAdapter(this.pathToProject);
      characterBase.addCharacter(this.url, this.characterType, this.nom, this.dialogues, "", "");

      // Demander l'actualisation de l'affichage des personnages
      listener.action(); // A DECOMMENTER QUAND ON L'INTEGRERA A LA FENETRE PRINCIPALE
      
      System.out.println("ok");
    }  
    
  }

  private void checkParameter(String param) {
    if (param.length() < 1 || param == null) {
      throw new NameException();
    }
  }
  public static void main(String args[]) {
    //CHEMIN A MODIFIER
    new AddCharacter("/home/aao9259/Bureau/Annee_1/TOB/Projet long/pl/project_example", null); 
    
  }     

}
