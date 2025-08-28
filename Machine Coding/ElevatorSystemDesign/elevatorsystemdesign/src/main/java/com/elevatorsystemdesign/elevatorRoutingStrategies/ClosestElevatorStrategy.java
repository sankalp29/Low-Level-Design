package com.elevatorsystemdesign.elevatorRoutingStrategies;

import java.util.List;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.model.ElevatorCar;

public class ClosestElevatorStrategy implements ElevatorRoutingStrategy {

    @Override
    public ElevatorCar findElevator(List<ElevatorCar> elevators, int sourceFloor, Direction direction) {
        ElevatorCar closestElevator = null;
        int closestDistance = Integer.MAX_VALUE;

        for (ElevatorCar elevatorCar : elevators) {
            int elevatorFloor = elevatorCar.getCurrentFloor();
            int distance = Math.abs(elevatorFloor - sourceFloor);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestElevator = elevatorCar;
            }
        }

        return closestElevator;
    }
}
