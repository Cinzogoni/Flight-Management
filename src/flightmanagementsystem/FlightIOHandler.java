package flightmanagementsystem;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
    
    public void handleAddFlight(){
        
    }
}
