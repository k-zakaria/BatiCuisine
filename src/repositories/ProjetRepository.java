package repositories;

import entities.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjetRepository {
    Optional<Projet> findById(int id) throws SQLException;
    public void add(Projet projet) throws SQLException;
    public void update(Projet projet) throws SQLException;
    public void delete(String nom) throws SQLException;
}
