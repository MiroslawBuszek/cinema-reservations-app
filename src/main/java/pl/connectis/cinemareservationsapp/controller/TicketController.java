package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.service.TicketService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/myticket")
    public ResponseEntity<List<TicketDTO>> getTicketById(@RequestParam Map<String, String> requestParam) {

        return new ResponseEntity<>(ticketService.getMyTicketsByExample(requestParam), HttpStatus.OK);

    }

    @GetMapping("/ticket")
    public ResponseEntity<List<TicketDTO>> getTicketsByExample(@RequestParam Map<String, String> requestParams) {

        return new ResponseEntity<>(ticketService.getTicketsByExample(requestParams), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<TicketDTO> addTicket(@RequestBody TicketDTO ticketDTO) {

        return new ResponseEntity<>(ticketService.makeReservation(ticketDTO), HttpStatus.CREATED);

    }

    // TODO: implement adding of multiple tickets with validation
    @PostMapping("/many")
    public Iterable<Ticket> addTicketList(@Valid @RequestBody Iterable<Ticket> ticketList) {
        return ticketService.saveAll(ticketList);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTicket(@RequestParam long id) {

        ticketService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
