package GameEditor.AddCharacters;

public class Attribut {
    private int id;
    private int borneMin;
    private int borneMax;
    private String nom;
    private int valDefaut;
    private boolean mortAValMin;

    public Attribut (int borneMin, int borneMax, String nom, int valDefaut, boolean mortAValMin){
        this.borneMin = borneMin;
        this.borneMax = borneMax;
        this.nom = nom;
        this.valDefaut = valDefaut;
        this.mortAValMin = mortAValMin;
    }

    public Attribut (int id, int borneMin, int borneMax, String nom, int valDefaut, boolean mortAValMin){
        this.id = id;
        this.borneMin = borneMin;
        this.borneMax = borneMax;
        this.nom = nom;
        this.valDefaut = valDefaut;
        this.mortAValMin = mortAValMin;
    }

    public String getNom() {
        return this.nom;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getMin() {
        return this.borneMin;
    }

    public Integer getMax() {
        return this.borneMax;
    }

    public Integer getDefaut() {
        return this.valDefaut;
    }

    public boolean getIsMortAValMin() {
        return this.mortAValMin;
    }

    @Override
    public String toString() {
        String s = this.nom + " : " + this.borneMin +" - "+ this.borneMax;
        if (this.mortAValMin) s = s + " †";
        return  s;
    }

}