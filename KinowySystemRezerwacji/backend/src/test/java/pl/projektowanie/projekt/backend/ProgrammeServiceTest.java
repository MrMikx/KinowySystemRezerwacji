package pl.projektowanie.projekt.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.repositories.ProgrammeRepository;
import pl.projektowanie.projekt.backend.bases.BaseProgrammeServiceTest;
import pl.projektowanie.projekt.backend.services.ProgrammeService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProgrammeServiceTest extends BaseProgrammeServiceTest {
    @Mock
    ProgrammeRepository repository;

    @InjectMocks
    ProgrammeService service;

    @Test
    public void programmeOfMovieShouldReturnListOfProgrammeOfGivenMovieId() {
        when(repository.findByMovieId(1)).thenReturn(programmeProjectionList);

        assertEquals(programmeProjectionList, service.getProgrammeOfMovie(1));
        assertEquals(2, service.getProgrammeOfMovie(1).size());
    }

    @Test
    public void addNewProgrammeShouldAddNewProgrammeToDatabase() {
        Programme newProgramme = new Programme(1L, LocalDate.parse("2022-02-01"), LocalTime.parse("14:30:00"), new Movie(1L));
        when(repository.save(any(Programme.class))).thenReturn(newProgramme);

        long addedProgrammeId = service.addNewProgramme(newProgramme);

        assertEquals(newProgramme.getId(), addedProgrammeId);
    }
}
