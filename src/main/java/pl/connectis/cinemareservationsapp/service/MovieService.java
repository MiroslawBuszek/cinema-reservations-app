package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(long id) {
        return movieRepository.findById(id);
    }

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Iterable<Movie> saveAll(Iterable<Movie> movieList) {
        return movieRepository.saveAll(movieList);
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
