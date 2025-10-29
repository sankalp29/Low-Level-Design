package com.flightmanagementsystem.interfaces;

import java.util.Date;
import java.util.List;

import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.InvalidAdminException;
import com.flightmanagementsystem.exceptions.InvalidFlightCreationException;
import com.flightmanagementsystem.exceptions.InvalidTripException;
import com.flightmanagementsystem.flight.FlightTrip;
import com.flightmanagementsystem.users.Admin;

public interface IFlightService {
    public String addFlight(Admin admin, String name, String model, Integer economyClassSeats, Integer businessClassSeats, Integer firstClassSeats) throws InvalidFlightCreationException, InvalidFlightCreationException, InvalidAdminException;

    public String addTrip(Admin admin, String flightId, String from, String to, Date departure, Integer travelTime) throws InvalidTripException, InvalidTripException, InvalidTripException, InvalidAdminException;

    public List<String> getFlights(String from, String to, Date date);

    public FlightTrip getTrip(String tripId) throws InvalidTripException;

    public void setPrice(Admin admin, String tripId, SeatType seatType, double price) throws InvalidTripException, InvalidAdminException;
}
