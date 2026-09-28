package model;

import java.time.LocalDateTime;
import ultils.Utils;

public class Flight extends Trip {

    private String flightNumber;
    private int totalSeats;
    private int availableSeats;

    public Flight() {
        super();
    }

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, long durationTime, int totalSeats, int availableSeats) {
        setFlightNumber(flightNumber);
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        setTotalSeats(totalSeats);
        setAvailableSeats(availableSeats);
        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public final void setFlightNumber(String flightNumber) {
        if (flightNumber == null || !flightNumber.matches("^F\\d{4}$")) {
            throw new IllegalArgumentException("Ma chuyen bay phai co dang Fxyzt (VD: F0001)!");
        }
        this.flightNumber = flightNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public final void setTotalSeats(int totalSeats) {
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Tong so ghe phai > 0!");
        }
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public final void setAvailableSeats(int availableSeats) {
        if (availableSeats < 0) {
            throw new IllegalArgumentException("So ghe trong khong duoc am!");
        }
        if (this.totalSeats > 0 && availableSeats > this.totalSeats) {
            throw new IllegalArgumentException("So ghe trong khong duoc vuot tong so ghe!");
        }
        this.availableSeats = availableSeats;
    }

    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public boolean cancelSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Utils.flightRow(flightNumber, departureCity, destinationCity,
                departureTime, arrivalTime,
                durationTime, totalSeats, availableSeats);
    }
}
