package repositories.implementations;

import entities.Materiau;
import entities.Projet;
import repositories.MateriauRepository;
import repositories.ProjetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MateriauRepositoryImp implements MateriauRepository {
    private Connection connection;
    private ProjetRepository projetRepository;

    public MateriauRepositoryImp(Connection connection, ProjetRepository projetRepository) {
        this.connection = connection;
        this.projetRepository = projetRepository;
    }

    @Override
    public void add(Materiau materiau) throws SQLException {
        String query = "INSERT INTO materiau (nom, type_composant, taux_TVA, cout_unitaire, quantite, cout_transport, coefficient_qualite, projet_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, materiau.getNom());
            preparedStatement.setString(2, materiau.getTypeComposant());
            preparedStatement.setDouble(3, materiau.getTaux_TVA());
            preparedStatement.setDouble(4, materiau.getCoutUnitaire());
            preparedStatement.setDouble(5, materiau.getQuantite());
            preparedStatement.setDouble(6, materiau.getCoutTransport());
            preparedStatement.setDouble(7, materiau.getCoefficientQualite());
            preparedStatement.setInt(8, materiau.getProjet().getId());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    materiau.setId(rs.getInt("id"));
                    System.out.println("Matériau ajouté avec succès avec l'ID : " + materiau.getId());
                }
            }
        }
    }


    @Override
    public void update(Materiau materiau) throws SQLException {
        String query = "UPDATE materiau SET nom = ?, type_composant = ?, taux_TVA = ?, cout_unitaire = ?, quantite = ?, cout_transport = ?, coefficient_qualite = ?, projet_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, materiau.getNom());
            preparedStatement.setString(2, materiau.getTypeComposant());
            preparedStatement.setDouble(3, materiau.getTaux_TVA());
            preparedStatement.setDouble(4, materiau.getCoutUnitaire());
            preparedStatement.setDouble(5, materiau.getQuantite());
            preparedStatement.setDouble(6, materiau.getCoutTransport());
            preparedStatement.setDouble(7, materiau.getCoefficientQualite());
            preparedStatement.setInt(8, materiau.getProjet().getId());
            preparedStatement.setInt(9, materiau.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Matériau mis à jour avec succès !");
            } else {
                System.out.println("Aucun matériau trouvé avec l'ID : " + materiau.getId());
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM materiau WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Matériau supprimé avec succès !");
            } else {
                System.out.println("Aucun matériau trouvé avec l'ID : " + id);
            }
        }
    }

    private Materiau mapResultSetToMateriau(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String typeComposant = rs.getString("type_composant");
        double taux_TVA = rs.getDouble("taux_TVA");
        double coutUnitaire = rs.getDouble("cout_unitaire");
        double quantite = rs.getDouble("quantite");
        double coutTransport = rs.getDouble("cout_transport");
        double coefficientQualite = rs.getDouble("coefficient_qualite");
        int projetId = rs.getInt("projet_id");
        Optional<Projet> projetOpt = projetRepository.findById(projetId);
        if (projetOpt.isEmpty()) {
            throw new SQLException("Projet avec l'ID " + projetId + " non trouvé.");
        }
        Projet projet = projetOpt.get();

        return new Materiau(id, nom, typeComposant, taux_TVA, coutUnitaire, quantite, coutTransport, coefficientQualite, projet);
    }
}
