package pl.projektowanie.projekt.database.projections;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ProgrammeProjection {
    Long getId();

    MovieInfo getMovie();

    LocalDate getDate();

    LocalTime getTime();

    //setters for test
    void setId(Long id);

    void setMovie(MovieInfo info);

    void setDate(LocalDate date);

    void setTime(LocalTime time);
}


