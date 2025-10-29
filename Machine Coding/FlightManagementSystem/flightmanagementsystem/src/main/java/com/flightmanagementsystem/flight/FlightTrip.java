package com.flightmanagementsystem.flight;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.NoAvailableSeatException;
import com.flightmanagementsystem.users.Passenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlightTrip {
    private final String tripId;
    private Flight flight;
    private String from;
    private String to;
    private Date departureTime;
    private Integer travelTime;
    private final List<Seat> seats;

    public FlightTrip(Flight flight, String from, String to, Date departureTime, Integer travelTime) {
        this.tripId = UUID.randomUUID().toString();
        this.flight = flight;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.travelTime = travelTime;
        this.seats = new CopyOnWriteArrayList<>(flight.getSeats());
    }

    public synchronized void setPrice(SeatType seatType, double price) {
        for (Seat seat : seats) {
            if (seat.getSeatType() == seatType) {
                seat.setPrice(price);
            }
        }
    }

    public Seat freezeSeat(Passenger passenger, SeatType seatType) throws NoAvailableSeatException {
        // Iterate through all seats of the requested type and try to freeze one
        // The seat's freezeSeat() method handles its own synchronization
        // We don't check status here because it can change between check and freeze (race condition)
        for (Seat seat : seats) {
            if (seat.getSeatType() == seatType) {
                // Let the seat handle its own availability check atomically
                if (seat.freezeSeat(passenger)) {
                    return seat;
                }
            }
        }
        throw new NoAvailableSeatException("No seat is currently available. Either they are booked or are being booked by other users");
    }
}
