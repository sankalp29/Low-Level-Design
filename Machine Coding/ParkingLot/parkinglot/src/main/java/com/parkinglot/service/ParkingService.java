package com.parkinglot.service;

import java.util.Optional;

import com.parkinglot.exception.IllegalParkingTicket;
import com.parkinglot.exception.SlotAlreadyFreeException;
import com.parkinglot.parkingTicket.ParkingTicket;
import com.parkinglot.vehicle.Vehicle;

public interface ParkingService {
    /**
     * Parks a vehicle and returns a parking ticket if successful
     * @param vehicle the vehicle to park
     * @return Optional containing parking ticket if parking was successful, empty otherwise
     */
    Optional<ParkingTicket> parkVehicle(Vehicle vehicle);
    
    /**
     * Handles vehicle exit, including fare calculation and payment processing
     * @param parkingTicket the parking ticket for the vehicle exiting
     * @throws IllegalParkingTicket if the parking ticket is invalid
     * @throws SlotAlreadyFreeException if the parking slot is already free
     */
    void exitVehicle(ParkingTicket parkingTicket) throws IllegalParkingTicket, SlotAlreadyFreeException;
}
