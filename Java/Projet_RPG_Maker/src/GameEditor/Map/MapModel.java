package GameEditor.Map;

import java.util.HashMap;
import java.util.Map;

import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Architecture.FileAdapter;
import GameEditor.Map.Tile.TileModel;
import GameEditor.Map.Tile.Coordinate;

public class MapModel {
    private Map<Coordinate, TileModel> map;
    private Map<Coordinate, String> portal;
    public DatabaseAdapter dbAdapter;
    public FileAdapter fileAdapter;
    private String mapPath;
    private String projectPath;

    public String getProjectPath() {
        return projectPath;
    }

    public MapModel(String projectPath, String mapPath) {
        this.projectPath = projectPath;
        this.mapPath = mapPath;

        this.dbAdapter = new DatabaseAdapter(this.projectPath);
        this.fileAdapter = new FileAdapter(this.projectPath);
        
        this.map = new HashMap<Coordinate, TileModel>();
        this.portal = fileAdapter.getPortal(this.getMapPath());
    }
    
    public void removePortal(Coordinate coord) {
    	this.portal.remove(coord);
    	this.fileAdapter.setPortal(this, this.portal);
    }
    
    public void addPortal(Coordinate coord, String mapPath) {
    	this.portal.put(coord,mapPath);
    	this.fileAdapter.setPortal(this, this.portal);
    	}
    
    public Map<Coordinate, String> getPortal() {
    	return this.portal;
    	}

    public void load() {
        Map<Coordinate, Integer> mapID = this.fileAdapter.getMap(this.mapPath);
        for (Coordinate coord : mapID.keySet()) {
            TileModel tile = this.dbAdapter.getTile(mapID.get(coord));
            this.map.put(coord, tile);
        }
        portal = fileAdapter.getPortal(mapPath);
    }

    public void changeTile(String mapFile, TileModel tile, Coordinate coord) {
        this.fileAdapter.changeTile(mapFile, coord, tile.getId());
    }

    public Map<Coordinate, TileModel> getMap() {
        return this.map;
    }

    public TileModel getTile(Coordinate coord) {
        return this.map.get(coord);
    }

    public TileModel getTileID(Integer id) {
        return this.dbAdapter.getTile(id);
    }

    public String getMapPath() {
        return mapPath;
    }

    public void setTile(Coordinate coord, String spritePath, Boolean isWall) {
        TileModel tile = this.getTile(coord);
        tile.setIsWall(isWall);
        tile.setSpritePath(spritePath);
    }
    
    public Coordinate getSpawn(){
    	return fileAdapter.getSpawn(this.mapPath);
    }
    
    public void setSpawn(Coordinate coords) {
    	fileAdapter.setSpawn(this.mapPath, coords);
    }
}
