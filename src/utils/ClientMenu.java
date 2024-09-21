package utils;

import entities.Client;
import services.ClientService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {
    private ClientService clientService;
    private Scanner scanner;

    public ClientMenu(ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.scanner = scanner;
    }

    /**
     * Permet à l'utilisateur de sélectionner un client existant ou d'en créer un nouveau.
     * @return Le client sélectionné ou créé, ou null si l'opération est annulée.
     */
    public Client selectOrCreateClient() {
        while (true) {
            System.out.println("\n--- Gestion des Clients ---");
            System.out.println("Souhaitez-vous :");
            System.out.println("1. Chercher un client existant");
            System.out.println("2. Ajouter un nouveau client");
            System.out.println("3. Annuler");
            System.out.print("Choisissez une option : ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Client existingClient = searchExistingClient();
                    if (existingClient != null) {
                        return existingClient;
                    }
                    break;
                case "2":
                    Client newClient = addNewClient();
                    if (newClient != null) {
                        return newClient;
                    }
                    break;
                case "3":
                    return null;
                default:
                    System.out.println("Option non valide. Veuillez choisir 1, 2 ou 3.");
            }
        }
    }

    private Client searchExistingClient() {
        System.out.println("\n--- Recherche de client existant ---");
        System.out.print("Entrez le nom du client : ");
        String nom = scanner.nextLine();

        try {
            Optional<Client> clientOpt = clientService.findByNom(nom);

            if (clientOpt.isPresent()) {
                Client client = clientOpt.get();
                System.out.println("Client trouvé !");
                displayClientDetails(client);

                System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    return client;
                } else {
                    return null;
                }
            } else {
                System.out.println("Client non trouvé !");
                System.out.print("Souhaitez-vous ajouter un nouveau client ? (y/n) : ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    return addNewClient();
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du client : " + e.getMessage());
        }
        return null;
    }

    private Client addNewClient() {
        System.out.println("\n--- Ajouter un nouveau client ---");
        try {
            System.out.print("Entrez le nom du client : ");
            String nom = scanner.nextLine();

            System.out.print("Entrez l'adresse du client : ");
            String address = scanner.nextLine();

            System.out.print("Entrez le numéro de téléphone du client : ");
            String telephone = scanner.nextLine();

            System.out.print("Le client est-il professionnel ? (y/n) : ");
            Boolean estProfessionnel = Boolean.parseBoolean(scanner.nextLine());

            Client newClient = new Client(nom, address, telephone, estProfessionnel);
            clientService.add(newClient);

            System.out.println("Le client a été ajouté avec succès !");
            return newClient;
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur de validation : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
        }
        return null;
    }

    private void displayClientDetails(Client client) {
        System.out.println("Nom : " + client.getNom());
        System.out.println("Adresse : " + client.getAdress());
        System.out.println("Numéro de téléphone : " + client.getTelephone());
        System.out.println("Professionnel : " + (client.getEstProfessionnel() ? "Oui" : "Non"));
    }
}

