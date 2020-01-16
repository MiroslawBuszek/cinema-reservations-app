package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.service.MovieService;
import pl.connectis.cinemareservationsapp.service.RoomService;
import pl.connectis.cinemareservationsapp.service.SessionService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/session/all")
    public Iterable<Session> getAllSessions() {
        return sessionService.findAll();
    }

    @GetMapping("/session/{id}")
    public List<Session> getSessionById(@PathVariable long id) {
        return sessionService.findById(id);
    }

    @PostMapping("/session")
    public Session addSession(@Valid @RequestBody Session session) {
        return sessionService.save(session);
    }

    @PostMapping("/session/{roomId}/{movieId}")
    public Session makeReservation(@PathVariable("roomId") long roomId, @PathVariable("movieId") long movieId, @Valid @RequestBody Session session) {
        session.setRoom(roomService.findById(roomId).get(0));
        session.setMovie(movieService.findById(movieId).get(0));
        return sessionService.save(session);
    }

    @PostMapping("/session/many")
    public Iterable<Session> addSessionList(@Valid @RequestBody Iterable<Session> sessionList) {
        return sessionService.saveAll(sessionList);
    }

    @DeleteMapping("/session/{id}")
    public void deleteSession(@PathVariable long id) {
        sessionService.deleteById(id);
    }
}
