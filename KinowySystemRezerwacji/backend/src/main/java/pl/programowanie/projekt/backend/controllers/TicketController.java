package pl.projektowanie.projekt.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.projektowanie.projekt.database.data.Reserve;
import pl.projektowanie.projekt.backend.services.TicketService;

@RestController
public class TicketController {
    private final TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/ticket/save")
    public ResponseEntity<Long> addNewTicket(@RequestBody Reserve reserve) {
        long createdId = service.addTicket(reserve);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }
}
