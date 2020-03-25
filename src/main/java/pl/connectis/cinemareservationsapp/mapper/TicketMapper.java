package pl.connectis.cinemareservationsapp.mapper;

import pl.connectis.cinemareservationsapp.dto.ReservationDTO;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.model.Seat;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    public TicketDTO mapDTOFromEntity(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setClient(ticket.getUser().getUsername());
        ticketDTO.setSessionId(ticket.getSession().getId());
        ticketDTO.setSeat(ticket.getSeat());
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
            User user = new User();
            user.setUsername(ticketDTO.getClient());
            ticket.setUser(user);
        }
        if (ticketDTO.getSessionId() != null) {
            Session session = new Session();
            session.setId(ticketDTO.getSessionId());
            ticket.setSession(session);
        }
        return ticket;
    }

    public List<Ticket> mapTicketsFromReservationDTO(ReservationDTO reservationDTO, String username) {
        List<Ticket> tickets = new ArrayList<>();
        List<Seat> reservedSeats = reservationDTO.getReservedSeats();
        for (Seat reservedSeat : reservedSeats) {
            Ticket ticket = new Ticket();
            User user = new User();
            user.setUsername(username);
            ticket.setUser(user);
            Session session = new Session();
            session.setId(reservationDTO.getSessionId());
            ticket.setSession(session);
            ticket.setSeat(reservedSeat);
            tickets.add(ticket);
        }
        return tickets;
    }

}
