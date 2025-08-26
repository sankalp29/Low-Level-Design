package com.parkinglot.parkingTicket;

import java.util.HashSet;
import java.util.Set;

import com.parkinglot.ParkingLot;

public class ParkingTicketRepository implements IParkingTicketRepository{
    private final Set<ParkingTicket> parkingTickets;

    public void storeTicket(ParkingLot parkingLot, ParkingTicket ticket) {
        parkingTickets.add(ticket);
    }

    public boolean find(ParkingTicket parkingTicket) {
        return parkingTickets.contains(parkingTicket) && parkingTicket.isOpen();
    }

    public ParkingTicketRepository() {
        this.parkingTickets = new HashSet<>();
    }
}
