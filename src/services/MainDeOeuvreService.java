package services;

import entities.MainDeOeuvre;
import entities.Projet;
import repositories.MainDeOeuvreRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MainDeOeuvreService {
    private MainDeOeuvreRepository mainDeOeuvreRepository;

    public MainDeOeuvreService(MainDeOeuvreRepository mainDeOeuvreRepository) {
        this.mainDeOeuvreRepository = mainDeOeuvreRepository;
    }

    public void addMainDeOeuvre(MainDeOeuvre mainDeOeuvre) throws SQLException {
        validateMainDeOeuvre(mainDeOeuvre);
        mainDeOeuvreRepository.add(mainDeOeuvre);
    }

    public Optional<List<MainDeOeuvre>> findAllByProjectId(Projet projet) throws SQLException {

        return mainDeOeuvreRepository.findAllByProjectId(projet);
    }

    public void updateMainDeOeuvre(MainDeOeuvre mainDeOeuvre) throws SQLException {
        validateMainDeOeuvre(mainDeOeuvre);
        mainDeOeuvreRepository.update(mainDeOeuvre);
    }

    public void deleteMainDeOeuvre(int id) throws SQLException {
        mainDeOeuvreRepository.delete(id);
    }

    private void validateMainDeOeuvre(MainDeOeuvre mainDeOeuvre) {
        if (mainDeOeuvre == null) {
            throw new IllegalArgumentException("MainDeOeuvre cannot be null");
        }
        if (mainDeOeuvre.getNom() == null || mainDeOeuvre.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Type de main-d'œuvre ne peut pas être vide");
        }
        if (mainDeOeuvre.getTauxHoraire() == null || mainDeOeuvre.getTauxHoraire() <= 0) {
            throw new IllegalArgumentException("Taux horaire doit être positif");
        }
        if (mainDeOeuvre.getHeuresTravail() == null || mainDeOeuvre.getHeuresTravail() <= 0) {
            throw new IllegalArgumentException("Heures travaillées doivent être positives");
        }
        if (mainDeOeuvre.getProductiviteOuvrier() == null || mainDeOeuvre.getProductiviteOuvrier() < 1.0) {
            throw new IllegalArgumentException("Facteur de productivité doit être au moins 1.0");
        }
        if (mainDeOeuvre.getProjet() == null) {
            throw new IllegalArgumentException("MainDeOeuvre doit être associée à un projet");
        }
    }
    private void validateNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("MainDeOeuvre name cannot be null or empty");
        }
    }
}
