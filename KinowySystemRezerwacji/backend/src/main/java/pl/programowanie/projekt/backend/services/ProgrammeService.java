package pl.projektowanie.projekt.backend.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.projektowanie.projekt.database.model.Programme;
import pl.projektowanie.projekt.database.projections.ProgrammeProjection;
import pl.projektowanie.projekt.database.repositories.ProgrammeRepository;

import java.util.List;

@Service
public class ProgrammeService {
    private final Logger logger = Logger.getLogger(ProgrammeService.class);
    private final ProgrammeRepository repository;

    @Autowired
    public ProgrammeService(ProgrammeRepository repository) {
        this.repository = repository;
    }

    public List<ProgrammeProjection> getProgrammeOfMovie(long id) {
        return repository.findByMovieId(id);
    }

    public long addNewProgramme(Programme programme) {
        logger.log(Level.INFO, "New movie screening has been added for movieId: " + programme.getMovie().getId());
        return repository.save(programme).getId();
    }
}
