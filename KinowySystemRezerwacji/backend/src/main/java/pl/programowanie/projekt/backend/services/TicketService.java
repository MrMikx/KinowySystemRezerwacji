package pl.projektowanie.projekt.backend.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projektowanie.projekt.database.data.Reserve;
import pl.projektowanie.projekt.database.model.Ticket;
import pl.projektowanie.projekt.database.repositories.TicketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;

@Service
public class TicketService {
    private final Logger logger = Logger.getLogger(TicketService.class);
    private final TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public long addTicket(Reserve reserve) {
        List<String> seats = getSeats(reserve);
        Ticket ticket = new Ticket();

        ticket.setSeat(String.join(",", seats));

        logger.log(Level.INFO, "New ticket was bought");
        return repository.save(ticket).getId();
    }

    private List<String> getSeats(Reserve reserve) {
        List<String> seats = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : reserve.getMap().entrySet()) {
            if (TRUE.equals(entry.getValue()))
                seats.add(entry.getKey());
        }

        return seats;
    }
}
