package com.irctcsystem.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.irctcsystem.model.Train;
import com.irctcsystem.model.TrainJourney;

public class TrainRepository {
    private final Map<String, Train> trains;
    private final Map<String, TrainJourney> trainJourneys;

    public void save(Train train) {
        trains.put(train.getTrainId(), train);
    }

    public Optional<Train> getTrainById(String trainId) {
        return Optional.ofNullable(trains.get(trainId));
    }

    public void save(TrainJourney trainJourney) {
       trainJourneys.put(trainJourney.getJourneyId(), trainJourney);
    }

    public Optional<TrainJourney> getTrainJourneyById(String journeyId) {
        return Optional.ofNullable(trainJourneys.get(journeyId));
    }

    public List<TrainJourney> getAllTrainJourneys() {
        return trainJourneys.values().stream().toList();
    }

    public TrainRepository() {
        this.trains = new ConcurrentHashMap<>();
        this.trainJourneys = new ConcurrentHashMap<>();
    }
}
