package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findById(long id);
}
