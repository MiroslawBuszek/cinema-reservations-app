package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovieByExample(Map<String, String> requestParam) {
        Movie movie = new Movie();
        if (requestParam.containsKey("id")) {
            movie.setId(Long.parseLong(requestParam.get("id")));
        }
        movie.setTitle(requestParam.get("title"));
        movie.setCategory(requestParam.get("category"));
        if (requestParam.containsKey("length")) {
            movie.setLength(Integer.parseInt(requestParam.get("length")));
        }
        if (requestParam.containsKey("ageLimit")) {
            movie.setAgeLimit(Integer.parseInt(requestParam.get("ageLimit")));
        }
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Movie> movieExample = Example.of(movie, caseInsensitiveExampleMatcher);
        return movieRepository.findAll(movieExample);
    }

    public Movie save(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        log.info("movie {id=" + savedMovie.getId() + "} was added" + savedMovie.toString());
        return savedMovie;
    }

    @Transactional
    public Movie updateById(Movie movie) {
        validateMovieExists(movie.getId());
        Movie savedMovie = movieRepository.save(movie);
        log.info("movie {id=" + savedMovie.getId() + "} was updated: " + savedMovie.toString());
        return savedMovie;
    }

    public void deleteById(Long id) {
        validateMovieExists(id);
        movieRepository.deleteById(id);
        log.info("movie {id=" + id + "} was deleted");
    }

    private void validateMovieExists(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
    }

}
