package com.flightmanagementsystem.flight;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.flightmanagementsystem.enums.SeatStatus;
import com.flightmanagementsystem.enums.SeatType;
import com.flightmanagementsystem.exceptions.SeatAlreadyBookedException;
import com.flightmanagementsystem.exceptions.UnreservedSeatBookingAttemptException;
import com.flightmanagementsystem.users.Passenger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Seat {
    private final String seatNo;
    private final SeatType seatType;
    private volatile SeatStatus seatStatus;
    private volatile Passenger bookedBy;
    @Setter
    private volatile Double price;
    private final ReentrantLock lock;
    private static final Integer TIMEOUT = 5;
    private volatile Long expiryTime;
    private volatile ScheduledFuture<?> scheduledRelease;
    
    // Shared scheduler for all seats to avoid resource leak
    private static final ScheduledExecutorService SHARED_SCHEDULER = 
        Executors.newScheduledThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors()),
            r -> {
                Thread t = new Thread(r);
                t.setDaemon(true); // Daemon thread so it doesn't prevent JVM shutdown
                t.setName("Seat-Release-Scheduler");
                return t;
            }
        );

    public Seat(String seatNo, SeatType seatType, Double price) {
        this.seatNo = seatNo;
        this.seatType = seatType;
        this.seatStatus = SeatStatus.AVAILABLE;
        this.price = price;
        this.lock = new ReentrantLock(true);
        this.expiryTime = 0L;
        this.scheduledRelease = null;
    }

    public boolean freezeSeat(Passenger passenger) {
        boolean lockAcquired = false;
        try {
            lockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
            if (!lockAcquired) {
                return false; // Could not acquire lock, seat likely being processed
            }
            
            if (seatStatus == SeatStatus.AVAILABLE) {
                seatStatus = SeatStatus.BOOKING_IN_PROCESS;
                System.out.println("Seat frozen for " + passenger + "\n");
                bookedBy = passenger;
                expiryTime = System.currentTimeMillis() + (TIMEOUT * 1000L);
                System.out.println("Seat locked for " + passenger + " till " + expiryTime + "\n");

                // Cancel any existing scheduled release
                if (scheduledRelease != null && !scheduledRelease.isDone()) {
                    scheduledRelease.cancel(false);
                }
                
                // Schedule automatic release after timeout
                scheduledRelease = SHARED_SCHEDULER.schedule(this::autoReleaseSeat, TIMEOUT, TimeUnit.SECONDS);
                return true;
            } 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();            
        } finally {
            if (lockAcquired) {
                lock.unlock();
            }
        }
        
        return false;
    }

    /**
     * Auto-release seat after timeout (called by scheduler)
     */
    private void autoReleaseSeat() {
        lock.lock();
        try {
            // Only release if still in BOOKING_IN_PROCESS state and not yet booked
            if (seatStatus == SeatStatus.BOOKING_IN_PROCESS) {
                System.out.println("Auto-releasing seat " + seatNo + " after timeout" + "\n");
                releaseSeatInternal();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Manually release seat (called when payment fails or booking is cancelled)
     * This is thread-safe and synchronized
     */
    public void releaseSeat() {
        lock.lock();
        try {
            // Cancel any scheduled release
            if (scheduledRelease != null && !scheduledRelease.isDone()) {
                scheduledRelease.cancel(false);
            }
            releaseSeatInternal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Internal method to release seat - must be called with lock held
     */
    private void releaseSeatInternal() {
        seatStatus = SeatStatus.AVAILABLE;
        bookedBy = null;
        expiryTime = 0L;
        scheduledRelease = null;
    }

    public boolean bookSeat(Passenger passenger) throws UnreservedSeatBookingAttemptException, SeatAlreadyBookedException {
        lock.lock();
        try {
            if (seatStatus == SeatStatus.BOOKED) {
                throw new SeatAlreadyBookedException("Cannot book already booked seat.");
            }
            if (seatStatus != SeatStatus.BOOKING_IN_PROCESS || !bookedBy.equals(passenger)) {
                throw new UnreservedSeatBookingAttemptException("The seat is not reserved by the user to be booked.");
            }
            
            // Cancel the scheduled release since booking is successful
            if (scheduledRelease != null && !scheduledRelease.isDone()) {
                scheduledRelease.cancel(false);
            }
        
            this.bookedBy = passenger;
            seatStatus = SeatStatus.BOOKED;
            System.out.println("Seat booked by " + passenger + "\n");
            return true;
            
        } catch (UnreservedSeatBookingAttemptException | SeatAlreadyBookedException e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
        return false;
    }
    
    /**
     * Shutdown the shared scheduler - call this when application is shutting down
     */
    public static void shutdownScheduler() {
        SHARED_SCHEDULER.shutdown();
        try {
            if (!SHARED_SCHEDULER.awaitTermination(5, TimeUnit.SECONDS)) {
                SHARED_SCHEDULER.shutdownNow();
            }
        } catch (InterruptedException e) {
            SHARED_SCHEDULER.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
