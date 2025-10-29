package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkinglot.floor.Floor;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;

public class MultiFloorParkingLotDisplay {
    
    public static void display(MultiFloorParkingLot parkingLot) {
        System.out.println("=== " + parkingLot.getName() + " ===");
        System.out.println("Address: " + parkingLot.getAddress());
        System.out.println();

        // Overall summary
        Map<ParkingSpotType, Integer> totalAvailable = new HashMap<>();
        
        for (Floor floor : parkingLot.getFloors()) {
            System.out.println("--- Floor " + floor.getFloorNumber() + " (" + floor.getFloorName() + ") ---");
            
            Map<ParkingSpotType, Integer> floorAvailable = new HashMap<>();
            
            for (ParkingSpotType spotType : ParkingSpotType.values()) {
                List<ParkingSpot> spots = floor.getParkingSpotMap().getOrDefault(spotType, new ArrayList<>());
                long available = spots.stream().filter(ParkingSpot::isFree).count();
                
                if (!spots.isEmpty()) {
                    floorAvailable.put(spotType, (int) available);
                    totalAvailable.put(spotType, totalAvailable.getOrDefault(spotType, 0) + (int) available);
                    
                    System.out.println(spotType.name() + ": " + available + "/" + spots.size() + " available");
                }
            }
            
            if (floorAvailable.isEmpty()) {
                System.out.println("No parking spots on this floor");
            }
            System.out.println();
        }
        
        // Total summary
        System.out.println("=== TOTAL AVAILABLE SPOTS ===");
        for (ParkingSpotType spotType : totalAvailable.keySet()) {
            System.out.println(spotType.name() + ": " + totalAvailable.get(spotType));
        }
        System.out.println();
    }
    
    public static void displayFloor(Floor floor) {
        System.out.println("--- Floor " + floor.getFloorNumber() + " (" + floor.getFloorName() + ") ---");
        
        for (ParkingSpotType spotType : ParkingSpotType.values()) {
            long available = floor.getAvailableSpots(spotType);
            int total = floor.getTotalSpots(spotType);
            
            if (total > 0) {
                System.out.println(spotType.name() + ": " + available + "/" + total + " available");
            }
        }
        System.out.println();
    }
}
