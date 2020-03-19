package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Movie;
import pl.connectis.cinemareservationsapp.service.MovieService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovieByExample(@RequestParam Map<String, String> requestParam) {

        return new ResponseEntity<>(movieService.getMovieByExample(requestParam), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {

        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Movie> updateMovie(@RequestParam Long id, @Valid @RequestBody Movie movie) {

        return new ResponseEntity<>(movieService.updateById(id, movie), HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteMovie(@RequestParam Long id) {

        movieService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
