package com.parkinglot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.parkinglot.exception.IllegalParkingSpotNumberException;
import com.parkinglot.exception.IllegalParkingTicket;
import com.parkinglot.exception.SlotAlreadyFreeException;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.parkingTicket.IParkingTicketRepository;
import com.parkinglot.parkingTicket.ParkingTicket;
import com.parkinglot.parkingTicket.ParkingTicketRepository;
import com.parkinglot.payment.PaymentProcessor;
import com.parkinglot.vehicle.Vehicle;
import com.parkinglot.vehicle.VehicleType;

import lombok.Getter;

@Getter
public class ParkingLot {
    private final String address;
    private final String name;
    private final Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap;
    private final Map<VehicleType, ParkingSpotType> vehicleTypeToSpotType;
    private final IParkingTicketRepository parkingTicketRepository;

    public Optional<ParkingTicket> findSpot(Vehicle vehicle) {
        Optional<ParkingSpot> parkingSpot = ParkingSpotFinderService.findSpot(this, vehicle.getVehicleType());
        if (parkingSpot.isEmpty()) {
            System.out.println("No parking spot was found!");
            return Optional.empty();
        }
        ParkingTicket parkingTicket = new ParkingTicket(vehicle, parkingSpot.get());
        parkingTicketRepository.storeTicket(this, parkingTicket);
        return Optional.of(parkingTicket);
    }

    public void exit(ParkingTicket parkingTicket) throws IllegalParkingTicket, SlotAlreadyFreeException {
        if (!parkingTicketRepository.find(parkingTicket)) {
            throw new IllegalParkingTicket("No such parking ticket exists");
        }
        parkingTicket.setExitTime();
        parkingTicket.getParkingSpot().freeSlot();
        double parkingFare = FareService.calculateFare(parkingTicket);
        PaymentProcessor.processPayment(parkingFare);
        parkingTicket.exit();
    }

    public void display() {
        ParkingLotDisplay.display(this);
    }
    
    public static class ParkingLotBuilder {
        private String address;
        private String name;
        private final Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap;

        ParkingLotBuilder() {
            parkingSpotMap = new HashMap<>();
        }

        public ParkingLotBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ParkingLotBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ParkingLotBuilder addParkingSpots(ParkingSpotType parkingSpotType, Integer numOfSpots) throws IllegalParkingSpotNumberException{
            if (numOfSpots <= 0) {
                throw new IllegalParkingSpotNumberException("Number of parking spots to be added cannot be <= zero.");
            }
            parkingSpotMap.putIfAbsent(parkingSpotType, new ArrayList<>());
            for (int i = 1; i <= numOfSpots; i++) {
                String spotId = parkingSpotType.name() + parkingSpotMap.get(parkingSpotType).size();
                parkingSpotMap.get(parkingSpotType).add(new ParkingSpot(spotId, parkingSpotType));
            }

            return this;
        }

        public ParkingLot build() {
            return new ParkingLot(address, name, parkingSpotMap);
        }
    }

    public static ParkingLotBuilder builder() {
        return new ParkingLotBuilder();
    }

    private ParkingLot(String address, String name, Map<ParkingSpotType, List<ParkingSpot>> parkingSpotMap) {
        this.address = address;
        this.name = name;
        this.parkingSpotMap = parkingSpotMap;
        this.vehicleTypeToSpotType = new HashMap<>();
        this.parkingTicketRepository = new ParkingTicketRepository();
        vehicleTypeToSpotType.put(VehicleType.MOTORCYCLE, ParkingSpotType.COMPACT);
        vehicleTypeToSpotType.put(VehicleType.CAR, ParkingSpotType.REGULAR);
        vehicleTypeToSpotType.put(VehicleType.TRUCK, ParkingSpotType.OVERSIZED);
    }
}
