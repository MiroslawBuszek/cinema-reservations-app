package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.service.MovieService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/all")
    public Iterable<Movie> getAllMovies() {
        return movieService.findAll();
    }

    @GetMapping("/movie/{id}")
    public List<Movie> getMovieById(@PathVariable long id) {
        return movieService.findById(id);
    }

    @PostMapping("/movie")
    public Movie addMovie(@Valid @RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @PostMapping("/movie/many")
    public Iterable<Movie> addMovieList(@Valid @RequestBody Iterable<Movie> movieList) {
        return movieService.saveAll(movieList);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable long id) {
        movieService.deleteById(id);
    }

    // TODO: fix the updateMovie method
    @PutMapping("/movie/{id}")
    public Movie updateMovie(@PathVariable long id, @Valid @RequestBody Movie updatedMovie) {
        return movieService.updateMovie(id, updatedMovie);
    }

}
