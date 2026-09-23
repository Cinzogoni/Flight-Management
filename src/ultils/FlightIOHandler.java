package ultils;

import controller.FlightManager;
import model.Flight;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FlightIOHandler {

    private FlightManager flightManager;
    private Scanner scan;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public FlightIOHandler() {
    }

    public FlightIOHandler(FlightManager flightManager, Scanner scan) {
        this.flightManager = flightManager;
        this.scan = scan;
    }

    public void handleAddFlight() {
        System.out.println("--- Them chuyen bay ---");
        try {
            System.out.print("Nhap ma chuyen bay (VD: F0001): ");
            String flightNumber = scan.nextLine().trim();
            if (Utils.findFlightByNumber(flightManager.getFlightList(), flightNumber) != null) {
                System.out.println("Ma chuyen bay da ton tai!");
                return;
            }

            System.out.print("Nhap diem di: ");
            String departureCity = scan.nextLine().trim();

            System.out.print("Nhap diem den: ");
            String destinationCity = scan.nextLine().trim();

            System.out.print("Nhap gio khoi hanh (dd/MM/yyyy HH:mm): ");
            LocalDateTime departureTime = LocalDateTime.parse(scan.nextLine().trim(), DATE_TIME_FORMATTER);

            System.out.print("Nhap gio den (dd/MM/yyyy HH:mm): ");
            LocalDateTime arrivalTime = LocalDateTime.parse(scan.nextLine().trim(), DATE_TIME_FORMATTER);

            System.out.print("Nhap tong so ghe: ");
            int totalSeats = Integer.parseInt(scan.nextLine().trim());

            System.out.print("Nhap so ghe trong: ");
            int availableSeats = Integer.parseInt(scan.nextLine().trim());

            Flight flight = new Flight(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, 0, totalSeats, availableSeats);
            flightManager.getFlightList().add(flight);
            System.out.println("Them chuyen bay thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void handleRemoveFlight() {
        System.out.println("--- Xoa chuyen bay ---");
        System.out.print("Nhap ma chuyen bay can xoa: ");
        String flightNumber = scan.nextLine().trim();

        Flight flight = Utils.findFlightByNumber(flightManager.getFlightList(), flightNumber);
        if (flight == null) {
            System.out.println("Khong tim thay chuyen bay co ma: " + flightNumber);
            return;
        }

        System.out.print("Ban co chac chan muon xoa chuyen bay nay? (Y/N): ");
        String confirm = scan.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            flightManager.getFlightList().remove(flight);
            System.out.println("Xoa chuyen bay thanh cong!");
        } else {
            System.out.println("Da huy xoa chuyen bay.");
        }
    }
    
    public void ExportToFile() {
        System.out.println("--- Xuat du lieu ra file Excel (CSV) ---");
        System.out.print("Nhap ten file (VD: flights.csv): ");
        String fileName = scan.nextLine().trim();
        if (fileName.isEmpty()) {
            fileName = "flights.csv";
        } else if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("FlightNumber,PassengerName,DepartureCity,DestinationCity,DepartureTime,ArrivalTime,TotalSeats,AvailableSeats");
            bw.newLine();

            for (Flight f : flightManager.getFlightList()) {
                String line = String.format("%s,%s,%s,%s,%s,%d,%d",
                        f.getFlightNumber(),
                        f.getDepartureCity(),
                        f.getDestinationCity(),
                        f.getDepartureTime().format(DATE_TIME_FORMATTER),
                        f.getArrivalTime().format(DATE_TIME_FORMATTER),
                        f.getTotalSeats(),
                        f.getAvailableSeats());
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Xuat danh sach chuyen bay thanh cong ra " + fileName);
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
    }
    
    public void ReadFromFile() {
        System.out.println("--- Doc du lieu tu file Excel (CSV) ---");
        System.out.print("Nhap ten file can doc (VD: flights.csv): ");
        String fileName = scan.nextLine().trim();
        if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            int count = 0;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length >= 8) {
                    String flightNumber = data[0].trim();
                    String passengerName = data[1].trim();
                    String departureCity = data[2].trim();
                    String destinationCity = data[3].trim();
                    LocalDateTime departureTime = LocalDateTime.parse(data[4].trim(), DATE_TIME_FORMATTER);
                    LocalDateTime arrivalTime = LocalDateTime.parse(data[5].trim(), DATE_TIME_FORMATTER);
                    int totalSeats = Integer.parseInt(data[6].trim());
                    int availableSeats = Integer.parseInt(data[7].trim());
                    
                    if (Utils.findFlightByNumber(flightManager.getFlightList(), flightNumber) == null) {
                        Flight flight = new Flight(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, 0, totalSeats, availableSeats);
                        flightManager.getFlightList().add(flight);
                        count++;
                    } else {
                        System.out.println("Bo qua chuyen bay " + flightNumber + " vi da ton tai.");
                    }
                }
            }
            System.out.println("Doc thanh cong " + count + " chuyen bay tu file " + fileName);
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi du lieu trong file: " + e.getMessage());
        }
    }
}
