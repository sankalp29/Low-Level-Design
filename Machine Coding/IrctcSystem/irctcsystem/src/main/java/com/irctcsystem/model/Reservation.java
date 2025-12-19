package com.irctcsystem.model;

import com.irctcsystem.constants.BookingStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class Reservation {
    private final String userId;
    private final Integer sourceIdx;
    private final Integer destIdx;
    private BookingStatus bookingStatus;

    public boolean overlaps(Integer fromIdx, Integer toIdx) {
        return !(toIdx < sourceIdx || fromIdx >= destIdx);
    }

    public void confirmBooking() {
        bookingStatus = BookingStatus.COMPLETED;
    }

    public Reservation(String userId, Integer sourceIdx, Integer destIdx) {
        this.userId = userId;
        this.sourceIdx = sourceIdx;
        this.destIdx = destIdx;
        this.bookingStatus = BookingStatus.LOCKED;
    }
}
