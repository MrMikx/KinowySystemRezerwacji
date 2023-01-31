package pl.projektowanie.projekt.backend.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.projections.MovieProjection;
import pl.projektowanie.projekt.database.repositories.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    private final Logger logger = Logger.getLogger(MovieService.class);
    private final MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "allMovies")
    public List<MovieProjection> getAllMoviesFromRepo() {
        return repository.findAllMovies();
    }

    public Movie getMovie(long id) {
        return repository.findById(id);
    }

    @CacheEvict(value = "allMovies", allEntries = true)
    public long updateMovie(Movie movie) {
        Movie repoMovie = repository.findById((long) movie.getId());

        repoMovie.setTitle(movie.getTitle());
        repoMovie.setCategory(movie.getCategory());
        repoMovie.setImageUrl(movie.getImageUrl());
        repoMovie.setLength(movie.getLength());
        repoMovie.setAge(movie.getAge());
        repoMovie.setReleaseDate(movie.getReleaseDate());
        repoMovie.setDescription(movie.getDescription());

        logger.log(Level.INFO, "Movie: " + movie.getTitle() + " has been updated");

        return repository.save(repoMovie).getId();
    }

    @CacheEvict(value = "allMovies", allEntries = true)
    public long addMovie(Movie movie) {
        logger.log(Level.INFO, "New movie: " + movie.getTitle() + " has been added");

        return repository.save(movie).getId();
    }

    @CacheEvict(value = "allMovies", allEntries = true)
    public long deleteMovie(long id) {
        logger.log(Level.INFO, "Movie: " + repository.findById(id).getTitle() + " has been deleted");

        repository.deleteById(id);
        return id;
    }
}
