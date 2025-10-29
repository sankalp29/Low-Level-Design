package com.parkinglot;

import java.util.List;
import java.util.Optional;

import com.parkinglot.exception.SpotOccupyException;
import com.parkinglot.floor.Floor;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.vehicle.VehicleType;

public class MultiFloorParkingSpotFinderService {
    
    public static Optional<ParkingSpot> findSpot(MultiFloorParkingLot parkingLot, VehicleType vehicleType) {
        ParkingSpotType parkingSpotType = parkingLot.getVehicleSpotMappingStrategy().getSpotTypeForVehicle(vehicleType);
        
        // Use floor selection strategy to find the best floor
        Optional<Floor> selectedFloor = parkingLot.getFloorSelectionStrategy()
                                                  .selectFloor(parkingLot.getFloors(), parkingSpotType);
        
        if (selectedFloor.isEmpty()) {
            return Optional.empty();
        }
        
        // Search for available spot on the selected floor
        List<ParkingSpot> parkingSpots = selectedFloor.get().getParkingSpotMap().get(parkingSpotType);
        if (parkingSpots == null) {
            return Optional.empty();
        }
        
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isFree()) {
                try {
                    parkingSpot.occupySlot();
                    return Optional.of(parkingSpot);
                } catch (SpotOccupyException e) {
                    System.out.println(e);
                }
            }
        }
        return Optional.empty();
    }

    private MultiFloorParkingSpotFinderService() {}
}
