package pl.connectis.cinemareservationsapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.ReservationDTO;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.service.ReservationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TicketDTO> makeReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.makeReservation(reservationDTO);
    }

}
