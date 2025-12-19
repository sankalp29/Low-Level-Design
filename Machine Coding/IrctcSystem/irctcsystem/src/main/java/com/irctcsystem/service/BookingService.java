package com.irctcsystem.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.irctcsystem.constants.BookingStatus;
import com.irctcsystem.exceptions.AuthorizationException;
import com.irctcsystem.exceptions.BookingException;
import com.irctcsystem.model.Booking;
import com.irctcsystem.model.Reservation;
import com.irctcsystem.model.Seat;
import com.irctcsystem.model.Station;
import com.irctcsystem.model.TrainJourney;
import com.irctcsystem.model.User;
import com.irctcsystem.repository.BookingRepository;
import com.irctcsystem.strategy.SeatAllocationStrategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SeatAllocationStrategy seatAllocationStrategy;
    private final TrainService trainService;

    public Booking reserve(User user, String trainJourneyId, Station source, Station dest, Integer seats) {
        validateUser(user);
        TrainJourney trainJourney = trainService.getTrainJourneyById(trainJourneyId).orElseThrow(() -> new IllegalArgumentException("Invalid journeyId passed."));
        if (!trainJourney.isBookable()) throw new BookingException("Bookings cannot be made to this Train journey anymore");
        Integer sourceIdx = trainJourney.getIndexOf(source);
        Integer destIdx = trainJourney.getIndexOf(dest);
        trainJourney.getLock().writeLock().lock();
        Booking booking = null;
        try {
            List<Seat> allocated = seatAllocationStrategy.allocateSeats(trainJourney, sourceIdx, destIdx, seats);
            if (allocated.size() < seats) throw new BookingException("Enough seats not available");
            
            for (Seat seat : allocated) {
                Reservation reservation = new Reservation(user.getId(), sourceIdx, destIdx);
                seat.addReservation(reservation);
            }
            
            booking = new Booking(UUID.randomUUID().toString(), trainJourney, user.getId(), allocated, sourceIdx, destIdx);
            bookingRepository.save(booking);

            return booking;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            trainJourney.getLock().writeLock().unlock();
        }
        
        return booking;
    }

    public void confirmBooking(User user, Booking booking) {
        validateUser(user);
        if (booking == null) throw new IllegalArgumentException("Invalid booking passed. ");
        if (!booking.isBookedBy(user.getId())) throw new AuthorizationException("User not authorized to confirm booking");
        if (booking.isConfirmed()) return; // Idempotent
        
        TrainJourney trainJourney = booking.getTrainJourney();
        trainJourney.getLock().writeLock().lock();
        try {
            for (Seat seat : booking.getSeats()) {
                for (Reservation reservation : seat.getReservations()) {
                    if (reservation.getUserId().equals(user.getId()) 
                        && reservation.getSourceIdx().equals(booking.getSourceIdx())
                        && reservation.getDestIdx().equals(booking.getDestIdx())) {
                            reservation.confirmBooking();
                    }
                }
            }
            booking.confirmBooking();
        }
        finally {
            trainJourney.getLock().writeLock().unlock();
        }
    }

    public void cancelBooking(User user, Booking booking) {
        validateUser(user);
        if (booking == null) throw new IllegalArgumentException("Invalid booking passed. ");
        if (!booking.isBookedBy(user.getId())) throw new AuthorizationException("User not authorized to cancel booking");
        if (!booking.isConfirmed()) throw new BookingException("Cannot cancel a draft booking.");
        if (booking.getBookingStatus().equals(BookingStatus.CANCELLED)) return;

        booking.cancelBooking();
        releaseSeats(booking);
        bookingRepository.cancelBooking(booking.getBookingId());        
        System.out.println(booking + " CANCELLED\n");
    }

    public Booking modifyBooking(User user, Booking booking, Station newSource, Station newDestination) {
        validateUser(user);
        if (booking == null) throw new IllegalArgumentException("Invalid booking passed");
        if (!booking.isBookedBy(user.getId())) throw new AuthorizationException("User not authorized to modify booking");
        if (!booking.isConfirmed()) throw new BookingException("Only confirmed bookings can be modified");

        TrainJourney journey = booking.getTrainJourney();

        Integer newSourceIdx = journey.getIndexOf(newSource);
        Integer newDestIdx = journey.getIndexOf(newDestination);

        if (newSourceIdx == -1 || newDestIdx == -1 || newSourceIdx >= newDestIdx) throw new BookingException("Invalid new journey segment");

        journey.getLock().writeLock().lock();
        try {
            int seatCount = booking.getSeats().size();
            // STEP 1: Temporarily remove old reservations
            
            List<Seat> oldSeats = booking.getSeats();

            for (Seat seat : oldSeats) {
                Iterator<Reservation> iterator = seat.getReservations().iterator();
                while (iterator.hasNext()) {
                    Reservation r = iterator.next();
                    if (matchesBooking(r, booking)) {
                        iterator.remove();
                    }
                }
            }

            // STEP 2: Try allocating seats for new segment
            List<Seat> newSeats = seatAllocationStrategy.allocateSeats(journey, newSourceIdx, newDestIdx, seatCount);

            if (newSeats.size() < seatCount) {
                // rollback old reservations
                for (Seat seat : oldSeats) {
                    seat.addReservation(new Reservation(booking.getUserId(), booking.getSourceIdx(), booking.getDestIdx()));
                }
                throw new BookingException("Seats not available for new segment");
            }

            // STEP 3: Commit new reservations
            for (Seat seat : newSeats) {
                seat.addReservation(new Reservation(booking.getUserId(), newSourceIdx, newDestIdx));
            }

            Booking newBooking = new Booking(UUID.randomUUID().toString(), journey, user.getId(), newSeats, newSourceIdx, newDestIdx);
            newBooking.confirmBooking();

            booking.cancelBooking();
            bookingRepository.cancelBooking(booking.getBookingId());
            bookingRepository.save(newBooking);

            return newBooking;
        } finally {
            journey.getLock().writeLock().unlock();
        }
    }

    public List<Booking> viewBookings(User user) {
        validateUser(user);
        return bookingRepository.getByUser(user.getId());
    }

    private boolean matchesBooking(Reservation r, Booking booking) {
        return r.getUserId().equals(booking.getUserId())
            && r.getSourceIdx().equals(booking.getSourceIdx())
            && r.getDestIdx().equals(booking.getDestIdx());
    }

    private void releaseSeats(Booking booking) {
        TrainJourney trainJourney = booking.getTrainJourney();
        String userId = booking.getUserId();
        List<Seat> seats = booking.getSeats();
        trainJourney.getLock().writeLock().lock();
        try {
            for (Seat seat : seats) {
                Iterator<Reservation> iterator = seat.getReservations().iterator();
                while (iterator.hasNext()) {
                    Reservation reservation = iterator.next();
                    if (reservation.getUserId().equals(userId) 
                        && reservation.getSourceIdx().equals(booking.getSourceIdx())
                        && reservation.getDestIdx().equals(booking.getDestIdx())) {
                        iterator.remove();
                    }
                }
            }
        }
        finally {
            trainJourney.getLock().writeLock().unlock();
        }
    }

    private void validateUser(User user) {
        if (user == null) throw new IllegalArgumentException("Not a valid user.");
    }
}