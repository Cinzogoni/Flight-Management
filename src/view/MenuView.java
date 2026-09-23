
package view;

import controller.FlightManager;
import model.Flight;
import ultils.FlightEditIOHandler;
import ultils.FlightIOHandler;
import java.util.Scanner;

public class MenuView {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        FlightManager flightManager = new FlightManager();
        FlightIOHandler flightIOHandler = new FlightIOHandler(flightManager, scan);
        FlightEditIOHandler flightEditIOHandler = new FlightEditIOHandler(flightManager, scan);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    flightIOHandler.handleAddFlight();
                    break;
                case "2":
                    flightIOHandler.handleRemoveFlight();
                    break;
                case "3":
                    flightEditIOHandler.handleEditFlight();
                    break;
                case "4":
                    
                    break;    
                case "5":
                    displayAllFlights(flightManager);
                    break;
                case "0":
                    running = false;
                    System.out.println("Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long thu lai!");
            }
        }

        scan.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== QUAN LY CHUYEN BAY =====");
        System.out.println("1. Them chuyen bay");
        System.out.println("2. Xoa chuyen bay");
        System.out.println("3. Sua thong tin chuyen bay");
        System.out.println("4. Tim kiem chuyen bay");
        System.out.println("5. Hien thi danh sach chuyen bay");
        System.out.println("0. Thoat");
        System.out.print("Nhap lua chon: ");
    }

    private static void displayAllFlights(FlightManager flightManager) {
        if (flightManager.getFlightList().isEmpty()) {
            System.out.println("Chua co chuyen bay nao!");
            return;
        }
        System.out.println("===== DANH SACH CHUYEN BAY =====");
        for (Flight flight : flightManager.getFlightList()) {
            System.out.println(flight);
        }
    }
}
