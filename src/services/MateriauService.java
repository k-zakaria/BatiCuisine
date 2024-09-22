package services;

import entities.Materiau;
import repositories.MateriauRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MateriauService {
    private MateriauRepository materiauRepository;

    public MateriauService(MateriauRepository materiauRepository) {
        this.materiauRepository = materiauRepository;
    }

    public void add(Materiau materiau) throws SQLException {
        validateMateriau(materiau);
        materiauRepository.add(materiau);
    }

    public Optional<Materiau> findByNom(String nom) throws SQLException {
        validateNom(nom);
        return materiauRepository.findByNom(nom);
    }

    public Optional<List<Materiau>> findAllByNom(String nom) throws SQLException {
        validateNom(nom);
        return materiauRepository.findAllByNom(nom);
    }


    private void validateMateriau(Materiau materiau) {
        if (materiau == null) {
            throw new IllegalArgumentException("Materiau cannot be null");
        }
        if (materiau.getNom() == null || materiau.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Materiau name cannot be null or empty");
        }
        if (materiau.getCoutUnitaire() == null || materiau.getCoutUnitaire() <= 0) {
            throw new IllegalArgumentException("Invalid cost for Materiau");
        }
        if (materiau.getQuantite() == null || materiau.getQuantite() <= 0) {
            throw new IllegalArgumentException("Invalid quantity for Materiau");
        }
    }

    private void validateNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Materiau name cannot be null or empty");
        }
    }
}
