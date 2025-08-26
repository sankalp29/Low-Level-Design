package com.parkinglot.vehicle;

import lombok.Getter;

@Getter
public abstract class Vehicle {
    
    private final String name;
    private final String number;
    private final VehicleType vehicleType;

    public Vehicle(String name, String number, VehicleType vehicleType) {
        this.name = name;
        this.number = number;
        this.vehicleType = vehicleType;
    }
}
