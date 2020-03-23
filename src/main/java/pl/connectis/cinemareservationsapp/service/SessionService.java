package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.mapper.SessionMapper;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository,
                          RoomRepository roomRepository,
                          MovieRepository movieRepository,
                          SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.sessionMapper = sessionMapper;
    }

    public List<SessionDTO> getSessionsByExample(Map<String, String> requestParams) {
        SessionDTO sessionDTO = new SessionDTO();
        if (requestParams.containsKey("movie")) {
            sessionDTO.setMovieId(Long.parseLong(requestParams.get("movie")));
        }
        if (requestParams.containsKey("room")) {
            sessionDTO.setRoomId(Long.parseLong(requestParams.get("room")));
        }
        if (requestParams.containsKey("date")) {
            sessionDTO.setStartDate(LocalDate.parse(requestParams.get("date")));
        }
        if (requestParams.containsKey("time")) {
            sessionDTO.setStartTime(LocalTime.parse(requestParams.get("time")));
        }
        if (requestParams.containsKey("price")) {
            sessionDTO.setTicketPrice(Double.parseDouble(requestParams.get("price")));
        }
        Session session = sessionMapper.mapEntityFromDTO(sessionDTO);
        Example<Session> sessionExample = Example.of(session);
        return sessionMapper.mapDTOFromEntity(sessionRepository.findAll(sessionExample));
    }

    public SessionDTO save(SessionDTO sessionDTO) {
        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO.getStartDate(), sessionDTO.getStartTime());
        Session session = sessionMapper.mapEntityFromDTO(sessionDTO);
        if (session.getReservedSeats() == null) {
            session.setReservedSeats(new ArrayList<>());
        }
        sessionRepository.save(session);
        return sessionMapper.mapDTOFromEntity(session);

    }

    @Transactional
    public SessionDTO updateById(Long id, SessionDTO sessionDTO) {

        Session existingSession = getSession(id);

        if (sessionDTO.getRoomId() != null) {
            validateRoomExists(sessionDTO.getRoomId());
            existingSession.setRoom(roomRepository.findById(sessionDTO.getRoomId()).get());
        }

        if (sessionDTO.getMovieId() != null) {
            validateMovieExists(sessionDTO.getMovieId());
            existingSession.setMovie(movieRepository.findById(sessionDTO.getMovieId()).get());
        }

        if (validateStartDate(sessionDTO)) {
            existingSession.setStartDate(sessionDTO.getStartDate());
        }

        if (validateStartTime(sessionDTO)) {
            existingSession.setStartTime(sessionDTO.getStartTime());
        }

        if (sessionDTO.getTicketPrice() != null) {
            existingSession.setTicketPrice(sessionDTO.getTicketPrice());
        }

        return sessionMapper.mapDTOFromEntity(existingSession);
    }

    private boolean validateStartDate(SessionDTO sessionDTO) {
        return sessionDTO.getStartDate() != null && sessionDTO.getStartDate().isAfter(LocalDate.now());
    }

    private boolean validateStartTime(SessionDTO sessionDTO) {

        LocalTime startTime = sessionDTO.getStartTime();

        if (startTime == null) {
            return false;
        }

        LocalDate startDate = sessionDTO.getStartDate();

        if (startDate == null) {
            startDate = getSession(sessionDTO.getId()).getStartDate();
        }

        LocalDateTime startDataTime = LocalDateTime.of(startDate, startTime);

        return startDataTime.isAfter(LocalDateTime.now());

    }

    public void deleteById(Long sessionId) {
        validateSessionExists(sessionId);
        sessionRepository.deleteById(sessionId);
    }

    private void validateSessionExists(Long sessionId) {
        if (!sessionRepository.findById(sessionId).isPresent()) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }
    }

    private void validateRoomExists(Long roomId) {
        if (!roomRepository.findById(roomId).isPresent()) {
            throw new ResourceNotFoundException("room {id=" + roomId + "} was not found");
        }
    }

    private void validateMovieExists(Long movieId) {
        if (!movieRepository.findById(movieId).isPresent()) {
            throw new ResourceNotFoundException("movie {id=" + movieId + "} was not found");
        }
    }

    private void validateStartTime(LocalDate startDate, LocalTime startTime) {
        LocalDateTime startDataTime = LocalDateTime.of(startDate, startTime);
        if (startDataTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in future");
        }
    }

    private Session getSession(Long sessionId) {

        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);

        if (!sessionOptional.isPresent()) {
            throw new ResourceNotFoundException("session {id=" + sessionId + "} was not found");
        }

        return sessionOptional.get();

    }

}
