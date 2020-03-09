package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.model.User;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
import pl.connectis.cinemareservationsapp.repository.TicketRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    public List<Ticket> findAll(Example<Ticket> exampleTicket) {
        return ticketRepository.findAll();
    }

    public Ticket findById(long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public TicketDTO save(TicketDTO ticketDTO) {
        Ticket ticket = convertToEntity(ticketDTO);
        save(ticket);
        return convertToDTO(ticket);
    }

    public Iterable<Ticket> saveAll(Iterable<Ticket> ticketList) {
        return ticketRepository.saveAll(ticketList);
    }

    public void deleteById(long id) {
        ticketRepository.deleteById(id);
    }


    public void reserveSeat(TicketDTO ticketDTO) {
        Session session = sessionRepository.findById(ticketDTO.getSessionId());
        List<Integer> reservedSeats = session.getReservedSeats();
        reservedSeats.add(ticketDTO.getRowNumber() * 1000 + ticketDTO.getSeatNumber());
    }

    @Transactional
    public TicketDTO makeReservation(TicketDTO ticketDTO) {
        reserveSeat(ticketDTO);
        return save(ticketDTO);
    }

    public boolean validateSeatUnoccupied(TicketDTO ticketDTO) {
        int ticketNumber = ticketDTO.getRowNumber() * 1000 + ticketDTO.getSeatNumber();
        List<Integer> reservedSeats = sessionRepository.findById(ticketDTO.getSessionId()).getReservedSeats();
        return !reservedSeats.contains(ticketNumber);
    }

    public boolean validateTicketExists(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        return ticket != null;
    }

    public boolean validateSessionExists(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        return session != null;
    }

    public boolean validateUserExists(long userId) {
        User user = userRepository.findById(userId);
        return user != null;
    }

    public TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        if (ticket.getUser() != null) {
            ticketDTO.setUserId(ticket.getUser().getId());
        }
        if (ticket.getSession() != null) {
            ticketDTO.setSessionId(ticket.getSession().getId());
        }
        ticketDTO.setRowNumber(ticket.getRowNumber());
        ticketDTO.setSeatNumber(ticket.getSeatNumber());
        ticketDTO.setPrice(ticket.getPrice());
        return ticketDTO;
    }

    public List<TicketDTO> convertToDTO(List<Ticket> ticketList) {
        List<TicketDTO> ticketDTOList = new ArrayList<>(ticketList.size());
        for (Ticket ticket : ticketList) {
            ticketDTOList.add(convertToDTO(ticket));
        }
        return ticketDTOList;
    }

    public Ticket convertToEntity(TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketDTO.getId());
        if (ticket == null) {
            ticket = new Ticket();
        }
        ticket.setUser(userRepository.findById(ticketDTO.getUserId()));
        ticket.setSession(sessionRepository.findById(ticketDTO.getSessionId()));
        ticket.setRowNumber(ticketDTO.getRowNumber());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setPrice(ticketDTO.getPrice());
        return ticket;
    }
}
