package flightmanagementsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightManager {
    private List<Flight> flightList = new ArrayList<>();
    private List<Reservation> reservationList = new ArrayList<>();
    private int autoReservationId = 1;

    public List<Flight> getFlightList() {
        return flightList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public int getAutoReservationId() {
        return autoReservationId;
    }

    //Hàm hỗ trợ dùng chung
    public Flight findFlightByNumber(String flightNumber){
        for(Flight f : flightList){
            if(f.getFlightNumber().equalsIgnoreCase(flightNumber)){
            return f;
            }
        }
        return null;
    }

    // FUNCTION 1: ADD FLIGHT (Thêm chuyến bay)
    public boolean addFlight(Flight flight){
        if(flight == null || flight.getFlightNumber() == null){
            return false;
        }

        if(findFlightByNumber(flight.getFlightNumber()) != null){
            return false;
        }

        flightList.add(flight);
        return true;
    }

    // FUNCTION 2: PASSENGER RESERVATION & BOOKING

    // 2a. Tìm kiếm chuyến bay thỏa mãn: Điểm đi + Điểm đến + Ngày đi + CÒN GHẾ TRỐNG
    public List<Flight> searchAvailableFlights(String departureCity, String destinationCity, LocalDate departureDate){
        List<Flight> result = new ArrayList<>();
        for(Flight f : flightList){
            boolean matchDeparture = f.getDepartureCity().equalsIgnoreCase(departureCity);
            boolean matchDestination = f.getDestinationCity().equalsIgnoreCase(destinationCity);
            boolean matchDate = f.getDepartureTime().toLocalDate().equals(departureDate);
            
            if (matchDeparture && matchDestination && matchDate && f.getAvailableSeats() > 0) {
                result.add(f);
            }
        }
        
        return result;
    }
    
    // 2b. Tiến hành Đặt vé (Make Reservation)
    public Reservation makeReservation(String flightNumber, String passengerName, String contactDetail){
        Flight flight = findFlightByNumber(flightNumber);
        
        if (flight == null || !flight.bookSeat()) {
            return null;
        }
        
        String reservationId = String.format("R%04d", autoReservationId++);
        
        Reservation newReservation = new Reservation(reservationId, passengerName, contactDetail, flight);
        reservationList.add(newReservation);
        
        return newReservation;
    }
    
    // Hàm hỗ trợ tìm đặt chỗ theo Reservation ID (dùng cho Function 3 sau này)
    public Reservation findReservationById(String reservationId){
        for(Reservation r : reservationList){
            if(r.getReservationId().equalsIgnoreCase(reservationId)){
                return r;
            }
        }
        return null;
    }
}
