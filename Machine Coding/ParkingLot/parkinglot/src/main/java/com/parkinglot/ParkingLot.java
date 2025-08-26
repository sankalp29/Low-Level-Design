package com.parkinglot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkinglot.exception.IllegalParkingSpotNumberException;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.strategy.VehicleSpotMappingStrategy;

import lombok.Getter;

@Getter
public class ParkingLot {
    private final String address;
    private final String name;
    private final Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap;
    private final VehicleSpotMappingStrategy vehicleSpotMappingStrategy;

    public void display() {
        ParkingLotDisplay.display(this);
    }
    
    public static class ParkingLotBuilder {
        private String address;
        private String name;
        private final Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap;
        private VehicleSpotMappingStrategy vehicleSpotMappingStrategy;

        ParkingLotBuilder() {
            parkingSpotMap = new HashMap<>();
        }

        public ParkingLotBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ParkingLotBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ParkingLotBuilder setVehicleSpotMappingStrategy(VehicleSpotMappingStrategy strategy) {
            this.vehicleSpotMappingStrategy = strategy;
            return this;
        }

        public ParkingLotBuilder addParkingSpots(ParkingSpotType parkingSpotType, Integer numOfSpots) throws IllegalParkingSpotNumberException{
            if (numOfSpots <= 0) {
                throw new IllegalParkingSpotNumberException("Number of parking spots to be added cannot be <= zero.");
            }
            parkingSpotMap.putIfAbsent(parkingSpotType, new ArrayList<>());
            for (int i = 1; i <= numOfSpots; i++) {
                String spotId = parkingSpotType.name() + parkingSpotMap.get(parkingSpotType).size();
                parkingSpotMap.get(parkingSpotType).add(new ParkingSpot(spotId, parkingSpotType));
            }

            return this;
        }

        public ParkingLot build() {
            return new ParkingLot(address, name, parkingSpotMap, vehicleSpotMappingStrategy);
        }
    }

    public static ParkingLotBuilder builder() {
        return new ParkingLotBuilder();
    }

    private ParkingLot(String address, String name, Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap, 
                      VehicleSpotMappingStrategy vehicleSpotMappingStrategy) {
        this.address = address;
        this.name = name;
        this.parkingSpotMap = parkingSpotMap;
        this.vehicleSpotMappingStrategy = vehicleSpotMappingStrategy;
    }
}
