package services;

import entities.Projet;
import repositories.ProjetRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProjetService {
    private ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public void addProjet(Projet projet) throws SQLException {
        validateProjet(projet);
        projetRepository.add(projet);
    }

    public Optional<Projet> findByNom(String nom) throws SQLException {
        validateNom(nom);
        return projetRepository.findByNom(nom);
    }

    public Optional<Projet> findById(int id) throws SQLException {
        return projetRepository.findById(id);
    }


    public List<Projet> findAllProjets() throws SQLException {
        return projetRepository.findAll();
    }


    public void deleteProjet(String nom) throws SQLException {
        validateNom(nom);
        projetRepository.delete(nom);
    }

    private void validateProjet(Projet projet) {
        if (projet == null) {
            throw new IllegalArgumentException("Projet cannot be null");
        }
        if (projet.getNom() == null || projet.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Projet name cannot be null or empty");
        }
        if (projet.getSurfaceCouisine() == null || projet.getSurfaceCouisine() <= 0) {
            throw new IllegalArgumentException("Surface de la cuisine doit être positive");
        }
    }

    private void validateNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Projet name cannot be null or empty");
        }
    }
}
