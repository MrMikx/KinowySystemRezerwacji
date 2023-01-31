package pl.projektowanie.projekt.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.projections.ProgrammeProjection;
import pl.projektowanie.projekt.backend.services.ProgrammeService;

import java.util.List;

@RestController
public class ProgrammeController {
    private final ProgrammeService service;

    @Autowired
    public ProgrammeController(ProgrammeService service) {
        this.service = service;
    }

    @GetMapping("/programme/{id}")
    public ResponseEntity<List<ProgrammeProjection>> getProgrammeOfMovie(@PathVariable long id) {
        List<ProgrammeProjection> programme = service.getProgrammeOfMovie(id);
        return new ResponseEntity<>(programme, HttpStatus.OK);
    }

    @PostMapping("/programme/add")
    public ResponseEntity<Long> addNewProgramme(@RequestBody Programme programme) {
        long createdId = service.addNewProgramme(programme);
        return new ResponseEntity<>(createdId, HttpStatus.OK);
    }
}
