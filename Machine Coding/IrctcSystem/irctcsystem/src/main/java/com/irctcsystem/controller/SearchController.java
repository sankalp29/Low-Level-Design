package com.irctcsystem.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.irctcsystem.model.Station;
import com.irctcsystem.model.TrainJourney;
import com.irctcsystem.service.SearchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchController {
    private final SearchService searchService;

    public Map<TrainJourney, Integer> searchTrains(LocalDateTime date, Station source, Station destination) {
        return searchService.searchTrains(date, source, destination);
    }
}
