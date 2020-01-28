package pl.connectis.cinemareservationsapp.service;

import org.modelmapper.ModelMapper;
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

import java.time.LocalDate;
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

    @Autowired
    ModelMapper modelMapper;

    public Iterable<Session> findAll(Example<Session> exampleSession) {
        return sessionRepository.findAll();
    }

    public SessionDTO findDTOById(long sessionId) {
        return convertToDTO(sessionRepository.findById(sessionId));
    }

    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Movie findMovieById(long movieId) {
        return movieRepository.findById(movieId);
    }

    public Room findRoomById(long roomId) {
        return roomRepository.findById(roomId);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public Session createSession(long roomId, long movieId, Session session) {

        session.setRoom(roomRepository.findById(roomId));
        session.setMovie(movieRepository.findById(movieId));
        session.setReservedSeats(new ArrayList<>());
        session.setStartDate(session.getStartTime().toLocalDate());
        return sessionRepository.save(session);
    }

    public Iterable<Session> saveAll(Iterable<Session> sessionList) {
        return sessionRepository.saveAll(sessionList);
    }

    @Transactional
    public Session updateById(long sessionId, Long roomId, Long movieId, Session session) {
        Session existingSession = sessionRepository.findById(sessionId);
        if (roomId != null) {
            existingSession.setRoom(roomRepository.findById((long)roomId));
        }
        if (movieId != null) {
            existingSession.setMovie(movieRepository.findById((long)movieId));
        }
        if (session.getStartTime() != null) {
            existingSession.setStartTime(session.getStartTime());
            existingSession.setStartDate(session.getStartTime().toLocalDate());
        }
        return existingSession;
    }

    public void deleteById(long id) {
        sessionRepository.deleteById(id);
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
        modelMapper.addMappings(Session::getMovie.getId, SessionDTO::setMovieId);
        modelMapper.addMappings(Session::getRoom.getId, SessionDTO::getRoomId);
        return modelMapper.map(session, SessionDTO.class);
    }

    public Session convertToEntity(SessionDTO sessionDTO) {
        return modelMapper.map(sessionDTO, Session.class);
    }

}
