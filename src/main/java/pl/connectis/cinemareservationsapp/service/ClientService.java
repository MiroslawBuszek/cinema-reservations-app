package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Client;
import pl.connectis.cinemareservationsapp.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(long id) {
        return clientRepository.findById(id);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Iterable<Client> saveAll(Iterable<Client> clientList) {
        return clientRepository.saveAll(clientList);
    }

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }
}
