package com.elevatorsystem.elevator;

import com.elevatorsystem.floor.Floor;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ElevatorCar {
    private int id;
    private Floor currentFloor;
    private ElevatorStatus elevatorStatus;
    private Direction elevatorDirection;
    private ElevatorDoorStatus elevatorDoorStatus;
    private List<Integer> requestedFloors;
    private static int nextId = 1;

    public ElevatorCar(Floor startingFloor) {
        this.id = nextId++;
        this.currentFloor = startingFloor;
        this.elevatorStatus = ElevatorStatus.IDLE;
        this.elevatorDirection = Direction.IDLE;
        this.elevatorDoorStatus = ElevatorDoorStatus.CLOSED;
        this.requestedFloors = new ArrayList<>();
    }
    
    // Business logic methods
    public void addFloorRequest(int floorNumber) {
        if (!requestedFloors.contains(floorNumber)) {
            requestedFloors.add(floorNumber);
            System.out.println("Elevator " + id + ": Added floor " + floorNumber + " to requests");
        }
    }

    public void removeFloorRequest(int floorNumber) {
        requestedFloors.remove(Integer.valueOf(floorNumber));
        System.out.println("Elevator " + id + ": Removed floor " + floorNumber + " from requests");
    }

    public boolean hasRequests() {
        return !requestedFloors.isEmpty();
    }

    public void openDoors() {
        this.elevatorDoorStatus = ElevatorDoorStatus.OPEN;
        System.out.println("Elevator " + id + ": Doors opened at floor " + currentFloor.getFloorNumber());
    }

    public void closeDoors() {
        this.elevatorDoorStatus = ElevatorDoorStatus.CLOSED;
        System.out.println("Elevator " + id + ": Doors closed at floor " + currentFloor.getFloorNumber());
    }

    public void moveToFloor(Floor targetFloor) {
        if (currentFloor.getFloorNumber() == targetFloor.getFloorNumber()) {
            System.out.println("Elevator " + id + ": Already at floor " + targetFloor.getFloorNumber());
            return;
        }

        if (currentFloor.getFloorNumber() < targetFloor.getFloorNumber()) {
            this.elevatorDirection = Direction.UP;
            this.elevatorStatus = ElevatorStatus.MOVING_UP;
        } else {
            this.elevatorDirection = Direction.DOWN;
            this.elevatorStatus = ElevatorStatus.MOVING_DOWN;
        }

        System.out.println("Elevator " + id + ": Moving from floor " + currentFloor.getFloorNumber() + 
                         " to floor " + targetFloor.getFloorNumber());
        
        // Simulate movement
        this.currentFloor = targetFloor;
        this.elevatorStatus = ElevatorStatus.IDLE;
        this.elevatorDirection = Direction.IDLE;
        
        System.out.println("Elevator " + id + ": Arrived at floor " + targetFloor.getFloorNumber());
    }

    public boolean isAvailable() {
        return elevatorStatus == ElevatorStatus.IDLE && !hasRequests();
    }

    @Override
    public String toString() {
        return "ElevatorCar{" +
                "id=" + id +
                ", currentFloor=" + (currentFloor != null ? currentFloor.getFloorNumber() : "null") +
                ", status=" + elevatorStatus +
                ", direction=" + elevatorDirection +
                ", doorStatus=" + elevatorDoorStatus +
                ", requests=" + requestedFloors +
                '}';
    }
}
