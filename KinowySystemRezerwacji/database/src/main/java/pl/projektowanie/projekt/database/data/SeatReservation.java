package pl.projektowanie.projekt.database.data;

import lombok.Data;

@Data
public class SeatReservation {
    private String seat;
    private boolean active;

    public boolean isActive() {
        return active;
    }
}
