package pl.projektowanie.projekt.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.projections.MovieProjection;
import pl.projektowanie.projekt.backend.services.MovieService;

import java.util.List;

@RestController
public class MovieController {
    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieProjection>> getMovies() {
        List<MovieProjection> movies = service.getAllMoviesFromRepo();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable long id) {
        Movie movie = service.getMovie(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PutMapping("/movies/edit")
    public ResponseEntity<Long> updateMovie(@RequestBody Movie movie) {
        long updateId = service.updateMovie(movie);
        return new ResponseEntity<>(updateId, HttpStatus.OK);
    }

    @PostMapping("movies/add")
    public ResponseEntity<Long> addNewMovie(@RequestBody Movie movie) {
        long createdId = service.addMovie(movie);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @DeleteMapping("movies/delete/{id}")
    public ResponseEntity<Long> deleteMovie(@PathVariable long id) {
        long deletedId = service.deleteMovie(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }
}
