package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.service.SessionService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/session/all")
    public Iterable<Session> getAllSessions() {
        return sessionService.findAll();
    }

    @GetMapping("/session/{id}")
    public List<Session> getSessionById(@PathVariable long id) {
        return sessionService.findById(id);
    }

    @GetMapping("/session")
    public List<Session> getSessionsByRoomIdOrRoomId(
            @RequestParam(value = "room", required = false) Long roomId,
            @RequestParam(value = "movie", required = false) Long movieId) {
        return sessionService.findByRoomIdOrMovieId(roomId, movieId);
    }

    @PostMapping("/session")
    public Session createSession(@RequestParam(value = "room") long roomId, @RequestParam(value = "movie") long movieId, @Valid @RequestBody Session session) {
        return sessionService.createSession(roomId, movieId, session);
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
