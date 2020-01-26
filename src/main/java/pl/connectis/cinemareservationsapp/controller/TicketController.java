package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.service.TicketService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticket/all")
    public Iterable<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/ticket/{id}")
    public Ticket getTicketById(@PathVariable long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            throw new ResourceNotFoundException("ticket {id=" + id + "} was not found");
        }
        return ticket;
    }

    @GetMapping("/ticket")
    public List<Ticket> getTicketByClientIdOrSessionId(
            @RequestParam(value = "client", required = false) Long clientId,
            @RequestParam(value = "session", required = false) Long sessionId) {
        if (clientId != null && !ticketService.validateClientExists(clientId)) {
            throw new ResourceNotFoundException("client {id=" + clientId + "} was not found");
        }
        if (sessionId != null && !ticketService.validateSessionExists(sessionId)) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
        return ticketService.findByClientIdOrSessionId(clientId, sessionId);
    }

    @PostMapping("/ticket")
    public Ticket addTicket(@RequestParam(value = "session") long sessionId,
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
                    " in row " + ticket.getRowNumber() + " is occupied");
        }
        return ticketService.createTicket(sessionId, clientId, ticket);
    }

    @PostMapping("/ticket/many")
    public Iterable<Ticket> addTicketList(@Valid @RequestBody Iterable<Ticket> ticketList) {
        return ticketService.saveAll(ticketList);
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity deleteTicket(@PathVariable long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            throw new ResourceNotFoundException("ticket {id=" + id + "} was not found");
        }
        ticketService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
