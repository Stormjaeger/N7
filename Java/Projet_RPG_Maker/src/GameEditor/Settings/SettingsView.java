package GameEditor.Settings;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class SettingsView {
	JPanel mainPanel;
    public SettingsView(JPanel settingsPanel) {
        super();
        this.mainPanel = settingsPanel;
    }
    public JPanel getPanel() {
    	return this.mainPanel;
    }
    public void display(JPanel mainPanel) {
    	this.mainPanel = mainPanel;
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new MatteBorder(-1, 1, -1, -1, Color.DARK_GRAY));
        Dimension dim = mainPanel.getSize();
        mainPanel.setPreferredSize(new Dimension(300, (int) dim.getHeight()));
        JLabel title = new JLabel();
        title.setText("Settings :");
        title.setBorder(new EmptyBorder(15, 10, 15, 10));
        mainPanel.add(title);

    }
    
    public void add(JLabel label) {
    	this.mainPanel.add(label);
    }
}
