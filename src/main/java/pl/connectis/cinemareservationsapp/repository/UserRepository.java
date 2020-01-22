package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Use_r;

@Repository
public interface UserRepository extends JpaRepository <Use_r, Long>{
    Use_r findByUsername(String username);
}
