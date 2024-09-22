package repositories;

import entities.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjetRepository {
    Optional<Projet> findById(int id) throws SQLException;
    Optional<Projet> findByNom(String nom) throws SQLException;
    List<Projet> findAll() throws SQLException;
    void add(Projet projet) throws SQLException;
    void update(Projet projet) throws SQLException;
    void delete(String nom) throws SQLException;
}
