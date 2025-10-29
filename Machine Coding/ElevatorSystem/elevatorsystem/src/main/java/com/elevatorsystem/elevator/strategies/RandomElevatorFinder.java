package com.elevatorsystem.elevator.strategies;

import com.elevatorsystem.building.Building;
import com.elevatorsystem.elevator.Direction;
import com.elevatorsystem.elevator.ElevatorCar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomElevatorFinder implements ElevatorFinderStrategy {
    private final Building building;
    private final Random random;

    public RandomElevatorFinder(Building building) {
        this.building = building;
        this.random = new Random();
    }

    @Override
    public ElevatorCar assignElevator() {
        List<ElevatorCar> elevators = building.getElevatorCars();
        
        // First try to find available elevators
        List<ElevatorCar> availableElevators = elevators.stream()
                .filter(ElevatorCar::isAvailable)
                .collect(Collectors.toList());
        
        if (!availableElevators.isEmpty()) {
            int randomIndex = random.nextInt(availableElevators.size());
            return availableElevators.get(randomIndex);
        }
        
        // If no elevator is available, assign a random one
        if (!elevators.isEmpty()) {
            int randomIndex = random.nextInt(elevators.size());
            return elevators.get(randomIndex);
        }
        
        return null; // No elevators in building
    }

    @Override
    public ElevatorCar assignElevator(int sourceFloor, Direction direction) {
        List<ElevatorCar> elevators = building.getElevatorCars();
        
        // First try to find the best available elevator (closest to source floor)
        List<ElevatorCar> availableElevators = elevators.stream()
                .filter(ElevatorCar::isAvailable)
                .collect(Collectors.toList());
        
        if (!availableElevators.isEmpty()) {
            // Find the closest available elevator
            ElevatorCar closestElevator = availableElevators.get(0);
            int minDistance = Math.abs(closestElevator.getCurrentFloor().getFloorNumber() - sourceFloor);
            
            for (ElevatorCar elevator : availableElevators) {
                int distance = Math.abs(elevator.getCurrentFloor().getFloorNumber() - sourceFloor);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestElevator = elevator;
                }
            }
            return closestElevator;
        }
        
        // If no elevator is available, assign a random one
        return assignElevator();
    }
}
