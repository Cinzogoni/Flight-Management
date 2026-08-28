package flightmanagementsystem;

public class Reservation {
    private String reservationId;
    private String passengerName;
    private String contactDetail;
    private Flight flight;

    public Reservation() {
    }

    public Reservation(String reservationId, String passengerName, String contactDetail, Flight flight) {
        setReservationId(reservationId);
        setPassengerName(passengerName);
        setContactDetail(contactDetail);
        setFlight(flight);
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public Flight getFlight() {
        return flight;
    }

    public final void setReservationId(String reservationId) {
        if (reservationId == null || reservationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma dat cho khong duoc de trong!");
        }
        this.reservationId = reservationId;
    }
 
    public final void setPassengerName(String passengerName) {
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten hanh khach khong duoc de trong!");
        }
        this.passengerName = passengerName;
    }
 
    public final void setContactDetail(String contactDetail) {
        if (contactDetail == null || contactDetail.trim().isEmpty()) {
            throw new IllegalArgumentException("Thong tin lien he khong duoc de trong!");
        }
        this.contactDetail = contactDetail;
    }
 
    public final void setFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Phai chon 1 chuyen bay de dat cho!");
        }
        this.flight = flight;
    }

    @Override
    public String toString() {
        String flightCode = (flight != null) ? flight.getFlightNumber() : "N/A";
        return "Reservation ID: " + reservationId 
             + " | Passenger: " + passengerName 
             + " | Contact: " + contactDetail 
             + " | Flight: " + flightCode;
    }  
}
