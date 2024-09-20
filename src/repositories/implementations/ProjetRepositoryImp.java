package repositories.implementations;

import entities.Client;
import entities.EtatProjet;
import entities.Projet;
import repositories.ProjetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjetRepositoryImp implements ProjetRepository {

    private Connection connection;

    public ProjetRepositoryImp(Connection connection){
        this.connection = connection;
    }

    public Projet genarateObject(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("id");
        String nom = resultSet.getString("nom");
        Double margeBeneficiaire = resultSet.getDouble("margeBeneficiaire");
        Double coutTotal = resultSet.getDouble("coutTotal");
        String etatString = resultSet.getString("etat");
        EtatProjet etat = EtatProjet.valueOf(etatString);
        Double surfaceCouisine = resultSet.getDouble("surfaceCouisine");

        Projet projet = new Projet(nom, margeBeneficiaire, coutTotal, etat, surfaceCouisine);
        projet.setId(id);
        return projet;

    }
    @Override
    public Optional<Projet> findByNom(String nom) throws SQLException{
        String query = "SELECT * FROM projet WHERE nom = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, nom);
            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    return Optional.of(genarateObject(resultSet));
                }
            }
        }
        return null;
    }

    @Override
    public Optional<List<Projet>> findAllByNom(String nom) throws SQLException {
        String query = "SELECT * FROM projet WHERE nom = ?";
        List<Projet> projets = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Projet projet = genarateObject(resultSet);
                    projets.add(projet);
                }
            }
        }

        if (projets.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(projets);
        }
    }
    @Override
    public void add(Projet projet) throws SQLException {
        String query = "INSERT INTO projet (nom, margeBeneficiaire, coutTotal, etat, surfaceCouisine) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projet.getNom());
            preparedStatement.setDouble(2, projet.getMargeBeneficiaire());
            preparedStatement.setDouble(3, projet.getCoutTotal());
            preparedStatement.setString(4, projet.getEtat().name());
            preparedStatement.setDouble(5, projet.getSurfaceCouisine());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Projet projet) throws SQLException{

    }
    @Override
    public void delete(String nom) throws SQLException{

    }
}
