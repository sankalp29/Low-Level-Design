package com.flightmanagementsystem.flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.interfaces.IFlightRepository;

public class FlightRepository implements IFlightRepository {
    private final Map<String, Flight> flights;
    private final Map<String, FlightTrip> trips;
    private static volatile FlightRepository instance;

    @Override
    public void saveFlight(Flight flight) {
        flights.put(flight.getFlightId(), flight);
    }

    @Override
    public Flight getFlight(String flightId) {
        return flights.getOrDefault(flightId, null);
    }

    @Override
    public void saveTrip(FlightTrip flightTrip) {
        trips.put(flightTrip.getTripId(), flightTrip);
    }

    @Override
    public FlightTrip getTrip(String tripId) throws InvalidTripException {
        if (!trips.containsKey(tripId)) throw new InvalidTripException("Trip with ID : " + tripId + " does not exist");
        return trips.get(tripId);
    }

    @Override
    public List<FlightTrip> getAllTrips() {
        // Use values() directly to avoid race condition with keySet iteration
        return new ArrayList<>(trips.values());
    }

    /**
     * Thread-safe singleton using double-checked locking with volatile
     */
    public static FlightRepository getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (FlightRepository.class) {
                if (instance == null) { // Second check (with locking)
                    instance = new FlightRepository();
                }
            }
        }
        return instance;
    }

    private FlightRepository() {
        flights = new ConcurrentHashMap<>();
        trips = new ConcurrentHashMap<>();
    }
}
