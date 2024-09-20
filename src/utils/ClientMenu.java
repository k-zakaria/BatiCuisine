package utils;

import entities.Client;
import repositories.ClientRepository;
import services.ClientService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {

    private ClientService clientService;

    public ClientMenu(ClientService clientService) {
        this.clientService = clientService;
    }

    public void displayMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- Recherche de client ---");
            System.out.println("1. Chercher un client existant");
            System.out.println("2. Ajouter un nouveau client");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchExistingClient(scanner);
                    break;
                case 2:
                    addNewClient(scanner);
                    break;
                case 3:
                    System.out.println("Merci d'avoir utilisé le système.");
                    return;
                default:
                    System.out.println("Option non valide. Veuillez choisir 1, 2 ou 3.");
            }
        }
    }

    private void searchExistingClient(Scanner scanner) throws SQLException {
        System.out.println("--- Recherche de client existant ---");
        System.out.print("Entrez le nom du client : ");
        String nom = scanner.nextLine();

        Optional<Client> client = clientService.findByNom(nom);

        if (client.isPresent()) {
            System.out.println("Client trouvé !");
            displayClientDetails(client.get());

            System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                System.out.println("Vous avez choisi de continuer avec ce client.");
            }
        } else {
            System.out.println("Client non trouvé !");
        }
    }

    private void addNewClient(Scanner scanner) throws SQLException {
        System.out.println("--- Ajouter un nouveau client ---");
        System.out.print("Entrez le nom du client : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez l'adresse du client : ");
        String address = scanner.nextLine();

        System.out.print("Entrez le numéro de téléphone du client : ");
        String telephone = scanner.nextLine();

        System.out.print("Le client est-il professionnel ? (true/false) : ");
        boolean estProfessionnel = scanner.nextBoolean();
        scanner.nextLine();

        Client newClient = new Client(nom, address, telephone, estProfessionnel);
        clientService.add(newClient);

        System.out.println("Le client a été ajouté avec succès !");
    }

    private void displayClientDetails(Client client) {
        System.out.println("Nom : " + client.getNom());
        System.out.println("Adresse : " + client.getAdress());
        System.out.println("Numéro de téléphone : " + client.getTelephone());
        System.out.println("Professionnel : " + (client.getEstProfessionnel() ? "Oui" : "Non"));
    }

}
