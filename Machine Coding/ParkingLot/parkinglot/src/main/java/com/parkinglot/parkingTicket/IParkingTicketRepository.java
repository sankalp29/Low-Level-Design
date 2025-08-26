package com.parkinglot.parkingTicket;

import com.parkinglot.ParkingLot;

public interface IParkingTicketRepository {
    public void storeTicket(ParkingLot parkingLot, ParkingTicket ticket);

    public boolean find(ParkingTicket parkingTicket);
}
