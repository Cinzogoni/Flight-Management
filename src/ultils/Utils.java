package ultils;

import java.time.LocalDate;
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
    public static List<Flight> searchAvailableFlights(List<Flight> flightList, String departureCity, String destinationCity, LocalDate departureDate) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flightList) {
            boolean matchDeparture = f.getDepartureCity().equalsIgnoreCase(departureCity);
            boolean matchDestination = f.getDestinationCity().equalsIgnoreCase(destinationCity);
            boolean matchDate = f.getDepartureTime().toLocalDate().equals(departureDate);

            if (matchDeparture && matchDestination && matchDate && f.getAvailableSeats() > 0) {
                result.add(f);
            }
        }

        return result;
    }
}
