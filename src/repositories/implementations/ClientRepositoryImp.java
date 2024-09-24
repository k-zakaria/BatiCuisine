package repositories.implementations;

import entities.Client;
import repositories.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryImp implements ClientRepository {

    private Connection connection;

    public ClientRepositoryImp(Connection connection) {
        this.connection = connection;
    }

    public Client genarateObject(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("id");
        String nom = resultSet.getString("nom");
        String adress = resultSet.getString("adress");
        String telephone = resultSet.getString("telephone");
        Boolean estProfessionnel = resultSet.getBoolean("estProfessionnel");

        Client client = new Client(nom, adress, telephone, estProfessionnel);
        client.setId(id);
        return client;

    }
    @Override
    public void add(Client client) throws SQLException{
        String query = "INSERT INTO client (nom, adress, telephone, estProfessionnel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,client.getNom());
            preparedStatement.setString(2, client.getAdress());
            preparedStatement.setString(3, client.getTelephone());
            preparedStatement.setBoolean(4, client.getEstProfessionnel());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                    System.out.println("Client ajouté avec nom: " + client.getNom());
                }
            }

        }
    }
    @Override
    public Optional<Client> findByNom(String nom) throws SQLException{
        String query = "SELECT * FROM client WHERE nom = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return Optional.of(genarateObject(resultSet));
            }
        }
        return null;
    }

    @Override
    public Optional<List<Client>> findAllByNom(String nom) throws SQLException {
        String query = "SELECT * FROM client WHERE nom = ?";
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = genarateObject(resultSet);
                    clients.add(client);
                }
            }
        }
        return clients.isEmpty() ? Optional.empty() : Optional.of(clients);
    }

    @Override
    public Optional<Client> findClientById(int clientId) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Optional.of(genarateObject(resultSet));
                } else {
                    throw new SQLException("Client non trouvé avec l'ID : " + clientId);
                }
            }
        }
        return null;
    }



    @Override
    public void update(Client client) throws SQLException{
        String query = "UPDATE client SET nom = ?, adress = ?, telephone = ?, estProfessionnel = ? WHERE nom = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(1, client.getAdress());
            preparedStatement.setString(1, client.getTelephone());
            preparedStatement.setBoolean(1, client.getEstProfessionnel());
            preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    client.setId(generatedKeys.getInt(1));
                    System.out.println("Client ajouté avec ID: " + client.getNom());
                }
            }
        }
    }

    @Override
    public void delete(String nom) throws SQLException{
        String query = "DELETE FROM client WHERE nom = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.executeUpdate();
            System.out.println(" Client supprimé avec Nom: " + nom);
        }
    }




}
