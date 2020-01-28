package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findById(long id);

    Client findByEmail(String email);

}
