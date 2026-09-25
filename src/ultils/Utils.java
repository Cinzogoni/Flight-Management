package ultils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Flight;

public class Utils {

    // Tìm kiếm chuyến bay theo number (Check noi bo)
    public static Flight findFlightByNumber(List<Flight> flightList, String flightNumber) {
        if (flightList == null || flightNumber == null) {
            return null;
        }
        for (Flight f : flightList) {
            if (f.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    // Tìm kiếm chuyến bay theo Điểm đi + Điểm đến + Ngày đi + CÒN GHẾ TRỐNG
    public static ArrayList<Flight> searchAvailableFlights(ArrayList<Flight> flightList, String flightNumber, String departureCity, String destinationCity, LocalDate departureDate) {
        ArrayList<Flight> result = new ArrayList<>();
        for (Flight f : flightList) {
            boolean matchFlightNumber = flightNumber != null && f.getFlightNumber().equalsIgnoreCase(flightNumber);
            boolean matchDeparture = departureCity != null && f.getDepartureCity().equalsIgnoreCase(departureCity);
            boolean matchDestination = destinationCity != null && f.getDestinationCity().equalsIgnoreCase(destinationCity);
            boolean matchDate = departureDate != null && f.getDepartureTime().toLocalDate().equals(departureDate);

            boolean matchAny = matchFlightNumber || matchDeparture || matchDestination || matchDate;

            if (matchAny && f.getAvailableSeats() > 0) {
                result.add(f);
            }
        }

        return result;
    }

    //Định dạng form cho toString
    private static final DateTimeFormatter DATE_TIME_FMT
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String FLIGHT_ROW_FORMAT
            = "| %-13s | %-15s | %-15s | %-16s | %-16s | %-9s | %-8s | %-9s |";

    private static final String FLIGHT_SEPARATOR
            = "+---------------+-----------------+-----------------+------------------+------------------+-----------+----------+-----------+";

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DATE_TIME_FMT);
    }

    public static String formatDuration(long minutes) {
        return (minutes / 60) + "h " + (minutes % 60) + "m";
    }

    public static String flightHeader() {
        return FLIGHT_SEPARATOR + "\n"
                + String.format(FLIGHT_ROW_FORMAT, "Ma chuyen bay", "Noi di", "Noi den",
                        "Gio khoi hanh", "Gio den", "Thoi gian", "Tong ghe", "Ghe trong")
                + "\n" + FLIGHT_SEPARATOR;
    }

    public static String flightFooter() {
        return FLIGHT_SEPARATOR;
    }

    public static String flightRow(String flightNumber, String departureCity, String destinationCity,
            LocalDateTime departureTime, LocalDateTime arrivalTime,
            long durationMinutes, int totalSeats, int availableSeats) {
        return String.format(FLIGHT_ROW_FORMAT,
                flightNumber,
                departureCity,
                destinationCity,
                formatDateTime(departureTime),
                formatDateTime(arrivalTime),
                formatDuration(durationMinutes),
                String.valueOf(totalSeats),
                String.valueOf(availableSeats));
    }
}
