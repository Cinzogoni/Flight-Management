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
import java.util.ArrayList;

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
            System.out.print("Nhap ma chuyen bay (VD: Fxxx): ");
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

    public void handleSearchFlight() {
        System.out.println("--- Tim kiem chuyen bay ---");

        while (true) {
            try {
                System.out.print("Nhap ma chuyen bay (Enter de bo qua): ");
                String flightNumber = scan.nextLine().trim();
                
                System.out.print("Nhap diem di (Enter de bo qua): ");
                String departureCity = scan.nextLine().trim();

                System.out.print("Nhap diem den (Enter de bo qua): ");
                String destinationCity = scan.nextLine().trim();

                System.out.print("Nhap ngay khoi hanh (dd/MM/yyyy) - (Enter de bo qua): ");
                String dateInput = scan.nextLine().trim();

                if (flightNumber.isEmpty() && departureCity.isEmpty() && destinationCity.isEmpty() && dateInput.isEmpty()) {
                    System.out.println("Ban phai dien it nhat 1 thong tin de tim kiem!");
                    System.out.print("Ban co muon thoat tim kiem khong? (Y/N): ");
                    String exit = scan.nextLine().trim();
                    if (exit.equalsIgnoreCase("Y")) {
                        System.out.println("Da huy tim kiem.");
                        return;
                    }
                    continue;
                }

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                java.time.LocalDate departureDate = dateInput.isEmpty()
                        ? null
                        : java.time.LocalDate.parse(dateInput, dateFormatter);

                ArrayList<Flight> availableFlights = Utils.searchAvailableFlights(
                        flightManager.getFlightList(),
                        flightNumber.isEmpty() ? null : flightNumber,
                        departureCity.isEmpty() ? null : departureCity,
                        destinationCity.isEmpty() ? null : destinationCity,
                        departureDate
                );

                if (availableFlights.isEmpty()) {
                    System.out.println("Khong tim thay chuyen bay phu hop hoac tat ca chuyen bay da het ghe!");
                } else {
                    System.out.println("\n===== KET QUA TIM KIEM =====");
                    System.out.println(Utils.flightHeader());
                    for (Flight f : availableFlights) {
                        System.out.println(Utils.flightRow(
                                f.getFlightNumber(),
                                f.getDepartureCity(),
                                f.getDestinationCity(),
                                f.getDepartureTime(),
                                f.getArrivalTime(),
                                f.getDurationTime(),
                                f.getTotalSeats(),
                                f.getAvailableSeats()
                        ));
                    }
                    System.out.println(Utils.flightFooter());
                }

            } catch (Exception e) {
                System.out.println("Loi dinh dang ngay nhap vao! Vui long nhap dung dang dd/MM/yyyy (VD: 18/10/2026).");
            }
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
                if (data.length >= 7) {
                    String flightNumber = data[0].trim();
                    String departureCity = data[1].trim();
                    String destinationCity = data[2].trim();
                    LocalDateTime departureTime = LocalDateTime.parse(data[3].trim(), DATE_TIME_FORMATTER);
                    LocalDateTime arrivalTime = LocalDateTime.parse(data[4].trim(), DATE_TIME_FORMATTER);
                    int totalSeats = Integer.parseInt(data[5].trim());
                    int availableSeats = Integer.parseInt(data[6].trim());

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
