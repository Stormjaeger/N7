package GameEditor.AddCharacters;
import GameEditor.AddTiles.ValidateListener;

public class RunAddCompetence {

    private String path;
    private ValidateListener listener;

    public RunAddCompetence(String path, ValidateListener listener) {
        this.path = path;
        this.listener = listener;
    }

    public void display() {
        new AddCompetence(this.path, this.listener);
    }
}