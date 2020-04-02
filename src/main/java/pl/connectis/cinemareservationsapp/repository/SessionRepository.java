package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Session;

@Repository
public interface SessionRepository extends CustomJpaRepository<Session, Long> {

    @Override
    default String entityName() {
        return "session";
    }
}
