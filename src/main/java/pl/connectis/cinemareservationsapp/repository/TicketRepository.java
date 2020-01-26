package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, QueryByExampleExecutor<Ticket> {

    Ticket findById(long id);

    List<Ticket> findByClientId(long roomId);

    List<Ticket> findBySessionId(long sessionId);

}
