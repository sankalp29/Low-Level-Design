package com.elevatorsystemdesign;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.elevatorRoutingStrategies.ElevatorRoutingStrategy;
import com.elevatorsystemdesign.exceptions.InvalidFloorRequestedException;
import com.elevatorsystemdesign.exceptions.InvalidNumberOfElevatorsException;
import com.elevatorsystemdesign.exceptions.InvalidNumberOfFloorsException;
import com.elevatorsystemdesign.model.ElevatorCar;

import lombok.Getter;

@Getter
public class ElevatorManager {
    private List<ElevatorCar> elevators;
    private int floors;
    private ElevatorRoutingStrategy elevatorRoutingStrategy;
    private ExecutorService executorService;

    public ElevatorManager(final int numOfElevators, final int numOfFloors, ElevatorRoutingStrategy routingStrategy) throws InvalidNumberOfElevatorsException, InvalidNumberOfFloorsException {
        if (numOfElevators <= 0) {
            throw new InvalidNumberOfElevatorsException("Number of Elevators cannot be negative or zero");
        }
            
        if (numOfFloors <= 0) {
            throw new InvalidNumberOfFloorsException("Number of floors cannot be negative or zero");
        }

        this.elevators = new ArrayList<>();
        this.floors = numOfFloors;
        this.executorService = Executors.newFixedThreadPool(numOfElevators);
        for (int i = 1; i <= numOfElevators; i++) {
            ElevatorCar elevatorCar = new ElevatorCar(i, 0, floors);
            elevators.add(elevatorCar);
            executorService.submit(elevatorCar);
        }

        this.elevatorRoutingStrategy = routingStrategy;
    }

    public synchronized ElevatorCar requestElevator(final int sourceFloor, final int destinationFloor, final Direction direction) throws InvalidFloorRequestedException {
        if (sourceFloor < 0 || sourceFloor > floors) {
            throw new InvalidFloorRequestedException("Invalid floor requested");
        }

        ElevatorCar elevator = elevatorRoutingStrategy.findElevator(elevators, sourceFloor, direction);
        elevator.addRequest(sourceFloor, destinationFloor);
        return elevator;
    }

    public void stop() {
        executorService.shutdown();
        for (ElevatorCar elevator : elevators) {
            elevator.stop();
        }
    }
}