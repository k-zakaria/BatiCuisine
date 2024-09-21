package services;

import entities.Client;
import repositories.ClientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

    public class ClientService {
        private ClientRepository clientRepository;

        public ClientService(ClientRepository clientRepository) {
            this.clientRepository = clientRepository;
        }

        public void add(Client client) throws SQLException {
            validateClient(client);
            clientRepository.add(client);
        }

        public Optional<Client> findByNom(String nom) throws SQLException{
            validateNom(nom);
            return clientRepository.findByNom(nom);
        }

        public Optional<List<Client>> findAllByNom(String nom) throws SQLException{
            validateNom(nom);
            return clientRepository.findAllByNom(nom);
        }

        public void update(Client client) throws SQLException{
            validateClient(client);
            clientRepository.update(client);
        }

        public void delete(String nom) throws SQLException{
            validateNom(nom);
            clientRepository.delete(nom);
        }

        private void validateClient(Client client) {
            if (client == null) {
                throw new IllegalArgumentException("Client cannot be null");
            }
            if (client.getNom() == null || client.getNom().trim().isEmpty()) {
                throw new IllegalArgumentException("Client name cannot be null or empty");
            }
            if (client.getAdress() == null || client.getAdress().trim().isEmpty()) {
                throw new IllegalArgumentException("Client address cannot be null or empty");
            }
            if (client.getTelephone() == null || client.getTelephone().trim().isEmpty()) {
                throw new IllegalArgumentException("Client telephone cannot be null or empty");
            }
        }

        private void validateNom(String nom) {
            if (nom == null || nom.trim().isEmpty()) {
                throw new IllegalArgumentException("Client name cannot be null or empty");
            }
        }


    }
