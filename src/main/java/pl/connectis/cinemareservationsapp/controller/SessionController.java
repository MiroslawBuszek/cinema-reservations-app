package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/session/all")
    public Iterable<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/session/{id}")
    public List<Session> getSessionById(@PathVariable long id) {
        return sessionRepository.findById(id);
    }

    @PostMapping("/session")
    public Session addSession(@Valid @RequestBody Session session) {
        return sessionRepository.save(session);
    }
}
