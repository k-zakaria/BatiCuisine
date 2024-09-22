import config.DatabaseConnection;
import repositories.ClientRepository;
import repositories.ProjetRepository;
import repositories.implementations.ClientRepositoryImp;
import repositories.implementations.ProjetRepositoryImp;
import services.ClientService;
import services.ProjetService;
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

            // Initialize services
            ClientService clientService = new ClientService(clientRepository);
            ProjetService projetService = new ProjetService(projetRepository);

            // Initialisation et affichage du menu principal
            MainMenu mainMenu = new MainMenu(projetService, clientService);
            mainMenu.displayMenu();

        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}
