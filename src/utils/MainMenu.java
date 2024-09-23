package utils;

import entities.*;
import services.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MainMenu {
    private ProjetService projetService;
    private ClientService clientService;
    private MateriauService materiauService;
    private MainDeOeuvreService mainDeOeuvreService;
    private DevisService devisService;
    private Scanner scanner;

    public MainMenu(ProjetService projetService, ClientService clientService,
                    MateriauService materiauService, MainDeOeuvreService mainDeOeuvreService,
                    DevisService devisService) {
        this.projetService = projetService;
        this.clientService = clientService;
        this.materiauService = materiauService;
        this.mainDeOeuvreService = mainDeOeuvreService;
        this.devisService = devisService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createNewProject();
                    break;
                case "2":
                    displayExistingProjects();
                    break;
                case "3":
                    calculateProjectCost();
                    break;
                case "4":
                    exit = true;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createNewProject() {
        try {
            System.out.println("\n--- Création d'un Nouveau Projet ---");
            Client client = selectOrCreateClient();
            if (client == null) {
                System.out.println("Création de projet annulée.");
                return;
            }

            System.out.println("--- Création d'un Nouveau Projet ---");
            System.out.print("Entrez le nom du projet : ");
            String nomProjet = scanner.nextLine().trim();

            System.out.print("Entrez la surface de la cuisine (en m²) : ");
            double surface = Double.parseDouble(scanner.nextLine());

            Projet projet = new Projet(nomProjet, 0.0, 0.0, EtatProjet.EN_COURS, surface);
            projet.setClient(client);
            projetService.addProjet(projet);
            System.out.println("Projet créé avec succès !");

            // Ajouter des matériaux
            addMaterialsToProject(projet);

            // Ajouter de la main-d'œuvre
            addMainDeOeuvreToProject(projet);

            // Calculer le coût total
            calculateAndDisplayCost(projet);

            // Enregistrer le devis
            saveDevis(projet);

            System.out.println("--- Fin du projet ---");

        } catch (NumberFormatException e) {
            System.out.println("Erreur de format numérique : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du projet : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Client selectOrCreateClient() {
        System.out.println("\n--- Gestion des Clients ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                return searchExistingClient();
            case "2":
                return addNewClient();
            default:
                System.out.println("Option invalide.");
                return null;
        }
    }

    private Client searchExistingClient() {
        try {
            System.out.println("\n--- Recherche de client existant ---");
            System.out.print("Entrez le nom du client : ");
            String nomClient = scanner.nextLine().trim();

            Optional<Client> clientOpt = clientService.findByNom(nomClient);

            if (clientOpt.isPresent()) {
                Client client = clientOpt.get();
                System.out.println("Client trouvé !");
                System.out.println("Nom : " + client.getNom());
                System.out.println("Adresse : " + client.getAdress());
                System.out.println("Numéro de téléphone : " + client.getTelephone());
                System.out.println("Est professionnel : " + (client.getEstProfessionnel() ? "Oui" : "Non"));

                System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
                String response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("y")) {
                    return client;
                } else {
                    System.out.println("Retour au menu précédent.");
                    return null;
                }
            } else {
                System.out.println("Client non trouvé. Retour au menu précédent.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du client : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    private Client addNewClient() {
        try {
            System.out.println("\n--- Ajout d'un Nouveau Client ---");
            System.out.print("Entrez le nom du client : ");
            String nom = scanner.nextLine().trim();

            System.out.print("Entrez l'adresse du client : ");
            String adresse = scanner.nextLine().trim();

            System.out.print("Entrez le numéro de téléphone du client : ");
            String telephone = scanner.nextLine().trim();

            System.out.print("Le client est-il professionnel ? (y/n) : ");
            String estProf = scanner.nextLine().trim();
            boolean estProfessionnel = estProf.equalsIgnoreCase("y");

            Client client = new Client(nom, adresse, telephone, estProfessionnel);
            clientService.add(client);
            System.out.println("Client ajouté avec succès !");
            return client;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void addMaterialsToProject(Projet projet) {
        System.out.println("\n--- Ajout des matériaux ---");
        boolean addMore = true;
        while (addMore) {
            try {
                System.out.print("Entrez le nom du matériau : ");
                String nomMateriau = scanner.nextLine().trim();

                System.out.print("Entrez la quantité de ce matériau (en m²) : ");
                double quantite = Double.parseDouble(scanner.nextLine());

                System.out.print("Entrez le coût unitaire de ce matériau (€/m²) : ");
                double coutUnitaire = Double.parseDouble(scanner.nextLine());

                System.out.print("Entrez le coût de transport de ce matériau (€) : ");
                double coutTransport = Double.parseDouble(scanner.nextLine());

                System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
                double coefficientQualite = Double.parseDouble(scanner.nextLine());

                // Définir le taux de TVA, par exemple 20%
                double taux_TVA = 20.0;

                Materiau materiau = new Materiau(0, nomMateriau, "Matériau", taux_TVA,
                        coutUnitaire, quantite, coutTransport, coefficientQualite, projet);

                materiauService.add(materiau);
                System.out.println("Matériau ajouté avec succès !");

                System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("y")) {
                    addMore = false;
                }

            } catch (NumberFormatException e) {
                System.out.println("Erreur de format numérique : " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du matériau : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addMainDeOeuvreToProject(Projet projet) {
        System.out.println("\n--- Ajout de la main-d'œuvre ---");
        boolean addMore = true;
        while (addMore) {
            try {
                System.out.print("Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : ");
                String type = scanner.nextLine().trim();

                System.out.print("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
                double tauxHoraire = Double.parseDouble(scanner.nextLine());

                System.out.print("Entrez le nombre d'heures travaillées : ");
                double heuresTravail = Double.parseDouble(scanner.nextLine());

                System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
                double facteurProductivite = Double.parseDouble(scanner.nextLine());

                // Définir le taux de TVA, par exemple 20%
                double taux_TVA = 20.0;

                MainDeOeuvre mainDeOeuvre = new MainDeOeuvre(0, type, "Main-d'œuvre", taux_TVA, tauxHoraire, heuresTravail, facteurProductivite, projet);

                mainDeOeuvreService.addMainDeOeuvre(mainDeOeuvre);
                System.out.println("Main-d'œuvre ajoutée avec succès !");

                System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("y")) {
                    addMore = false;
                }

            } catch (NumberFormatException e) {
                System.out.println("Erreur de format numérique : " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la main-d'œuvre : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private double calculateAndDisplayCost(Projet projet) {
        double coutTotalFinal = 0.0;
        try {
            System.out.println("\n--- Calcul du coût total ---");
            System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
            String applyTVA = scanner.nextLine().trim();
            double tvaPercentage = 0.0;
            if (applyTVA.equalsIgnoreCase("y")) {
                System.out.print("Entrez le pourcentage de TVA (%) : ");
                tvaPercentage = Double.parseDouble(scanner.nextLine());
            }

            System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
            String applyMarge = scanner.nextLine().trim();
            double margePercentage = 0.0;
            if (applyMarge.equalsIgnoreCase("y")) {
                System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
                margePercentage = Double.parseDouble(scanner.nextLine());
            }

            System.out.println("Calcul du coût en cours...");

            Optional<List<Materiau>> materiauxOpt = materiauService.findAllByProjetId(projet);
            List<Materiau> materiaux = materiauxOpt.orElse(Collections.emptyList());

            Optional<List<MainDeOeuvre>> mainDeOeuvreOpt = mainDeOeuvreService.findAllByProjectId(projet);
            List<MainDeOeuvre> mainDeOeuvres = mainDeOeuvreOpt.orElse(Collections.emptyList());

            // Calcul des coûts
            double totalMateriauxAvantTVA = 0.0;
            double totalMateriauxAvecTVA = 0.0;
            double totalMainDeOeuvreAvantTVA = 0.0;
            double totalMainDeOeuvreAvecTVA = 0.0;

            DecimalFormat df = new DecimalFormat("#,##0.00");

            System.out.println("\n--- Détail des Coûts ---");

            // Matériaux
            System.out.println("1. Matériaux :");
            for (Materiau m : materiaux) {
                double coutTotal = m.getCoutUnitaire() * m.getQuantite() * m.getCoefficientQualite() + m.getCoutTransport();
                System.out.println("- " + m.getNom() + " : " + df.format(coutTotal) + " € (quantité : " + m.getQuantite()
                        + " m², coût unitaire : " + m.getCoutUnitaire() + " €/m², qualité : " + m.getCoefficientQualite()
                        + ", transport : " + m.getCoutTransport() + " €)");
                totalMateriauxAvantTVA += coutTotal;
            }
            System.out.println("**Coût total des matériaux avant TVA : " + df.format(totalMateriauxAvantTVA) + " €**");
            totalMateriauxAvecTVA = totalMateriauxAvantTVA * (1 + tvaPercentage / 100);
            System.out.println("**Coût total des matériaux avec TVA (" + tvaPercentage + "%) : " + df.format(totalMateriauxAvecTVA) + " €**");

            // Main-d'œuvre
            System.out.println("2. Main-d'œuvre :");
            for (MainDeOeuvre mdo : mainDeOeuvres) {
                double coutTotal = mdo.getTauxHoraire() * mdo.getHeuresTravail() * mdo.getProductiviteOuvrier();
                System.out.println("- " + mdo.getNom() + " : " + df.format(coutTotal) + " € (taux horaire : " + mdo.getTauxHoraire()
                        + " €/h, heures travaillées : " + mdo.getHeuresTravail() + " h, productivité : " + mdo.getProductiviteOuvrier() + ")");
                totalMainDeOeuvreAvantTVA += coutTotal;
            }
            System.out.println("**Coût total de la main-d'œuvre avant TVA : " + df.format(totalMainDeOeuvreAvantTVA) + " €**");
            totalMainDeOeuvreAvecTVA = totalMainDeOeuvreAvantTVA * (1 + tvaPercentage / 100);
            System.out.println("**Coût total de la main-d'œuvre avec TVA (" + tvaPercentage + "%) : " + df.format(totalMainDeOeuvreAvecTVA) + " €**");

            // Coût total avant marge
            double coutTotalAvantMarge = totalMateriauxAvecTVA + totalMainDeOeuvreAvecTVA;
            System.out.println("3. Coût total avant marge : " + df.format(coutTotalAvantMarge) + " €");

            // Marge bénéficiaire
            double margeBeneficiaire = coutTotalAvantMarge * (margePercentage / 100);
            System.out.println("4. Marge bénéficiaire (" + margePercentage + "%) : " + df.format(margeBeneficiaire) + " €");

            // Coût total final
            coutTotalFinal = coutTotalAvantMarge + margeBeneficiaire;
            System.out.println("**Coût total final du projet : " + df.format(coutTotalFinal) + " €**");

            // Mise à jour du projet avec le coût total
            projet.setCoutTotal(coutTotalFinal);
            projetService.updateProjet(projet);

        } catch (NumberFormatException e) {
            System.out.println("Erreur de format numérique : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du coût : " + e.getMessage());
            e.printStackTrace();
        }
        return coutTotalFinal;  // Retour du coût total final
    }


    private void saveDevis(Projet projet) {
        System.out.println("\n--- Enregistrement du Devis ---");
        try {
            // Appel de la méthode calculateAndDisplayCost pour obtenir le montant estimé
            double montantEstime = calculateAndDisplayCost(projet);
            System.out.println("Montant estimé du devis (coût total final) : " + montantEstime + " €");

            System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
            String dateEmissionStr = scanner.nextLine().trim();
            LocalDate dateEmission = LocalDate.parse(dateEmissionStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
            String dateValiditeStr = scanner.nextLine().trim();
            LocalDate dateValidite = LocalDate.parse(dateValiditeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Le devis est-il accepté ? (y/n) : ");
            boolean isAccepte = scanner.nextLine().trim().equalsIgnoreCase("y");

            System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("y")) {
                // Création du devis
                Devis devis = new Devis(0, projet, montantEstime, dateEmission, dateValidite, isAccepte);

                // Appel au service pour enregistrer le devis
                devisService.addDevis(devis);

                System.out.println("Devis enregistré avec succès !");
            } else {
                System.out.println("Devis non enregistré.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Format de date incorrect. Veuillez utiliser le format jj/mm/aaaa.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du devis : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void displayExistingProjects() {
        try {
            System.out.println("\n--- Liste des Projets Existants ---");
            List<Projet> projets = projetService.findAllProjets();
            if (projets.isEmpty()) {
                System.out.println("Aucun projet trouvé.");
                return;
            }
            for (Projet projet : projets) {
                System.out.println(projet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des projets : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void calculateProjectCost() {
        try {
            System.out.println("\n--- Calcul du coût d'un Projet ---");
            System.out.print("Entrez le nom du projet : ");
            String nomProjet = scanner.nextLine().trim();

            Optional<Projet> projetOpt = projetService.findByNom(nomProjet);
            if (projetOpt.isEmpty()) {
                System.out.println("Projet non trouvé !");
                return;
            }
            Projet projet = projetOpt.get();

            // Recalculer le coût si nécessaire
            calculateAndDisplayCost(projet);

        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du coût du projet : " + e.getMessage());
            e.printStackTrace();
        }
    }
}