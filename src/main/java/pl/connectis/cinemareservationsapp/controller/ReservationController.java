package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.ReservationDTO;
import pl.connectis.cinemareservationsapp.dto.SeatDTO;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("/session//seats")
    public ResponseEntity<List<SeatDTO>> getSeats(@RequestParam Long id) {

        return new ResponseEntity<>(reservationService.getSeats(id), HttpStatus.OK);

    }

    // TODO: implement adding of multiple tickets with validation
    @PostMapping("/reservation")
    public ResponseEntity<List<TicketDTO>> makeReservation(@RequestBody ReservationDTO reservationDTO) {

        return new ResponseEntity<>(reservationService.makeReservation(reservationDTO), HttpStatus.CREATED);

    }
}
