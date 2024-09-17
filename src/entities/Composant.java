package entities;

public class Composant {

    protected int id;
    protected String nom;
    protected String typeComposant;
    protected Double taux_TVA;

    public Composant(int id, String nom, String typeComposant, Double taux_TVA) {
        this.id = id;
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.taux_TVA = taux_TVA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }

    public Double getTaux_TVA() {
        return taux_TVA;
    }

    public void setTaux_TVA(Double taux_TVA) {
        this.taux_TVA = taux_TVA;
    }

    @Override
    public String toString() {
        return "Composant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", typeComposant='" + typeComposant + '\'' +
                ", taux_TVA=" + taux_TVA +
                '}';
    }
}
