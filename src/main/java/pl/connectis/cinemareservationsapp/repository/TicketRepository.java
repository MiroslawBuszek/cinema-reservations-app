package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
