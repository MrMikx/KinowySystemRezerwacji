package pl.projektowanie.projekt.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.model.Reservation;
import pl.projektowanie.projekt.database.model.Ticket;
import pl.projektowanie.projekt.database.projections.ReservationProjection;
import pl.projektowanie.projekt.database.repositories.MovieRepository;
import pl.projektowanie.projekt.database.repositories.ProgrammeRepository;
import pl.projektowanie.projekt.database.repositories.ReservationRepository;
import pl.projektowanie.projekt.database.repositories.TicketRepository;
import pl.projektowanie.projekt.backend.services.ReservationService;
import pl.projektowanie.projekt.database.data.ReservationInfo;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
    @Mock
    ReservationRepository reservationRepo;

    @Mock
    TicketRepository ticketRepo;

    @Mock
    MovieRepository movieRepo;

    @Mock
    ProgrammeRepository programmeRepo;

    @Mock
    Movie movie;

    @Mock
    Ticket ticket;

    @Mock
    Programme programme;

    @InjectMocks
    ReservationService service;

    @Test
    public void getReservationsShouldReturnReservationData() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        ReservationProjection reservationProjection = factory.createProjection(ReservationProjection.class);

        reservationProjection.setId(1L);
        reservationProjection.setMovieId(1L);
        reservationProjection.setTitle("Title1");
        reservationProjection.setDate(LocalDate.parse("2022-02-01"));
        reservationProjection.setTime(LocalTime.parse("14:30:00"));

        when(reservationRepo.getReservationData(movie, programme)).thenReturn(reservationProjection);

        assertEquals(reservationProjection, service.getReservation(movie, programme));
        assertEquals(reservationProjection.getTitle(), service.getReservation(movie, programme).getTitle());
    }

    @Test
    public void addReservationShouldAddNewReservationToDatabase() {
        ReservationInfo reservationInfo = new ReservationInfo(1L, 1L, LocalTime.parse("15:30:00"), LocalDate.parse("2022-02-01"), "Title", "Jan", "Kowalski", "test@o2.pl", "123123123");
        when(ticketRepo.getById(1L)).thenReturn(ticket);
        when(movieRepo.findById(1L)).thenReturn(movie);
        when(programmeRepo.getById(1L)).thenReturn(programme);

        Reservation reservation = new Reservation(1L, movie, ticket, programme, "Jan", "Kowalski", "test@o2.pl", "123123123");
        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);

        Long addedId = service.addReservation(reservationInfo, 1L);

        assertEquals(reservation.getId(), addedId);
    }
}
