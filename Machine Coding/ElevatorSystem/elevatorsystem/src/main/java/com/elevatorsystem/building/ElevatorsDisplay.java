package com.elevatorsystem.building;

import java.util.List;
import java.util.stream.Collectors;

import com.elevatorsystem.elevator.ElevatorCar;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorsDisplay {
    private Building building;

    public List<ElevatorDetails> getElevatorsDisplay() {
        return building.getElevatorCars().stream()
                .map(this::convertToElevatorDetails)
                .collect(Collectors.toList());
    }

    private ElevatorDetails convertToElevatorDetails(ElevatorCar elevator) {
        return new ElevatorDetails(
                elevator.getId(),
                elevator.getCurrentFloor().getFloorNumber(),
                elevator.getElevatorStatus(),
                elevator.getElevatorDirection(),
                elevator.getElevatorDoorStatus()
        );
    }

    public void displayElevatorStatus() {
        System.out.println("\n=== Elevators Display ===");
        List<ElevatorDetails> elevatorDetails = getElevatorsDisplay();
        for (ElevatorDetails details : elevatorDetails) {
            System.out.println(details);
        }
        System.out.println("========================\n");
    }
}
