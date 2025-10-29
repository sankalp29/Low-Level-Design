package com.flightmanagementsystem.search;

import java.util.Date;
import java.util.List;

public interface ISearchService {
    public List<String> searchFlights(String from, String to, Date date); 
}
