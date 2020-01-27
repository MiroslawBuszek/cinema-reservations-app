package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.service.MovieService;

import javax.validation.Valid;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public Iterable<Movie> getAllMovies() {
        return movieService.findAll();
    }

    @GetMapping
    public Movie getMovieById(@RequestParam long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
        return movie;
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
        if (movieService.findByTitle(movie.getTitle()) != null) {
            throw new BadRequestException("movie {title=" + movie.getTitle() + "} was found");
        }
        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    public ResponseEntity<Iterable> addMovieList(@Valid @RequestBody Iterable<Movie> movieList) {
        for (Movie movie : movieList) {
            if (movieService.findByTitle(movie.getTitle()) != null) {
                throw new BadRequestException("movie {title=" + movie.getTitle() + "} was found");
            }
        }
        return new ResponseEntity<>(movieService.saveAll(movieList), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Movie> updateMovie(@RequestParam long id, @Valid @RequestBody Movie movie) {
        Movie existingMovie = movieService.findById(id);
        if (existingMovie == null) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        } else {
            return new ResponseEntity(movieService.updateById(id, movie), HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteMovie(@RequestParam long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            throw new ResourceNotFoundException("movie {id=" + id + "} was not found");
        }
        movieService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
