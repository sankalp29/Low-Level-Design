package com.elevatorsystem;

import com.elevatorsystem.building.Building;
import com.elevatorsystem.elevator.Direction;
import com.elevatorsystem.elevator.ElevatorCar;
import com.elevatorsystem.elevator.strategies.ElevatorFinderStrategy;
import com.elevatorsystem.floor.Floor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorService {
    private final Building building;
    private ElevatorFinderStrategy elevatorFinderStrategy;
    
    public ElevatorCar requestElevator(int sourceFloor, Direction direction) {
        // Validate source floor
        if (sourceFloor < 0 || sourceFloor >= building.getFloors().size()) {
            System.out.println("Invalid floor number: " + sourceFloor);
            return null;
        }
        
        // Find an elevator using the strategy
        ElevatorCar assignedElevator = elevatorFinderStrategy.assignElevator(sourceFloor, direction);
        
        if (assignedElevator == null) {
            System.out.println("No elevator available for request from floor " + sourceFloor);
            return null;
        }
        
        System.out.println("Elevator " + assignedElevator.getId() + " assigned to floor " + sourceFloor + 
                         " for direction " + direction);
        
        // Move elevator to source floor if not already there
        Floor targetFloor = building.getFloors().get(sourceFloor);
        if (assignedElevator.getCurrentFloor().getFloorNumber() != sourceFloor) {
            assignedElevator.moveToFloor(targetFloor);
        }
        
        // Open doors for passenger
        assignedElevator.openDoors();
        
        return assignedElevator;
    }
    
    public void requestFloor(ElevatorCar elevator, int destinationFloor) {
        // Validate destination floor
        if (destinationFloor < 0 || destinationFloor >= building.getFloors().size()) {
            System.out.println("Invalid destination floor: " + destinationFloor);
            return;
        }
        
        // Add floor request to elevator
        elevator.addFloorRequest(destinationFloor);
        
        // Close doors and move
        elevator.closeDoors();
        
        Floor targetFloor = building.getFloors().get(destinationFloor);
        elevator.moveToFloor(targetFloor);
        
        // Remove the request and open doors at destination
        elevator.removeFloorRequest(destinationFloor);
        elevator.openDoors();
        
        System.out.println("Passenger reached destination floor " + destinationFloor);
    }
    
    public void displayElevatorStatus() {
        System.out.println("\n=== Elevator Status ===");
        for (ElevatorCar elevator : building.getElevatorCars()) {
            System.out.println(elevator.toString());
        }
        System.out.println("=====================\n");
    }
    
    public Building getBuilding() {
        return building;
    }
}
