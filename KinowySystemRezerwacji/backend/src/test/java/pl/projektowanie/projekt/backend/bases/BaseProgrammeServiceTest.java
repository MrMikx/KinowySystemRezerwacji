package pl.projektowanie.projekt.backend.bases;

import org.junit.Before;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import pl.projektowanie.projekt.database.projections.MovieInfo;
import pl.projektowanie.projekt.database.projections.ProgrammeProjection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BaseProgrammeServiceTest {
    protected List<ProgrammeProjection> programmeProjectionList = new ArrayList<>();

    @Before
    public void prepareListOfProgramme() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

        ProgrammeProjection programmeProjection = factory.createProjection(ProgrammeProjection.class);
        ProgrammeProjection programmeProjection2 = factory.createProjection(ProgrammeProjection.class);

        MovieInfo movieInfo = factory.createProjection(MovieInfo.class);
        movieInfo.setId(1L);

        programmeProjection.setId(1L);
        programmeProjection.setMovie(movieInfo);
        programmeProjection.setDate(LocalDate.parse("2022-02-01"));
        programmeProjection.setTime(LocalTime.parse("14:30:00"));
        programmeProjectionList.add(programmeProjection);

        programmeProjection2.setId(2L);
        programmeProjection2.setMovie(movieInfo);
        programmeProjection2.setDate(LocalDate.parse("2022-02-01"));
        programmeProjection2.setTime(LocalTime.parse("15:30:00"));
        programmeProjectionList.add(programmeProjection2);
    }
}
