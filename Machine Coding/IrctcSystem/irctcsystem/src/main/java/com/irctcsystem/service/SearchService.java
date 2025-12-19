package com.irctcsystem.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.irctcsystem.model.Coach;
import com.irctcsystem.model.Seat;
import com.irctcsystem.model.Station;
import com.irctcsystem.model.TrainJourney;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchService {
    private final TrainService trainService;

    public Map<TrainJourney, Integer> searchTrains(LocalDateTime date, Station source, Station destination) {
        List<TrainJourney> allTrainJourneys = trainService.getAllTrainJourneys();
        List<TrainJourney> matching = allTrainJourneys.stream().filter(journey -> matchesRoute(journey, date, source, destination)).toList();
        Map<TrainJourney, Integer> availableSeats = new HashMap<>();
        for (TrainJourney journey : matching) {
            journey.getLock().readLock().lock();
            try {
                availableSeats.put(journey, getAvailableSeats(journey, source, destination));    
            } finally {
                journey.getLock().readLock().unlock();
            }
        }
        
        return availableSeats;
    }
    
    private Integer getAvailableSeats(TrainJourney trainJourney, Station source, Station destination) {
        Integer availableSeats = 0;
        Integer sourceIdx = trainJourney.getIndexOf(source);
        Integer destIdx = trainJourney.getIndexOf(destination);
        for (Coach coach : trainJourney.getCoaches()) {
            for (Seat seat : coach.getSeats()) {
                if (seat.isAvailable(sourceIdx, destIdx)) {
                    availableSeats++;
                }
            }
        }

        return availableSeats;
    }

    private boolean matchesRoute(TrainJourney trainJourney, LocalDateTime date, Station source, Station destination) {
        if (!trainJourney.getStartsAt().toLocalDate().equals(date.toLocalDate())) return false;

        List<Station> stations = trainJourney.getTrain().getRoute();
        Integer sourceIdx = stations.indexOf(source);
        Integer destIdx = stations.indexOf(destination);

        return (sourceIdx != -1 && destIdx != -1 && sourceIdx < destIdx);
    }
}
