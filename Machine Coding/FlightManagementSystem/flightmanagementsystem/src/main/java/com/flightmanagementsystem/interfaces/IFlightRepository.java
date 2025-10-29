package com.flightmanagementsystem.interfaces;

import java.util.List;

import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.flight.Flight;
import com.flightmanagementsystem.flight.FlightTrip;

public interface IFlightRepository {
    public void saveFlight(Flight flight);
    public Flight getFlight(String flightId);
    public void saveTrip(FlightTrip flightTrip);
    public FlightTrip getTrip(String tripId) throws InvalidTripException;
    public List<FlightTrip> getAllTrips();
}
