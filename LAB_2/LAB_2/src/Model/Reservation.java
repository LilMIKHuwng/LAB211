package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation extends Flight implements Serializable {

    private String reservationId;
    private String name;
    private String seat;

    public Reservation(String reservationId, String name, String seat, String flightNumber, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats, int countSeats) {
        super(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, availableSeats, countSeats);
        this.reservationId = reservationId;
        this.name = name;
        this.seat = seat;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("| %-14s | %-15s | %-16s | %-16s | %-16s | %-16s | %-4s |",
                reservationId, name, departureCity, destinationCity, formatter.format(departureTime),
                formatter.format(arrivalTime), seat);
    }

}
