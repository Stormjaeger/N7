package GameEditor.ModifyTile;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Map.Tile.TileModel;

public class RunModifyTile {

    private String path;
    private ValidateListener listener;
    
    public RunModifyTile(String path, ValidateListener listener) {
        this.path = path;
        this.listener = listener;
    }

    public void display(TileModel Tile) {
        new ModifyTile(this.path, Tile, this.listener);
    }
}