package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
import pl.connectis.cinemareservationsapp.repository.TicketRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    public List<TicketDTO> getTicketsByExample(Map<String, String> requestParam) {

        TicketDTO ticketDTO = new TicketDTO();

        if (requestParam.containsKey("id")) {
            ticketDTO.setId(Long.parseLong(requestParam.get("id")));
        }

        if (requestParam.containsKey("sessionId")) {
            ticketDTO.setSessionId(Long.parseLong(requestParam.get("sessionId")));
        }

        if (requestParam.containsKey("client")) {
            ticketDTO.setSessionId(Long.parseLong(requestParam.get("client")));
        }

        Example<Ticket> ticketExample = Example.of(convertToEntity(ticketDTO));
        return convertToDTO(ticketRepository.findAll(ticketExample));

    }

    public List<TicketDTO> getMyTicketsByExample(Map<String, String> requestParam) {

        String client = authenticationFacade.getAuthentication().getName();

        if (requestParam.containsKey("client")) {
            requestParam.remove("client");
        }

        requestParam.put("client", client);
        return getTicketsByExample(requestParam);

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

        validateTicketExists(id);
        ticketRepository.deleteById(id);

    }


    public void reserveSeat(TicketDTO ticketDTO) {

        Session session = sessionRepository.findById(ticketDTO.getSessionId());
        List<Integer> reservedSeats = session.getReservedSeats();
        reservedSeats.add(ticketDTO.getRowNumber() * 1000 + ticketDTO.getSeatNumber());

    }

    @Transactional
    public TicketDTO makeReservation(TicketDTO ticketDTO) {

        validateUserExists(ticketDTO.getClient());
        validateSessionExists(ticketDTO.getSessionId());
        validateSeatUnoccupied(ticketDTO);
        reserveSeat(ticketDTO);
        return save(ticketDTO);

    }

    private void validateSeatUnoccupied(TicketDTO ticketDTO) {
        int ticketNumber = ticketDTO.getRowNumber() * 1000 + ticketDTO.getSeatNumber();
        List<Integer> reservedSeats = sessionRepository.findById(ticketDTO.getSessionId()).getReservedSeats();

        if (reservedSeats.contains(ticketNumber)) {
            throw new BadRequestException("seat " + ticketDTO.getSeatNumber() +
                " in row " + ticketDTO.getRowNumber() + " is reserved");
        }

    }

    private void validateTicketExists(long ticketId) {

        if (ticketRepository.findById(ticketId) == null) {
            throw new ResourceNotFoundException("ticket {id=" + ticketId + "} was not found");
        }

    }
    private void validateSessionExists(long sessionId) {

        if (sessionRepository.findById(sessionId) == null) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }

    }

    private void validateUserExists(String username) {

        if (userRepository.findByUsername(username) == null) {
            throw new ResourceNotFoundException("user {username=" + username + "} was not found");

        }

    }

    public TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
//        if (ticket.getUser() != null) {
//            ticketDTO.setUserId(ticket.getUser().getId());
//        }
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
//        ticket.setUser(userRepository.findByUsername(ticketDTO.getUserId()));
        ticket.setSession(sessionRepository.findById(ticketDTO.getSessionId()));
        ticket.setRowNumber(ticketDTO.getRowNumber());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setPrice(ticketDTO.getPrice());
        return ticket;
    }
}
