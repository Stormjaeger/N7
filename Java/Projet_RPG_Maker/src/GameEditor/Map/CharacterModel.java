package GameEditor.Map;

public class CharacterModel {
    private Integer id;
    private String spritePath;
    private String characterType;
    private String name;
    private String idAttributs;
    private String idCompetences; 
    private Integer size;

    public CharacterModel(int character_id, String character_spritePath, String characterType, String character_name, String idAttributs, String idCompetences) {
        this.id = character_id;
        this.spritePath = character_spritePath;
        this.characterType = characterType;
        this.name = character_name;
        this.idAttributs = idAttributs;
        this.idCompetences = idCompetences; 
        this.size = 32;
    }

    public String getNom(){
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

    public String getIdAttributs() {
        return this.idAttributs;
    }

    public String getIdCompetences() {
        return this.idCompetences;
    }

    public void setSpritePath(String path) {
        this.spritePath = path;
    }

    public String getCharacterType() {
        return this.characterType;
    }

    public boolean estMonstre(){
        return this.characterType.equals("Monstre");    
    } 

    public void setCharacterType(String type) {
        this.characterType = type;
    }

    @Override
    public String toString() {
        return "Personnage " + this.id + ": " + this.name + ", " + this.spritePath + ", " + this.characterType + ", " + this.idAttributs;
    }
}

