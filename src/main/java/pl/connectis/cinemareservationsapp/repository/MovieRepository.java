package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Movie;

@Repository
public interface MovieRepository extends CustomJpaRepository<Movie, Long> {

    default Movie findOrThrow(Long id) {
        return findByIdOrThrow(id, "movie");
    }

    default void existsOrThrow(Long id) {
        existsByIdOrThrow(id, "movie");
    }

}
