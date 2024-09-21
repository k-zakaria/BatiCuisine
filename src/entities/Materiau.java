package entities;

public class Materiau extends Composant {
    private Double coutUnitaire;
    private Double quantite;
    private Double coutTransport;
    private Double coefficientQualite;
    private Projet projet; // Ajout de la référence au projet

    public Materiau(int id, String nom, String typeComposant, Double taux_TVA,
                    Double coutUnitaire, Double quantite, Double coutTransport,
                    Double coefficientQualite, Projet projet) {
        super(id, nom, typeComposant, taux_TVA);
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
        this.projet = projet;
    }

    // Getters et Setters

    public Double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(Double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(Double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public Double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(Double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    @Override
    public String toString() {
        return "Materiau{" +
                "coutUnitaire=" + coutUnitaire +
                ", quantite=" + quantite +
                ", coutTransport=" + coutTransport +
                ", coefficientQualite=" + coefficientQualite +
                ", projet=" + projet +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", typeComposant='" + typeComposant + '\'' +
                ", taux_TVA=" + taux_TVA +
                '}';
    }
}
