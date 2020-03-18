package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;

import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> getMovieByExample(Map<String, String> requestParam) {

        Movie movie = new Movie();

        if (requestParam.containsKey("id")) {
            movie.setId(Long.parseLong(requestParam.get("id")));
        }

        if (requestParam.containsKey("title")) {
            movie.setTitle(requestParam.get("title"));
        }

        if (requestParam.containsKey("category")) {
            movie.setCategory(requestParam.get("category"));
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
    public Movie updateById(long id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id);
        if (movie.getTitle() != null) {
            existingMovie.setTitle(movie.getTitle());
        }
        if (movie.getCategory() != null) {
            existingMovie.setCategory(movie.getCategory());
        }
        if (movie.getLength() != 0) {
            existingMovie.setLength(movie.getLength());
        }
        if (movie.getDescription() != null) {
            existingMovie.setDescription(movie.getDescription());
        }
        if (movie.getAgeLimit() != 0) {
            existingMovie.setAgeLimit(movie.getAgeLimit());
        }
        return existingMovie;
    }

    public void deleteById(long id) {
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(long id, Movie updatedMovie) {
        return updatedMovie;
    }
}
