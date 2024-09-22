package repositories.implementations;


import entities.EtatProjet;
import entities.Projet;
import org.postgresql.util.PGobject;
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
    public Optional<Projet> findById(int id) throws SQLException {
        String query = "SELECT * FROM projet WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Projet projet = genarateObject(rs);
                    return Optional.of(projet);
                }
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<Projet> findByNom(String nom) throws SQLException {
        String query = "SELECT * FROM projet WHERE nom = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Projet projet = genarateObject(rs);
                    return Optional.of(projet);
                }
            }
        }
        return Optional.empty();
    }
    @Override
    public List<Projet> findAll() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT * FROM projet";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                projets.add(genarateObject(rs));
            }
        }
        return projets;
    }



    @Override
    public void add(Projet projet) throws SQLException {
        String query = "INSERT INTO projet (nom, margeBeneficiaire, coutTotal, etat, surfaceCouisine, id_client) VALUES (?,?,?,?,?,?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projet.getNom());
            preparedStatement.setDouble(2, projet.getMargeBeneficiaire());
            preparedStatement.setDouble(3, projet.getCoutTotal());

            PGobject etatEnum = new PGobject();
            etatEnum.setType("etatprojet");
            etatEnum.setValue(projet.getEtat().name());
            preparedStatement.setObject(4, etatEnum);

            preparedStatement.setDouble(5, projet.getSurfaceCouisine());

            preparedStatement.setInt(6, projet.getClient().getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    projet.setId(resultSet.getInt("id"));
                    System.out.println("Projet ajouté avec succès avec l'ID: " + projet.getId());
                }
            }
        }
    }

    @Override
    public void update(Projet projet) throws SQLException{

    }
    @Override
    public void delete(String nom) throws SQLException{

    }
}
