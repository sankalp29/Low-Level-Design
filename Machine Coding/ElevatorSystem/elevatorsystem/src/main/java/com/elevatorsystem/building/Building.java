package com.elevatorsystem.building;

import java.util.ArrayList;
import java.util.List;

import com.elevatorsystem.elevator.ElevatorCar;
import com.elevatorsystem.floor.Floor;

public class Building {
    private final String name;
    private final List<Floor> floors;
    private final List<ElevatorCar> elevatorCars;

    public Building(String name, int noOfFloors, int noOfElevators) {
        this.name = name;
        floors = new ArrayList<>();
        ElevatorsDisplay elevatorsDisplay = new ElevatorsDisplay(this);
        for (int i = 0; i <= noOfFloors; i++) {
            floors.add(new Floor(i, this, elevatorsDisplay));
        }

        elevatorCars = new ArrayList<>();
        for (int i = 1; i <= noOfElevators; i++) {
            elevatorCars.add(new ElevatorCar(floors.get(0)));
        }
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<ElevatorCar> getElevatorCars() {
        return elevatorCars;
    }

    public String getName() {
        return name;
    }
}

