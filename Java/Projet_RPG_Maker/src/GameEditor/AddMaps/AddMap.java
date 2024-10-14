package GameEditor.AddMaps;

import javax.swing.JFrame;
import javax.swing.*;

import GameEditor.AddTiles.NameException;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Architecture.FileAdapter;
import GameEditor.AssetsView.AssetsTilesView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;


public class AddMap implements ActionListener {
    private ValidateListener listener;
    private String path;
    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    private JFrame frame;
    private Map<String, JTextField> textFields = new HashMap<>();
  

    public AddMap(String path, ValidateListener listener){
        this.listener = listener;
        this.path = path; 
         
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //On crée le dossier

        // Créer le cadre (JFrame)
        this.frame = new JFrame("Création d'une nouvelle Map");
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2));

        // Créer les composants
        JLabel nameLabel = new JLabel("Nom de la Map:");
        this.nameField = new JTextField();
        JLabel xLabel = new JLabel("Longueur :");
        this.xField = new JTextField();
        JLabel yLabel = new JLabel("Hauteur :");
        this.yField = new JTextField();
        JButton submitButton = new JButton("Confirmer");
        submitButton.addActionListener(new AddMapFolder(this.path, this.frame, this.textFields, this.listener)); 
            

         // Ajouter les composants à la fenêtre + visibilité
         frame.add(nameLabel);
         addRow("nom", nameField); 
         frame.add(xLabel);
         addRow("x", xField); 
         frame.add(yLabel);
         addRow("y", yField); 
         frame.add(submitButton);
         frame.setVisible(true);
        
    

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
      } else {
        System.out.println("Erreur sur le type de panel");
      }

    }
    this.frame.add(panel);
  }

 
}


   

