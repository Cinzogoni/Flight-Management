package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Flight {
    private String flightNumber;
    private String passengerName;
    private String departureCity;
    private String destinationCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private long durationTime;
    private int totalSeats;
    private int availableSeats;

    public Flight() {
    }

    public Flight(String flightNumber, String passengerName, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, long durationTime, int totalSeats, int availableSeats) {
        setFlightNumber(flightNumber);
        this.passengerName = passengerName;
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

    public String getDepartureCity() {
        return departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public final void setFlightNumber(String flightNumber) {
        if (flightNumber == null || !flightNumber.matches("^F\\d{4}$")) {
            throw new IllegalArgumentException("Ma chuyen bay phai co dang Fxyzt (VD: F0001)!");
        }
        this.flightNumber = flightNumber;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public final void setDepartureTime(LocalDateTime departureTime) {
        if (departureTime == null) {
            throw new IllegalArgumentException("Gio khoi hanh khong duoc de trong!");
        }
        if (this.arrivalTime != null && departureTime.isAfter(this.arrivalTime)) {
            throw new IllegalArgumentException("Gio khoi hanh phai truoc gio den!");
        }
        this.departureTime = departureTime;
        recalculateDuration();
    }

    public final void setArrivalTime(LocalDateTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Gio den khong duoc de trong!");
        }
        if (this.departureTime != null && arrivalTime.isBefore(this.departureTime)) {
            throw new IllegalArgumentException("Gio den phai sau gio khoi hanh!");
        }
        this.arrivalTime = arrivalTime;
        recalculateDuration();
    }

    private void recalculateDuration() {
        if (departureTime != null && arrivalTime != null && !arrivalTime.isBefore(departureTime)) {
            this.durationTime = Duration.between(departureTime, arrivalTime).toMinutes();
        } else {
            this.durationTime = 0;
        }
    }

    public final void setTotalSeats(int totalSeats) {
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Tong so ghe phai > 0!");
        }
        this.totalSeats = totalSeats;
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
    
    public boolean bookSeat(){
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }
    
    public boolean cancelSeat(){
        if (availableSeats < totalSeats) {
            availableSeats++;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        long hours = durationTime / 60;
        long mins = durationTime % 60;
        String durationStr = hours + "h " + mins + "m";
        String passengerStr = (passengerName != null && !passengerName.trim().isEmpty()) ? passengerName : "Chua co";

        return flightNumber + " | "
            + passengerStr + " | "
            + departureCity + " | " 
            + destinationCity + " | " 
            + departureTime + " | " 
            + arrivalTime + " | "
            + durationStr + " | Total: " 
            + totalSeats + " | Available: " 
            + availableSeats;
    }
}
