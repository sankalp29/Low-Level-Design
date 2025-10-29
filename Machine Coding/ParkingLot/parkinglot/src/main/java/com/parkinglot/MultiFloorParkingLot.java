package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkinglot.exception.IllegalParkingSpotNumberException;
import com.parkinglot.floor.Floor;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.strategy.FloorSelectionStrategy;
import com.parkinglot.strategy.LowestFloorFirstStrategy;
import com.parkinglot.strategy.VehicleSpotMappingStrategy;

import lombok.Getter;

@Getter
public class MultiFloorParkingLot {
    private final String address;
    private final String name;
    private final List<Floor> floors;
    private final VehicleSpotMappingStrategy vehicleSpotMappingStrategy;
    private final FloorSelectionStrategy floorSelectionStrategy;

    public void display() {
        MultiFloorParkingLotDisplay.display(this);
    }
    
    public static class MultiFloorParkingLotBuilder {
        private String address;
        private String name;
        private final List<Floor> floors;
        private VehicleSpotMappingStrategy vehicleSpotMappingStrategy;
        private FloorSelectionStrategy floorSelectionStrategy;

        MultiFloorParkingLotBuilder() {
            floors = new ArrayList<>();
        }

        public MultiFloorParkingLotBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public MultiFloorParkingLotBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MultiFloorParkingLotBuilder setVehicleSpotMappingStrategy(VehicleSpotMappingStrategy strategy) {
            this.vehicleSpotMappingStrategy = strategy;
            return this;
        }

        public MultiFloorParkingLotBuilder setFloorSelectionStrategy(FloorSelectionStrategy strategy) {
            this.floorSelectionStrategy = strategy;
            return this;
        }

        public MultiFloorParkingLotBuilder addFloor(int floorNumber, String floorName) {
            Map<ParkingSpotType, List<ParkingSpot>> floorSpots = new HashMap<>();
            Floor floor = new Floor(floorNumber, floorName, floorSpots);
            floors.add(floor);
            return this;
        }

        public MultiFloorParkingLotBuilder addParkingSpotsToFloor(int floorNumber, ParkingSpotType parkingSpotType, 
                                                                  Integer numOfSpots) throws IllegalParkingSpotNumberException {
            if (numOfSpots <= 0) {
                throw new IllegalParkingSpotNumberException("Number of parking spots to be added cannot be <= zero.");
            }

            Floor targetFloor = floors.stream()
                                     .filter(floor -> floor.getFloorNumber() == floorNumber)
                                     .findFirst()
                                     .orElseThrow(() -> new IllegalArgumentException("Floor " + floorNumber + " not found"));

            Map<ParkingSpotType, List<ParkingSpot>> spotMap = targetFloor.getParkingSpotMap();
            spotMap.putIfAbsent(parkingSpotType, new ArrayList<>());
            
            for (int i = 1; i <= numOfSpots; i++) {
                String spotId = "F" + floorNumber + "_" + parkingSpotType.name() + spotMap.get(parkingSpotType).size();
                spotMap.get(parkingSpotType).add(new ParkingSpot(spotId, parkingSpotType));
            }

            return this;
        }

        public MultiFloorParkingLot build() {
            // Set default strategies if not provided
            if (floorSelectionStrategy == null) {
                floorSelectionStrategy = new LowestFloorFirstStrategy();
            }
            
            return new MultiFloorParkingLot(address, name, floors, vehicleSpotMappingStrategy, floorSelectionStrategy);
        }
    }

    public static MultiFloorParkingLotBuilder builder() {
        return new MultiFloorParkingLotBuilder();
    }

    private MultiFloorParkingLot(String address, String name, List<Floor> floors,
                                VehicleSpotMappingStrategy vehicleSpotMappingStrategy,
                                FloorSelectionStrategy floorSelectionStrategy) {
        this.address = address;
        this.name = name;
        this.floors = floors;
        this.vehicleSpotMappingStrategy = vehicleSpotMappingStrategy;
        this.floorSelectionStrategy = floorSelectionStrategy;
    }
}
