package com.elevatorsystemdesign.elevatorRoutingStrategies;

import java.util.List;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.constants.ElevatorStatus;
import com.elevatorsystemdesign.model.ElevatorCar;

public class OptimalRoutingStrategy implements ElevatorRoutingStrategy {

    @Override
    public ElevatorCar findElevator(List<ElevatorCar> elevators, int sourceFloor, Direction direction) {
        ElevatorCar optimalElevatorCar = null;
        int bestScore = Integer.MAX_VALUE;

        // First pass: Look for truly idle elevators with no pending requests
        for (ElevatorCar elevatorCar : elevators) {
            if (elevatorCar.getElevatorStatus().equals(ElevatorStatus.IDLE) && 
                elevatorCar.getPendingRequestsCount() == 0) {
                int distance = Math.abs(sourceFloor - elevatorCar.getCurrentFloor());
                if (distance < bestScore) {
                    bestScore = distance;
                    optimalElevatorCar = elevatorCar;
                }
            }
        }
        
        // If no idle elevator found, look for suitable ones
        if (optimalElevatorCar == null) {
            bestScore = Integer.MAX_VALUE;
            for (ElevatorCar elevatorCar : elevators) {
                if (isSuitable(elevatorCar, sourceFloor)) {
                    int distance = Math.abs(sourceFloor - elevatorCar.getCurrentFloor());
                    int pendingRequests = elevatorCar.getPendingRequestsCount();
                    
                    // Calculate a score: distance + penalty for pending requests
                    int score = distance + (pendingRequests * 3); // Increased penalty
                    
                    if (score < bestScore) {
                        bestScore = score;
                        optimalElevatorCar = elevatorCar;
                    }
                }
            }
        }

        return optimalElevatorCar != null ? optimalElevatorCar : findClosestElevator(elevators, sourceFloor, direction);
    }

    private boolean isSuitable(ElevatorCar elevatorCar, final int sourceFloor) {
        // Prefer idle elevators first
        if (elevatorCar.getElevatorStatus().equals(ElevatorStatus.IDLE)) {
            return true;
        }
        
        // For moving elevators, check if the request is in the same direction
        return ((elevatorCar.getDirection().equals(Direction.UP) && sourceFloor >= elevatorCar.getCurrentFloor()) || 
               (elevatorCar.getDirection().equals(Direction.DOWN) && sourceFloor <= elevatorCar.getCurrentFloor()));
    }

    private ElevatorCar findClosestElevator(List<ElevatorCar> elevators, int sourceFloor, Direction direction) {
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
