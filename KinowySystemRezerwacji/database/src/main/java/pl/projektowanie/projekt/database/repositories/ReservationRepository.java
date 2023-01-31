package pl.projektowanie.projekt.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.model.Reservation;
import pl.projektowanie.projekt.database.projections.ReservationProjection;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(nativeQuery = true, value =
            "select p.id, p.movie_id as 'movieId', m.title, p.date, p.time " +
                    "from programme p " +
                    "left join movie m on m.id = p.movie_id " +
                    "where p.id = :programme and m.id = :movie")
    ReservationProjection getReservationData(Movie movie, Programme programme);
}
