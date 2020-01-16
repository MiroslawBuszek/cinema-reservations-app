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

    @PostMapping("/ticket")
    public Ticket addTicket(@Valid @RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }
}
