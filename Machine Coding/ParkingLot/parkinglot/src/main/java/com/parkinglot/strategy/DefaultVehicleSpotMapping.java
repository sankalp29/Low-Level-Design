package com.parkinglot.strategy;

import java.util.HashMap;
import java.util.Map;

import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.vehicle.VehicleType;

public class DefaultVehicleSpotMapping implements VehicleSpotMappingStrategy {
    private final Map<VehicleType, ParkingSpotType> vehicleTypeToSpotType;

    public DefaultVehicleSpotMapping() {
        this.vehicleTypeToSpotType = new HashMap<>();
        // Default mapping configuration
        vehicleTypeToSpotType.put(VehicleType.MOTORCYCLE, ParkingSpotType.COMPACT);
        vehicleTypeToSpotType.put(VehicleType.CAR, ParkingSpotType.REGULAR);
        vehicleTypeToSpotType.put(VehicleType.TRUCK, ParkingSpotType.OVERSIZED);
    }

    public DefaultVehicleSpotMapping(Map<VehicleType, ParkingSpotType> customMapping) {
        this.vehicleTypeToSpotType = new HashMap<>(customMapping);
    }

    @Override
    public ParkingSpotType getSpotTypeForVehicle(VehicleType vehicleType) {
        ParkingSpotType spotType = vehicleTypeToSpotType.get(vehicleType);
        if (spotType == null) {
            throw new IllegalArgumentException("No parking spot type configured for vehicle type: " + vehicleType);
        }
        return spotType;
    }
}
