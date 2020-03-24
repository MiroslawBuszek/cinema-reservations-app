package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.ReservationDTO;
import pl.connectis.cinemareservationsapp.dto.SeatDTO;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.mapper.TicketMapper;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
import pl.connectis.cinemareservationsapp.repository.TicketRepository;
import pl.connectis.cinemareservationsapp.repository.UserRepository;
import pl.connectis.cinemareservationsapp.security.AuthenticationFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservationService {

    private final AuthenticationFacade authenticationFacade;
    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    private final int SEAT_ADDRESS_CONSTANT = 1000;

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

    public List<SeatDTO> getSeats(Long sessionId) {

        List<SeatDTO> seats = new ArrayList<>();
        List<Integer> layoutList = getLayoutList(getSession(sessionId).getRoom().getLayout());
        for (int row = 1; row < (layoutList.size() + 1); row++) {
            for (int seat = 1; seat < (layoutList.get(row - 1) + 1); seat++) {
                seats.add(getSeat(sessionId, row, seat));
            }
        }
        return seats;

    }

    private SeatDTO getSeat(Long sessionId, int row, int seat) {
        List<Integer> reservedSeats = getSession(sessionId).getReservedSeats();
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setRowNumber(row);
        seatDTO.setSeatNumber(seat);
        if (reservedSeats.contains(row * SEAT_ADDRESS_CONSTANT + seat)) {
            seatDTO.setSold(true);
        } else {
            seatDTO.setSold(false);
        }
        return seatDTO;
    }

    private Session getSession(Long sessionId) {

        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);

        if (!sessionOptional.isPresent()) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }

        return sessionOptional.get();

    }

    private List<Integer> getLayoutList(String layout) {

        List<String> layoutStringList;
        List<Integer> layoutIntegerList = new ArrayList<>();
        layoutStringList = Arrays.asList(layout.split(","));

        for (String rowCapacityString : layoutStringList) {

            layoutIntegerList.add(Integer.parseInt(rowCapacityString));

        }

        return layoutIntegerList;

    }

    public List<TicketDTO> makeReservation(ReservationDTO reservationDTO) {
        String username = authenticationFacade.getAuthentication().getName();
        return makeReservation(reservationDTO, username);
    }

    @Transactional
    public List<TicketDTO> makeReservation(ReservationDTO reservationDTO, String username) {

        validateSessionExists(reservationDTO.getSessionId());

        List<SeatDTO> seatsFromReservation = reservationDTO.getReservedSeats();
        ArrayList<Integer> reservedSeats = sessionRepository.findById(reservationDTO.getSessionId()).get().getReservedSeats();

        for (SeatDTO seat : seatsFromReservation) {
            Integer seatNumber = seat.getRowNumber() * SEAT_ADDRESS_CONSTANT + seat.getSeatNumber();
            if (reservedSeats.contains(seatNumber)) {
                throw new BadRequestException("seat " + seat.getSeatNumber() +
                        " in row " + seat.getRowNumber() + " is reserved");
            }
        }


        List<Ticket> ticketsForReservation = mapTicketsFromReservationDTO(reservationDTO, username);
        for (Ticket ticket : ticketsForReservation) {
            log.info(ticket.toString());
            reserveSeat(ticket);
        }
        return ticketMapper.mapDTOFromEntity(ticketRepository.saveAll(ticketsForReservation));

    }

    private void reserveSeat(Ticket ticket) {

        Session session = ticket.getSession();
        ArrayList<Integer> reservedSeats = session.getReservedSeats();
        reservedSeats.add(ticket.getRowNumber() * 1000 + ticket.getSeatNumber());
        session.setReservedSeats(reservedSeats);

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
            ticket.setPrice(sessionRepository.findById(ticket.getSession().getId()).get().getTicketPrice());
        }
        return tickets;
    }

}
