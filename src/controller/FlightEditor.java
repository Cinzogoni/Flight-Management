package controller;

import model.Flight;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

// Xu ly nghiep vu Sua/Cap nhat chuyen bay. Tach rieng khoi FlightManager de khong sua file cu,
// chi thao tac qua API cong khai da co cua FlightManager va Flight.
public class FlightEditor {
    private final FlightManager flightManager;

    public FlightEditor(FlightManager flightManager) {
        this.flightManager = flightManager;
    }

    // Cap nhat toan bo thong tin cua 1 chuyen bay da ton tai (khong doi ma chuyen bay).
    public boolean updateFlight(String flightNumber, String departureCity, String destinationCity,
                                 LocalDateTime departureTime, LocalDateTime arrivalTime,
                                 int totalSeats, int availableSeats) {
        Flight flight = flightManager.findFlightByNumber(flightNumber);
        if (flight == null) {
            throw new NoSuchElementException("Khong tim thay chuyen bay co ma: " + flightNumber);
        }

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
        Flight flight = flightManager.findFlightByNumber(oldFlightNumber);
        if (flight == null) {
            throw new NoSuchElementException("Khong tim thay chuyen bay co ma: " + oldFlightNumber);
        }
        if (!oldFlightNumber.equalsIgnoreCase(newFlightNumber)
                && flightManager.findFlightByNumber(newFlightNumber) != null) {
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

    // Flight.setDepartureTime/setArrivalTime tu kiem tra cheo voi gia tri HIEN TAI cua nhau,
    // nen phai chon dung thu tu goi de khong bi vuong trang thai trung gian khong hop le.
    // (Da chung minh: luon ton tai it nhat 1 trong 2 thu tu hop le khi cap gio moi da valid.)
    private void applySchedule(Flight flight, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (!departureTime.isAfter(flight.getArrivalTime())) {
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
        } else {
            flight.setArrivalTime(arrivalTime);
            flight.setDepartureTime(departureTime);
        }
    }

    // setTotalSeats khong phu thuoc availableSeats nen phai set truoc, roi moi set availableSeats
    // de no duoc kiem tra dung theo tong ghe MOI.
    private void applySeats(Flight flight, int totalSeats, int availableSeats) {
        flight.setTotalSeats(totalSeats);
        flight.setAvailableSeats(availableSeats);
    }
}
