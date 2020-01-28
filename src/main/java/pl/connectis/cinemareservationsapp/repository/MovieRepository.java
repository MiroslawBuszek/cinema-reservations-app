package pl.connectis.cinemareservationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findById(long id);

    Movie findByTitle(String title);

}
