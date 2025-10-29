package com.flightmanagementsystem.bookings;

import java.time.LocalDateTime;
import java.util.UUID;

import com.flightmanagementsystem.enums.BookingStatus;
import com.flightmanagementsystem.flight.FlightTrip;
import com.flightmanagementsystem.flight.Seat;
import com.flightmanagementsystem.users.Passenger;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Booking {
    private final String bookingId;
    private final Passenger passenger;
    private final FlightTrip flightTrip;
    private final Seat seat;
    private final LocalDateTime bookedAt;
    private BookingStatus bookingStatus;

    public Booking(Passenger passenger, FlightTrip flightTrip, Seat seat) {
        this.bookingId = UUID.randomUUID().toString();
        this.passenger = passenger;
        this.flightTrip = flightTrip;
        this.seat = seat;
        this.bookedAt = LocalDateTime.now();
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public void cancelBooking() {
        bookingStatus = BookingStatus.CANCELLED;
    }
}
