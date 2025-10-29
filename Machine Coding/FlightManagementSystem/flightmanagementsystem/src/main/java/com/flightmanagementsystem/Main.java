package com.flightmanagementsystem;

import java.time.Instant;
import java.util.Date;
import com.flightmanagementsystem.bookings.BookingService;
import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.InvalidAdminException;
import com.flightmanagementsystem.exceptions.InvalidBookingException;
import com.flightmanagementsystem.exceptions.InvalidFlightCreationException;
import com.flightmanagementsystem.exceptions.InvalidPassengerException;
import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.exceptions.NoAvailableSeatException;
import com.flightmanagementsystem.exceptions.PaymentFailedException;
import com.flightmanagementsystem.exceptions.SeatAlreadyBookedException;
import com.flightmanagementsystem.exceptions.UnreservedSeatBookingAttemptException;
import com.flightmanagementsystem.flight.FlightService;
import com.flightmanagementsystem.payment.PaymentService;
import com.flightmanagementsystem.users.Account;
import com.flightmanagementsystem.users.Admin;
import com.flightmanagementsystem.users.Passenger;

public class Main {
    public static void main(String[] args) {
        // testBookingFlowWithSingleUser();
        // testBookingFlowWithTwoUsersConcurrent();
        testBookingFlowWithTwoUsersConcurrentAndCancelBooking();
    }

    private static void testBookingFlowWithSingleUser() {
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService(flightService, new PaymentService());
        Admin admin = new Admin("Admin", "admin@email.com", new Account("admin1"));

        try {
            String flightId = flightService.addFlight(admin, "Emirates", "A380", 150, 40, 10);
            String tripId = flightService.addTrip(admin, flightId, "Mumbai", "Delhi", Date.from(Instant.now()), 120);
            Passenger passenger = new Passenger("Sankalp", "sankalp@email.com", new Account("sankalp_username"));
            String bookingId = bookingService.bookSeat(passenger, tripId, SeatType.BUSINESS);
            bookingService.cancelBooking(passenger, bookingId);
        } catch (InvalidFlightCreationException | InvalidAdminException | InvalidTripException | InvalidPassengerException | NoAvailableSeatException | UnreservedSeatBookingAttemptException | SeatAlreadyBookedException | PaymentFailedException | InvalidBookingException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testBookingFlowWithTwoUsersConcurrent() {
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService(flightService, new PaymentService());
        Admin admin = new Admin("Admin", "admin@email.com", new Account("admin1"));

        String flightId;
        try {
            flightId = flightService.addFlight(admin, "Emirates", "A380", 150, 1, 10);
            String tripId = flightService.addTrip(admin, flightId, "Mumbai", "Delhi", Date.from(Instant.now()), 120);
            Passenger passenger1 = new Passenger("Sankalp", "sankalp@email.com", new Account("sankalp_username"));
            Passenger passenger2 = new Passenger("Janvi", "janvi@email.com", new Account("janvi_username"));
            
            Runnable runnable1 = () -> {
                try {
                    String bookingId = bookingService.bookSeat(passenger1, tripId, SeatType.BUSINESS);
                    System.out.println("Booking Thread 1 : " + bookingId);
                } catch (InvalidTripException | InvalidPassengerException | NoAvailableSeatException | UnreservedSeatBookingAttemptException | SeatAlreadyBookedException | PaymentFailedException e) {
                    System.out.println(e.getMessage());
                }
            };

            Runnable runnable2 = () -> {
                try {
                    String bookingId = bookingService.bookSeat(passenger2, tripId, SeatType.BUSINESS);
                    System.out.println("Booking Thread 2 : " + bookingId);
                } catch (InvalidTripException | InvalidPassengerException | NoAvailableSeatException | UnreservedSeatBookingAttemptException | SeatAlreadyBookedException | PaymentFailedException e) {
                    System.out.println(e.getMessage());
                }
            };
            
            Thread t1 = new Thread(runnable1);
            Thread t2 = new Thread(runnable2);
            t1.start();
            t2.start();
        } catch (InvalidFlightCreationException | InvalidAdminException | InvalidTripException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testBookingFlowWithTwoUsersConcurrentAndCancelBooking() {
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService(flightService, new PaymentService());
        Admin admin = new Admin("Admin", "admin@email.com", new Account("admin1"));

        String flightId;
        try {
            flightId = flightService.addFlight(admin, "Emirates", "A380", 150, 1, 10);
            String tripId = flightService.addTrip(admin, flightId, "Mumbai", "Delhi", Date.from(Instant.now()), 120);
            Passenger passenger1 = new Passenger("Sankalp", "sankalp@email.com", new Account("sankalp_username"));
            Passenger passenger2 = new Passenger("Janvi", "janvi@email.com", new Account("janvi_username"));

            // Use array to hold booking IDs (arrays are mutable, so effectively final)
            final String[] bookingIds = new String[2];
            
            Runnable runnable1 = () -> {
                try {
                    bookingIds[0] = bookingService.bookSeat(passenger1, tripId, SeatType.BUSINESS);
                    System.out.println("Booking Thread 1 : " + bookingIds[0]);
                } catch (InvalidTripException | InvalidPassengerException | NoAvailableSeatException | UnreservedSeatBookingAttemptException | SeatAlreadyBookedException | PaymentFailedException e) {
                    System.out.println(e.getMessage());
                }
            };

            Runnable runnable2 = () -> {
                try {
                    bookingIds[1] = bookingService.bookSeat(passenger2, tripId, SeatType.BUSINESS);
                    System.out.println("Booking Thread 2 : " + bookingIds[1]);
                } catch (InvalidTripException | InvalidPassengerException | NoAvailableSeatException | UnreservedSeatBookingAttemptException | SeatAlreadyBookedException | PaymentFailedException e) {
                    System.out.println(e.getMessage());
                }
            };

            Thread t1 = new Thread(runnable1);
            Thread t2 = new Thread(runnable2);
            t1.start();
            t2.start();

            // Wait for booking threads to complete before cancelling
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Booking threads interrupted");
            }

            Runnable runnable3 = () -> {
                try {
                    if (bookingIds[0] != null) {
                        bookingService.cancelBooking(passenger1, bookingIds[0]);
                        System.out.println("Cancelling Thread 1 : " + bookingIds[0]);
                    }
                } catch (InvalidBookingException e) {
                    System.out.println(e.getMessage());
                }
            };

            Runnable runnable4 = () -> {
                try {
                    if (bookingIds[1] != null) {
                        bookingService.cancelBooking(passenger2, bookingIds[1]);
                        System.out.println("Cancelling Thread 2 : " + bookingIds[1]);
                    }
                } catch (InvalidBookingException e) {
                    System.out.println(e.getMessage());
                }
            };

            Thread t3 = new Thread(runnable3);
            Thread t4 = new Thread(runnable4);
            t3.start();
            t4.start();
        } catch (InvalidFlightCreationException | InvalidAdminException | InvalidTripException e) {
            System.out.println(e.getMessage());
        }
    }
}