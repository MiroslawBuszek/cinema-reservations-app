package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;
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

    @PostMapping("/session")
    public Session addSession(@Valid @RequestBody Session session) {
        return sessionService.save(session);
    }
}
