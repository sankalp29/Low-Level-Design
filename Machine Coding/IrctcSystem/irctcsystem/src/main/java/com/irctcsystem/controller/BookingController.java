package com.irctcsystem.controller;

import java.util.List;

import com.irctcsystem.model.Booking;
import com.irctcsystem.model.Station;
import com.irctcsystem.model.User;
import com.irctcsystem.service.BookingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    public Booking reserve(User user, String trainJourneyId, Station source, Station dest, Integer seats) {
        return bookingService.reserve(user, trainJourneyId, source, dest, seats);
    }

    public void confirmBooking(User user, Booking booking) {
        bookingService.confirmBooking(user, booking);
    }

    public void cancelBooking(User user, Booking booking) {
        bookingService.cancelBooking(user, booking);
    }

    public List<Booking> viewBookings(User user) {
        return bookingService.viewBookings(user);
    }
}
