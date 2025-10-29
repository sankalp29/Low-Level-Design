package com.parkinglot.strategy;

import java.util.List;
import java.util.Optional;

import com.parkinglot.floor.Floor;
import com.parkinglot.parkingSpot.ParkingSpotType;

public interface FloorSelectionStrategy {
    /**
     * Selects the best floor to search for parking based on the strategy
     * @param floors List of available floors
     * @param spotType Required parking spot type
     * @return Optional containing the selected floor, empty if no suitable floor found
     */
    Optional<Floor> selectFloor(List<Floor> floors, ParkingSpotType spotType);
}
