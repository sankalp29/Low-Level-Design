package com.parkinglot;

import java.util.List;
import java.util.Optional;

import com.parkinglot.exception.SpotOccupyException;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.vehicle.VehicleType;

public class ParkingSpotFinderService {
    public static Optional<ParkingSpot> findSpot(ParkingLot parkingLot, VehicleType vehicleType) {
        ParkingSpotType parkingSpotType = parkingLot.getVehicleSpotMappingStrategy().getSpotTypeForVehicle(vehicleType);
        List<ParkingSpot> parkingSpots = parkingLot.getParkingSpotMap().get(parkingSpotType);
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

    private ParkingSpotFinderService() {}
}
