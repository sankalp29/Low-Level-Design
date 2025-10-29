package com.flightmanagementsystem.interfaces;

import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.InvalidBookingException;
import com.flightmanagementsystem.exceptions.InvalidPassengerException;
import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.exceptions.NoAvailableSeatException;
import com.flightmanagementsystem.exceptions.PaymentFailedException;
import com.flightmanagementsystem.exceptions.SeatAlreadyBookedException;
import com.flightmanagementsystem.exceptions.UnreservedSeatBookingAttemptException;
import com.flightmanagementsystem.users.Passenger;

public interface IBookingService {
    public String bookSeat(Passenger passenger, String tripId, SeatType seatType) throws InvalidTripException, InvalidPassengerException, NoAvailableSeatException, UnreservedSeatBookingAttemptException, SeatAlreadyBookedException, PaymentFailedException;

    public boolean cancelBooking(Passenger passenger, String bookingId) throws InvalidBookingException, PaymentFailedException;
}
