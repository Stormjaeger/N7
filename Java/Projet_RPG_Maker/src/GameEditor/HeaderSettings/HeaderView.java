package GameEditor.HeaderSettings;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import GameEngine.Run;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GameEditor.Editor.EditorView;

public class HeaderView {
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private EditorView editor;
    Run demo;
    
    public HeaderView(EditorView editor) {
        this.leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.editor = editor;
    }

    public void display(JPanel mainPanel) {
        mainPanel.setLayout(new GridLayout(1, 3));
        mainPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        JLabel modeEdit = new JLabel();
        modeEdit.setText("Mode d'édition: ");
        this.leftPanel.add(modeEdit);

        ChoixModeEdition modeBox = new ChoixModeEdition(editor);
        this.leftPanel.add(modeBox);

        JButton playButton = new JButton("Play");
        this.centerPanel.add(playButton);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // on lance le jeu
                Run demonstration = new Run(new GameEngine.Map.MapModel(editor.getMap().getProjectPath(),editor.getMap().getMapPath()));
                demo = demonstration;
                demo.running();
            }
        });
        
        JButton pauseButton = new JButton("Pause");
        this.centerPanel.add(pauseButton);

        JButton stopButton = new JButton("Stop");
        this.centerPanel.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // on veut close la window
            	if (demo == null) {
            	} else {
            		demo.gameWindow.close();
            	}
            }
        });

        mainPanel.add(this.leftPanel);
        mainPanel.add(this.centerPanel);
        mainPanel.add(this.rightPanel);
    }

    
}
