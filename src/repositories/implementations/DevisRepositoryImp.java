package repositories.implementations;

import entities.Devis;
import entities.Projet;
import repositories.DevisRepository;
import repositories.ProjetRepository;


import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class DevisRepositoryImp implements DevisRepository {
    private Connection connection;
    private ProjetRepository projetRepository; // Dépendance au ProjetRepository

    public DevisRepositoryImp(Connection connection, ProjetRepository projetRepository) {
        this.connection = connection;
        this.projetRepository = projetRepository;
    }

    @Override
    public void add(Devis devis) throws SQLException {
        String query = "INSERT INTO devis (montantestime, dateemission, datevalidite, isaccepte, id_projet) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, devis.getMontantEstime());
            preparedStatement.setDate(2, Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(3, Date.valueOf(devis.getDateValidite()));
            preparedStatement.setBoolean(4, devis.isAccepte());
            preparedStatement.setInt(5, devis.getProjet().getId());

            preparedStatement.executeUpdate();
            System.out.println("Devis ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du devis : " + e.getMessage());
            throw e;
        }
    }


    @Override
    public Optional<Devis> findById(int id) throws SQLException {
        String query = "SELECT * FROM devis WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Devis devis = mapResultSetToDevis(rs);
                    return Optional.of(devis);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du devis : " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }


    private Devis mapResultSetToDevis(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double montantEstime = rs.getDouble("montantestime");
        LocalDate dateEmission = rs.getDate("dateemission").toLocalDate();
        LocalDate dateValidite = rs.getDate("datevalidite").toLocalDate();
        boolean isAccepte = rs.getBoolean("isaccepte");
        int projetId = rs.getInt("id_projet");

        Optional<Projet> projetOpt = projetRepository.findById(projetId);
        if (projetOpt.isEmpty()) {
            throw new SQLException("Projet avec l'ID " + projetId + " non trouvé.");
        }
        Projet projet = projetOpt.get();

        return new Devis(id, projet, montantEstime, dateEmission, dateValidite, isAccepte);
    }

}
