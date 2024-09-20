package repositories;

import entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    public Optional<Client> findByNom(String nom) throws SQLException;
    public Optional<List<Client>> findAllByNom(String nom) throws SQLException;
    public void add(Client client) throws SQLException;
    public void update(Client client) throws SQLException;
    public void delete(String nom) throws SQLException;

}
