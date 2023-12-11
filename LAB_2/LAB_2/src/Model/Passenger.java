package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Passenger extends Flight implements Serializable {

    private String cccd;
    private String reservationId;
    private String name;
    private String contactDetails;

    public Passenger(String cccd, String reservationId, String name, String contactDetails, String flightNumber, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats, int countSeats) {
        super(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, availableSeats, countSeats);
        this.cccd = cccd;
        this.reservationId = reservationId;
        this.name = name;
        this.contactDetails = contactDetails;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
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

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-13s | %-14s | %-15s | %-15s |", cccd, flightNumber, reservationId, name, contactDetails);
    }

}
