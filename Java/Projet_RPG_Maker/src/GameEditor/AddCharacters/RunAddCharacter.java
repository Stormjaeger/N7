package GameEditor.AddCharacters;
import GameEditor.AddTiles.ValidateListener;

public class RunAddCharacter {

    private String path;
    private ValidateListener listener;

    public RunAddCharacter(String path, ValidateListener listener) {
        this.path = path;
        this.listener = listener;
    }

    public void display() {
        new AddCharacter(this.path, this.listener);
    }
}