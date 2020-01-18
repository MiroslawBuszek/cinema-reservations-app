package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SessionRepository sessionRepository;

    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public List<Ticket> findById(long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public List<Ticket> findByClientId(long clientId) {
        return ticketRepository.findByClientId(clientId);
    }

    public List<Ticket> findBySessionId(long sessionId) {
        return ticketRepository.findBySessionId(sessionId);
    }

    public List<Ticket> findByClientIdOrSessionId(Long clientId, Long sessionId) {
        if(clientId != null) {
            return findByClientId(clientId);
        }
        if(sessionId != null) {
            return findBySessionId(sessionId);
        }
        return new ArrayList<>();
    }

    public void setEmptySeatsInSession(long sessionId) {
        List<Ticket> tickets = findBySessionId(sessionId);
        int[] reservedSeats = new int[tickets.size()];
        int ticketCounter = 0;
        for (Ticket ticket : tickets) {
            int rowNumber = ticket.getRowNumber();
            int seatNumber = ticket.getSeatNumber();
            reservedSeats[ticketCounter++] = rowNumber*1000+seatNumber;
        }
        sessionRepository.findById(sessionId).get(0).setReservedSeats(reservedSeats);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(long sessionId, Ticket ticket) {
        ticket.setSession(sessionRepository.findById(sessionId).get(0));
        return ticketRepository.save(ticket);
    }

    public Iterable<Ticket> saveAll(Iterable<Ticket> ticketList) {
        return ticketRepository.saveAll(ticketList);
    }

    public void deleteById(long id) {
        ticketRepository.deleteById(id);
    }
}
