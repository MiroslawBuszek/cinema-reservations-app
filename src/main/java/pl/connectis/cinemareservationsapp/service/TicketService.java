package pl.connectis.cinemareservationsapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.model.Client;
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
    ClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;

    public Iterable<Ticket> findAll(Example<Ticket> exampleTicket) {
        return ticketRepository.findAll();
    }

    public Ticket findById(long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public Client findClientById(long clientId) {
        return clientRepository.findById(clientId);
    }

    public Session findSessionById(long sessionId) {
        return sessionRepository.findById(sessionId);
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

    @Transactional
    public Ticket createTicket(long sessionId, long clientId, Ticket ticket) {
        Session session = sessionRepository.findById(sessionId);
        ArrayList<Integer> reservedSeats = new ArrayList<>(session.getReservedSeats());
        reservedSeats.add(ticket.getRowNumber() * 1000 + ticket.getSeatNumber());
        session.setReservedSeats(reservedSeats);
        ticket.setSession(session);
        ticket.setClient(clientRepository.findById(clientId));
        return ticketRepository.save(ticket);
    }

    public Iterable<Ticket> saveAll(Iterable<Ticket> ticketList) {
        return ticketRepository.saveAll(ticketList);
    }

    public void deleteById(long id) {
        ticketRepository.deleteById(id);
    }

    public boolean validateSeatUnoccupied(Ticket ticket, long sessionId) {
        int ticketNumber = ticket.getRowNumber() * 1000 + ticket.getSeatNumber();
        ArrayList<Integer> reservedSeats = sessionRepository.findById(sessionId).getReservedSeats();
        return !reservedSeats.contains(ticketNumber);
    }

    public boolean validateSessionExists(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            return false;
        }
        return true;
    }

    public boolean validateClientExists(long clientId) {
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            return false;
        }
        return true;
    }
}
