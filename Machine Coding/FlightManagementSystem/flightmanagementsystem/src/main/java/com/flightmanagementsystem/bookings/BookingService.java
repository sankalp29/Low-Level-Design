package com.flightmanagementsystem.bookings;

import java.util.List;

import com.flightmanagementsystem.enums.BookingStatus;
import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.InvalidBookingException;
import com.flightmanagementsystem.exceptions.InvalidPassengerException;
import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.exceptions.NoAvailableSeatException;
import com.flightmanagementsystem.exceptions.PaymentFailedException;
import com.flightmanagementsystem.exceptions.SeatAlreadyBookedException;
import com.flightmanagementsystem.exceptions.UnreservedSeatBookingAttemptException;
import com.flightmanagementsystem.flight.FlightTrip;
import com.flightmanagementsystem.flight.Seat;
import com.flightmanagementsystem.interfaces.IBookingRepository;
import com.flightmanagementsystem.interfaces.IBookingService;
import com.flightmanagementsystem.interfaces.IFlightService;
import com.flightmanagementsystem.payment.PaymentService;
import com.flightmanagementsystem.users.Passenger;

public class BookingService implements IBookingService {
    private final IFlightService flightService;
    private final IBookingRepository bookingRepository;
    private final PaymentService paymentService;
    
    @Override
    public String bookSeat(Passenger passenger, String tripId, SeatType seatType) throws InvalidTripException, InvalidPassengerException, NoAvailableSeatException, UnreservedSeatBookingAttemptException, SeatAlreadyBookedException, PaymentFailedException {
        if (passenger == null) throw new InvalidPassengerException("Invalid passenger.");
        FlightTrip flightTrip = flightService.getTrip(tripId);
        Seat seat = flightTrip.freezeSeat(passenger, seatType);
        boolean response = paymentService.processPayment(seat.getPrice());
        if (!response) {
            seat.releaseSeat();
            throw new PaymentFailedException("Payment failed. Seat could not be booked.");
        }
        seat.bookSeat(passenger);
        Booking booking = new Booking(passenger, flightTrip, seat);
        bookingRepository.saveBooking(booking);
        System.out.println("Booking Complete : " + booking + "\n");
        return booking.getBookingId();
    }

    @Override
    public boolean cancelBooking(Passenger passenger, String bookingId) throws InvalidBookingException, PaymentFailedException {
        Booking booking = bookingRepository.getBookingById(bookingId);
        if (booking == null) throw new InvalidBookingException("Booking not found.");
        if (!booking.getPassenger().getUserId().equals(passenger.getUserId())) throw new InvalidBookingException("Booking not created by user.");
        
        synchronized (booking) {
            if (booking.getBookingStatus().equals(BookingStatus.CANCELLED)) throw new InvalidBookingException("Booking already cancelled.");
            Seat seat = booking.getSeat();
            boolean response = paymentService.processRefund(seat.getPrice());
            if (!response) {
                throw new PaymentFailedException("Refund failed. Booking could not be cancelled.");
            }
            booking.cancelBooking();
            seat.releaseSeat();
            System.out.println("Booking cancelled successfully" + "\n");
            return true;
        }
    }

    public BookingService(IFlightService flightService, PaymentService paymentService) {
        this.flightService = flightService;
        this.bookingRepository = BookingRepository.getInstance();
        this.paymentService = paymentService;
    }
}
