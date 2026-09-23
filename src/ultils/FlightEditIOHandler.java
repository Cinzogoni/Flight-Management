package ultils;

import controller.FlightEditor;
import controller.FlightManager;
import model.Flight;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// I/O rieng cho chuc nang Sua/Cap nhat chuyen bay. Tach khoi FlightIOHandler de khong sua file cu,
// dung chung FlightManager va goi nghiep vu qua FlightEditor.
public class FlightEditIOHandler {
    private final FlightManager flightManager;
    private final FlightEditor flightEditor;
    private final Scanner scan;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public FlightEditIOHandler(FlightManager flightManager, Scanner scan) {
        this.flightManager = flightManager;
        this.flightEditor = new FlightEditor(flightManager);
        this.scan = scan;
    }

    // Sua thong tin chuyen bay: cho phep giu nguyen tung truong bang cach nhan Enter.
    public void handleEditFlight() {
        System.out.print("Nhap ma chuyen bay can sua: ");
        String flightNumber = scan.nextLine().trim();

        Flight flight = Utils.findFlightByNumber(flightManager.getFlightList(), flightNumber);
        if (flight == null) {
            System.out.println("Khong tim thay chuyen bay co ma: " + flightNumber);
            return;
        }

        System.out.println("Thong tin hien tai: " + flight);
        System.out.println("(Nhan Enter de giu nguyen gia tri cu)");

        try {
            System.out.print("Diem di moi [" + flight.getDepartureCity() + "]: ");
            String departureCity = readOptionalString(flight.getDepartureCity());

            System.out.print("Diem den moi [" + flight.getDestinationCity() + "]: ");
            String destinationCity = readOptionalString(flight.getDestinationCity());

            System.out.print("Gio khoi hanh moi (dd/MM/yyyy HH:mm) [" + flight.getDepartureTime().format(DATE_TIME_FORMATTER) + "]: ");
            LocalDateTime departureTime = readOptionalDateTime(flight.getDepartureTime());

            System.out.print("Gio den moi (dd/MM/yyyy HH:mm) [" + flight.getArrivalTime().format(DATE_TIME_FORMATTER) + "]: ");
            LocalDateTime arrivalTime = readOptionalDateTime(flight.getArrivalTime());

            System.out.print("Tong so ghe moi [" + flight.getTotalSeats() + "]: ");
            int totalSeats = readOptionalInt(flight.getTotalSeats());

            System.out.print("So ghe trong moi [" + flight.getAvailableSeats() + "]: ");
            int availableSeats = readOptionalInt(flight.getAvailableSeats());

            boolean success = flightEditor.updateFlight(flightNumber, departureCity, destinationCity,
                    departureTime, arrivalTime, totalSeats, availableSeats);
            System.out.println(success ? "Cap nhat chuyen bay thanh cong!" : "Cap nhat chuyen bay that bai!");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Loi du lieu: " + e.getMessage());
        }
    }

    // Doi ma chuyen bay (ma la khoa chinh, phai kiem tra trung lap).
    public void handleEditFlightNumber() {
        System.out.print("Nhap ma chuyen bay hien tai: ");
        String oldFlightNumber = scan.nextLine().trim();

        System.out.print("Nhap ma chuyen bay moi (VD: F0001): ");
        String newFlightNumber = scan.nextLine().trim();

        try {
            boolean success = flightEditor.editFlightNumber(oldFlightNumber, newFlightNumber);
            System.out.println(success ? "Doi ma chuyen bay thanh cong!" : "Doi ma chuyen bay that bai!");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Loi du lieu: " + e.getMessage());
        }
    }

    private String readOptionalString(String currentValue) {
        String input = scan.nextLine().trim();
        return input.isEmpty() ? currentValue : input;
    }

    private LocalDateTime readOptionalDateTime(LocalDateTime currentValue) {
        String input = scan.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Dinh dang ngay gio khong hop le, phai la dd/MM/yyyy HH:mm!");
        }
    }

    private int readOptionalInt(int currentValue) {
        String input = scan.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Gia tri phai la so nguyen!");
        }
    }
}
