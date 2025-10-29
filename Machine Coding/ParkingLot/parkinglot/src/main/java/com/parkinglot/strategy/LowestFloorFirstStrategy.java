package com.parkinglot.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.parkinglot.floor.Floor;
import com.parkinglot.parkingSpot.ParkingSpotType;

/**
 * Prioritizes lower floors (closer to ground) for easier access
 */
public class LowestFloorFirstStrategy implements FloorSelectionStrategy {
    
    @Override
    public Optional<Floor> selectFloor(List<Floor> floors, ParkingSpotType spotType) {
        return floors.stream()
                    .filter(floor -> floor.getAvailableSpots(spotType) > 0)
                    .min(Comparator.comparing(Floor::getFloorNumber));
    }
}
