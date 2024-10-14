package GameEditor.Map.Tile;

public class TileModel {
    private Integer id;
    private String spritePath;
    private boolean isWall;
    private String name;
    private Integer size;

    public TileModel(int tile_id, String tile_spritePath, boolean tile_isWall, String tile_name) {
        this.id = tile_id;
        this.spritePath = tile_spritePath;
        this.isWall = tile_isWall;
        this.name = tile_name;
        this.size = 32;
    }

    public String getName() {
        return this.name;
    }
    
    public Integer getSize() {
        return this.size;
    }

    public Integer getId() {
        return this.id;
    }

    public String getSpritePath() {
        return this.spritePath;
    }

    public void setSpritePath(String path) {
        this.spritePath = path;
    }

    public boolean getIsWall() {
        return this.isWall;
    }

    public void setIsWall(Boolean wall) {
        this.isWall = wall;
    }

    @Override
    public String toString() {
        return "Tuile " + this.id + ": " + this.name;
    }
}
