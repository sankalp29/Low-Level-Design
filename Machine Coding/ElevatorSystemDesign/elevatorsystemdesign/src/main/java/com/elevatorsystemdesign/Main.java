package com.elevatorsystemdesign;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.elevatorRoutingStrategies.OptimalRoutingStrategy;
import com.elevatorsystemdesign.exceptions.InvalidFloorRequestedException;
import com.elevatorsystemdesign.exceptions.InvalidNumberOfElevatorsException;
import com.elevatorsystemdesign.exceptions.InvalidNumberOfFloorsException;
import com.elevatorsystemdesign.model.ElevatorCar;
import com.elevatorsystemdesign.model.ElevatorRequest;

public class Main {
    public static void main(String[] args) throws InvalidNumberOfElevatorsException, InvalidNumberOfFloorsException, InvalidFloorRequestedException, InterruptedException {
        ElevatorManager elevatorManager = new ElevatorManager(2, 15, new OptimalRoutingStrategy());
        runFourElevatorCalls(elevatorManager);        
    }

    private static void runFourElevatorCalls(ElevatorManager elevatorManager) throws InterruptedException {
        Runnable task1 = () -> {
            try {
                ElevatorRequest request = new ElevatorRequest(12, 13, Direction.UP);
                ElevatorCar elevator1 = elevatorManager.requestElevator(request);
                System.out.println("Task 1: Got Elevator " + elevator1.getElevatorId());
            } catch (InvalidFloorRequestedException e) {
                e.printStackTrace();
            }
        };
        
        Runnable task2 = () -> {
            try {
                ElevatorRequest request = new ElevatorRequest(5, 1, Direction.DOWN);
                ElevatorCar elevator2 = elevatorManager.requestElevator(request);
                System.out.println("Task 2: Got Elevator " + elevator2.getElevatorId());
            } catch (InvalidFloorRequestedException e) {
                e.printStackTrace();
            }
        };

        Runnable task3 = () -> {
            try {
                ElevatorRequest request = new ElevatorRequest(10, 14, Direction.DOWN);
                ElevatorCar elevator2 = elevatorManager.requestElevator(request);
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

        t1.join();
        t2.join();
        t3.join();

        elevatorManager.stop();
    }
}