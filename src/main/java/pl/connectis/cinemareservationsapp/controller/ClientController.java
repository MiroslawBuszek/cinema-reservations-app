package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Client;
import pl.connectis.cinemareservationsapp.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public Iterable<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping
    public Client getClientById(@RequestParam long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ResourceNotFoundException("client {id=" + id + "} was not found");
        }
        return client;
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
        if (clientService.findByEmail(client.getEmail()) != null) {
            throw new BadRequestException("client {email=" + client.getEmail() + "} was found");
        }
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    public ResponseEntity<Iterable> addClientList(@Valid @RequestBody Iterable<Client> clientList) {
        for (Client client : clientList) {
            if (clientService.findByEmail(client.getEmail()) != null) {
                throw new BadRequestException("client {email=" + client.getEmail() + "} was found");
            }
        }
        return new ResponseEntity<>(clientService.saveAll(clientList), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestParam long id, @Valid @RequestBody Client client) {
        Client existingClient = clientService.findById(id);
        if (existingClient == null) {
            throw new ResourceNotFoundException("client {id=" + id + "} was not found");
        } else if (!client.getEmail().equals(existingClient.getEmail())) {
            throw new BadRequestException("{email=" + client.getEmail() +
                    "} does not correspond to client of {id=" + id + "}");
        } else {
            return new ResponseEntity<>(clientService.updateById(id, client), HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteClient(@RequestParam long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ResourceNotFoundException("client {id=" + id + "} was not found");
        }
        clientService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
