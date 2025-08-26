package com.parkinglot.parkingTicket;

import java.time.LocalDateTime;

import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.vehicle.Vehicle;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ParkingTicket {
    private final Vehicle vehicle;
    private final LocalDateTime parkingTime;
    private ParkingSpot parkingSpot;
    private LocalDateTime exitTime;
    private TicketStatus ticketStatus;

    public ParkingTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.parkingTime = LocalDateTime.now();
        this.ticketStatus = TicketStatus.OPEN;
    }

    public boolean isOpen() {
        return ticketStatus.equals(TicketStatus.OPEN);
    }

    public void setExitTime() {
        this.exitTime = LocalDateTime.now();
    }

    public void exit() {
        this.ticketStatus = TicketStatus.CLOSED;
    }
}
