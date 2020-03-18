package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.SeatDTO;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    public List<SessionDTO> getSessionsByExample(Map<String, String> requestParams) {

        SessionDTO sessionDTO = new SessionDTO();

        if (requestParams.containsKey("id")) {
            validateSessionExists(Long.parseLong(requestParams.get("id")));
            sessionDTO.setId(Long.parseLong(requestParams.get("id")));
        }
        if (requestParams.containsKey("movie")) {
            validateMovieExists(Long.parseLong(requestParams.get("movie")));
            sessionDTO.setMovieId(Long.parseLong(requestParams.get("movie")));
        }
        if (requestParams.containsKey("room")) {
            validateRoomExists(Long.parseLong(requestParams.get("room")));
            sessionDTO.setRoomId(Long.parseLong(requestParams.get("room")));
        }
        if (requestParams.containsKey("date")) {
            sessionDTO.setStartDateTime(LocalDateTime.parse(requestParams.get("date")));
        }

        Example<Session> sessionExample = Example.of(convertToEntity(sessionDTO));

        return convertToDTO(sessionRepository.findAll(sessionExample));

    }

    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public SessionDTO save(SessionDTO sessionDTO) {

        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO.getStartDateTime());
        sessionDTO.setReservedSeats(new ArrayList<>());
        Session session = convertToEntity(sessionDTO);
        save(session);
        return convertToDTO(session);

    }


    @Transactional
    public SessionDTO updateById(SessionDTO sessionDTO) {
        validateSessionExists(sessionDTO.getId());

        Session existingSession = sessionRepository.findById(sessionDTO.getId());

        if (sessionDTO.getRoomId() != 0) {
            validateRoomExists(sessionDTO.getRoomId());
            existingSession.setRoom(roomRepository.findById(sessionDTO.getRoomId()));
        }

        if (sessionDTO.getMovieId() != 0) {
            validateMovieExists(sessionDTO.getMovieId());
            existingSession.setMovie(movieRepository.findById(sessionDTO.getMovieId()));
        }

        if (sessionDTO.getStartDateTime() != null) {
            validateStartTime(sessionDTO.getStartDateTime());
            existingSession.setStartTime(sessionDTO.getStartDateTime());
            existingSession.setStartDate(sessionDTO.getStartDateTime().toLocalDate());
        }

        return convertToDTO(existingSession);
    }

    public void deleteById(long sessionId) {

        validateSessionExists(sessionId);
        sessionRepository.deleteById(sessionId);

    }

    private void validateSessionExists(long sessionId) {

        if (sessionRepository.findById(sessionId) == null) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }

    }

    private void validateRoomExists(long roomId) {

        if (roomRepository.findById(roomId) == null) {
            throw new ResourceNotFoundException("room {id=" + roomId + "} was not found");
        }

    }

    private void validateMovieExists(long movieId) {

        if (movieRepository.findById(movieId) == null) {
            throw new ResourceNotFoundException("movie {id=" + movieId + "} was not found");
        }

    }

    private void validateStartTime(LocalDateTime startTime) {

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in future");
        }

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
        sessionDTO.setStartDateTime(session.getStartTime());
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
        session.setStartTime(sessionDTO.getStartDateTime());
        if (sessionDTO.getStartDateTime() != null) {
            session.setStartDate(sessionDTO.getStartDateTime().toLocalDate());
        }
        return session;
    }

    public List<SeatDTO> getSeats(long id) {

        validateSessionExists(id);
        List<SeatDTO> seats = new ArrayList<>();
        List<Integer> reservedSeats = sessionRepository.findById(id).getReservedSeats();
        List<Integer> layoutList = getLayoutList(sessionRepository.findById(id).getRoom().getLayout());

        for (int i = 0; i < layoutList.size(); i++) {

            for (int j = 0; j < layoutList.get(i); j++) {

                int seatAddress = i * 1000 + j;

                if (reservedSeats.contains(seatAddress)) {
                    seats.add(new SeatDTO(i, j, true));
                } else {
                    seats.add(new SeatDTO(i, j, false));
                }

            }

        }

        return seats;

    }

    private List<Integer> getLayoutList(String layout) {

        List<String> layoutStringList;
        List<Integer> layoutIntegerList = new ArrayList<>();
        layoutStringList = Arrays.asList(layout.split(","));

        for (String rowCapacityString : layoutStringList) {

            layoutIntegerList.add(Integer.parseInt(rowCapacityString));

        }

        return layoutIntegerList;

    }
}
