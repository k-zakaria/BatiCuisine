package repositories;

import entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    Optional<Client> findByNom(String nom) throws SQLException;
    Optional<List<Client>> findAllByNom(String nom) throws SQLException;
    Optional<Client> findClientById(int clientId) throws SQLException;
    void add(Client client) throws SQLException;
    void update(Client client) throws SQLException;
    void delete(String nom) throws SQLException;

}
