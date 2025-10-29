package com.parkinglot.floor;

import java.util.List;
import java.util.Map;

import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;

import lombok.Getter;

@Getter
public class Floor {
    private final int floorNumber;
    private final String floorName;
    private final Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap;
    
    public Floor(int floorNumber, String floorName, Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap) {
        this.floorNumber = floorNumber;
        this.floorName = floorName;
        this.parkingSpotMap = parkingSpotMap;
    }
    
    /**
     * Get total number of spots of a specific type on this floor
     */
    public int getTotalSpots(ParkingSpotType spotType) {
        List<ParkingSpot> spots = parkingSpotMap.get(spotType);
        return spots != null ? spots.size() : 0;
    }
    
    /**
     * Get number of available spots of a specific type on this floor
     */
    public long getAvailableSpots(ParkingSpotType spotType) {
        List<ParkingSpot> spots = parkingSpotMap.get(spotType);
        if (spots == null) return 0;
        
        return spots.stream()
                   .filter(ParkingSpot::isFree)
                   .count();
    }
    
    @Override
    public String toString() {
        return "Floor{" +
                "floorNumber=" + floorNumber +
                ", floorName='" + floorName + '\'' +
                '}';
    }
}
