package pl.projektowanie.projekt.database.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfo {
    private Long id;
    private Long movieId;
    private LocalTime time;
    private LocalDate date;
    private String title;
    private String name;
    private String surname;
    private String email;
    private String phone;
}
