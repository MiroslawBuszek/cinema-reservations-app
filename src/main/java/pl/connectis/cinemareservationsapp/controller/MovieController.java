package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.ResourceExistsException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.service.MovieService;

import javax.validation.Valid;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/all")
    public Iterable<Movie> getAllMovies() {
        return movieService.findAll();
    }

    @GetMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
        return movie;
    }

    @PostMapping("/movie")
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
        if (movieService.findByTitle(movie.getTitle())  == null) {
            throw new ResourceExistsException("movie {title=" + movie.getTitle() + "} was found");
        }
        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);
    }

    @PostMapping("/movie/many")
    public ResponseEntity<Iterable> addMovieList(@Valid @RequestBody Iterable<Movie> movieList) {
        for (Movie movie : movieList) {
            if (movieService.findByTitle(movie.getTitle()) != null) {
                throw new ResourceExistsException("movie {title=" + movie.getTitle() + "} was found");
            }
        }
        return new ResponseEntity<>(movieService.saveAll(movieList), HttpStatus.CREATED);
    }

    @PutMapping("/movie")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @Valid @RequestBody Movie movie) {
        Movie existingMovie = movieService.findById(id);
        if (existingMovie == null) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        } else {
            return new ResponseEntity(movieService.updateById(id, movie), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity deleteMovie(@PathVariable long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
        movieService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
