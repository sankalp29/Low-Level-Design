package com.irctcsystem.model;

import java.time.LocalDateTime;
import java.util.List;

import com.irctcsystem.constants.BookingStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Booking {
    private final String bookingId;
    private final String userId;
    private final TrainJourney trainJourney;
    private final List<Seat> seats;
    private final Integer sourceIdx;
    private final Integer destIdx;
    private final LocalDateTime bookedAt;
    private LocalDateTime lastUpdatedAt;
    private BookingStatus bookingStatus;

    public void confirmBooking() {
        bookingStatus = BookingStatus.COMPLETED;
        updateLastUpdated();
    }

    public void cancelBooking() {
        bookingStatus = BookingStatus.CANCELLED;
        updateLastUpdated();
    }

    public boolean isConfirmed() {
        return bookingStatus.equals(BookingStatus.COMPLETED);
    }

    public boolean isBookedBy(String userId) {
        return this.userId != null && this.userId.equals(userId);
    }

    private void updateLastUpdated() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Booking(String bookingId, TrainJourney trainJourney, String userId, List<Seat> seats, Integer sourceIdx, Integer destIdx) {
        this.bookingId = bookingId;
        this.trainJourney = trainJourney;
        this.userId = userId;
        this.seats = seats;
        this.sourceIdx = sourceIdx;
        this.destIdx = destIdx;
        this.bookedAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
        this.bookingStatus = BookingStatus.LOCKED;
    }
}
