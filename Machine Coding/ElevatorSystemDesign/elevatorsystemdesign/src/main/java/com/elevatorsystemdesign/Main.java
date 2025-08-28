package com.elevatorsystemdesign;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.elevatorRoutingStrategies.OptimalRoutingStrategy;
import com.elevatorsystemdesign.exceptions.InvalidFloorRequestedException;
import com.elevatorsystemdesign.exceptions.InvalidNumberOfElevatorsException;
import com.elevatorsystemdesign.exceptions.InvalidNumberOfFloorsException;
import com.elevatorsystemdesign.model.ElevatorCar;

public class Main {
    public static void main(String[] args) throws InvalidNumberOfElevatorsException, InvalidNumberOfFloorsException, InvalidFloorRequestedException, InterruptedException {
        ElevatorManager elevatorManager = new ElevatorManager(2, 15, new OptimalRoutingStrategy());
        runFourElevatorCalls(elevatorManager);        
    }

    private static void runFourElevatorCalls(ElevatorManager elevatorManager) throws InterruptedException {
        Runnable task1 = () -> {
            try {
                ElevatorCar elevator1 = elevatorManager.requestElevator(12, 13, Direction.UP);
                System.out.println("Task 1: Got Elevator " + elevator1.getElevatorId());
            } catch (InvalidFloorRequestedException e) {
                e.printStackTrace();
            }
        };
        
        Runnable task2 = () -> {
            try {
                ElevatorCar elevator2 = elevatorManager.requestElevator(5, 1, Direction.DOWN);
                System.out.println("Task 2: Got Elevator " + elevator2.getElevatorId());
            } catch (InvalidFloorRequestedException e) {
                e.printStackTrace();
            }
        };

        Runnable task3 = () -> {
            try {
                ElevatorCar elevator2 = elevatorManager.requestElevator(10, 14, Direction.DOWN);
                System.out.println("Task 3: Got Elevator " + elevator2.getElevatorId());
            } catch (InvalidFloorRequestedException  e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        Thread t3 = new Thread(task3);

        t1.start();
        t2.start();
        t3.start();

        elevatorManager.stop();
    }
}