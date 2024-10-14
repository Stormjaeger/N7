package GameEditor.AddCharacters;


import javax.swing.*;

import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.FlowLayout;

public class AddCompetence extends JFrame implements ActionListener {
    private String nom; 
    private Integer idAttribut; 
    private double coefMult;
    private double coefAdd;
   
    private String pathToProject;
    private Map<String, JTextField> textFields = new HashMap<>();
    private Map<String, Integer> dicoNom = new HashMap<>();
    private JComboBox<String> refAttribut;
    

    private ValidateListener listener;



    public AddCompetence(String pathToProject, ValidateListener listener) {

        super("Ajouter une compétence");

        this.pathToProject = pathToProject;
        this.listener = listener;
        this.idAttribut = 0;
        this.coefMult = 1;
        this.coefAdd = 0;
        this.nom = "Inconnu";
        

        addWindowListener(new FermerFenetre());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        // Création d'un tableau d'options pour le menu déroulant
        DatabaseAdapter attributBase = new DatabaseAdapter(this.pathToProject);
        List<Attribut> attributs = new ArrayList<>();
        
    
        attributs = attributBase.getAllAttributs();
        
        for (Attribut a : attributs) {
            
            dicoNom.put(a.getNom(), a.getId()); 
        }
        // Convertir la liste en tableau
        Collection<String> valuesCollection = dicoNom.keySet();
        String[] options = valuesCollection.toArray(new String[0]);
        
        // Création du menu déroulant avec les options
        this.refAttribut = new JComboBox<>(options);

        //Création des lignes 
        addRow("Nom", new JTextField());
        addRow("Attribut", this.refAttribut);
        addRow("Coefficient multiplicatif", new JTextField());
        addRow("Coefficient additif", new JTextField());
        
        JButton valider = new JButton("Valider");
        addButtons(valider);
        valider.addActionListener(this);
     
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
        } else if (component instanceof JComboBox<?>){
            
            this.refAttribut = (JComboBox <String>) component;

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

        try {
            this.idAttribut = this.dicoNom.get((String) this.refAttribut.getSelectedItem());
             
            this.nom = textFields.get("Nom").getText();
            
            this.coefAdd = Double.parseDouble(textFields.get("Coefficient additif").getText());
            this.coefMult = Double.parseDouble(textFields.get("Coefficient multiplicatif").getText());

            setVisible(false);

    

        } catch (NumberFormatException e) {
            this.setVisible(false);
            JFrame exceptionFrame = new JFrame("Erreur");
        
            exceptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            exceptionFrame.setSize(400, 100); // Définir la taille de la fenêtre

            // Créer un panneau
            JPanel exceptionPanel = new JPanel();

            // Créer un label avec du texte
            JLabel exceptionLabel = new JLabel("Merci de renseigner un nombre pour les coefficients");

            // Ajouter le label au panneau
            exceptionPanel.add(exceptionLabel);

            // Ajouter le panneau à la fenêtre
            exceptionFrame.add(exceptionPanel);

            // Rendre la fenêtre visible
            exceptionFrame.setVisible(true);

        }



        //enregistrement dans la base de donnée SQL
        DatabaseAdapter base = new DatabaseAdapter(this.pathToProject);
        base.addCompetence(this.nom, this.idAttribut, this.coefAdd, this.coefMult);

        // TEST récupération des variables (à supprimer)
        System.out.println("test (à supprimer) :");
        System.out.println("coef additif : " + this.coefAdd);
        System.out.println("coef multi : " + this.coefMult);
        System.out.println("id: " + this.idAttribut);
        System.out.println("Nom: " + this.nom);
        

        // Demander l'actualisation de l'affichage des compétences
        listener.action();
        

    }

    public static void main(String args[]) {
        //CHEMIN A MODIFIER
        new AddCompetence("/home/aao9259/Bureau/Annee_1/TOB/Projet long/pl/project_example", null); 
        
      } 

}

    
