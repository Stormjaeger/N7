package GameEditor.ViewModels;

import javax.swing.*;
import GameEditor.Architecture.DatabaseAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel implements ActionListener {

    private BufferedImage image;
    private JComboBox<String> ref;
    private Map<String, Integer> dico;
    private String path;

    public ImagePanel(String path, JComboBox<String> ref, Map<String, Integer> dico) {
        this.path = path;
        this.ref = ref;
        this.dico = dico;

        DatabaseAdapter base = new DatabaseAdapter(path);
        if (this.dico.get(this.ref.getSelectedItem()) == null) {
            System.out.println("NULL !");
        }
        String dossier = base.getCharacter(this.dico.get(this.ref.getSelectedItem())).getSpritePath();
        
        String chemin = this.path + "/assets/img/heros/" + dossier + "/" + dossier + "_down_1.png"; 
        System.out.println(chemin); 
        this.setImage(chemin);
    }

    // Charger l'image depuis un chemin
    public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner l'image sur le JPanel
        if (image != null) {
            // Redimensionner l'image à 300x300 pixels
            Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            
            // Calculer les coordonnées pour centrer l'image
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int x = (panelWidth - 500) / 2;
            int y = (panelHeight - 500) / 2;

            g.drawImage(scaledImage, x+100, y, 300, 300, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DatabaseAdapter base = new DatabaseAdapter(path);
        String dossier = base.getCharacter(this.dico.get(this.ref.getSelectedItem())).getSpritePath();
        String chemin = this.path + "/assets/img/heros/" + dossier + "/" + dossier + "_down_1.png"; 
        this.setImage(chemin);
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Dummy JComboBox and Map for example purposes
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Item1", "Item2"});
        Map<String, Integer> dummyMap = Map.of("Item1", 1, "Item2", 2);

        // Créer un ImagePanel avec les éléments spécifiés
        ImagePanel imagePanel = new ImagePanel("path/to/your/database", comboBox, dummyMap);
        imagePanel.setPreferredSize(new Dimension(500, 500));

        // Ajouter l'ImagePanel au cadre
        frame.getContentPane().add(imagePanel);
        frame.pack(); // Ajuste la taille du frame selon la taille préférée des composants
        frame.setVisible(true);
    }
}
