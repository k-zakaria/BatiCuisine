package services;

import entities.Devis;
import entities.EtatProjet;
import repositories.DevisRepository;
import repositories.ProjetRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class DevisService {
    private DevisRepository devisRepository;
    private ProjetRepository projetRepository;

    public DevisService(DevisRepository devisRepository, ProjetRepository projetRepository) {
        this.devisRepository = devisRepository;
        this.projetRepository = projetRepository;
    }

    public void addDevis(Devis devis) throws SQLException {
        validateDevis(devis);
        devisRepository.add(devis);
    }

    public Optional<Devis> findByProjectName(String projectName) throws SQLException {
        return devisRepository.findByProjectName(projectName);
    }
    public void updateDevis(Devis devis) throws SQLException {
        validateDevis(devis);

        if (devis.getDateValidite().isBefore(LocalDate.now())) {
            System.out.println("Le temps de validité pour ce devis est déjà dépassé !");
            projetRepository.updateProjectStatus(devis.getProjet(), EtatProjet.ANNULE);
            return;
        }
        devisRepository.update(devis);
        System.out.println("Devis mis à jour avec succès !");
    }



    private void validateDevis(Devis devis) {
        if (devis == null) {
            throw new IllegalArgumentException("Devis ne peut pas être null.");
        }
        if (devis.getProjet() == null) {
            throw new IllegalArgumentException("Le projet associé au devis ne peut pas être null.");
        }
        if (devis.getMontantEstime() <= 0) {
            throw new IllegalArgumentException("Le montant estimé du devis doit être supérieur à 0.");
        }
        if (devis.getDateEmission() == null) {
            throw new IllegalArgumentException("La date d'émission du devis ne peut pas être null.");
        }
        if (devis.getDateValidite() == null) {
            throw new IllegalArgumentException("La date de validité du devis ne peut pas être null.");
        }

        // Vérifier si la date d'émission précède la date de validité
        if (devis.getDateEmission().isAfter(devis.getDateValidite())) {
            throw new IllegalArgumentException("La date d'émission ne peut pas être postérieure à la date de validité.");
        }
    }

}
