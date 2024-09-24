package repositories;

import entities.Devis;

import java.sql.SQLException;
import java.util.Optional;

public interface DevisRepository {
    void add(Devis devis) throws SQLException;
    Optional<Devis> findByProjectName(String projectName) throws SQLException;
    void update(Devis devis) throws SQLException;
}
