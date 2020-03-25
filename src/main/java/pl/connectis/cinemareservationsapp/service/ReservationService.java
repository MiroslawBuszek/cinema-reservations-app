package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.ReservationDTO;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.mapper.TicketMapper;
import pl.connectis.cinemareservationsapp.model.Seat;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
import pl.connectis.cinemareservationsapp.repository.TicketRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.AuthenticationFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReservationService {

    private final AuthenticationFacade authenticationFacade;
    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    public ReservationService(AuthenticationFacade authenticationFacade,
                              SessionRepository sessionRepository,
                              TicketRepository ticketRepository,
                              UserRepository userRepository,
                              TicketMapper ticketMapper) {
        this.authenticationFacade = authenticationFacade;
        this.sessionRepository = sessionRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<Seat> getSeats(Long sessionId) {
        validateSessionExists(sessionId);
        return new ArrayList<>(sessionRepository.findById(sessionId).get().getSeats().values());
    }

    public List<TicketDTO> makeReservation(ReservationDTO reservationDTO) {
        String username = authenticationFacade.getAuthentication().getName();
        return makeReservation(reservationDTO, username);
    }

    @Transactional
    public List<TicketDTO> makeReservation(ReservationDTO reservationDTO, String username) {
        validateSessionExists(reservationDTO.getSessionId());
        validateSeatsAreNotAlreadyReserved(reservationDTO);
        List<Seat> seatsFromReservation = reservationDTO.getReservedSeats();
        log.info(seatsFromReservation.toString());
        List<Ticket> ticketsForReservation = mapTicketsFromReservationDTO(reservationDTO, username);
        for (Ticket ticket : ticketsForReservation) {
            ticket.getSeat().setSold(true);
            log.info(ticket.toString());
        }
        return ticketMapper.mapDTOFromEntity(ticketRepository.saveAll(ticketsForReservation));
    }

    private void validateSeatsAreNotAlreadyReserved(ReservationDTO reservationDTO) {
        List<Seat> seatsFromReservation = reservationDTO.getReservedSeats();
        Map<String, Seat> seatsFromSession = sessionRepository.findById(reservationDTO.getSessionId()).get().getSeats();
        for (Seat seatFromReservation : seatsFromReservation) {
            String mapKey = seatFromReservation.getRowNumber() + "x" + seatFromReservation.getSeatNumber();
            Seat seatFromSession = seatsFromSession.get(mapKey);
            if (seatFromSession.isSold()) {
                throw new BadRequestException("seat " + seatFromReservation.getSeatNumber() +
                        " in row " + seatFromReservation.getRowNumber() + " is reserved");
            }
        }
    }

    private void validateSessionExists(Long sessionId) {
        if (!sessionRepository.findById(sessionId).isPresent()) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
    }

    private List<Ticket> mapTicketsFromReservationDTO(ReservationDTO reservationDTO, String username) {
        List<Ticket> tickets = ticketMapper.mapTicketsFromReservationDTO(reservationDTO, username);
        for (Ticket ticket : tickets) {
            ticket.setUser(userRepository.findByUsername(username));
            ticket.setSession(sessionRepository.findById(ticket.getSession().getId()).get());
            Seat seatFromSession = getSeatFromSession(reservationDTO.getSessionId(), ticket.getSeat());
            ticket.setSeat(seatFromSession);
        }
        return tickets;
    }

    private Seat getSeatFromSession(Long sessionId, Seat seatFromReservation) {
        int row = seatFromReservation.getRowNumber();
        int seat = seatFromReservation.getSeatNumber();
        String mapKey = row + "x" + seat;
        Seat seatFromSession = sessionRepository.findById(sessionId).get().getSeats().get(mapKey);
        seatFromSession.setSold(true);
        return seatFromSession;
    }

}
