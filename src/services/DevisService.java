package services;

import entities.Devis;
import repositories.DevisRepository;

import java.sql.SQLException;
import java.util.Optional;

public class DevisService {
    private DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    public void addDevis(Devis devis) throws SQLException {
        validateDevis(devis);
        devisRepository.add(devis);
    }

    public Optional<Devis> findById(int id) throws SQLException {
        return devisRepository.findById(id);
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
