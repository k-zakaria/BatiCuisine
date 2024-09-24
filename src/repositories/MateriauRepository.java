package repositories;

import entities.Materiau;
import entities.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MateriauRepository {
    void add(Materiau materiau) throws SQLException;
    void update(Materiau materiau) throws SQLException;
    Optional<List<Materiau>> findAllByProjetId(Projet projet) throws SQLException;
    void delete(int id) throws SQLException;
}
