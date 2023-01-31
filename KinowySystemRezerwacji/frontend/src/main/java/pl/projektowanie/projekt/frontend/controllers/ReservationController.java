package pl.projektowanie.projekt.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import pl.projektowanie.projekt.database.data.ReservationInfo;
import pl.projektowanie.projekt.database.data.Reserve;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {
    WebClient webClient = WebClient.create("http://localhost:8081");

    @GetMapping("movies/programme/reserve/{m_id}/{p_id}")
    public String displayReservationForm(Model model, @PathVariable long m_id, @PathVariable long p_id) {
        List<String> rows = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        ReservationInfo reservationInfo = webClient.get()
                .uri("reservation/" + m_id + "/" + p_id)
                .retrieve()
                .bodyToMono(ReservationInfo.class)
                .block();

        Reserve reservedSeats = webClient.get()
                .uri("reservation/seats/" + p_id)
                .retrieve()
                .bodyToMono(Reserve.class)
                .block();

        model.addAttribute("rows", rows);
        model.addAttribute("reservationInfo", reservationInfo);
        model.addAttribute("reservedSeats", reservedSeats);
        return "reserveSeat";
    }

    @PostMapping("/reserve/save")
    public String saveReservation(@ModelAttribute("reservedSeats") Reserve reserve,
                                  @ModelAttribute("reservationInfo") ReservationInfo reservationInfo) {
        Long addTicket = webClient.post()
                .uri("/ticket/save")
                .body(Mono.just(reserve), Reserve.class)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        Long addReservation = webClient.post()
                .uri("/reservation/save/" + addTicket)
                .body(Mono.just(reservationInfo), ReservationInfo.class)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String displaySuccess() {
        return "success";
    }
}
