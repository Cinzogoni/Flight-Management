package controller;

import model.Flight;
import ultils.Utils;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

// Xu ly nghiep vu Sua/Cap nhat chuyen bay.
public class FlightEditor {

    private final FlightManager flightManager;

    public FlightEditor(FlightManager flightManager) {
        this.flightManager = flightManager;
    }

    // Helper tim chuyen bay qua Utils hoac quăng exception
    private Flight getFlightOrThrow(String flightNumber) {
        Flight flight = Utils.findFlightByNumber(flightManager.getFlightList(), flightNumber);
        if (flight == null) {
            throw new NoSuchElementException("Khong tim thay chuyen bay co ma: " + flightNumber);
        }
        return flight;
    }

    // Cap nhat toan bo thong tin cua 1 chuyen bay da ton tai (khong doi ma chuyen bay).
    public boolean updateFlight(String flightNumber, String departureCity, String destinationCity,
            LocalDateTime departureTime, LocalDateTime arrivalTime,
            int totalSeats, int availableSeats) {
        Flight flight = getFlightOrThrow(flightNumber);

        validateCities(departureCity, destinationCity);
        validateSchedulePair(departureTime, arrivalTime);

        flight.setDepartureCity(departureCity);
        flight.setDestinationCity(destinationCity);
        applySchedule(flight, departureTime, arrivalTime);
        applySeats(flight, totalSeats, availableSeats);
        return true;
    }

    // Doi ma chuyen bay (khoa chinh), kiem tra trung lap voi cac chuyen bay khac truoc khi doi.
    public boolean editFlightNumber(String oldFlightNumber, String newFlightNumber) {
        Flight flight = getFlightOrThrow(oldFlightNumber);

        if (!oldFlightNumber.equalsIgnoreCase(newFlightNumber)
                && Utils.findFlightByNumber(flightManager.getFlightList(), newFlightNumber) != null) {
            throw new IllegalArgumentException("Ma chuyen bay " + newFlightNumber + " da ton tai!");
        }

        flight.setFlightNumber(newFlightNumber);
        return true;
    }

    private void validateCities(String departureCity, String destinationCity) {
        if (departureCity == null || departureCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Diem di khong duoc de trong!");
        }
        if (destinationCity == null || destinationCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Diem den khong duoc de trong!");
        }
        if (destinationCity.equalsIgnoreCase(departureCity)) {
            throw new IllegalArgumentException("Diem den phai khac diem di!");
        }
    }

    private void validateSchedulePair(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (departureTime == null || arrivalTime == null) {
            throw new IllegalArgumentException("Gio khoi hanh va gio den khong duoc de trong!");
        }
        if (departureTime.isAfter(arrivalTime)) {
            throw new IllegalArgumentException("Gio khoi hanh phai truoc gio den!");
        }
    }

    private void applySchedule(Flight flight, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (!departureTime.isAfter(flight.getArrivalTime())) {
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
        } else {
            flight.setArrivalTime(arrivalTime);
            flight.setDepartureTime(departureTime);
        }
    }

    private void applySeats(Flight flight, int totalSeats, int availableSeats) {
        flight.setTotalSeats(totalSeats);
        flight.setAvailableSeats(availableSeats);
    }
}
