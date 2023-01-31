package pl.projektowanie.projekt.database.projections;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationProjection {
    Long getId();

    Long getMovieId();

    String getTitle();

    LocalDate getDate();

    LocalTime getTime();

    //    setters for test
    void setId(Long id);

    void setMovieId(Long movieId);

    void setTitle(String title);

    void setDate(LocalDate date);

    void setTime(LocalTime time);
}
