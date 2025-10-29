package com.flightmanagementsystem.interfaces;

import java.util.List;

import com.flightmanagementsystem.bookings.Booking;

public interface IBookingRepository {
    public void saveBooking(Booking booking);
    public List<Booking> getBookingsByUser(String userId);
    public Booking getBookingById(String bookingId);
}
