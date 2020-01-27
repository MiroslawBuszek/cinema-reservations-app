package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.service.TicketService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            throw new ResourceNotFoundException("ticket {id=" + id + "} was not found");
        }
        return ticket;
    }

    @GetMapping
    public Iterable<Ticket> getTicketByExample(@RequestParam Map<String, String> requestParams) {

        Ticket ticket = new Ticket();

        if (requestParams.containsKey("id")) {
            ticket.setId(Long.parseLong(requestParams.get("id")));
        }
        if (requestParams.containsKey("client")) {
            ticket.setClient(ticketService.findClientById(Long.parseLong(requestParams.get("client"))));
        }
        if (requestParams.containsKey("session")) {
            ticket.setSession(ticketService.findSessionById(Long.parseLong(requestParams.get("session"))));
        }

        Example<Ticket> exampleTicket = Example.of(ticket);

        return ticketService.findAll(exampleTicket);
    }

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@RequestParam(value = "session") long sessionId,
                            @RequestParam(value = "client") long clientId,
                            @Valid @RequestBody Ticket ticket) {
        if (!ticketService.validateClientExists(clientId)) {
            throw new ResourceNotFoundException("client {id=" + clientId + "} was not found");
        }
        if (!ticketService.validateSessionExists(sessionId)) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
        if (!ticketService.validateSeatUnoccupied(ticket, sessionId)) {
            throw new BadRequestException("seat " + ticket.getSeatNumber() +
                    " in row " + ticket.getRowNumber() + " is reserved");
        }
        return new ResponseEntity<>(ticketService.createTicket(sessionId, clientId, ticket), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    public Iterable<Ticket> addTicketList(@Valid @RequestBody Iterable<Ticket> ticketList) {
        return ticketService.saveAll(ticketList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTicket(@PathVariable long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            throw new ResourceNotFoundException("ticket {id=" + id + "} was not found");
        }
        ticketService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
