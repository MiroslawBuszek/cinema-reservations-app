package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Session> findAll() {
        return sessionRepository.findAll();
    }

    public List<Session> findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public List<Session> findByRoomId(long roomId) {
        return sessionRepository.findByRoomId(roomId);
    }

    public List<Session> findByMovieId(long movieId) {
        return sessionRepository.findByMovieId(movieId);
    }

    public List<Session> findByRoomIdOrMovieId(Long roomId, Long movieId) {
        if(movieId != null) {
            return findByMovieId(movieId);
        }
        if(roomId != null) {
            return findByRoomId(roomId);
        }
        return new ArrayList<>();
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public Session createSession(long roomId, long movieId, Session session) {
        session.setRoom(roomRepository.findById(roomId).get(0));
        session.setMovie(movieRepository.findById(movieId).get(0));
        return sessionRepository.save(session);
    }

    public Iterable<Session> saveAll(Iterable<Session> sessionList) {
        return sessionRepository.saveAll(sessionList);
    }

    public void deleteById(long id) {
        sessionRepository.deleteById(id);
    }
}
