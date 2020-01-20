package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public List<Ticket> getTicketById(@PathVariable long id) {
        return ticketService.findById(id);
    }

    @GetMapping("/ticket")
    public List<Ticket> getTicketByClientIdOrSessionId(
            @RequestParam(value = "client", required = false) Long clientId,
            @RequestParam(value = "session", required = false) Long sessionId) {
        return ticketService.findByClientIdOrSessionId(clientId, sessionId);
    }

    @PostMapping("/ticket")
    public Ticket addTicket(@RequestParam(value = "session") long sessionId,
                            @RequestParam(value = "client") long clientId,
                            @Valid @RequestBody Ticket ticket) {
        return ticketService.createTicket(sessionId, clientId, ticket);
    }

    @PostMapping("/ticket/many")
    public Iterable<Ticket> addTicketList(@Valid @RequestBody Iterable<Ticket> ticketList) {
        return ticketService.saveAll(ticketList);
    }

    @DeleteMapping("/ticket/{id}")
    public void deleteTicket(@PathVariable long id) {
        ticketService.deleteById(id);
    }

}
