package com.irctcsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.irctcsystem.model.CoachTemplate;
import com.irctcsystem.model.Station;
import com.irctcsystem.model.User;
import com.irctcsystem.service.TrainService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrainController {
    private final TrainService trainService;

    public String createTrain(User user, String name, List<Station> route, List<CoachTemplate> coachTemplate) {
        return trainService.createTrain(user, name, route, coachTemplate);
    }

    public String createTrainJourney(User user, String trainId, LocalDateTime startsAt, LocalDateTime endsAt) {
        return trainService.createTrainJourney(user, trainId, startsAt, endsAt);
    }

    public void cancelJourney(User user, String trainJourneyId) {
        trainService.cancelJourney(user, trainJourneyId);
    }
}
