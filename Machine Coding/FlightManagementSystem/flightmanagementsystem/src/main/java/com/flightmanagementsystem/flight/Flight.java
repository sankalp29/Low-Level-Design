package com.flightmanagementsystem.flight;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import com.flightmanagementsystem.enums.SeatType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Flight {
    private final String flightId;
    private final String name;
    private final String model;
    private final List<Seat> seats;
    
    public Flight(String name, String model, Integer economyClassSeats, Integer businessClassSeats, Integer firstClassSeats) {
        this.flightId = UUID.randomUUID().toString();
        this.name = name;
        this.model = model;
        this.seats = new CopyOnWriteArrayList<>();

        for (int i = 0; i < economyClassSeats; i++) {
            seats.add(new Seat("EC-" + (i+1), SeatType.ECONOMY, 0.0));
        }

        for (int i = 0; i < businessClassSeats; i++) {
            seats.add(new Seat("BS-" + (i+1), SeatType.BUSINESS, 0.0));
        }

        for (int i = 0; i < firstClassSeats; i++) {
            seats.add(new Seat("FC-" + (i+1), SeatType.FIRST, 0.0));
        }
    }
}
