package com.irctcsystem.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Seat {
    private final String seatId;
    private final List<Reservation> reservations;

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public boolean isAvailable(Integer from, Integer to) {
        for (Reservation reservation : reservations) {
            if (reservation.overlaps(from, to)) return false;
        }
        return true;
    }

    public Seat(String seatId) {
        this.seatId = seatId;
        this.reservations = new ArrayList<>();
    }
}
