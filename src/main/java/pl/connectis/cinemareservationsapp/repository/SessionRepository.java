package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Session;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findById(long id);

    List<Session> findByRoomId(long roomId);

    List<Session> findByMovieId(long movieId);

}
