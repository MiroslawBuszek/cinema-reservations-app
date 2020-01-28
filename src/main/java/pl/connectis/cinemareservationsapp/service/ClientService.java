package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Client updateById(long id, Client client) {
        Client existingClient = clientRepository.findById(id);
        if (client.getFirstName() != null) {
            existingClient.setFirstName(client.getFirstName());
        }
        if (client.getLastName() != null) {
            existingClient.setLastName(client.getLastName());
        }
        if (client.getAge() != 0) {
            existingClient.setAge(client.getAge());
        }
        return existingClient;
    }

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }
}
