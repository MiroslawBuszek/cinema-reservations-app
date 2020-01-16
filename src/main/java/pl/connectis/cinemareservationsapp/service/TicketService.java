package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public List<Ticket> findById(long id) {
        return ticketRepository.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Iterable<Ticket> saveAll(Iterable<Ticket> ticketList) {
        return ticketRepository.saveAll(ticketList);
    }

    public void deleteById(long id) {
        ticketRepository.deleteById(id);
    }
}
