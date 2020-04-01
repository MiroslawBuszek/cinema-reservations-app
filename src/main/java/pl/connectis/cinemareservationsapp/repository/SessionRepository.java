package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Session;

@Repository
public interface SessionRepository extends CustomJpaRepository<Session, Long> {

    default Session findOrThrow(Long id) {
        return findByIdOrThrow(id, "session");
    }

    default void existsOrThrow(Long id) {
        existsByIdOrThrow(id, "session");
    }

}
