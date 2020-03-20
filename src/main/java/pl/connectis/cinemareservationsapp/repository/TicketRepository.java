package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAll(Example example);

    Optional<Ticket> findById(Long id);

}
