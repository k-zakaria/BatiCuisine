package entities;

import java.time.LocalDate;

public class Devi {

    private int id;
    private Double mantantEstime;

    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private Boolean isAccepte;

    public Devi(int id, Double mantantEstime, LocalDate dateEmission, LocalDate dateValidite, Boolean isAccepte) {
        this.id = id;
        this.mantantEstime = mantantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.isAccepte = isAccepte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMantantEstime() {
        return mantantEstime;
    }

    public void setMantantEstime(Double mantantEstime) {
        this.mantantEstime = mantantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public Boolean getAccepte() {
        return isAccepte;
    }

    public void setAccepte(Boolean accepte) {
        isAccepte = accepte;
    }

    @Override
    public String toString() {
        return "Devi{" +
                "id=" + id +
                ", mantantEstime=" + mantantEstime +
                ", dateEmission=" + dateEmission +
                ", dateValidite=" + dateValidite +
                ", isAccepte=" + isAccepte +
                '}';
    }
}
