package pl.projektowanie.projekt.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projektowanie.projekt.database.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
