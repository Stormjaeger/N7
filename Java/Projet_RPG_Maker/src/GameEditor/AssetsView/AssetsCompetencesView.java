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


public class AssetsCompetencesView implements ActionListener, ValidateListener, AssetsView {
    private String pathToProject;
    private JPanel panelCompetence;
    private DatabaseAdapter competenceBase;
    private ValidateListener listener;

    public AssetsCompetencesView(String path) {
        super();
        this.pathToProject = path;
        this.competenceBase = new DatabaseAdapter(path);
        this.panelCompetence = new JPanel();
    }

    public void setListener (ValidateListener listener){
        this.listener = listener; 
    } 

    public void display(JPanel mainPanel) {

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new MatteBorder(-1, -1, -1, 1, Color.DARK_GRAY));
        Dimension dim = mainPanel.getSize();
        mainPanel.setPreferredSize(new Dimension(300, (int) dim.getHeight()));
        this.update();
        mainPanel.add(this.panelCompetence);
    }

    public void update() {
        this.panelCompetence.removeAll();
        List<Competence> listCompetences = this.competenceBase.getAllCompetences();

        this.panelCompetence.setLayout(new BoxLayout(this.panelCompetence, BoxLayout.Y_AXIS));

        this.panelCompetence.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel title = new JLabel();
        title.setText("Compétences :");
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.panelCompetence.add(title);

        JButton button = new JButton("+");
        // button.setBorder(new EmptyBorder(0, 0, 10, 0));
        button.addActionListener(this);
        this.panelCompetence.add(button);

        for (Competence competence : listCompetences) {
            JLabel newCompetence = new JLabel();
            newCompetence.setText(competence.toString());
            newCompetence.setBorder(new EmptyBorder(0, 10, 10, 0));

            this.panelCompetence.add(newCompetence);
        }

        this.panelCompetence.revalidate();
        this.panelCompetence.repaint();
        this.listener.action(); 
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        RunAddCompetence runCompetence = new RunAddCompetence(this.pathToProject, this);
        runCompetence.display();

    }

    @Override
    public void action() {
        this.update();

    }

}