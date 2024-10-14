package GameEditor.HeaderSettings;
import GameEditor.Editor.EditorView;
import javax.swing.JComboBox;
 
import java.awt.event.*;

public class ChoixModeEdition extends JComboBox<String> implements ItemListener {
    private EditorView editor;

    public ChoixModeEdition(EditorView editor) {
        this.editor = editor;
        this.addItem("Cartes");
        this.addItem("Personnages");
        this.addItem("Monstres");
        this.addItem("Compétences");
        this.addItem("Attributs");
        this.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) { 
        if (e.getSource() == this) {
            if (this.getSelectedItem() == "Cartes") {
                this.editor.cleanEditor();
                this.editor.displayMap();
            }

            if (this.getSelectedItem() == "Personnages") {
                this.editor.cleanEditor();
                this.editor.displayCharacter();
            }

            if (this.getSelectedItem() == "Monstres") {
                this.editor.cleanEditor();
                this.editor.displayMonster();
            }

            if (this.getSelectedItem() == "Compétences") {
                this.editor.cleanEditor();
                this.editor.displayAbility();
            }

            if (this.getSelectedItem() == "Attributs") {
                this.editor.cleanEditor();
                this.editor.displayAttribute();
            }
        }
    }
}
