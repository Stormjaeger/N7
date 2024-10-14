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

import GameEditor.AddCharacters.RunAddCharacter;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Map.CharacterModel;
import GameEditor.ViewModels.ViewCompetences;

public class AssetsCharactersView implements ActionListener, ValidateListener, AssetsView {
    private String pathToProject;
    private JPanel panelCharacter;
    private DatabaseAdapter characterBase;
    private ValidateListener listener; 

    public AssetsCharactersView(String path) {
        super();
        this.pathToProject = path;
        this.characterBase = new DatabaseAdapter(path);
        this.panelCharacter = new JPanel();
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
        mainPanel.add(this.panelCharacter);
    }

    public void update() {
        
        this.panelCharacter.removeAll();
        List<CharacterModel> listCharacters = this.characterBase.getAllCharacters();

        this.panelCharacter.setLayout(new BoxLayout(this.panelCharacter, BoxLayout.Y_AXIS));

        this.panelCharacter.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel title = new JLabel();
        title.setText("Personnages :");
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.panelCharacter.add(title);

        JButton button = new JButton("+");
        // button.setBorder(new EmptyBorder(0, 0, 10, 0));
        button.addActionListener(this);
        this.panelCharacter.add(button);

        for (CharacterModel character : listCharacters) {
            
            JLabel newCharacter = new JLabel();
            newCharacter.setText(character.toString());
            newCharacter.setBorder(new EmptyBorder(0, 10, 10, 0));
            System.out.println(character); 
            this.panelCharacter.add(newCharacter);
            
        }

        this.panelCharacter.revalidate();
        this.panelCharacter.repaint();
        this.listener.action(); 
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        RunAddCharacter runCharacter = new RunAddCharacter(this.pathToProject, this);
        runCharacter.display();

    }

    @Override
    public void action() {
        this.update();
        
    }

}
