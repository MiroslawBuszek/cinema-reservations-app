package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.model.Room;
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

    public List<Session> findAll(Example<Session> exampleSession) {
        return sessionRepository.findAll();
    }

    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public SessionDTO save(SessionDTO sessionDTO) {
        Session session = convertToEntity(sessionDTO);
        save(session);
        return convertToDTO(session);
    }

    public Iterable<Session> saveAll(Iterable<Session> sessionList) {
        return sessionRepository.saveAll(sessionList);
    }

    @Transactional
    public SessionDTO updateById(SessionDTO sessionDTO) {
        Session existingSession = sessionRepository.findById(sessionDTO.getId());
        if (sessionDTO.getRoomId() != 0) {
            existingSession.setRoom(roomRepository.findById(sessionDTO.getRoomId()));
        }
        if (sessionDTO.getMovieId() != 0) {
            existingSession.setMovie(movieRepository.findById(sessionDTO.getMovieId()));
        }
        if (sessionDTO.getReservedSeats() != null) {
            existingSession.setReservedSeats(sessionDTO.getReservedSeats());
        }
        if (sessionDTO.getStartTime() != null) {
            existingSession.setStartTime(sessionDTO.getStartTime());
            existingSession.setStartDate(sessionDTO.getStartTime().toLocalDate());
        }
        return convertToDTO(existingSession);
    }

    public void deleteById(long id) {
        sessionRepository.deleteById(id);
    }

    public boolean validateSessionExists(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            return false;
        }
        return true;
    }

    public boolean validateRoomExists(long roomId) {
        Room room = roomRepository.findById(roomId);
        if (room == null) {
            return false;
        }
        return true;
    }

    public boolean validateMovieExists(long movieId) {
        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            return false;
        }
        return true;
    }

    public SessionDTO convertToDTO(Session session) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(session.getId());
        if (session.getMovie() != null) {
            sessionDTO.setMovieId(session.getMovie().getId());
        }
        if (session.getRoom() != null) {
            sessionDTO.setRoomId(session.getRoom().getId());
        }
        sessionDTO.setReservedSeats(session.getReservedSeats());
        sessionDTO.setStartTime(session.getStartTime());
        return sessionDTO;
    }

    public List<SessionDTO> convertToDTO(List<Session> sessionList) {
        List<SessionDTO> sessionDTOList = new ArrayList<>(sessionList.size());
        for (Session session : sessionList) {
            sessionDTOList.add(convertToDTO(session));
        }
        return sessionDTOList;
    }

    public Session convertToEntity(SessionDTO sessionDTO) {
        Session session = sessionRepository.findById(sessionDTO.getId());
        if (session == null) {
            session = new Session();
        }
        session.setMovie(movieRepository.findById(sessionDTO.getMovieId()));
        session.setRoom(roomRepository.findById(sessionDTO.getRoomId()));
        session.setReservedSeats(sessionDTO.getReservedSeats());
        session.setStartTime(sessionDTO.getStartTime());
        if (sessionDTO.getStartTime() != null) {
            session.setStartDate(sessionDTO.getStartTime().toLocalDate());
        }
        return session;
    }

}
