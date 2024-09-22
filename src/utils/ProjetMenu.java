package utils;

import entities.Client;
import entities.EtatProjet;
import entities.Projet;
import services.ProjetService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjetMenu {
    private ProjetService projetService;
    private Scanner scanner;
    private Client selectedClient;

    public ProjetMenu(ProjetService projetService, Scanner scanner, Client selectedClient) {
        this.projetService = projetService;
        this.scanner = scanner;
        this.selectedClient = selectedClient;
    }

    /**
     * Permet à l'utilisateur de créer un nouveau projet.
     */
    public void createNewProject() {
        if (selectedClient == null) {
            System.out.println("Aucun client sélectionné. Retour au menu principal.");
            return;
        }

        System.out.println("\n--- Création d'un Nouveau Projet ---");
        try {
            System.out.print("Entrez le nom du projet : ");
            String nomProjet = scanner.nextLine();

            System.out.print("Entrez la marge bénéficiaire (en %) : ");
            Double margeBeneficiaire = Double.parseDouble(scanner.nextLine());

            System.out.print("Entrez le coût total estimé : ");
            Double coutTotal = Double.parseDouble(scanner.nextLine());

            System.out.print("Entrez l'état du projet (EN_COURS, TERMINE, ANNULE) : ");
            String etatInput = scanner.nextLine();
            EtatProjet etat = EtatProjet.valueOf(etatInput.toUpperCase());

            System.out.print("Entrez la surface de la cuisine (en m²) : ");
            Double surfaceCuisine = Double.parseDouble(scanner.nextLine());

            Projet nouveauProjet = new Projet(nomProjet, margeBeneficiaire, coutTotal, etat, surfaceCuisine);
            nouveauProjet.setClient(selectedClient);

            projetService.add(nouveauProjet);
            System.out.println("Projet ajouté avec succès !");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur de validation : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du projet : " + e.getMessage());
        }
    }

    /**
     * Permet d'afficher tous les projets existants.
     */
    public void displayExistingProjects() {
        try {
            Optional<List<Projet>> projetsOpt = projetService.findAllByNom(selectedClient.getNom());

            if (projetsOpt.isPresent()) {
                List<Projet> projets = projetsOpt.get();
                System.out.println("\n--- Projets Existants ---");
                for (Projet projet : projets) {
                    displayProjectDetails(projet);
                }
            } else {
                System.out.println("Aucun projet trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des projets : " + e.getMessage());
        }
    }

    /**
     * Permet de calculer le coût total d'un projet en fonction de sa marge bénéficiaire.
     */
    public void calculateProjectCost() {
        System.out.print("\nEntrez le nom du projet pour calculer le coût : ");
        String nomProjet = scanner.nextLine();

        try {
            Optional<Projet> projetOpt = projetService.findByNom(nomProjet);

            if (projetOpt.isPresent()) {
                Projet projet = projetOpt.get();
                // Exemple de calcul du coût, ajustez selon vos besoins
                double coutTotal = projet.getCoutTotal();
                double marge = projet.getMargeBeneficiaire();
                double coutAvecMarge = coutTotal + (coutTotal * marge / 100);

                System.out.println("Le coût total du projet '" + projet.getNom() + "' avec marge est de : " + coutAvecMarge + " DH");
            } else {
                System.out.println("Projet non trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du coût : " + e.getMessage());
        }
    }

    private void displayProjectDetails(Projet projet) {
        System.out.println("\n-----");
        System.out.println("ID : " + projet.getId());
        System.out.println("Nom : " + projet.getNom());
        System.out.println("Marge Bénéficiaire : " + projet.getMargeBeneficiaire() + "%");
        System.out.println("Coût Total : " + projet.getCoutTotal() + " €");
        System.out.println("État : " + projet.getEtat());
        System.out.println("Surface Cuisine : " + projet.getSurfaceCouisine() + " m²");
        System.out.println("Client : " + projet.getClient().getNom());
        System.out.println("-----");
    }
}

