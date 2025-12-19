package com.irctcsystem.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.irctcsystem.model.Booking;

public class BookingRepository {
    private final Map<String, Booking> bookings;

    public void save(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    public Optional<Booking> getById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public void cancelBooking(String bookingId) {
        bookings.remove(bookingId);
    }

    public List<Booking> getByUser(String userId) {
        return bookings.values().stream().filter(booking -> booking.isBookedBy(userId)).toList();
    }

    public BookingRepository() {
        this.bookings = new HashMap<>();
    }
}
