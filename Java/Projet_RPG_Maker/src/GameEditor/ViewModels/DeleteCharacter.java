package GameEditor.ViewModels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;

import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;

public class DeleteCharacter implements ActionListener{
    private JComboBox id;
    private String path;
    private ValidateListener listener;
    private Map <String, Integer> dicoNom; 

    public DeleteCharacter(String path, JComboBox id, Map <String, Integer> dicoNom, ValidateListener listener){
        this.id = id;
        this.path = path;
        this.listener = listener;
        this.dicoNom = dicoNom; 
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        
        System.out.println("SUPPRESSION");
        DatabaseAdapter base = new DatabaseAdapter(path);
        base.deleteCharacter(this.dicoNom.get(this.id.getSelectedItem())); 
        listener.action();
    }

} 