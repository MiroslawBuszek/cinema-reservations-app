package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, String> {

    User findByUsername(String username);

}
