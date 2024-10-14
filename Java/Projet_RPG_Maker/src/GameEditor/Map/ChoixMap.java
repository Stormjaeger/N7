package GameEditor.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import GameEditor.AddTiles.ValidateListener;
import GameEditor.Editor.EditorView;
import GameEditor.ViewModels.MapListener;

public class ChoixMap implements ActionListener {
	private String path;
	private JComboBox<String> mapChoisie; 
	private MapListener listener; 
	
	
	public String getPath() {
		return this.path;
	}
	public ChoixMap(String path, JComboBox<String> mapChoisie, MapListener listener) {
		this.path = path;
		this.mapChoisie  = mapChoisie;
		this.listener = listener; 
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String choix = (String) mapChoisie.getSelectedItem();
		System.out.println(choix); 
		MapModel mapModel = new MapModel(this.path, "/example_" + choix + "/" + choix + ".map");
        mapModel.fileAdapter.setSpawn(mapModel.getMapPath(), mapModel.fileAdapter.getSpawn(mapModel.getMapPath()));
        mapModel.load();
		listener.action(mapModel); 

	}

}
