package GameEditor.ViewModels;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Map.CharacterModel;
import GameEditor.AddTiles.ValidateListener;

public class ViewCharacter implements ViewModel, ActionListener, ValidateListener {

    private String pathToProject;
    private Map<String, Integer> dicoNom = new HashMap<>();
    private Integer idCharacter; 
    private JComboBox<String> refCharacter;
    private JComboBox<String> refAttribut; 
    private String[] options;
    private JPanel panel; 
    private ValidateListener listener;
    private BufferedImage image; 
     
    public ViewCharacter(String path, ValidateListener listener) {
        this.pathToProject = path;
        this.idCharacter = 1;
        this.listener = listener; 
    } 
        
    public void display(JPanel main) {
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        this.panel = main; 
        updateOption();
        displayComboBox();
        
        // Créer les deux boutons
        JButton attributBouton = new JButton("Ajouter un attribut");
        JButton supprimerBouton = new JButton("Supprimer");

        // Ajouter les boutons au panneau
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(attributBouton);
        buttonPanel.add(Box.createHorizontalStrut(20)); 
        buttonPanel.add(supprimerBouton);
        main.add(buttonPanel);

        // Ajouter un espace vertical entre les boutons et l'image
        main.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Ajouter l'ImagePanel
        ImagePanel image = new ImagePanel(this.pathToProject, this.refCharacter, this.dicoNom); 
        image.setPreferredSize(new Dimension(500,500));
        main.add(image);

        // Ajouter les ActionListeners aux boutons
        attributBouton.addActionListener(this);
        DeleteCharacter deleteCharacter = new DeleteCharacter(pathToProject, this.refCharacter, this.dicoNom, this.listener);
        supprimerBouton.addActionListener(deleteCharacter);
        this.refCharacter.addActionListener(image); 
    }
    
    // Mettre à jour le JComboBox avec la nouvelle liste d'options
    private void updateComboBox() {
        displayComboBox();
        panel.revalidate(); // Rafraîchir le panneau pour refléter les modifications
        panel.repaint(); 
    }

    private void displayComboBox(){
        // Création du menu déroulant avec les options
        this.refCharacter = new JComboBox<>(this.options);
        refCharacter.setMaximumSize(new Dimension(100, 30));
        addRow("Personnage : ", this.panel, this.refCharacter);
    } 

    public void action(){
        update(); 
    } 

    public void update(){
        this.panel.removeAll(); 
        this.display(this.panel);
        panel.revalidate(); // Rafraîchir le panneau pour refléter les modifications
        panel.repaint(); 
    } 

    private void updateOption(){
        this.dicoNom.clear();
        // Création d'un tableau d'options pour le menu déroulant
        DatabaseAdapter base = new DatabaseAdapter(this.pathToProject);
        List<CharacterModel> characters = base.getAllCharacters();
        
        for (CharacterModel a : characters) {
            dicoNom.put(a.getNom(), a.getId()); 
        }
        // Convertir la liste en tableau
        Collection<String> valuesCollection = dicoNom.keySet();
        this.options = valuesCollection.toArray(new String[0]);
    } 

    private void addRow(String titre, JPanel main, JComponent... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setPreferredSize(new Dimension(500, 100));
        panel.setMaximumSize(new Dimension(500, 1000));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(Box.createHorizontalStrut(200));
        JLabel label = new JLabel(titre);
        label.setLabelFor(components[0]);
        panel.add(label);

        for (JComponent component : components) {
            panel.add(Box.createHorizontalStrut(10));
            panel.add(component);

            if (component instanceof JComboBox<?>){
                this.refCharacter = (JComboBox<String>) component;
            } else {
                System.out.println("Erreur sur le type de panel");
            }
        }
        main.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.idCharacter = this.dicoNom.get(this.refCharacter.getSelectedItem());
        new AddAttributeToCharacter(this.pathToProject, this.idCharacter, this.listener);
    } 
        
    
}
