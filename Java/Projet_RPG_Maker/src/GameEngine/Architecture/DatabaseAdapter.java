package GameEngine.Architecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import GameEngine.Map.Tile.TileModel;

public class DatabaseAdapter {
    private String dbPath;
    private Connection connection;

    public DatabaseAdapter(String pathToProject) {
        this.dbPath = "jdbc:sqlite:" + pathToProject + "/assets/database/";
        try {
            this.createTableTiles();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connect(String dbName) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: SQLite not found...");
            e.printStackTrace();
        }
        this.connection = DriverManager.getConnection(this.dbPath + dbName);
    }

    private void close() throws SQLException {
        this.connection.close();
    }

    /**
     * Creation de la table tuile si elle n'existe pas
     * 
     * @throws SQLException
     */
    private void createTableTiles() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tiles (id integer PRIMARY KEY, name text, path text, isWall boolean, modifiers text);";
        this.connect("game.db");
        Statement stmt = this.connection.createStatement();
        stmt.execute(sql);

    }

    /**
     * Ajouter une tuile dans la db
     */
    public void addTile(String tilePath, Boolean isWall, String name) {
        try {
            String sql = "INSERT INTO tiles(name,path,isWall) VALUES(?,?,?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, tilePath);
            pstmt.setBoolean(3, isWall);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recuperer une tuile
     */
    public TileModel getTile(Integer id) {
        TileModel tile = null;
        try {
            String sql = "SELECT * FROM tiles WHERE (id) = (?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                tile = new TileModel(result.getInt("id"), result.getString("path"), result.getBoolean("isWall"),
                        result.getString("name"));
            } else {
                tile = new TileModel(0, "default.png", false, "default");
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tile;
    }

    /**
     * Récupérer toutes les tuiles créées
     */
    public List<TileModel> getAllTiles() {
        List<TileModel> tiles = new ArrayList<TileModel>();
        try {
            String sql = "SELECT * FROM tiles";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                TileModel tile = new TileModel(result.getInt("id"), result.getString("path"),
                        result.getBoolean("isWall"),
                        result.getString("name"));
                tiles.add(tile);
                System.out.println(tile);
            }

            this.close();

            return tiles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    public void deleteTile(Integer id) {
        try {
            String sql = "DELETE FROM tiles WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPlayer() {
        String player = null;
        try {
            String sql = "SELECT name FROM characters WHERE characterType = 'Jouable' LIMIT 1";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                player = result.getString("name");
            }
            result.close();
            pstmt.close();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }
}
