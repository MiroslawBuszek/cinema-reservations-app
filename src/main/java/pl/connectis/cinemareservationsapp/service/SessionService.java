package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.dto.SeatDTO;
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
import java.util.*;

@Slf4j
@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    SessionMapper sessionMapper;

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

        Session session = sessionMapper.mapEntityFromDTO(sessionDTO);
        Example<Session> sessionExample = Example.of(session);
        return sessionMapper.mapDTOFromEntity(sessionRepository.findAll(sessionExample));

    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public SessionDTO save(SessionDTO sessionDTO) {

        validateMovieExists(sessionDTO.getMovieId());
        validateRoomExists(sessionDTO.getRoomId());
        validateStartTime(sessionDTO.getStartDate(), sessionDTO.getStartTime());
        Session session = sessionMapper.mapEntityFromDTO(sessionDTO);
        save(session);
        return sessionMapper.mapDTOFromEntity(session);

    }

    @Transactional
    public SessionDTO updateById(SessionDTO sessionDTO) {

        validateSessionExists(sessionDTO.getId());
        Session existingSession = sessionRepository.findById(sessionDTO.getId());

        if (sessionDTO.getRoomId() != 0) {
            validateRoomExists(sessionDTO.getRoomId());
            existingSession.setRoom(roomRepository.findById(sessionDTO.getRoomId()));
        }

        if (sessionDTO.getMovieId() != null) {
            validateMovieExists(sessionDTO.getMovieId());
            existingSession.setMovie(movieRepository.findById(sessionDTO.getMovieId()).get());
        }

        if (sessionDTO.getStartDate() != null && sessionDTO.getStartTime() != null) {
            validateStartTime(sessionDTO.getStartDate(), sessionDTO.getStartTime());
            existingSession.setStartTime(sessionDTO.getStartTime());
        }

        return sessionMapper.mapDTOFromEntity(existingSession);
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

    private void validateStartTime(LocalDate startDate, LocalTime startTime) {

        LocalDateTime startDataTime = LocalDateTime.of(startDate, startTime);
        if (startDataTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("start time should be in future");
        }

    }



    public List<SeatDTO> getSeats(long id) {

        validateSessionExists(id);
        List<SeatDTO> seats = new ArrayList<>();
        List<Integer> reservedSeats = sessionRepository.findById(id).getReservedSeats();
        List<Integer> layoutList = getLayoutList(sessionRepository.findById(id).getRoom().getLayout());

        for (int i = 1; i < (layoutList.size() + 1 ); i++) {

            for (int j = 1; j < (layoutList.get(i - 1) + 1); j++) {

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
