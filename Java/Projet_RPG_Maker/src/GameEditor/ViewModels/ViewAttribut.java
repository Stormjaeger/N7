package GameEditor.ViewModels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameEditor.AddCharacters.Attribut;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;

public class ViewAttribut implements ViewModel, ActionListener, ValidateListener {

    private String pathToProject;
    private Map<String, Integer> dicoNom = new HashMap<>();
    private Integer idAttribut;
    private JComboBox<String> refAttribut;
    private String[] options;
    private JPanel panel;
    private ValidateListener listener;
    private BufferedImage image;

    public ViewAttribut(String path, ValidateListener listener) {
        this.pathToProject = path;
        this.idAttribut = 0;
        this.listener = listener;
    }

    public void display(JPanel main) {
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 30));
        this.panel = main;
        updateOption();
        displayComboBox();

        // Créer le bouton supprimer
        JButton supprimerBouton = new JButton("Supprimer");
        supprimerBouton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Centrer le bouton horizontalement
        supprimerBouton.addActionListener(this);

        // Ajouter le bouton au panneau
        main.add(Box.createVerticalStrut(10)); // Ajouter un petit espace vertical
        main.add(supprimerBouton);
    }

    private void displayComboBox() {
        // Création du menu déroulant avec les options
        this.refAttribut = new JComboBox<>(this.options);
        refAttribut.setMaximumSize(new Dimension(200, 30));
        addRow("Attribut : ", this.panel, this.refAttribut);
    }

    public void action() {
        this.update();
    }

    public void update() {
        this.panel.removeAll();
        this.display(this.panel);
        panel.revalidate(); // Rafraîchir le panneau pour refléter les modifications
        panel.repaint();
    }

    private void updateOption() {
        this.dicoNom.clear();
        // Création d'un tableau d'options pour le menu déroulant
        DatabaseAdapter base = new DatabaseAdapter(this.pathToProject);
        List<Attribut> attributs = base.getAllAttributs();

        for (Attribut a : attributs) {
            dicoNom.put(a.getNom(), a.getId());
        }
        // Convertir la liste en tableau
        Collection<String> valuesCollection = dicoNom.keySet();
        this.options = valuesCollection.toArray(new String[0]);
    }

    private void addRow(String titre, JPanel main, JComponent... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setPreferredSize(new Dimension(200, 30));
        panel.setMaximumSize(new Dimension(400, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 150, 5, 100));
        JLabel label = new JLabel(titre);
        label.setLabelFor(components[0]);
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));

        for (JComponent component : components) {
            panel.add(component);
            if (component instanceof JComboBox<?>) {
                this.refAttribut = (JComboBox<String>) component;
            } else {
                System.out.println("Erreur sur le type de panel");
            }
        }
        main.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.idAttribut = this.dicoNom.get(this.refAttribut.getSelectedItem());
        DatabaseAdapter base = new DatabaseAdapter(this.pathToProject);
        base.deleteAttribut(this.idAttribut);
        listener.action();
    }
}


