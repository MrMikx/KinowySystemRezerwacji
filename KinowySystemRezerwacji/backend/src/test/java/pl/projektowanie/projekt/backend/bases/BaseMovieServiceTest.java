package pl.projektowanie.projekt.backend.bases;

import org.junit.Before;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.projections.MovieProjection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseMovieServiceTest {
    protected List<MovieProjection> movieProjectionList = new ArrayList<>();
    protected Movie testMovie = new Movie(1L, "Test", "Category", "url", 100, 12, LocalDate.parse("2010-10-01"), "description");

    @Before
    public void prepareListForMovieServiceTest(){
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        MovieProjection movieProjection = factory.createProjection(MovieProjection.class);
        MovieProjection movieProjection2 = factory.createProjection(MovieProjection.class);

        movieProjection.setId(1L);
        movieProjection.setTitle("Test");
        movieProjection.setCategory("TestCategory");
        movieProjection.setImageUrl("url");
        movieProjection.setLength(123);
        movieProjection.setReleaseDate(LocalDate.parse("2010-10-01"));
        movieProjection.setDescription("description");
        movieProjectionList.add(movieProjection);

        movieProjection2.setId(2L);
        movieProjection2.setTitle("Test2");
        movieProjection2.setCategory("TestCategory, Test Category2");
        movieProjection2.setImageUrl("url2");
        movieProjection2.setLength(122);
        movieProjection2.setReleaseDate(LocalDate.parse("2012-10-01"));
        movieProjection2.setDescription("description2");
        movieProjectionList.add(movieProjection2);
    }
}
