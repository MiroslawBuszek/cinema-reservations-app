package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.SessionDTO;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.mapper.SessionMapper;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.model.Seat;
import pl.connectis.cinemareservationsapp.model.Session;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;
import pl.connectis.cinemareservationsapp.repository.SessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

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
        Session session = new Session();
        if (requestParams.containsKey("movie") &&
                movieRepository.findById(Long.parseLong(requestParams.get("movie"))).isPresent()) {
            session.setMovie(movieRepository.findById(Long.parseLong(requestParams.get("movie"))).get());
        }
        if (requestParams.containsKey("room") &&
                roomRepository.findById(Long.parseLong(requestParams.get("room"))).isPresent()) {
            session.setRoom(roomRepository.findById(Long.parseLong(requestParams.get("room"))).get());
        }
        if (requestParams.containsKey("date")) {
            session.setStartDate(LocalDate.parse(requestParams.get("date")));
        }
        if (requestParams.containsKey("time")) {
            session.setStartTime(LocalTime.parse(requestParams.get("time")));
        }
        if (requestParams.containsKey("price")) {
            session.setTicketPrice(Double.parseDouble(requestParams.get("price")));
        }
        Example<Session> sessionExample = Example.of(session);
        return sessionMapper.mapDTOFromEntity(sessionRepository.findAll(sessionExample));
    }

    public SessionDTO save(SessionDTO sessionDTO) {
        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO.getStartDate(), sessionDTO.getStartTime());
        Session session = mapEntityFromDTO(sessionDTO);
        sessionRepository.save(session);
        return sessionMapper.mapDTOFromEntity(session);

    }

    private Session mapEntityFromDTO(SessionDTO sessionDTO) {
        Session session = sessionMapper.mapEntityFromDTO(sessionDTO);
        if (roomRepository.findById(session.getRoom().getId()).isPresent()) {
            session.setRoom(roomRepository.findById(session.getRoom().getId()).get());
            session.setSeats(createSeatList(roomRepository.findById(session.getRoom().getId()).get()));
        }
        if (movieRepository.findById(session.getMovie().getId()).isPresent()) {
            session.setMovie(movieRepository.findById(session.getMovie().getId()).get());
        }
        return session;
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

    private Map<String, Seat> createSeatList(Room room) {
        Map<String, Seat> sessionSeats = new HashMap<>();
        String layoutString = room.getLayout();
        String[] layoutStringArray = layoutString.split(",");
        int [] layoutIntArray = Stream.of(layoutStringArray).mapToInt(Integer::parseInt).toArray();
        int row = 0;
        int seat = 0;
        for (int i = 0; i < layoutIntArray.length; i++) {
            row++;
            for (int j = 0; j < layoutIntArray[i]; j++) {
                seat++;
                sessionSeats.put(row + "x" + seat, new Seat(row, seat, false));
            }
            seat = 0;
        }
        return sessionSeats;
    }

}
