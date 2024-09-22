package entities;

public enum EtatProjet {
    EN_COURS,
    TERMINE,
    ANNULE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
