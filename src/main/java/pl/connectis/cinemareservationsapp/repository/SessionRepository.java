package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.connectis.cinemareservationsapp.model.Session;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findById(long id);
}
