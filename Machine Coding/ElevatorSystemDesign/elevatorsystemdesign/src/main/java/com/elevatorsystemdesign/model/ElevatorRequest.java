package com.elevatorsystemdesign.model;

import com.elevatorsystemdesign.constants.Direction;
import lombok.Getter;

@Getter
public class ElevatorRequest {
    private final int sourceFloor;
    private final int destinationFloor;
    private final Direction direction;
    
    public ElevatorRequest(int sourceFloor, int destinationFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }
    
    @Override
    public String toString() {
        return "Request: " + sourceFloor + " -> " + destinationFloor + " (" + direction + ")";
    }
}
