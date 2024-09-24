package entities;

public class MainDeOeuvre extends Composant {

    private Double tauxHoraire;
    private Double heuresTravail;
    private Double productiviteOuvrier;
    private Projet projet;

    public MainDeOeuvre(int id, String nom, String typeComposant, Double taux_TVA, Double tauxHoraire, Double heuresTravail, Double productiviteOuvrier, Projet projet) {
        super(id, nom, typeComposant, taux_TVA);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
        this.projet = projet;
    }

    public Double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public Double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(Double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public Double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(Double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    @Override
    public String toString() {
        return "MainDeOeuvre{" +
                "tauxHoraire=" + tauxHoraire +
                ", heuresTravail=" + heuresTravail +
                ", productiviteOuvrier=" + productiviteOuvrier +
                ", projet=" + projet +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", typeComposant='" + typeComposant + '\'' +
                ", taux_TVA=" + taux_TVA +
                '}';
    }
}
