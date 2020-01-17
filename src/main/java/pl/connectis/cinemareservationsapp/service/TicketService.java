package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Session;
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

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

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

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(long sessionId, Ticket ticket) {
        ticket.setSession(sessionRepository.findById(sessionId).get(0));
        return ticketRepository.save(ticket);
//        int ticketRow = ticket.getRowNumber();
//        int ticketSeat = ticket.getSeatNumber();
//        int sessionSeat = ticketRow*1000+ticketSeat;
//        Session session = sessionRepository.findById(sessionId).get(0);
//        int[] reservedSeats = session.getReservedSeats();
    }

    public Iterable<Ticket> saveAll(Iterable<Ticket> ticketList) {
        return ticketRepository.saveAll(ticketList);
    }

    public void deleteById(long id) {
        ticketRepository.deleteById(id);
    }
}
