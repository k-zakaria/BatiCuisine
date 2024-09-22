package repositories;

import entities.MainDeOeuvre;
import java.sql.SQLException;

public interface MainDeOeuvreRepository {
    void add(MainDeOeuvre mainDeOeuvre) throws SQLException;
    void update(MainDeOeuvre mainDeOeuvre) throws SQLException;
    void delete(int id) throws SQLException;
}
