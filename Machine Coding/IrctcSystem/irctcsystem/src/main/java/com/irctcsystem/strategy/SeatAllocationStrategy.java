package com.irctcsystem.strategy;

import java.util.List;

import com.irctcsystem.model.Seat;
import com.irctcsystem.model.TrainJourney;

public interface SeatAllocationStrategy {
    List<Seat> allocateSeats(TrainJourney trainJourney, Integer sourceIdx, Integer destIdx, Integer seats);
}
