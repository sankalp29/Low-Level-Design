package com.parkinglot.strategy;

import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.vehicle.VehicleType;

public interface VehicleSpotMappingStrategy {
    /**
     * Determines the appropriate parking spot type for a given vehicle type
     * @param vehicleType the type of vehicle
     * @return the corresponding parking spot type
     */
    ParkingSpotType getSpotTypeForVehicle(VehicleType vehicleType);
}
