package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Session;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findById(long id);

    List<Session> findByRoomId(long roomId);

    List<Session> findByMovieId(long movieId);

    List<Session> findByStartDate(LocalDate localDate);

}
