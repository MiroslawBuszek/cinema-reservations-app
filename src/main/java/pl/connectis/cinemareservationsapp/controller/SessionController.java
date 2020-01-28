package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.service.SessionService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public Iterable<SessionDTO> getSessionsByExample(@RequestParam Map<String, String> requestParams) {

        SessionDTO sessionDTO = new SessionDTO();

        if (requestParams.containsKey("id")) {
            validateSessionExists(Long.parseLong(requestParams.get("id")));
            sessionDTO.setId(Long.parseLong(requestParams.get("id")));
        }
        if (requestParams.containsKey("movie")) {
            validateMovieExists(Long.parseLong(requestParams.get("movie")));
            sessionDTO.setMovieId(Long.parseLong(requestParams.get("movie")));
        }
        if (requestParams.containsKey("room")) {
            validateRoomExists(Long.parseLong(requestParams.get("room")));
            sessionDTO.setRoomId(Long.parseLong(requestParams.get("room")));
        }
        if (requestParams.containsKey("date")) {
            sessionDTO.setStartTime(LocalDateTime.parse(requestParams.get("date")));
        }

        Example<Session> exampleSession = Example.of(sessionService.convertToEntity(sessionDTO));

        return sessionService.convertToDTO(sessionService.findAll(exampleSession));
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO.getStartTime());
        return new ResponseEntity<>(sessionService.save(sessionDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SessionDTO> updateSession(@RequestBody SessionDTO sessionDTO) {
        validateSessionExists(sessionDTO.getId());
        if (sessionDTO.getRoomId() != 0) {
            validateRoomExists(sessionDTO.getRoomId());
        }
        if (sessionDTO.getMovieId() != 0) {
            validateMovieExists(sessionDTO.getMovieId());
        }
        validateStartTime(sessionDTO.getStartTime());
        return new ResponseEntity(sessionService.updateById(sessionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity deleteSession(@RequestParam long id) {
        Session session = sessionService.findById(id);
        if (session == null) {
            throw new ResourceNotFoundException("session {id=" + id + "} was not found");
        }
        sessionService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    void validateSessionExists(long sessionId ) {
        if (!sessionService.validateSessionExists(sessionId)) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
    }

    void validateMovieExists(long movieId ) {
        if (!sessionService.validateMovieExists(movieId)) {
            throw new ResourceNotFoundException("movie {id=" + movieId + "} was not found");
        }
    }
    void validateRoomExists(long roomId ) {
        if (!sessionService.validateRoomExists(roomId)) {
            throw new ResourceNotFoundException("room {id=" + roomId + "} was not found");
        }
    }

    void validateStartTime(LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in future");
        }
    }

}
