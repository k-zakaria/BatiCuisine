package entities;

public class Projet {

    private int id;
    private String nom;
    private Double margeBeneficiaire;
    private Double coutTotal;
    private EtatProjet etat;
    private Double surfaceCouisine;

    public Projet(int id, String nom, Double margeBeneficiaire, Double coutTotal, EtatProjet etat, Double surfaceCouisine) {
        this.id = id;
        this.nom = nom;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etat = etat;
        this.surfaceCouisine = surfaceCouisine;
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

    public Double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(Double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public Double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(Double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public EtatProjet getEtat() {
        return etat;
    }

    public void setEtat(EtatProjet etat) {
        this.etat = etat;
    }

    public Double getSurfaceCouisine() {
        return surfaceCouisine;
    }

    public void setSurfaceCouisine(Double surfaceCouisine) {
        this.surfaceCouisine = surfaceCouisine;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", margeBeneficiaire=" + margeBeneficiaire +
                ", coutTotal=" + coutTotal +
                ", etat=" + etat +
                ", surfaceCouisine=" + surfaceCouisine +
                '}';
    }
}
