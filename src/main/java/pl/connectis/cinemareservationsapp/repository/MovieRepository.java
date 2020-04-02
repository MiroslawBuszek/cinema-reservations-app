package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Movie;

@Repository
public interface MovieRepository extends CustomJpaRepository<Movie, Long> {

    @Override
    default String entityName() {
        return "movie";
    }
}
