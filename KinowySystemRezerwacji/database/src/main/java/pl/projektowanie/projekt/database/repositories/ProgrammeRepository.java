package pl.projektowanie.projekt.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.projections.ProgrammeProjection;

import java.util.List;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    @Query(value = "select p from Programme p where p.movie.id = ?1 and p.date >= current_date")
    List<ProgrammeProjection> findByMovieId(long id);
}
