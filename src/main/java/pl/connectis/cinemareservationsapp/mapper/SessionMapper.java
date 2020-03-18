package pl.connectis.cinemareservationsapp.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class SessionMapper {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    public SessionDTO mapDTOFromEntity(Session session) {

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(session.getId());
        if (session.getMovie() != null) {
            sessionDTO.setMovieId(session.getMovie().getId());
        }
        if (session.getRoom() != null) {
            sessionDTO.setRoomId(session.getRoom().getId());
        }
        sessionDTO.setStartDateTime(session.getStartTime());
        return sessionDTO;

    }

    public List<SessionDTO> mapDTOFromEntity(List<Session> sessions) {

        List<SessionDTO> sessionDTOs = new ArrayList<>(sessions.size());

        for (Session session : sessions) {

            sessionDTOs.add(mapDTOFromEntity(session));

        }

        return sessionDTOs;

    }

    public Session convertToEntity(SessionDTO sessionDTO) {

        Session session = new Session();
        session.setMovie(movieRepository.findById(sessionDTO.getMovieId()));
        session.setRoom(roomRepository.findById(sessionDTO.getRoomId()));
        session.setStartTime(sessionDTO.getStartDateTime());
        session.setStartDate(sessionDTO.getStartDateTime().toLocalDate());
        return session;

    }

}
