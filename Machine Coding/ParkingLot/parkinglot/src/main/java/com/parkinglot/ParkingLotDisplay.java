package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;

public class ParkingLotDisplay {
    public static void display(ParkingLot parkingLot) {
        Map<ParkingSpotType, List<ParkingSpot>> spots = parkingLot.getParkingSpotMap();
        Map<ParkingSpotType, Integer> freeSpots = new HashMap<>();
        for (ParkingSpotType parkingSpotType : spots.keySet()) {
            for (ParkingSpot parkingSpot : spots.getOrDefault(parkingSpotType, new ArrayList<>())) {
                if (parkingSpot.isFree()) {
                    freeSpots.put(parkingSpotType, freeSpots.getOrDefault(parkingSpotType, 0) + 1);
                }
            }
        }
        
        System.out.println("AVAILABLE PARKING SLOTS: ");
        for (ParkingSpotType parkingSpotType : freeSpots.keySet()) {
            System.out.println(parkingSpotType.name() + " : " + freeSpots.get(parkingSpotType));
        }
        System.out.println();
    }
}
