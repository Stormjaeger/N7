package Launcher;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LauncherView extends JFrame implements ActionListener {
    boolean open = false;
    boolean create = false;
    String[] path;

    public LauncherView() {
        super("Ystoria Launcher");
        BufferedImage logo;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel bg = new JPanel();
        JPanel fg = new JPanel();

        try {
            logo = ImageIO.read(new File("res/logo.jpg"));
            ImageIcon logoImg = new ImageIcon(logo);
            JLabel logoLabel = new JLabel(logoImg);
            bg.add(logoLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = new String[1];
        addWindowListener(new FermerFenetre());
        JButton create = new JButton("Créer un nouveau jeu");
        JFrame frame = new JFrame();
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    path[0] = file.getAbsolutePath();
                    dispose();
                    GameEditor.Main.create(path[0]);
                } else {
                    path = null;
                }
            }
        });
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        fg.add(create);

        JButton button = new JButton("Ouvrir un projet");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    path[0] = file.getAbsolutePath();
                    dispose();
                    GameEditor.Main.run(path[0]);
                } else {
                    path = null;
                }
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        fg.add(Box.createVerticalStrut(10));
        fg.add(button);
        fg.setOpaque(false);
        mainPanel.add(bg);
        mainPanel.add(fg);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }

    private class FermerFenetre extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            dispose();
        }
    }

}
