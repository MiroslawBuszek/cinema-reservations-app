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

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{id}")
    public SessionDTO getSessionById(@PathVariable long id) {
        SessionDTO sessionDTO = sessionService.findDTOById(id);
        if (sessionDTO == null) {
            throw new ResourceNotFoundException("session {id=" + id + "} was not found");
        }
        return sessionDTO;
    }

    @GetMapping
    public Iterable<Session> getSessionsByExample(@RequestParam Map<String, String> requestParams) {

        Session session = new Session();

        if (requestParams.containsKey("id")) {
            session.setId(Long.parseLong(requestParams.get("id"))); ;
        }
        if (requestParams.containsKey("movie")) {
            session.setMovie(sessionService.findMovieById(Long.parseLong(requestParams.get("movie"))));
        }
        if (requestParams.containsKey("room")) {
            session.setRoom(sessionService.findRoomById(Long.parseLong(requestParams.get("room"))));
        }
        if (requestParams.containsKey("date")) {
            session.setStartDate(LocalDate.parse(requestParams.get("date")));
        }

        Example<Session> exampleSession = Example.of(session);

        return sessionService.findAll(exampleSession);
    }

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestParam(value = "room") long roomId,
                                        @RequestParam(value = "movie") long movieId,
                                        @Valid @RequestBody Session session) {
        if (!sessionService.validateRoomExists(roomId)) {
            throw new ResourceNotFoundException("room {id=" + roomId + "} was not found");
        }
        if (!sessionService.validateMovieExists(movieId)) {
            throw new ResourceNotFoundException("movie {id=" + movieId + "} was not found");
        }
        if (session.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in the future");
        }
        return new ResponseEntity<>(sessionService.createSession(roomId, movieId, session), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable(name = "id") long sessionId,
                                                 @RequestParam(value = "room", required = false) Long roomId,
                                                 @RequestParam(value = "movie", required = false) Long movieId,
                                                 @Valid @RequestBody Session session) {
        Session existingSession = sessionService.findById(sessionId);
        if (existingSession == null) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
        if (roomId != null && !sessionService.validateRoomExists(roomId)) {
            throw new ResourceNotFoundException("room {id=" + roomId + "} was not found");
        }
        if (movieId != null && !sessionService.validateMovieExists(movieId)) {
            throw new ResourceNotFoundException("movie {id=" + movieId + "} was not found");
        }
        if (session.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in future");
        }
        return new ResponseEntity(sessionService.updateById(sessionId, roomId, movieId, session), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSession(@PathVariable long id) {
        Session session = sessionService.findById(id);
        if (session == null) {
            throw new ResourceNotFoundException("session {id=" + id + "} was not found");
        }
        sessionService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
