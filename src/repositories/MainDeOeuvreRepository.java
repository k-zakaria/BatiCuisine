package repositories;

import entities.MainDeOeuvre;
import entities.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MainDeOeuvreRepository {
    void add(MainDeOeuvre mainDeOeuvre) throws SQLException;
    Optional<List<MainDeOeuvre>> findAllByProjectId(Projet projet) throws SQLException;
    void update(MainDeOeuvre mainDeOeuvre) throws SQLException;
    void delete(int id) throws SQLException;
}
