package repositories;

import entities.Devis;

import java.sql.SQLException;
import java.util.Optional;

public interface DevisRepository {
    void add(Devis devis) throws SQLException;
    Optional<Devis> findById(int id) throws SQLException;
}
