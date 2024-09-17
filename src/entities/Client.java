package entities;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private int id;
    private String nom;
    private String adress;
    private String telephone;
    private Boolean estProfessionnel;
    private List<Projet> projets ;

    public Client(int id, String nom, String adress, String telephone, Boolean estProfessionnel){
        this.id = id;
        this.nom = nom;
        this.adress = adress;
        this.telephone = telephone;
        this.estProfessionnel = estProfessionnel;
        this.projets = new ArrayList<>();
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNom(){
        return nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getEstProfessionnel() {
        return estProfessionnel;
    }

    public void setEstProfessionnel(Boolean estProfessionnel) {
        this.estProfessionnel = estProfessionnel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adress='" + adress + '\'' +
                ", telephone='" + telephone + '\'' +
                ", estProfessionnel=" + estProfessionnel +
                '}';
    }
}
