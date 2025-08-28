package com.elevatorsystemdesign.model;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.elevatorsystemdesign.constants.Direction;
import com.elevatorsystemdesign.constants.ElevatorStatus;
import com.elevatorsystemdesign.exceptions.InvalidFloorRequestedException;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ElevatorCar implements Runnable {
    private final int elevatorId;
    @Setter
    private Direction direction;
    @Setter
    private ElevatorStatus elevatorStatus;
    @Setter
    private volatile int currentFloor;
    private final int floors;
    private final LinkedBlockingDeque<Integer> requestQueue;
    private Lock lock;
    private volatile boolean running;

    public ElevatorCar(final int elevatorId, final int startingFloor, final int floors) {
        this.elevatorId = elevatorId;
        this.direction = Direction.UP;
        this.elevatorStatus = ElevatorStatus.IDLE;
        this.currentFloor = startingFloor;
        this.floors = floors;
        this.lock = new ReentrantLock();
        this.requestQueue = new LinkedBlockingDeque<>();
        this.running = true;
    }

    public synchronized void addRequest(final int sourceFloor, final int destinationFloor) throws InvalidFloorRequestedException {
        if (sourceFloor < 0 || sourceFloor > floors) {
            throw new InvalidFloorRequestedException("Requested floor does not exist.");
        }
        System.out.println("New request made to Elevator " + elevatorId);
        if (currentFloor != sourceFloor) {
            requestQueue.add(sourceFloor);
        }
        requestQueue.add(destinationFloor);
        this.notify();
    }
    
    public synchronized void addRequest(ElevatorRequest request) throws InvalidFloorRequestedException {
        addRequest(request.getSourceFloor(), request.getDestinationFloor());
    }

    private void moveToFloor(final int floorNumber) {
        if (floorNumber > currentFloor) {
            direction = Direction.UP;
        } else if (floorNumber < currentFloor) {
            direction = Direction.DOWN;
        } else {
            return; // Already at the target floor
        }
        
        elevatorStatus = ElevatorStatus.MOVING;

        System.out.println("Moving Elevator " + elevatorId + " from Floor " + currentFloor + " -> Floor " + floorNumber);

        while (currentFloor != floorNumber) {
            try {
                Thread.sleep(500);
                currentFloor = currentFloor + (direction.equals(Direction.UP) ? 1 : -1);
                System.out.println("Elevator " + elevatorId + " at Floor " + currentFloor + " now");
            } catch (InterruptedException ex) {
                System.out.println(ex);
                Thread.currentThread().interrupt();
                return;
            }
        }

        elevatorStatus = ElevatorStatus.IDLE;
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (this) {
                    // Wait until there are requests or we're told to stop
                    while (requestQueue.isEmpty() && running) {
                        this.wait();
                    }
                }
                
                // Process all available requests
                while (!requestQueue.isEmpty()) {
                    Integer nextTargetFloor = requestQueue.poll();
                    if (nextTargetFloor != null) {
                        moveToFloor(nextTargetFloor);
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public void stop() {
        running = false;
        synchronized (this) {
            this.notify();
        }
    }
    
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
    
    public int getPendingRequestsCount() {
        return requestQueue.size();
    }
}