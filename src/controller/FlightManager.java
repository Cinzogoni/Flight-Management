package controller;

import model.Flight;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightManager {
    private List<Flight> flightList = new ArrayList<>();

    public List<Flight> getFlightList() {
        return flightList;
    }
    
    
}
