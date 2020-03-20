package pl.connectis.cinemareservationsapp.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    public TicketDTO mapDTOFromEntity(Ticket ticket) {

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setClient(ticket.getUser().getUsername());
        ticketDTO.setSessionId(ticket.getSession().getId());
        ticketDTO.setRowNumber(ticket.getRowNumber());
        ticketDTO.setSeatNumber(ticket.getSeatNumber());
        ticketDTO.setPrice(ticket.getPrice());
        return ticketDTO;

    }

    public List<TicketDTO> mapDTOFromEntity(List<Ticket> ticketList) {

        List<TicketDTO> ticketDTOs = new ArrayList<>(ticketList.size());

        for (Ticket ticket : ticketList) {

            ticketDTOs.add(mapDTOFromEntity(ticket));

        }

        return ticketDTOs;
    }

    public Ticket mapEntityFromDTO(TicketDTO ticketDTO) {

        Ticket ticket = new Ticket();
        if (ticketDTO.getClient() != null) {
            ticket.setUser(userRepository.findByUsername(ticketDTO.getClient()));
        }
        if (ticketDTO.getSessionId() != null) {
            ticket.setSession(sessionRepository.findById(ticketDTO.getSessionId()).get());
        }
        ticket.setRowNumber(ticketDTO.getRowNumber());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setPrice(ticketDTO.getPrice());
        return ticket;

    }

}
