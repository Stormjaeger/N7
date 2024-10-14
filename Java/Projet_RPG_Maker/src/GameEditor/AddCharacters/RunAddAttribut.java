package GameEditor.AddCharacters;
import GameEditor.AddTiles.ValidateListener;

public class RunAddAttribut {

    private String path;
    private ValidateListener listener;

    public RunAddAttribut(String path, ValidateListener listener) {
        this.path = path;
        this.listener = listener;
    }

    public void display() {
        new AddAttribut(this.path, this.listener);
    }
}