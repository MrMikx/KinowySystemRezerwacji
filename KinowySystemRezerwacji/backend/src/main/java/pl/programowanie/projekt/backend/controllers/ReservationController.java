package pl.projektowanie.projekt.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projektowanie.projekt.database.data.ReservationInfo;
import pl.projektowanie.projekt.database.data.Reserve;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.projections.ReservationProjection;
import projektowanie.projekt.backend.services.ReservationService;

@RestController
public class ReservationController {
    ReservationService service;

    @Autowired
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("reservation/{movie}/{programme}")
    public ResponseEntity<ReservationProjection> getAllReservationsOfProgramme(@PathVariable Movie movie, @PathVariable Programme programme) {
        ReservationProjection reservations = service.getReservation(movie, programme);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("reservation/seats/{programme}")
    public ResponseEntity<Reserve> getReservedSeats(@PathVariable Programme programme) {
        Reserve reserve = service.getReservedSeatsForProgramme(programme);
        return new ResponseEntity<>(reserve, HttpStatus.OK);
    }

    @PostMapping("/reservation/save/{newTicketId}")
    public ResponseEntity<Long> addNewReservation(@RequestBody ReservationInfo reservationInfo, @PathVariable long newTicketId) {
        long createdId = service.addReservation(reservationInfo, newTicketId);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }
}
