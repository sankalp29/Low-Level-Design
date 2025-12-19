package com.irctcsystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.irctcsystem.constants.JourneyStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude={"coaches", "lock"})
public class TrainJourney {
    private final String journeyId;
    private final Train train;
    private List<Coach> coaches;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private JourneyStatus journeyStatus;
    private final ReentrantReadWriteLock lock;

    public Integer getIndexOf(Station station) {
        return train.getRoute().indexOf(station);
    }

    public void cancelJourney() {
        this.journeyStatus = JourneyStatus.CANCELLED;
    }

    public boolean isBookable() {
        return journeyStatus.equals(JourneyStatus.NOT_STARTED);
    }

    public TrainJourney(String journeyId, Train train, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.journeyId = journeyId;
        this.train = train;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.journeyStatus = JourneyStatus.NOT_STARTED;
        this.coaches = createCoaches(train.getCoachTemplate());
        this.lock = new ReentrantReadWriteLock();
    }

    private List<Coach> createCoaches(List<CoachTemplate> coachTemplates) {
        List<Coach> coachList = new ArrayList<>();
        for (CoachTemplate coachTemplate : coachTemplates) {
            coachList.add(new Coach(coachTemplate.getCoachId(), coachTemplate.getCapacity()));
        }

        return coachList;
    }
}
