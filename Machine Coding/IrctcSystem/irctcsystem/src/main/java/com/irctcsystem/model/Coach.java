package com.irctcsystem.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Coach {
    private final String coachId;
    private final Integer capacity;
    private final List<Seat> seats;

    public Coach(String coachId, Integer capacity) {
        this.coachId = coachId;
        this.capacity = capacity;
        this.seats = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            seats.add(new Seat(coachId + "-" + (i + 1)));
        }
    }
}
