package utils;

import entities.Client;
import services.ClientService;
import services.ProjetService;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private ProjetService projetService;
    private ClientService clientService;
    private Scanner scanner;

    public MainMenu(ProjetService projetService, ClientService clientService) {
        this.projetService = projetService;
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");

            String input = scanner.nextLine();

            switch (input) {
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
                    System.out.println("Merci d'avoir utilisé le système. Au revoir !");
                    return;
                default:
                    System.out.println("Option non valide. Veuillez choisir 1, 2, 3 ou 4.");
            }
        }
    }

    private void createNewProject() {
        ClientMenu clientMenu = new ClientMenu(clientService, scanner);
        Client selectedClient = clientMenu.selectOrCreateClient();

        if (selectedClient == null) {
            System.out.println("Opération annulée. Retour au menu principal.");
            return;
        }

        ProjetMenu projetMenu = new ProjetMenu(projetService, scanner, selectedClient);
        projetMenu.createNewProject();
    }

    private void displayExistingProjects() {
        ProjetMenu projetMenu = new ProjetMenu(projetService, scanner, null);
        projetMenu.displayExistingProjects();
    }

    private void calculateProjectCost() {
        ProjetMenu projetMenu = new ProjetMenu(projetService, scanner, null);
        projetMenu.calculateProjectCost();
    }
}
