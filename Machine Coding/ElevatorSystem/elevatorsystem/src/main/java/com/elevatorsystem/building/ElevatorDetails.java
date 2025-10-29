package com.elevatorsystem.building;

import com.elevatorsystem.elevator.Direction;
import com.elevatorsystem.elevator.ElevatorDoorStatus;
import com.elevatorsystem.elevator.ElevatorStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ElevatorDetails {
    private final int elevatorId;
    private final int currentFloor;
    private final ElevatorStatus status;
    private final Direction direction;
    private final ElevatorDoorStatus doorStatus;
}
