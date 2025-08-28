package com.elevatorsystemdesign.elevatorRoutingStrategies;

import java.util.List;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.model.ElevatorCar;

public interface ElevatorRoutingStrategy {
    ElevatorCar findElevator(List<ElevatorCar> elevators, int sourceFloor, Direction direction);
}
