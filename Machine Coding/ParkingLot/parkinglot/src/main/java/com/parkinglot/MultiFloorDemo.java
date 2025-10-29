package com.parkinglot;

import java.util.Optional;

import com.parkinglot.exception.IllegalParkingSpotNumberException;
import com.parkinglot.exception.IllegalParkingTicket;
import com.parkinglot.exception.SlotAlreadyFreeException;
import com.parkinglot.parkingSpot.ParkingSpotType;
import com.parkinglot.parkingTicket.ParkingTicket;
import com.parkinglot.parkingTicket.ParkingTicketRepository;
import com.parkinglot.service.MultiFloorParkingServiceImpl;
import com.parkinglot.service.ParkingService;
import com.parkinglot.strategy.DefaultVehicleSpotMapping;
import com.parkinglot.strategy.LowestFloorFirstStrategy;
import com.parkinglot.vehicle.Car;
import com.parkinglot.vehicle.Motorcycle;
import com.parkinglot.vehicle.Vehicle;

public class MultiFloorDemo {
    
    public static void main(String[] args) {
        demonstrateMultiFloorParking();
    }
    
    private static void demonstrateMultiFloorParking() {
        System.out.println("🏢 Multi-Floor Parking Lot Demo");
        System.out.println("================================\n");
        
        // Create a 3-floor parking lot
        MultiFloorParkingLot multiFloorParkingLot = null;
        try {
            multiFloorParkingLot = MultiFloorParkingLot.builder()
                    .setAddress("Tech Park, Bangalore")
                    .setName("Multi-Level Smart Parking")
                    .setVehicleSpotMappingStrategy(new DefaultVehicleSpotMapping())
                    .setFloorSelectionStrategy(new LowestFloorFirstStrategy())
                    // Ground Floor (0) - Mostly cars and trucks
                    .addFloor(0, "Ground Floor")
                    .addParkingSpotsToFloor(0, ParkingSpotType.REGULAR, 2)
                    .addParkingSpotsToFloor(0, ParkingSpotType.OVERSIZED, 1)
                    // First Floor (1) - Mixed
                    .addFloor(1, "First Floor") 
                    .addParkingSpotsToFloor(1, ParkingSpotType.COMPACT, 2)
                    .addParkingSpotsToFloor(1, ParkingSpotType.REGULAR, 2)
                    // Second Floor (2) - Compact vehicles
                    .addFloor(2, "Second Floor")
                    .addParkingSpotsToFloor(2, ParkingSpotType.COMPACT, 3)
                    .build();
        } catch (IllegalParkingSpotNumberException e) {
            System.out.println("Error creating parking lot: " + e.getMessage());
            return;
        }
        
        // Create service
        ParkingService parkingService = new MultiFloorParkingServiceImpl(
            multiFloorParkingLot, 
            new ParkingTicketRepository()
        );
        
        // Display initial state
        System.out.println("📊 Initial Parking Lot State:");
        multiFloorParkingLot.display();
        
        // Test parking multiple vehicles
        System.out.println("🚗 Testing Vehicle Parking:");
        System.out.println("---------------------------");
        
        Vehicle motorcycle1 = new Motorcycle("R15", "MH01FH6453");
        Vehicle motorcycle2 = new Motorcycle("Ninja", "MH01FH8935"); 
        Vehicle motorcycle3 = new Motorcycle("Duke", "MH01FH7777");
        Vehicle car1 = new Car("Vento", "MH01FH1234");
        Vehicle car2 = new Car("Civic", "MH01FH5678");
        
        // Park vehicles and observe floor selection
        Optional<ParkingTicket> ticket1 = parkingService.parkVehicle(motorcycle1);
        System.out.println("Motorcycle 1 parked: " + (ticket1.isPresent() ? "✅ Spot: " + ticket1.get().getParkingSpot().getSpotId() : "❌ Failed"));
        
        Optional<ParkingTicket> ticket2 = parkingService.parkVehicle(car1);
        System.out.println("Car 1 parked: " + (ticket2.isPresent() ? "✅ Spot: " + ticket2.get().getParkingSpot().getSpotId() : "❌ Failed"));
        
        Optional<ParkingTicket> ticket3 = parkingService.parkVehicle(motorcycle2);
        System.out.println("Motorcycle 2 parked: " + (ticket3.isPresent() ? "✅ Spot: " + ticket3.get().getParkingSpot().getSpotId() : "❌ Failed"));
        
        Optional<ParkingTicket> ticket4 = parkingService.parkVehicle(car2);
        System.out.println("Car 2 parked: " + (ticket4.isPresent() ? "✅ Spot: " + ticket4.get().getParkingSpot().getSpotId() : "❌ Failed"));
        
        Optional<ParkingTicket> ticket5 = parkingService.parkVehicle(motorcycle3);
        System.out.println("Motorcycle 3 parked: " + (ticket5.isPresent() ? "✅ Spot: " + ticket5.get().getParkingSpot().getSpotId() : "❌ Failed"));
        
        System.out.println();
        
        // Display state after parking
        System.out.println("📊 Parking Lot State After Parking:");
        multiFloorParkingLot.display();
        
        // Test exit
        System.out.println("🚪 Testing Vehicle Exit:");
        System.out.println("-------------------------");
        
        if (ticket1.isPresent()) {
            try {
                parkingService.exitVehicle(ticket1.get());
                System.out.println("Motorcycle 1 exited successfully ✅");
            } catch (IllegalParkingTicket | SlotAlreadyFreeException e) {
                System.out.println("Error during exit: " + e.getMessage());
            }
        }
        
        System.out.println();
        
        // Display final state
        System.out.println("📊 Final Parking Lot State:");
        multiFloorParkingLot.display();
        
        System.out.println("🎉 Multi-Floor Demo Complete!");
    }
}
