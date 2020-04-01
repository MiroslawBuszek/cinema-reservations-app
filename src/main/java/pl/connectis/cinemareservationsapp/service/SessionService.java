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

    private final long ADS_AND_MAINTENANCE_TIME = 30;

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
        return sessionMapper.mapDTOFromEntity(
                sessionRepository.findAll(getSessionExampleFromRequestParams(requestParams)));
    }

    private Example<Session> getSessionExampleFromRequestParams(Map<String, String> requestParams) {
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
        return Example.of(session);
    }

    @Transactional
    public List<Seat> getSeats(Long sessionId) {
        validateSessionExists(sessionId);
        return new ArrayList<>(sessionRepository.findById(sessionId).get().getSeats().values());
    }

    @Transactional
    public SessionDTO save(SessionDTO sessionDTO) {
        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO);
        Session session = mapEntityFromDTO(sessionDTO);
        Session savedSession = sessionRepository.save(session);
        log.info("session {id= " + savedSession.getId() + "} was added: " + savedSession.toString());
        return sessionMapper.mapDTOFromEntity(savedSession);
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
    public SessionDTO updateById(SessionDTO sessionDTO) {
        validateSessionExists(sessionDTO.getId());
        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO);
        Session session = mapEntityFromDTO(sessionDTO);
        Session savedSession = sessionRepository.save(session);
        log.info("session {id=" + savedSession.getId() + "} was updated: " + savedSession.toString());
        return sessionMapper.mapDTOFromEntity(savedSession);
    }

    public void deleteById(Long sessionId) {
        validateSessionExists(sessionId);
        sessionRepository.deleteById(sessionId);
        log.info("session {id=" + sessionId + "} was deleted");
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

    private void validateStartTime(SessionDTO validatedSession) {
        LocalDate validatedStartDate = validatedSession.getStartDate();
        LocalTime validatedStartTime = validatedSession.getStartTime();
        LocalDateTime validatedStartDateTime = LocalDateTime.of(validatedStartDate, validatedStartTime);
        if (validatedStartDateTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in future");
        }
        Map<LocalDateTime, LocalDateTime> contiguousSessions = getContiguousSessionsStartAndEndTimes(validatedSession);
        if (contiguousSessions.isEmpty()) {
            return;
        }
        int validatedSessionMovieLength = movieRepository.findById(validatedSession.getMovieId()).get().getLength();
        LocalDateTime validatedEndDateTime = validatedStartDateTime.plusMinutes(validatedSessionMovieLength)
                .plusMinutes(ADS_AND_MAINTENANCE_TIME);
        contiguousSessions.forEach((sessionStart, sessionEnd) ->
                validateSessionsOverlapping(validatedStartDateTime, validatedEndDateTime, sessionStart, sessionEnd));
    }

    private Map<LocalDateTime, LocalDateTime> getContiguousSessionsStartAndEndTimes(SessionDTO validatedSessionDTO) {
        Map<LocalDateTime, Session> contiguousSessions = getSessionsStartDateTimesMap(
                validatedSessionDTO.getRoomId(), validatedSessionDTO.getStartDate());
        contiguousSessions.putAll(getSessionsStartDateTimesMap(
                validatedSessionDTO.getRoomId(), validatedSessionDTO.getStartDate().minusDays(1)));
        contiguousSessions.putAll(getSessionsStartDateTimesMap(
                validatedSessionDTO.getRoomId(), validatedSessionDTO.getStartDate().plusDays(1)));
        Map<LocalDateTime, LocalDateTime> startAndEndDateTimes = new HashMap<>();
        contiguousSessions.forEach((startDateTime, session) -> startAndEndDateTimes.put(startDateTime, startDateTime
                .plusMinutes(contiguousSessions.get(startDateTime).getMovie().getLength())
                .plusMinutes(ADS_AND_MAINTENANCE_TIME)));
        return startAndEndDateTimes;
    }

    private Map<LocalDateTime, Session> getSessionsStartDateTimesMap(Long roomId, LocalDate sessionStartDate) {
        Room sessionRoom = roomRepository.findById(roomId).get();
        Example<Session> sessionExample = Example.of(
                new Session(null,null, sessionRoom,
                        null, sessionStartDate,
                        null,null));
        List<Session> sessionsList = sessionRepository.findAll(sessionExample);
        Map<LocalDateTime, Session> sessionsMap = new HashMap<>();
        sessionsList.forEach(session -> sessionsMap.put(
                LocalDateTime.of(session.getStartDate(), session.getStartTime()), session));
        return sessionsMap;
    }

    private void validateSessionsOverlapping(LocalDateTime validatedStart,
                                             LocalDateTime validatedEnd,
                                             LocalDateTime sessionStart,
                                             LocalDateTime sessionEnd) {
        if (!validatedStart.isBefore(sessionStart) && validatedStart.isBefore(sessionEnd)) {
            throw new BadRequestException("session start is before end of previous session");
        }
        if (validatedStart.isBefore(sessionStart) && !validatedEnd.isBefore(sessionStart)) {
            throw new BadRequestException("session end is after start of next session");
        }
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
