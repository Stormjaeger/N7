package GameEditor.AddCharacters;

public class Competence {
    private String nom; 
    private Attribut attribut; 
    private double coefMult;
    private double coefAdd;
    private Integer id; 
   

    public Competence (Integer id, String nom, Attribut attribut, double coefAdd, double coefMult){
        this.nom = nom;
        this.id = id;  
        this.attribut = attribut; 
        this.coefMult = coefMult; 
        this.coefAdd = coefAdd; 
    }

    public String getNom(){
        return this.nom; 
    }

    public Integer getId(){
        return this.id; 
    } 

    @Override
    public String toString() {
        return this.nom + " : " + this.coefMult +" x "+ this.attribut.getNom() + " + " + this.coefAdd;
    }



}
