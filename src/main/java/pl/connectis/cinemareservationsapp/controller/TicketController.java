package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.TicketDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Ticket;
import pl.connectis.cinemareservationsapp.service.TicketService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            throw new ResourceNotFoundException("ticket {id=" + id + "} was not found");
        }
        return ticket;
    }

    // TODO: Fix query by example search
    @GetMapping
    public Iterable<TicketDTO> getTicketByExample(@RequestParam Map<String, String> requestParams) {

        TicketDTO ticketDTO = new TicketDTO();

        if (requestParams.containsKey("id")) {
            validateTicketExists(Long.parseLong(requestParams.get("id")));
            ticketDTO.setId(Long.parseLong(requestParams.get("id")));
        }
//        if (requestParams.containsKey("user")) {
//            validateUserExists(Long.parseLong(requestParams.get("user")));
//            ticketDTO.setUserId(Long.parseLong(requestParams.get("user")));
//        }
        if (requestParams.containsKey("session")) {
            validateSessionExists(Long.parseLong(requestParams.get("session")));
            ticketDTO.setSessionId(Long.parseLong(requestParams.get("session")));
        }

        Example<Ticket> exampleTicket = Example.of(ticketService.convertToEntity(ticketDTO));

        return ticketService.convertToDTO(ticketService.findAll(exampleTicket));
    }

    @PostMapping
    public ResponseEntity<TicketDTO> addTicket(@RequestBody TicketDTO ticketDTO) {
//        validateUserExists(ticketDTO.getUserId());
        validateSessionExists(ticketDTO.getSessionId());
        validateSeatUnoccupied(ticketDTO);
        return new ResponseEntity<>(ticketService.makeReservation(ticketDTO), HttpStatus.CREATED);
    }

    // TODO: implement adding of multiple tickets with validation
    @PostMapping("/many")
    public Iterable<Ticket> addTicketList(@Valid @RequestBody Iterable<Ticket> ticketList) {
        return ticketService.saveAll(ticketList);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTicket(@RequestParam long id) {
        validateTicketExists(id);
        ticketService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    void validateTicketExists(long ticketId) {
        if (!ticketService.validateTicketExists(ticketId)) {
            throw new ResourceNotFoundException("ticket {id=" + ticketId + "} was not found");
        }
    }

    void validateUserExists(String username) {
        if (!ticketService.validateUserExists(username)) {
            throw new ResourceNotFoundException("user {username=" + username + "} was not found");
        }
    }

    void validateSessionExists(long sessionId) {
        if (!ticketService.validateSessionExists(sessionId)) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
    }

    void validateSeatUnoccupied(TicketDTO ticketDTO) {
        if (!ticketService.validateSeatUnoccupied(ticketDTO)) {
            throw new BadRequestException("seat " + ticketDTO.getSeatNumber() +
                    " in row " + ticketDTO.getRowNumber() + " is reserved");
        }
    }


}
