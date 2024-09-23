import config.DatabaseConnection;
import repositories.*;
import repositories.implementations.*;
import services.*;
import utils.MainMenu;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try  {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Initialize repositories
            ClientRepository clientRepository = new ClientRepositoryImp(connection);
            ProjetRepository projetRepository = new ProjetRepositoryImp(connection);
            MateriauRepository materiauRepository = new MateriauRepositoryImp(connection,projetRepository);
            MainDeOeuvreRepository mainDeOeuvreRepository = new MainDeOeuvreRepositoryImp(connection,projetRepository);
            DevisRepository devisRepository = new DevisRepositoryImp(connection,projetRepository);

            // Initialize services
            ClientService clientService = new ClientService(clientRepository);
            ProjetService projetService = new ProjetService(projetRepository);
            MateriauService materiauService = new MateriauService(materiauRepository);
            MainDeOeuvreService mainDeOeuvreService = new MainDeOeuvreService(mainDeOeuvreRepository);
            DevisService devisService = new DevisService(devisRepository);

            // Initialisation et affichage du menu principal
            MainMenu mainMenu = new MainMenu(projetService, clientService, materiauService, mainDeOeuvreService, devisService);
            mainMenu.displayMenu();

        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}
