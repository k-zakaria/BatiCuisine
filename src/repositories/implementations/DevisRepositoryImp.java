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
    public Optional<Devis> findByProjectName(String projectName) throws SQLException {
        String query = "SELECT d.* FROM devis d JOIN projet p ON d.id_projet = p.id WHERE p.nom = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Devis devis = mapResultSetToDevis(rs);
                    return Optional.of(devis);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du devis par nom de projet : " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public void update(Devis devis) throws SQLException {
        String query = "UPDATE devis SET montantestime = ?, dateemission = ?, datevalidite = ?, isaccepte = ?, id_projet = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, devis.getMontantEstime());
            preparedStatement.setDate(2, Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(3, Date.valueOf(devis.getDateValidite()));
            preparedStatement.setBoolean(4, devis.isAccepte());
            preparedStatement.setInt(5, devis.getProjet().getId());
            preparedStatement.setInt(6, devis.getId()); // Assuming you have a getId() method in Devis

            preparedStatement.executeUpdate();
            System.out.println("Devis mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du devis : " + e.getMessage());
            throw e;
        }
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
