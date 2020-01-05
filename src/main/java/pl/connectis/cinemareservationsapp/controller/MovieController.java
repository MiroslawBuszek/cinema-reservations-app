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

    @GetMapping("/movies")
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/movies/{id}")
    public List<Movie> getMovieById(@PathVariable long id) {
        return movieRepository.findById(id);
    }

    @PostMapping("/movies")
    public Movie addMovie(@Valid @RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

}
