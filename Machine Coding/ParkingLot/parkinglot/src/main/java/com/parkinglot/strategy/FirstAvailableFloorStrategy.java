package com.parkinglot.strategy;

import java.util.List;
import java.util.Optional;

import com.parkinglot.floor.Floor;
import com.parkinglot.parkingSpot.ParkingSpotType;

/**
 * Selects the first floor that has available spots of the required type
 */
public class FirstAvailableFloorStrategy implements FloorSelectionStrategy {
    
    @Override
    public Optional<Floor> selectFloor(List<Floor> floors, ParkingSpotType spotType) {
        return floors.stream()
                    .filter(floor -> floor.getAvailableSpots(spotType) > 0)
                    .findFirst();
    }
}
