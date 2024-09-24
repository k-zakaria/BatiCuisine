package repositories;

import entities.EtatProjet;
import entities.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjetRepository {
    Optional<Projet> findById(int id) throws SQLException;
    Optional<Projet> findByNom(String nom) throws SQLException;
    List<Projet> findAll() throws SQLException;
    void add(Projet projet) throws SQLException;
    void updateProjectStatus(Projet projet, EtatProjet etat) throws SQLException;
    void delete(String nom) throws SQLException;
}
