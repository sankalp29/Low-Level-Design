package com.flightmanagementsystem.flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.InvalidAdminException;
import com.flightmanagementsystem.exceptions.InvalidFlightCreationException;
import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.interfaces.IFlightRepository;
import com.flightmanagementsystem.interfaces.IFlightService;
import com.flightmanagementsystem.users.Admin;

public class FlightService implements IFlightService {
    private final IFlightRepository flightRepository;
    
    @Override
    public synchronized String addFlight(Admin admin, String name, String model, Integer economyClassSeats, Integer businessClassSeats, Integer firstClassSeats) throws InvalidFlightCreationException, InvalidFlightCreationException, InvalidAdminException {
        if (admin == null) throw new InvalidAdminException("Invalid admin. Cannot add a flight");
        if (economyClassSeats + businessClassSeats + firstClassSeats <= 0) throw new InvalidFlightCreationException("You cannot create a flight without seats");
        if (name == null || name.isEmpty()) throw new InvalidFlightCreationException("Flight must have a name.");
        
        Flight flight = new Flight(name, model, economyClassSeats, businessClassSeats, firstClassSeats);
        flightRepository.saveFlight(flight);
        return flight.getFlightId();
    }

    @Override
    public synchronized String addTrip(Admin admin, String flightId, String from, String to, Date departure, Integer travelTime) throws InvalidTripException, InvalidTripException, InvalidTripException, InvalidAdminException {
        if (admin == null) throw new InvalidAdminException("Invalid admin. Cannot add a trip");
        if (flightId == null || flightId.isEmpty()) throw new InvalidTripException("A trip cannot be created without a flight.");
        if (from == null || from.isEmpty()) throw new InvalidTripException("A trip needs to have a source.");
        if (to == null || to.isEmpty()) throw new InvalidTripException("A trip needs to have a destination.");

        Flight flight = flightRepository.getFlight(flightId);
        FlightTrip trip = new FlightTrip(flight, from, to, departure, travelTime);
        flightRepository.saveTrip(trip);
        return trip.getTripId();
    }

    @Override
    public List<String> getFlights(String from, String to, Date date) {
        List<FlightTrip> flightTrips = flightRepository.getAllTrips();
        List<String> filteredFlights = new ArrayList<>();
        for (FlightTrip trip : flightTrips) {
            if (trip.getFrom().toLowerCase().equals(from.toLowerCase()) && trip.getTo().toLowerCase().equals(to.toLowerCase())
                    && trip.getDepartureTime().getDate() == date.getDate()) {
                        filteredFlights.add(trip.getTripId());
            }
        }
        return filteredFlights;
    }

    @Override
    public FlightTrip getTrip(String tripId) throws InvalidTripException {
        return flightRepository.getTrip(tripId);
    }

    @Override
    public void setPrice(Admin admin, String tripId, SeatType seatType, double price) throws InvalidTripException, InvalidAdminException {
        if (admin == null) throw new InvalidAdminException("Invalid admin. Cannot add a trip");
        FlightTrip flightTrip = getTrip(tripId);
        flightTrip.setPrice(seatType, price);
    }

    public FlightService() {
        flightRepository = FlightRepository.getInstance();
    }    
}
