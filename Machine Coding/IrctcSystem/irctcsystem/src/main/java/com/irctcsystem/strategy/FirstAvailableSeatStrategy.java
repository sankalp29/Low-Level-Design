package com.irctcsystem.strategy;

import java.util.ArrayList;
import java.util.List;

import com.irctcsystem.model.Coach;
import com.irctcsystem.model.Seat;
import com.irctcsystem.model.TrainJourney;

public class FirstAvailableSeatStrategy implements SeatAllocationStrategy {

    @Override
    public List<Seat> allocateSeats(TrainJourney trainJourney, Integer sourceIdx, Integer destIdx, Integer seats) {
        List<Seat> allocatedSeats = new ArrayList<>();
        for (Coach coach : trainJourney.getCoaches()) {
            for (Seat seat : coach.getSeats()) {
                if (seat.isAvailable(sourceIdx, destIdx)) {
                    allocatedSeats.add(seat);
                    if (allocatedSeats.size() == seats) return allocatedSeats;
                }
            }
        }

        return allocatedSeats;
    }
}
