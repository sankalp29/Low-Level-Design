package com.parkinglot.parkingSpot;

import com.parkinglot.exception.SlotAlreadyFreeException;
import com.parkinglot.exception.SpotOccupyException;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ParkingSpot {
    private final String spotId;
    private final ParkingSpotType parkingSpotType;
    private SpotStatus spotStatus;
    
    public ParkingSpot(String spotId, ParkingSpotType parkingSpotType) {
        this.spotId = spotId;
        this.parkingSpotType = parkingSpotType;
        this.spotStatus = SpotStatus.FREE;
    }

    public synchronized boolean isFree() {
        return spotStatus.equals(SpotStatus.FREE);
    }

    public synchronized void occupySlot() throws SpotOccupyException {
        if (!spotStatus.equals(SpotStatus.FREE)) {
            throw new SpotOccupyException("Spot is not FREE. Cannot be occupied.");
        }
        spotStatus = SpotStatus.OCCUPIED;
        System.out.println("Parking Spot " + this + " assigned.");
    }

    public synchronized void freeSlot() throws SlotAlreadyFreeException {
        if (spotStatus.equals(SpotStatus.FREE)) {
            throw new SlotAlreadyFreeException("Spot is already FREE.");
        }
        System.out.println("Parking Spot " + this + " is now FREE.");
        spotStatus = SpotStatus.FREE;
    }
}
