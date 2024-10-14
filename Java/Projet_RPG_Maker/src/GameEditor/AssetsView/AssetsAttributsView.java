package GameEditor.AssetsView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.event.*;
import java.util.List;

import GameEditor.AddCharacters.*;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;


public class AssetsAttributsView implements ActionListener, ValidateListener, AssetsView {
    private String pathToProject;
    private JPanel panelAttribut;
    private DatabaseAdapter attributBase;
    private ValidateListener listener; 

    public AssetsAttributsView(String path) {
        super();
        this.pathToProject = path;
        this.attributBase = new DatabaseAdapter(path);
        this.panelAttribut = new JPanel();
    }

    public void setListener(ValidateListener listener){
        this.listener = listener; 
    } 

    public void display(JPanel mainPanel) {

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new MatteBorder(-1, -1, -1, 1, Color.DARK_GRAY));
        Dimension dim = mainPanel.getSize();
        mainPanel.setPreferredSize(new Dimension(300, (int) dim.getHeight()));
        this.update();
        mainPanel.add(this.panelAttribut);
    }

    public void update() {
        this.panelAttribut.removeAll();
        List<Attribut> listAttributs = this.attributBase.getAllAttributs();

        this.panelAttribut.setLayout(new BoxLayout(this.panelAttribut, BoxLayout.Y_AXIS));

        this.panelAttribut.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel title = new JLabel();
        title.setText("Attributs : ");
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.panelAttribut.add(title);

        JButton button = new JButton("+");
        // button.setBorder(new EmptyBorder(0, 0, 10, 0));
        button.addActionListener(this);
        this.panelAttribut.add(button);

        for (Attribut attribut : listAttributs) {
            JLabel newAttribut = new JLabel();
            newAttribut.setText(attribut.toString());
            newAttribut.setBorder(new EmptyBorder(0, 10, 10, 0));

            this.panelAttribut.add(newAttribut);
        }

        this.panelAttribut.revalidate();
        this.panelAttribut.repaint();
        listener.action();
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        RunAddAttribut runAttribut = new RunAddAttribut(this.pathToProject, this);
        runAttribut.display();

    }

    @Override
    public void action() {
        this.update();

    }

}