package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.ClientNotFoundException;
import pl.connectis.cinemareservationsapp.model.Client;
import pl.connectis.cinemareservationsapp.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client/all")
    public Iterable<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        return clientService.findById(id);
    }

    @PostMapping("/client")
    public Client addClient(@Valid @RequestBody Client client) {
        return clientService.save(client);
    }

    @PostMapping("/client/many")
    public Iterable<Client> addClientList(@Valid @RequestBody Iterable<Client> clientList) {
        return clientService.saveAll(clientList);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable long id) {
        clientService.deleteById(id);
    }

}
