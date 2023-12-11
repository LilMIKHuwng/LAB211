package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight implements Serializable, Comparable<Flight>{

    protected String flightNumber;
    protected String departureCity;
    protected String destinationCity;
    protected LocalDateTime departureTime;
    protected LocalDateTime arrivalTime;
    protected int availableSeats;
    protected int countSeats;

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats, int countSeats) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.countSeats = countSeats;
    }

    public int getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(int countSeats) {
        this.countSeats = countSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        //chỉ các Date về đúng format để in ra
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("| %-13s | %-16s | %-16s | %-16s | %-16s | %-5d |", flightNumber,
                departureCity, destinationCity, formatter.format(departureTime), formatter.format(arrivalTime),
                availableSeats);
    }

    @Override
    public int compareTo(Flight t) {
        return this.departureTime.isBefore(t.departureTime) ? 1 : this.departureTime.isEqual(t.departureTime) ? 0 : -1;
    }

}
