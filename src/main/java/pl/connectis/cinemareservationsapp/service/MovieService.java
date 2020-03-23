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
import java.util.Optional;

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
        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateById(Long id, Movie movie) {
        Movie existingMovie = getMovie(id);
        if (movie.getTitle() != null) {
            existingMovie.setTitle(movie.getTitle());
        }
        if (movie.getCategory() != null) {
            existingMovie.setCategory(movie.getCategory());
        }
        if (movie.getLength() != null) {
            existingMovie.setLength(movie.getLength());
        }
        if (movie.getDescription() != null) {
            existingMovie.setDescription(movie.getDescription());
        }
        if (movie.getAgeLimit() != null) {
            existingMovie.setAgeLimit(movie.getAgeLimit());
        }
        return movieRepository.save(existingMovie);
    }

    private Movie getMovie(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (!movieOptional.isPresent()) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
        return movieOptional.get();
    }

    public void deleteById(Long id) {
        validateMovieExists(id);
        movieRepository.deleteById(id);
    }

    private void validateMovieExists(Long id) {
        if (!movieRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
    }
}
