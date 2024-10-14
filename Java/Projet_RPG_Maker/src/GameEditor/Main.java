package GameEditor;

import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Architecture.FileAdapter;
import GameEditor.Editor.EditorView;
import GameEditor.Map.MapModel;
import GameEditor.Map.Tile.Coordinate;

public class Main {
    public static void main(String[] args) {
        String projectPath = "/home/aao9259/Bureau/Annee_1/TOB/Projet long/pl/project_example";
        DatabaseAdapter base = new DatabaseAdapter(projectPath);
        
        base.deleteTile(15);
        MapModel mapModel = new MapModel(projectPath, "/example_map_1/map_1.map");
        mapModel.load();

        EditorView editor = new EditorView(projectPath, mapModel);
        editor.displayMap();

    }

    public static void run(String projectPath) {
        MapModel mapModel = new MapModel(projectPath, "/example_map_1/map_1.map");
        mapModel.load();

        EditorView editor = new EditorView(projectPath, mapModel);
        editor.displayMap();

    }

    public static void create(String projectPath) {
        projectPath = projectPath + "/new_project";
        FileAdapter fa = new FileAdapter(projectPath);
        fa.createFolder("");
        fa.createFolder("/src");
        fa.createFolder("/assets");
        fa.createFolder("/assets/database");
        fa.createFolder("/assets/img");
        fa.createFolder("/assets/img/characters");
        fa.createFolder("/assets/img/heros");
        fa.createFolder("/assets/img/tiles");
        fa.createFolder("/assets/img/tiles");
        fa.createFolder("/src/example_map_1");
        fa.createMap("map_1.map", 10, 10);
        // fa.setSpawn("example_map_1/map_1.map", new Coordinate(0, 0));
        MapModel mapModel = new MapModel(projectPath, "map_1");
        mapModel.load();

        // DatabaseAdapter db = new DatabaseAdapter(projectPath);

        EditorView editor = new EditorView(projectPath, mapModel);
        editor.displayMap();

    }

}
