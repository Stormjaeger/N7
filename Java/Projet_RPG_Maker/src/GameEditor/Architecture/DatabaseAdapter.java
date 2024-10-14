package GameEditor.Architecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import GameEditor.Map.CharacterModel;
import GameEditor.Map.Tile.TileModel;
import GameEditor.AddCharacters.Attribut;
import GameEditor.AddCharacters.Competence;
public class DatabaseAdapter {
    private String dbPath;
    private Connection connection;

    public DatabaseAdapter(String pathToProject) {
        this.dbPath = "jdbc:sqlite:" + pathToProject + "/assets/database/";
        try {
            this.createTableTiles();
            this.createTableCharacters();
            this.createTableAttribut();
            this.createTableCompetences();
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
     * Creation de la table personnage si elle n'existe pas
     * 
     * @throws SQLException
     */
    private void createTableCharacters() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS characters (id integer PRIMARY KEY, name text, path text, characterType text, dialogues text, idAttributs text, idCompetences text, modifiers text);";
        this.connect("game.db");
        Statement stmt = this.connection.createStatement();
        stmt.execute(sql);

    }

    /**
     * Creation de la table attribut si elle n'existe pas
     * 
     * @throws SQLException
     */
    private void createTableAttribut() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS attributs (id integer PRIMARY KEY, name text, min int, max int, defaut int, deathOnMin boolean, modifiers text);";
        this.connect("game.db");
        Statement stmt = this.connection.createStatement();
        stmt.execute(sql);

    }

    /**
     * Creation de la table competences si elle n'existe pas
     * 
     * @throws SQLException
     */
    private void createTableCompetences() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS competences (id integer PRIMARY KEY, name text, idAttribut integer, coefAdd double, coefMult double, modifiers text);";
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
     * Ajouter un personnage dans la db
     */
    public void addCharacter(String characterPath, String characterType, String name, String dialogues, String idAttributs, String idCompetences) {
        try {
            String sql = "INSERT INTO characters(name,path,characterType,dialogues,idAttributs, idCompetences) VALUES(?,?,?,?,?,?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, characterPath);
            pstmt.setString(3, characterType);
            pstmt.setString(4, dialogues);
            pstmt.setString(5, idAttributs);
            pstmt.setString(6, idCompetences);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajouter un attribut dans la db
     */
    public void addAttribut(int min, int max, String name, int defaut, boolean deathOnMin) {
        try {
            String sql = "INSERT INTO attributs(name,min,max,defaut,deathOnMin) VALUES(?,?,?,?,?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, min);
            pstmt.setInt(3, max);
            pstmt.setInt(4, defaut); 
            pstmt.setBoolean(5, deathOnMin);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajouter un attribut dans la db
     */
    public void addCompetence(String name, int idAttribut, double coefAdd, double coefMult) {
        try {
            String sql = "INSERT INTO competences(name,idAttribut,coefAdd,coefMult) VALUES(?,?,?,?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, idAttribut);
            pstmt.setDouble(3, coefAdd);
            pstmt.setDouble(4, coefMult); 
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
     * Recuperer un personnage
     */
    public CharacterModel getCharacter(Integer id) {
        CharacterModel character = new CharacterModel(0, "default.png", "Non Jouable", "default", "", "");
        if (id == null){
            
            //addCharacter("default.png", " Non Jouable", "default", "", "", "");
            return character;
        }  
        
        
        try {
            String sql = "SELECT * FROM characters WHERE (id) = (?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                character = new CharacterModel(result.getInt("id"), result.getString("path"), result.getString("characterType"),
                        result.getString("name"), result.getString("idAttributs"), result.getString("idCompetences"));
            } else {
                
                addCharacter("characterPath", "Jouable", "Nom ", "", "", "");
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return character;
    }

    /**
     * Recuperer un attribut
     */
    public Attribut getAttribut(Integer id) {
        Attribut attribut = null;
        try {
            String sql = "SELECT * FROM attributs WHERE (id) = (?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                attribut = new Attribut(result.getInt("id"), result.getInt("min"),
                    result.getInt("max"),
                    result.getString("name"),
                    result.getInt("defaut"),
                    result.getBoolean("deathOnMin"));
            } else {
                attribut = new Attribut(0, 0, 0, "default",0,false);
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attribut;
    }

    /**
     * Recuperer une competence
     */
    public Competence getCompetence(Integer id) {
        Competence competence = null;
        try {
            String sql = "SELECT * FROM competences WHERE (id) = (?)";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                competence = new Competence(result.getInt("id"), result.getString("name"), getAttribut(result.getInt("id")),
                    result.getDouble("coefAdd"),
                    result.getDouble("coefMult"));
                    
            } else {
                competence = new Competence(result.getInt("id"),"default", getAttribut(1), 0.0,1.0);
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competence;
    }

    /**
     * Récupérer toutes les tuiles créées
     */
    public  List<TileModel>getAllTiles() {
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
                
                //Mis en muet pour ne pas flood la console
                //System.out.println(tile);
            }

            this.close();

            return tiles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    /**
     * Récupérer tous les personnages crées
     */
    public  List<CharacterModel>getAllCharacters() {
        List<CharacterModel> characters = new ArrayList<CharacterModel>();
        try {
            String sql = "SELECT * FROM characters";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                CharacterModel character = new CharacterModel(result.getInt("id"), result.getString("path"),
                        result.getString("characterType"),
                        result.getString("name"), result.getString("idAttributs"),  result.getString("idCompetences"));
                characters.add(character);
                System.out.println(character);
            }

            this.close();

            return characters;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return characters;
    }

    public  List<Attribut>getAllAttributs() {
        List<Attribut> attributs = new ArrayList<Attribut>();
        try {
            String sql = "SELECT * FROM attributs";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                Attribut attribut = new Attribut(result.getInt("id"), result.getInt("min"),
                        result.getInt("max"),
                        result.getString("name"),
                        result.getInt("defaut"),
                        result.getBoolean("deathOnMin"));
                attributs.add(attribut);
                System.out.println(attribut);
            }

            this.close();

            return attributs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributs;
    }

    public  List<Competence>getAllCompetences() {
        List<Competence> competences = new ArrayList<Competence>();
        try {
            String sql = "SELECT * FROM competences";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                Competence competence = new Competence(result.getInt("id"), result.getString("name"), getAttribut(result.getInt("id")),
                    result.getDouble("coefAdd"),
                    result.getDouble("coefMult"));
              
                competences.add(competence);
                System.out.println(competence);
            }

            this.close();

            return competences;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competences;
    }
    
    public void updateTile(Integer id, String nom, String url, Boolean IsWall){
        try {
            String sql = "UPDATE tiles SET name = ?, path = ? ,isWall = ? WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, url);
            pstmt.setBoolean(3, IsWall);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            this.close(); 
            
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
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

    public void deleteCharacter(Integer id) {
        try {
            System.out.println("DELETE CHACRATER");
            String sql = "DELETE FROM characters WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAttribut(Integer id) {
        try {
            String sql = "DELETE FROM attributs WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompetence(Integer id) {
        try {
            String sql = "DELETE FROM competences WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCharacterAttribut(Integer idCharacter, String nvIdAttribut){
        try {
            String sql = "UPDATE characters SET idAttributs = ? WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, nvIdAttribut);
            pstmt.setInt(2, idCharacter);
            pstmt.executeUpdate();
            this.close(); 
            
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }

    public void updateCharacterCompetence(Integer idCharacter, String nvIdCompetence){
        try {
            String sql = "UPDATE characters SET idCompetences = ? WHERE id = ?";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, nvIdCompetence);
            pstmt.setInt(2, idCharacter);
            pstmt.executeUpdate();
            this.close(); 
            
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }

    public String getPlayer() {
        String player = null;
        try {
            String sql = "SELECT path FROM characters LIMIT 1";
            this.connect("game.db");
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                player = result.getString("path");
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
