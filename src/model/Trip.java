package model;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Trip {
    protected String departureCity;
    protected String destinationCity;
    protected LocalDateTime departureTime;
    protected LocalDateTime arrivalTime;
    protected long durationTime;

    public Trip() {
    }

    public Trip(String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, long durationTime) {
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public final void setDepartureTime(LocalDateTime departureTime) {
        if (departureTime == null) {
            throw new IllegalArgumentException("Gio khoi hanh khong duoc de trong!");
        }
        if (this.arrivalTime != null && departureTime.isAfter(this.arrivalTime)) {
            throw new IllegalArgumentException("Gio khoi hanh phai truoc gio den!");
        }
        this.departureTime = departureTime;
        recalculateDuration();
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public final void setArrivalTime(LocalDateTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Gio den khong duoc de trong!");
        }
        if (this.departureTime != null && arrivalTime.isBefore(this.departureTime)) {
            throw new IllegalArgumentException("Gio den phai sau gio khoi hanh!");
        }
        this.arrivalTime = arrivalTime;
        recalculateDuration();
    }

    public long getDurationTime() {
        return durationTime;
    }

    protected void recalculateDuration() {
        if (departureTime != null && arrivalTime != null && !arrivalTime.isBefore(departureTime)) {
            this.durationTime = Duration.between(departureTime, arrivalTime).toMinutes();
        } else {
            this.durationTime = 0;
        }
    }
}
