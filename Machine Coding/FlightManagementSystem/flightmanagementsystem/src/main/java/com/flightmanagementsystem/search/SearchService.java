package com.flightmanagementsystem.search;

import java.util.Date;
import java.util.List;

import com.flightmanagementsystem.flight.FlightService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchService implements ISearchService {
    private final FlightService flightService;

    @Override
    public List<String> searchFlights(String from, String to, Date date) {
        return flightService.getFlights(from, to, date);
    }

}
