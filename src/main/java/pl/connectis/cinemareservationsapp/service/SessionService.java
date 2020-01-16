package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public Iterable<Session> findAll() {
        return sessionRepository.findAll();
    }

    public List<Session> findById(long id) {
        return sessionRepository.findById(id);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public Iterable<Session> saveAll(Iterable<Session> sessionList) {
        return sessionRepository.saveAll(sessionList);
    }

    public void deleteById(long id) {
        sessionRepository.deleteById(id);
    }
}
