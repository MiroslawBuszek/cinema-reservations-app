package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.repository.MovieRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movie/all")
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/movie/{id}")
    public List<Movie> getMovieById(@PathVariable long id) {
        return movieRepository.findById(id);
    }

    @PostMapping("/movie")
    public Movie addMovie(@Valid @RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PostMapping("/movie/many")
    public Iterable<Movie> addMovieList(@Valid @RequestBody Iterable<Movie> movieList) {
        return movieRepository.saveAll(movieList);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable long id) {
        movieRepository.deleteById(id);
    }

    // TODO: fix the updateMovie method
    @PutMapping("/movie/{id}")
    public Movie updateMovie(@Valid @RequestBody Movie updatedMovie, @PathVariable long id) {
        return movieRepository.save(updatedMovie);
    }

}
