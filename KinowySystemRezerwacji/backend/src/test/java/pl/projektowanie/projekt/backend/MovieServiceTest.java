package pl.projektowanie.projekt.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.repositories.MovieRepository;
import pl.projektowanie.projekt.backend.bases.BaseMovieServiceTest;
import pl.projektowanie.projekt.backend.services.MovieService;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest extends BaseMovieServiceTest {
    @Mock
    MovieRepository repository;

    @InjectMocks
    MovieService service;

    @Test
    public void getAllMoviesFromRepoShouldReturnProperListOfMovies() {
        when(repository.findAllMovies()).thenReturn(movieProjectionList);

        assertEquals(movieProjectionList, service.getAllMoviesFromRepo());
        assertEquals(movieProjectionList.get(0).getTitle(), service.getAllMoviesFromRepo().get(0).getTitle());
    }

    @Test
    public void getMovieByIdShouldReturnMovieOfThatId() {
        when(repository.findById(1L)).thenReturn(testMovie);

        assertEquals(testMovie, service.getMovie(1));
    }

    @Test
    public void updateMovieShouldChangeSomeValues() {
        Movie changedDataMovie = new Movie(1L, "ChangedTitle", "Category", "url", 110, 16, LocalDate.parse("2010-10-02"), "description");

        when(repository.findById(1L)).thenReturn(testMovie);
        when(repository.save(any(Movie.class))).thenReturn(changedDataMovie);

        long returnedMovie = service.updateMovie(changedDataMovie);

        assertEquals(changedDataMovie.getTitle(), repository.findById(returnedMovie).getTitle());
        assertEquals(changedDataMovie.getLength(), repository.findById(returnedMovie).getLength());
        assertEquals(changedDataMovie.getReleaseDate(), repository.findById(returnedMovie).getReleaseDate());
        assertEquals(changedDataMovie.getAge(), repository.findById(returnedMovie).getAge());
    }

    @Test
    public void addMovieShouldAddNewMovieToDatabase() {
        when(repository.findById(1L)).thenReturn(testMovie);
        when(repository.save(any(Movie.class))).thenReturn(testMovie);

        long addedMovieId = service.addMovie(testMovie);

        assertEquals(testMovie.getTitle(), repository.findById(addedMovieId).getTitle());
    }

    @Test
    public void deleteMovieShouldDeleteMovieFromDatabase() {
        when(repository.findById(1L)).thenReturn(testMovie);

        service.deleteMovie(1);

        verify(repository, times(1)).deleteById(1L);
    }
}
