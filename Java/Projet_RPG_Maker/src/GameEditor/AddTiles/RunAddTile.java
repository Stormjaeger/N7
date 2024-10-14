package GameEditor.AddTiles;

public class RunAddTile {

    private String path;
    private ValidateListener listener;

    public RunAddTile(String path, ValidateListener listener) {
        this.path = path;
        this.listener = listener;
    }

    public void display() {
        new AddTile(this.path, this.listener);
    }
}