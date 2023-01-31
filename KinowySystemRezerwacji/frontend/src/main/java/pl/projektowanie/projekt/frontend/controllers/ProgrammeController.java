package pl.projekotwanie.projekt.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import pl.projektowanie.projekt.database.model.Movie;
import pl.projektowanie.projekt.database.model.Programme;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProgrammeController {
    WebClient webClient = WebClient.create("http://localhost:8081");

    @GetMapping("movies/programme/{id}")
    public String displayProgramme(Model model, @PathVariable long id) {
        Programme[] programme = webClient.get()
                .uri("/programme/" + id)
                .retrieve()
                .bodyToMono(Programme[].class)
                .block();

        List<Programme> programmeList = Arrays.asList(programme);
        model.addAttribute("programmeList", programmeList);

        return "movieProgramme";
    }

    @GetMapping("movies/programme/addForm")
    public String displayAddProgrammeForm(Model model) {
        Movie[] movies = webClient.get()
                .uri("/movies")
                .retrieve()
                .bodyToMono(Movie[].class)
                .block();

        List<Movie> movieList = Arrays.asList(movies);

        model.addAttribute("programme", new Programme());
        model.addAttribute("movies", movieList);
        return "addProgrammeForm";
    }

    @PostMapping("programme/addNew")
    public String submitAddProgramme(@ModelAttribute Programme programme) {
        Long programmeAdd = webClient.post()
                .uri("/programme/add")
                .body(Mono.just(programme), Programme.class)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        return "redirect:/movies";
    }
}
