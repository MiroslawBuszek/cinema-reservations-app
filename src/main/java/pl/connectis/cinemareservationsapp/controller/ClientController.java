package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.ClientExistsException;
import pl.connectis.cinemareservationsapp.exceptions.ClientNotFoundException;
import pl.connectis.cinemareservationsapp.model.Client;
import pl.connectis.cinemareservationsapp.service.ClientService;

import javax.validation.Valid;

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
            throw new ClientNotFoundException("client {id=" + id + "} was not found");
        }
        return client;
    }

    @PostMapping("/client")
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
        if (clientService.findByEmail(client.getEmail()) != null) {
            throw new ClientExistsException("client {email=" + client.getEmail() + "} was found");
        }
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }

    @PostMapping("/client/many")
    public ResponseEntity<Iterable> addClientList(@Valid @RequestBody Iterable<Client> clientList) {
        for (Client client : clientList) {
            if (clientService.findByEmail(client.getEmail()) != null) {
                throw new ClientExistsException("client {email=" + client.getEmail() + "} was found");
            }
        }
        return new ResponseEntity<>(clientService.saveAll(clientList), HttpStatus.CREATED);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity deleteClient(@PathVariable long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ClientNotFoundException("client {id=" + id + "} was not found");
        }
        clientService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
