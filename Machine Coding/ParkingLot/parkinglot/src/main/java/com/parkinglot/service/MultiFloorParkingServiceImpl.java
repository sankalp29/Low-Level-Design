package com.parkinglot.service;

import java.util.Optional;

import com.parkinglot.FareService;
import com.parkinglot.MultiFloorParkingLot;
import com.parkinglot.MultiFloorParkingSpotFinderService;
import com.parkinglot.exception.IllegalParkingTicket;
import com.parkinglot.exception.SlotAlreadyFreeException;
import com.parkinglot.parkingSpot.ParkingSpot;
import com.parkinglot.parkingTicket.IParkingTicketRepository;
import com.parkinglot.parkingTicket.ParkingTicket;
import com.parkinglot.payment.PaymentProcessor;
import com.parkinglot.vehicle.Vehicle;

public class MultiFloorParkingServiceImpl implements ParkingService {
    private final MultiFloorParkingLot parkingLot;
    private final IParkingTicketRepository ticketRepository;

    public MultiFloorParkingServiceImpl(MultiFloorParkingLot parkingLot, IParkingTicketRepository ticketRepository) {
        this.parkingLot = parkingLot;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<ParkingTicket> parkVehicle(Vehicle vehicle) {
        Optional<ParkingSpot> parkingSpot = MultiFloorParkingSpotFinderService.findSpot(parkingLot, vehicle.getVehicleType());
        if (parkingSpot.isEmpty()) {
            System.out.println("No parking spot was found on any floor!");
            return Optional.empty();
        }
        ParkingTicket parkingTicket = new ParkingTicket(vehicle, parkingSpot.get());
        ticketRepository.storeTicket(null, parkingTicket); // Note: MultiFloor doesn't need parkingLot reference
        return Optional.of(parkingTicket);
    }

    @Override
    public void exitVehicle(ParkingTicket parkingTicket) throws IllegalParkingTicket, SlotAlreadyFreeException {
        if (!ticketRepository.find(parkingTicket)) {
            throw new IllegalParkingTicket("No such parking ticket exists");
        }
        parkingTicket.setExitTime();
        parkingTicket.getParkingSpot().freeSlot();
        double parkingFare = FareService.calculateFare(parkingTicket);
        PaymentProcessor.processPayment(parkingFare);
        parkingTicket.exit();
    }
}
