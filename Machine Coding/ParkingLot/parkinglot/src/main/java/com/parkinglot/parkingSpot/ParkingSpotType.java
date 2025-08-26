package com.parkinglot.parkingSpot;

public enum ParkingSpotType {
    COMPACT (10),
    REGULAR (25),
    OVERSIZED (50);

    private final Integer costPerHour;
    ParkingSpotType(Integer costPerHour) {
        this.costPerHour = costPerHour;
    }

    public Integer getCostPerHour() {
        return costPerHour;
    }
}
