package com.parkinglot;

import java.util.Optional;

import com.parkinglot.exception.IllegalParkingSpotNumberException;
import com.parkinglot.exception.IllegalParkingTicket;
import com.parkinglot.exception.SlotAlreadyFreeException;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.parkingTicket.ParkingTicket;
import com.parkinglot.parkingTicket.ParkingTicketRepository;
import com.parkinglot.service.ParkingService;
import com.parkinglot.service.ParkingServiceImpl;
import com.parkinglot.strategy.DefaultVehicleSpotMapping;
import com.parkinglot.vehicle.Car;
import com.parkinglot.vehicle.Motorcycle;
import com.parkinglot.vehicle.Vehicle;

public class Main {
    public static void main(String[] args) {
        testWithTwoMotorcyclesNonConcurrent();
        testWithTwoMotorcyclesConcurrent();
        testWithOneMotorcycleOneCarConcurrent();
    }

    private static void testWithOneMotorcycleOneCarConcurrent() {
        ParkingLot parkingLot1 = null;
        try {
            parkingLot1 = ParkingLot.builder()
                                            .setAddress("CCM Nashik")
                                            .setName("CCM Parking")
                                            .setVehicleSpotMappingStrategy(new DefaultVehicleSpotMapping())
                                            .addParkingSpots(ParkingSpotType.COMPACT, 1)
                                            .addParkingSpots(ParkingSpotType.REGULAR, 1)
                                            .addParkingSpots(ParkingSpotType.OVERSIZED, 1)
                                            .build();
        } catch (IllegalParkingSpotNumberException e) {
            System.out.println(e);
        }
        parkingLot1.display();
        
        ParkingService parkingService = new ParkingServiceImpl(parkingLot1, new ParkingTicketRepository());
        Vehicle motorcycle = new Motorcycle("R15", "MH01FH6453");
        Vehicle car = new Car("Vento", "MH01FH8935");
        final ParkingService service = parkingService;

        Runnable task1 = new Runnable() {
            public void run() {                
                Optional<ParkingTicket> parkingTicket = service.parkVehicle(motorcycle);
                System.out.println("Parking Ticket 1 : " + parkingTicket);
            }
        };

        Runnable task2 = new Runnable() {
            public void run() {                
                Optional<ParkingTicket> parkingTicket = service.parkVehicle(car);
                System.out.println("Parking Ticket 2 : " + parkingTicket);
            }
        };

        Thread t1 = new Thread(task1, "Thread 1");
        Thread t2 = new Thread(task2, "Thread 2");

        t1.start();
        t2.start();

        parkingLot1.display();
    }

    private static void testWithTwoMotorcyclesConcurrent() {
        ParkingLot parkingLot1 = null;
        try {
            parkingLot1 = ParkingLot.builder()
                                            .setAddress("CCM Nashik")
                                            .setName("CCM Parking")
                                            .setVehicleSpotMappingStrategy(new DefaultVehicleSpotMapping())
                                            .addParkingSpots(ParkingSpotType.COMPACT, 1)
                                            .addParkingSpots(ParkingSpotType.REGULAR, 1)
                                            .addParkingSpots(ParkingSpotType.OVERSIZED, 1)
                                            .build();
        } catch (IllegalParkingSpotNumberException e) {
            System.out.println(e);
        }
        parkingLot1.display();

        ParkingService parkingService = new ParkingServiceImpl(parkingLot1, new ParkingTicketRepository());
        Vehicle motorcycle1 = new Motorcycle("R15", "MH01FH6453");
        Vehicle motorcycle2 = new Motorcycle("Ninja", "MH01FH8935");
        final ParkingService service = parkingService;

        Runnable task1 = new Runnable() {
            public void run() {                
                Optional<ParkingTicket> parkingTicket = service.parkVehicle(motorcycle1);
                System.out.println("Parking Ticket 1 : " + parkingTicket);
            }
        };

        Runnable task2 = new Runnable() {
            public void run() {                
                Optional<ParkingTicket> parkingTicket = service.parkVehicle(motorcycle2);
                System.out.println("Parking Ticket 2 : " + parkingTicket);
            }
        };

        Thread t1 = new Thread(task1, "Thread 1");
        Thread t2 = new Thread(task2, "Thread 2");

        t1.start();
        t2.start();
        parkingLot1.display();

    }

    private static void testWithTwoMotorcyclesNonConcurrent() {
        ParkingLot parkingLot1 = null;
        try {
            parkingLot1 = ParkingLot.builder()
                                            .setAddress("CCM Nashik")
                                            .setName("CCM Parking")
                                            .setVehicleSpotMappingStrategy(new DefaultVehicleSpotMapping())
                                            .addParkingSpots(ParkingSpotType.COMPACT, 1)
                                            .addParkingSpots(ParkingSpotType.REGULAR, 1)
                                            .addParkingSpots(ParkingSpotType.OVERSIZED, 1)
                                            .build();
        } catch (IllegalParkingSpotNumberException e) {
            System.out.println(e);
        }
        parkingLot1.display();
        
        ParkingService parkingService = new ParkingServiceImpl(parkingLot1, new ParkingTicketRepository());
        Vehicle motorcycle1 = new Motorcycle("R15", "MH01FH6453");
        Vehicle motorcycle2 = new Motorcycle("Ninja", "MH01FH8935");
        
        Optional<ParkingTicket> parkingTicket1 = parkingService.parkVehicle(motorcycle1);
        System.out.println("Parking Ticket 1 : " + parkingTicket1);
        Optional<ParkingTicket> parkingTicket2 = parkingService.parkVehicle(motorcycle2);
        System.out.println("Parking Ticket 2 : " + parkingTicket2);
        parkingLot1.display();

        try {
            Thread.sleep(10000);
            parkingService.exitVehicle(parkingTicket1.get());
        } catch (IllegalParkingTicket | SlotAlreadyFreeException | InterruptedException e) {
            e.printStackTrace();
        }
        parkingLot1.display();
        parkingTicket2 = parkingService.parkVehicle(motorcycle2);
        System.out.println("** After Parking Vacated **: \nParking Ticket 2 : " + parkingTicket2);
    }
}