package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Client;
import pl.connectis.cinemareservationsapp.repository.ClientRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/client/all")
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/client/{id}")
    public List<Client> getClientById(@PathVariable long id) {
        return clientRepository.findById(id);
    }

    @PostMapping("/client")
    public Client addClient(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

}
