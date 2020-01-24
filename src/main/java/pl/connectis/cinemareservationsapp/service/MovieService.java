package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void deleteById(long id) {
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(long id, Movie updatedMovie) {
        return updatedMovie;
    }
}
