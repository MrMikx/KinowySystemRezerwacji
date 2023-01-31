package pl.projektowanie.projekt.backend.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projektowanie.projekt.database.data.ReservationInfo;
import pl.projektowanie.projekt.database.data.Reserve;
import pl.projektowanie.projekt.database.data.SeatReservation;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.model.Reservation;
import pl.projektowanie.projekt.database.projections.ReservationProjection;
import pl.projektowanie.projekt.database.repositories.MovieRepository;
import pl.projektowanie.projekt.database.repositories.ProgrammeRepository;
import pl.projektowanie.projekt.database.repositories.ReservationRepository;
import pl.projektowanie.projekt.database.repositories.TicketRepository;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class ReservationService {
    private final Logger logger = Logger.getLogger(ReservationService.class);
    private final ReservationRepository reservationRepo;
    private final ProgrammeRepository programmeRepo;
    private final TicketRepository ticketRepo;
    private final MovieRepository movieRepo;
    private final MailService mailService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepo, ProgrammeRepository programmeRepo, TicketRepository ticketRepo, MovieRepository movieRepo, MailService mailService) {
        this.reservationRepo = reservationRepo;
        this.programmeRepo = programmeRepo;
        this.ticketRepo = ticketRepo;
        this.movieRepo = movieRepo;
        this.mailService = mailService;
    }

    public ReservationProjection getReservation(Movie movie, Programme programme) {
        return reservationRepo.getReservationData(movie, programme);
    }

    public Reserve getReservedSeatsForProgramme(Programme programme) {
        Reserve reserve = new Reserve();
        SeatReservation seatReservation = new SeatReservation();

        Map<String, Boolean> mapMovie = new HashMap<>();

        getReservedSeats(programme.getId()).forEach(seat -> mapMovie.put(seat, true));
        reserve.setMap(mapMovie);
        reserve.setSeatReservation(seatReservation);

        return reserve;
    }

    private Set<String> getReservedSeats(long id) {
        Programme programme = programmeRepo.getById(id);
        Set<String> reservedSeats = new HashSet<>();

        for (Reservation reservation : programme.getReservations()) {
            reservedSeats.addAll(Arrays.asList(reservation.getTicket().getSeat().split(",")));
        }
        return reservedSeats;
    }

    public long addReservation(ReservationInfo info, long newTicketId) {
        Reservation newReservation = new Reservation();

        newReservation.setMovie(movieRepo.getById(info.getMovieId()));
        newReservation.setTicket(ticketRepo.getById(newTicketId));
        newReservation.setProgramme(programmeRepo.getById(info.getId()));
        newReservation.setName(info.getName());
        newReservation.setSurname(info.getSurname());
        newReservation.setEmail(info.getEmail());
        newReservation.setPhone(info.getPhone());

        String movieTitle = movieRepo.getById(info.getMovieId()).getTitle();

        logger.log(Level.INFO, "New reservation has been added for movie: " + movieTitle);

        try {
            mailService.sendMail(
                    info.getEmail(),
                    "Potwierdzenie rezerwacji biletu na film " + movieTitle,
                    "Rezerwacja na: " + info.getName() + " " + info.getSurname()
                            + "<br>Zarezerwowane miejsca: " + ticketRepo.getById(newTicketId).getSeat()
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return reservationRepo.save(newReservation).getId();
    }
}
