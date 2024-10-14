package GameEditor.ViewModels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import GameEditor.AddCharacters.Attribut;
import GameEditor.AddCharacters.Competence;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.AssetsView.AssetsCharactersView;
import GameEditor.Map.CharacterModel;

import javax.swing.JFrame;

public class AddCompetenceToCharacter extends JFrame implements ActionListener {
    private String pathToProject;
    private Integer idAttribut;
    private Integer idCharacter;
    private Map<String, Integer> dicoNom = new HashMap<>();
    private JComboBox refAttribut;
    private ValidateListener listener;

    public AddCompetenceToCharacter(String path, Integer idCharacter, ValidateListener listener) {

        super("Compétence à ajouter");
        this.pathToProject = path;
        this.idCharacter = idCharacter;
        this.idAttribut = 1;
        this.listener = listener;

        setSize(400, 200);

        // Créer le panneau principal (JPanel)
        JPanel panel = new JPanel();

        addWindowListener(new FermerFenetre());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel("Sélectionner la compétence à ajouter");

        // Création d'un tableau d'options pour le menu déroulant
        DatabaseAdapter attributBase = new DatabaseAdapter(this.pathToProject);
        List<Competence> attributs = new ArrayList<>();

        attributs = attributBase.getAllCompetences();

        for (Competence a : attributs) {

            dicoNom.put(a.getNom(), a.getId());
        }
        // Convertir la liste en tableau
        Collection<String> valuesCollection = dicoNom.keySet();
        String[] options = valuesCollection.toArray(new String[0]);

        // Création du menu déroulant avec les options
        this.refAttribut = new JComboBox<>(options);

        JComboBox<String> comboBox = new JComboBox<>(options);

        // Ajouter le label et le JComboBox au panneau
        panel.add(label);
        // Ajouter le panneau à la fenêtre
        this.add(panel);
        // panel.add(Box.createRigidArea(new Dimension(0, 2))); // Ajouter un espace
        // vertical entre le label et le JComboBox
        addRow("Compétence", comboBox);

        JButton valider = new JButton("Valider");
        addButtons(valider);
        valider.addActionListener(this);

        setSize(600, 300);
        setVisible(true);

    }

    private void addRow(String titre, JComponent... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        JLabel label = new JLabel(titre);
        label.setLabelFor(components[0]);
        panel.add(label);

        for (JComponent component : components) {
            panel.add(Box.createHorizontalStrut(10));
            panel.add(component);

            if (component instanceof JComboBox<?>) {

                this.refAttribut = (JComboBox<String>) component;

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

    private class FermerFenetre extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);

        this.idAttribut = this.dicoNom.get((String) this.refAttribut.getSelectedItem());
        DatabaseAdapter base = new DatabaseAdapter(this.pathToProject);

        System.out.println("Liste des compétences avant : " + base.getCharacter(this.idCharacter));

        String listeAttributs = (base.getCharacter(this.idCharacter)).getIdCompetences();
        listeAttributs = listeAttributs + ", " + this.idAttribut;
        base.updateCharacterCompetence(this.idCharacter, listeAttributs);

        // TEST
        System.out.println("Liste des compétences après : " + base.getCharacter(this.idCharacter));
        listener.action();

    }

    public static void main(String args[]) {
        // CHEMIN A MODIFIER
        new AddAttributeToCharacter("/home/aao9259/Bureau/Annee_1/TOB/Projet long/pl/project_example", 1, null);

    }

}
