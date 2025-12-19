package com.irctcsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.irctcsystem.model.CoachTemplate;
import com.irctcsystem.model.Station;
import com.irctcsystem.model.Train;
import com.irctcsystem.model.TrainJourney;
import com.irctcsystem.model.User;
import com.irctcsystem.repository.TrainRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrainService {
    private final TrainRepository trainRepository;

    public String createTrain(User user, String name, List<Station> route, List<CoachTemplate> coachTemplate) {
        authorizeAdmin(user);
        Train train = new Train(UUID.randomUUID().toString(), name, route, coachTemplate);
        trainRepository.save(train);

        return train.getTrainId();
    }

    public Optional<Train> getTrainById(String trainId) {
        return trainRepository.getTrainById(trainId);
    }

    public String createTrainJourney(User user, String trainId, LocalDateTime startsAt, LocalDateTime endsAt) {
        authorizeAdmin(user);
        Train train = trainRepository.getTrainById(trainId).orElseThrow(() -> new IllegalArgumentException("Invalid trainId passed"));
        TrainJourney trainJourney = new TrainJourney(UUID.randomUUID().toString(), train, startsAt, endsAt);
        trainRepository.save(trainJourney);
        return trainJourney.getJourneyId();
    }

    public Optional<TrainJourney> getTrainJourneyById(String journeyId) {
        return trainRepository.getTrainJourneyById(journeyId);
    }

    public void cancelJourney(User user, String trainJourneyId) {
        authorizeAdmin(user);
        TrainJourney trainJourney = trainRepository.getTrainJourneyById(trainJourneyId).orElseThrow(() -> new IllegalArgumentException("Invalid journeyId passed"));
        trainJourney.cancelJourney();
        System.out.println(trainJourney + " CANCELLED");
    }

    public List<TrainJourney> getAllTrainJourneys() {
        return trainRepository.getAllTrainJourneys();
    }
    
    private void authorizeAdmin(User user) {
        if (user == null || !user.isAdmin())
            throw new SecurityException("Admin access required to perform operation");
    }
}