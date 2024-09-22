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

    // Ajouter un nouveau projet
    public void add(Projet projet) throws SQLException {
        if (validateProjet(projet)) {
            projetRepository.add(projet);
        } else {
            throw new IllegalArgumentException("Le projet est invalide.");
        }
    }

    // Rechercher un projet par nom (retourne un projet unique)
    public Optional<Projet> findByNom(String nom) throws SQLException {
        if (nom != null && !nom.isEmpty()) {
            return projetRepository.findByNom(nom);
        } else {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
    }

    // Rechercher tous les projets ayant le même nom
    public Optional<List<Projet>> findAllByNom(String nom) throws SQLException {
        if (nom != null && !nom.isEmpty()) {
            return projetRepository.findAllByNom(nom);
        } else {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
    }

    // Mise à jour d'un projet existant
    public void update(Projet projet) throws SQLException {
        if (validateProjet(projet)) {
            projetRepository.update(projet);
        } else {
            throw new IllegalArgumentException("Le projet est invalide.");
        }
    }

    // Suppression d'un projet par son nom
    public void delete(String nom) throws SQLException {
        if (nom != null && !nom.isEmpty()) {
            projetRepository.delete(nom);
        } else {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
    }

    // Valider les informations d'un projet avant de l'ajouter ou le mettre à jour
    private boolean validateProjet(Projet projet) {
        if (projet == null) {
            return false;
        }
        if (projet.getNom() == null || projet.getNom().isEmpty()) {
            return false;
        }
        if (projet.getMargeBeneficiaire() == null || projet.getMargeBeneficiaire() <= 0) {
            return false;
        }
        if (projet.getCoutTotal() == null || projet.getCoutTotal() <= 0) {
            return false;
        }
        if (projet.getEtat() == null) {
            return false;
        }
        if (projet.getSurfaceCouisine() == null || projet.getSurfaceCouisine() <= 0) {
            return false;
        }
        return true;
    }
}
