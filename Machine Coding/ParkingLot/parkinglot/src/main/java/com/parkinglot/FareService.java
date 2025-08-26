package com.parkinglot;

import java.time.Duration;
import java.time.LocalDateTime;

import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingTicket.ParkingTicket;

public class FareService {
    public static double calculateFare(ParkingTicket parkingTicket) {
        LocalDateTime entryTime = parkingTicket.getParkingTime();
        LocalDateTime exitTime = parkingTicket.getExitTime();
        Long duration = Duration.between(exitTime, entryTime).toNanos()*1000;
        ParkingSpot parkingSpot = parkingTicket.getParkingSpot();
        double parkingFare = parkingSpot.getParkingSpotType().getCostPerHour() * duration;
        System.out.println("Parking Fare : Rs. " + parkingFare);
        return parkingFare;
    }
}
