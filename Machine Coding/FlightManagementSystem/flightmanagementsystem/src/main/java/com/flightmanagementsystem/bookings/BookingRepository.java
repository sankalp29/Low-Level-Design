package com.flightmanagementsystem.bookings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.flightmanagementsystem.interfaces.IBookingRepository;

public class BookingRepository implements IBookingRepository {
    private final Map<String, Booking> bookings;
    private final Map<String, List<Booking>> userBookings;
    private static volatile BookingRepository instance;

    @Override
    public void saveBooking(Booking booking) {
        userBookings.computeIfAbsent(booking.getPassenger().getUserId(), list -> new CopyOnWriteArrayList<>()).add(booking);
        bookings.put(booking.getBookingId(), booking);
    }

    @Override
    public List<Booking> getBookingsByUser(String userId) {
        return new ArrayList<>(userBookings.getOrDefault(userId, new ArrayList<>()));
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookings.getOrDefault(bookingId, null);
    }
    
    /**
     * Thread-safe singleton using double-checked locking with volatile
     */
    public static BookingRepository getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (BookingRepository.class) {
                if (instance == null) { // Second check (with locking)
                    instance = new BookingRepository();
                }
            }
        }
        return instance;
    }

    private BookingRepository() {
        userBookings = new ConcurrentHashMap<>();
        bookings = new ConcurrentHashMap<>();
    }
}
