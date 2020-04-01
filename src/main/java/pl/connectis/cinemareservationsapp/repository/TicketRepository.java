package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Ticket;

@Repository
public interface TicketRepository extends CustomJpaRepository<Ticket, Long> {

    default void existsOrThrow(Long id) {
        existsByIdOrThrow(id, "ticket");
    }

}
