package repositories.implementations;

import entities.MainDeOeuvre;
import entities.Projet;
import repositories.MainDeOeuvreRepository;
import repositories.ProjetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainDeOeuvreRepositoryImp implements MainDeOeuvreRepository {
    private Connection connection;
    private ProjetRepository projetRepository;

    public MainDeOeuvreRepositoryImp(Connection connection, ProjetRepository projetRepository) {
        this.connection = connection;
        this.projetRepository = projetRepository;
    }

    @Override
    public void add(MainDeOeuvre mainDeOeuvre) throws SQLException {
        String query = "INSERT INTO maindoeuvre (nom, typecomposant, taux_tva, tauxhoraire, heurestravail, productiviteouvrier, id_projet) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mainDeOeuvre.getNom());
            preparedStatement.setString(2, mainDeOeuvre.getTypeComposant());
            preparedStatement.setDouble(3, mainDeOeuvre.getTaux_TVA());
            preparedStatement.setDouble(4, mainDeOeuvre.getTauxHoraire());
            preparedStatement.setDouble(5, mainDeOeuvre.getHeuresTravail());
            preparedStatement.setDouble(6, mainDeOeuvre.getProductiviteOuvrier());
            preparedStatement.setInt(7, mainDeOeuvre.getProjet().getId());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    mainDeOeuvre.setId(rs.getInt("id"));
                    System.out.println("Main-d'œuvre ajoutée avec succès avec l'ID : " + mainDeOeuvre.getId());
                }
            }
        }
    }
    public Optional<List<MainDeOeuvre>> findAllByProjectId(Projet projet) throws SQLException {
        String sql = "SELECT * FROM maindoeuvre WHERE id_projet = ?";
        List<MainDeOeuvre> mainDoeuvres = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projet.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MainDeOeuvre mainDeOeuvre = mapResultSetToMainDeOeuvre(resultSet);
                mainDoeuvres.add(mainDeOeuvre);
            }

            return mainDoeuvres.isEmpty() ? Optional.empty() : Optional.of(mainDoeuvres);
        }
    }

    @Override
    public void update(MainDeOeuvre mainDeOeuvre) throws SQLException {
        String query = "UPDATE main_de_oeuvre SET nom = ?, type_composant = ?, taux_TVA = ?, taux_horaire = ?, heures_travail = ?, productivite_ouvrier = ?, projet_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mainDeOeuvre.getNom());
            preparedStatement.setString(2, mainDeOeuvre.getTypeComposant());
            preparedStatement.setDouble(3, mainDeOeuvre.getTaux_TVA());
            preparedStatement.setDouble(4, mainDeOeuvre.getTauxHoraire());
            preparedStatement.setDouble(5, mainDeOeuvre.getHeuresTravail());
            preparedStatement.setDouble(6, mainDeOeuvre.getProductiviteOuvrier());
            preparedStatement.setInt(7, mainDeOeuvre.getProjet().getId());
            preparedStatement.setInt(8, mainDeOeuvre.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Main-d'œuvre mise à jour avec succès !");
            } else {
                System.out.println("Aucune main-d'œuvre trouvée avec l'ID : " + mainDeOeuvre.getId());
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM main_de_oeuvre WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Main-d'œuvre supprimée avec succès !");
            } else {
                System.out.println("Aucune main-d'œuvre trouvée avec l'ID : " + id);
            }
        }
    }

    private MainDeOeuvre mapResultSetToMainDeOeuvre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String typeComposant = rs.getString("typecomposant");
        double taux_TVA = rs.getDouble("taux_tva");
        double tauxHoraire = rs.getDouble("tauxhoraire");
        double heuresTravail = rs.getDouble("heurestravail");
        double productiviteOuvrier = rs.getDouble("productiviteouvrier");
        int projetId = rs.getInt("id_projet");

        Optional<Projet> projetOpt = projetRepository.findById(projetId);
        if (projetOpt.isEmpty()) {
            throw new SQLException("Projet avec l'ID " + projetId + " non trouvé.");
        }
        Projet projet = projetOpt.get();

        return new MainDeOeuvre(id, nom, typeComposant, taux_TVA, tauxHoraire, heuresTravail, productiviteOuvrier, projet);
    }
}
