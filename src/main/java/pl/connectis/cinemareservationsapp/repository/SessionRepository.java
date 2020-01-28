package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Session;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findById(long id);

    List<Session> findAll();

}
