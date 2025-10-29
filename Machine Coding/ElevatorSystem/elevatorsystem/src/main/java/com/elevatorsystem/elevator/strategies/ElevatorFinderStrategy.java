package com.elevatorsystem.elevator.strategies;

import com.elevatorsystem.elevator.Direction;
import com.elevatorsystem.elevator.ElevatorCar;

public interface ElevatorFinderStrategy {
    ElevatorCar assignElevator();
    ElevatorCar assignElevator(int sourceFloor, Direction direction);
}
