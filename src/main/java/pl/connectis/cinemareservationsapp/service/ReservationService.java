package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.connectis.cinemareservationsapp.security.IAuthenticationFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservationService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketMapper ticketMapper;

    public List<SeatDTO> getSeats(Long sessionId) {

        List<SeatDTO> seats = new ArrayList<>();
        List<Integer> reservedSeats = getSession(sessionId).getReservedSeats();
        List<Integer> layoutList = getLayoutList(getSession(sessionId).getRoom().getLayout());

        for (int i = 1; i < (layoutList.size() + 1 ); i++) {

            for (int j = 1; j < (layoutList.get(i - 1) + 1); j++) {

                int seatAddress = i * 1000 + j;

                if (reservedSeats.contains(seatAddress)) {
                    seats.add(new SeatDTO(i, j, true));
                } else {
                    seats.add(new SeatDTO(i, j, false));
                }

            }

        }
        return seats;

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
            Integer seatNumber = seat.getRowNumber() * 1000 + seat.getSeatNumber();
            if (reservedSeats.contains(seatNumber)) {
                throw new BadRequestException("seat " + seat.getSeatNumber() +
                        " in row " + seat.getRowNumber() + " is reserved");
            }
        }


        List<Ticket> ticketsForReservation = ticketMapper.mapTicketsFromReservationDTO(reservationDTO, username);
        for (Ticket ticket : ticketsForReservation) {
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

}
