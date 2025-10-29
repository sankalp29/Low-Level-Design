package com.elevatorsystem;

import com.elevatorsystem.building.Building;
import com.elevatorsystem.elevator.Direction;
import com.elevatorsystem.elevator.ElevatorCar;
import com.elevatorsystem.elevator.strategies.RandomElevatorFinder;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Elevator System Demo ===\n");
        
        // Create a building with 10 floors and 3 elevators
        Building building = new Building("Tech Tower", 10, 3);
        
        // Create elevator service with random elevator finder strategy
        RandomElevatorFinder elevatorFinder = new RandomElevatorFinder(building);
        ElevatorService elevatorService = new ElevatorService(building, elevatorFinder);
        
        // Display initial elevator status
        System.out.println("Initial Elevator Status:");
        elevatorService.displayElevatorStatus();
        
        // Simulate elevator requests
        System.out.println("=== Simulation Scenarios ===\n");
        
        // Scenario 1: Person on floor 3 wants to go up
        System.out.println("Scenario 1: Person on floor 3 wants to go up");
        ElevatorCar elevator1 = elevatorService.requestElevator(3, Direction.UP);
        if (elevator1 != null) {
            // Person wants to go to floor 8
            elevatorService.requestFloor(elevator1, 8);
        }
        
        // Display elevator status after scenario 1
        elevatorService.displayElevatorStatus();
        
        // Scenario 2: Person on floor 7 wants to go down
        System.out.println("Scenario 2: Person on floor 7 wants to go down");
        ElevatorCar elevator2 = elevatorService.requestElevator(7, Direction.DOWN);
        if (elevator2 != null) {
            // Person wants to go to floor 2
            elevatorService.requestFloor(elevator2, 2);
        }
        
        // Display elevator status after scenario 2
        elevatorService.displayElevatorStatus();
        
        // Scenario 3: Multiple requests
        System.out.println("Scenario 3: Multiple simultaneous requests");
        ElevatorCar elevator3a = elevatorService.requestElevator(1, Direction.UP);
        if (elevator3a != null) {
            elevatorService.requestFloor(elevator3a, 5);
        }
        
        ElevatorCar elevator3b = elevatorService.requestElevator(9, Direction.DOWN);
        if (elevator3b != null) {
            elevatorService.requestFloor(elevator3b, 4);
        }
        
        // Display final elevator status
        elevatorService.displayElevatorStatus();
        
        // Scenario 4: Test floor panel interactions
        System.out.println("Scenario 4: Testing floor panel interactions");
        testFloorPanelInteractions(building);
        
        // Scenario 5: Test elevator display
        System.out.println("Scenario 5: Testing elevator display");
        building.getFloors().get(0).getElevatorsDisplay().displayElevatorStatus();
        
        System.out.println("=== Demo Complete ===");
    }
    
    private static void testFloorPanelInteractions(Building building) {
        // Test floor panel on floor 5
        System.out.println("Testing floor panel on floor 5:");
        building.getFloors().get(5).getFloorPanel().pressUpButton();
        building.getFloors().get(5).getFloorPanel().pressDownButton();
        building.getFloors().get(5).getFloorPanel().releaseUpButton();
        building.getFloors().get(5).getFloorPanel().releaseDownButton();
        
        // Test edge cases
        System.out.println("\nTesting edge cases:");
        building.getFloors().get(5).getFloorPanel().pressUpButton(); // Press again
        building.getFloors().get(5).getFloorPanel().releaseDownButton(); // Release again
    }
}