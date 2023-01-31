package pl.projektowanie.projekt.database.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "programme")
public class Programme {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @OneToMany(mappedBy = "programme", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Reservation> reservations;

    public Programme(long id, LocalDate date, LocalTime time, Movie movie) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.movie = movie;
    }
}
