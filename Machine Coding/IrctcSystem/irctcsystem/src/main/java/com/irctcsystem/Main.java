package com.irctcsystem;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.irctcsystem.constants.Role;
import com.irctcsystem.controller.BookingController;
import com.irctcsystem.controller.TrainController;
import com.irctcsystem.controller.UserController;
import com.irctcsystem.model.Booking;
import com.irctcsystem.model.CoachTemplate;
import com.irctcsystem.model.Station;
import com.irctcsystem.model.User;
import com.irctcsystem.repository.BookingRepository;
import com.irctcsystem.repository.TrainRepository;
import com.irctcsystem.repository.UserRepository;
import com.irctcsystem.service.BookingService;
import com.irctcsystem.service.TrainService;
import com.irctcsystem.service.UserService;
import com.irctcsystem.strategy.FirstAvailableSeatStrategy;

public class Main {
    public static void main(String[] args) {
        testSameStationDeboardAndOnboard();
    }

    private static void testConcurrentNonCompetingBooking() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        TrainRepository trainRepository = new TrainRepository();
        TrainService trainService = new TrainService(trainRepository);
        TrainController trainController = new TrainController(trainService);
        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, new FirstAvailableSeatStrategy(), trainService);
        BookingController bookingController = new BookingController(bookingService);
    
        User admin = userController.createUser("admin", "admin@email", Role.ADMIN);
        User user1 = userController.createUser("user1", "user1@email", Role.CUSTOMER);
        User user2 = userController.createUser("user2", "user2@email", Role.CUSTOMER);

        Station station1 = new Station("S1", "CSMT");
        Station station2 = new Station("S2", "Dadar");
        Station station3 = new Station("S3", "Ghatkopar");
        Station station4 = new Station("S4", "Thane");
        Station station5 = new Station("S5", "Kasara");
        List<Station> stations = Arrays.asList(station1, station2, station3, station4, station5);
        String train1 = trainController.createTrain(admin, "Rajdhani", stations, Arrays.asList(new CoachTemplate("A1", 1)));
        String journey1 = trainController.createTrainJourney(admin, train1, LocalDateTime.now(), LocalDateTime.now());
        
        Booking booking1 = bookingController.reserve(user1, journey1, station1, station3, 1);
        System.out.println("Booking1 = " + booking1);
        System.out.println();

        bookingController.confirmBooking(user1, booking1);
        System.out.println(booking1);
        System.out.println();

        Booking booking2 = bookingController.reserve(user2, journey1, station4, station5, 1);
        System.out.println("Booking2 = " + booking2);
        System.out.println();

        bookingController.confirmBooking(user2, booking2);
        System.out.println(booking2);
    }

    private static void testConcurrentBooking() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        TrainRepository trainRepository = new TrainRepository();
        TrainService trainService = new TrainService(trainRepository);
        TrainController trainController = new TrainController(trainService);
        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, new FirstAvailableSeatStrategy(), trainService);
        BookingController bookingController = new BookingController(bookingService);
    
        User admin = userController.createUser("admin", "admin@email", Role.ADMIN);
        User user1 = userController.createUser("user1", "user1@email", Role.CUSTOMER);
        User user2 = userController.createUser("user2", "user2@email", Role.CUSTOMER);

        Station station1 = new Station("S1", "CSMT");
        Station station2 = new Station("S2", "Dadar");
        Station station3 = new Station("S3", "Ghatkopar");
        Station station4 = new Station("S4", "Thane");
        Station station5 = new Station("S5", "Kasara");
        List<Station> stations = Arrays.asList(station1, station2, station3, station4, station5);
        String train1 = trainController.createTrain(admin, "Rajdhani", stations, Arrays.asList(new CoachTemplate("A1", 1)));
        String journey1 = trainController.createTrainJourney(admin, train1, LocalDateTime.now(), LocalDateTime.now());
        
        Booking booking1 = bookingController.reserve(user1, journey1, station1, station3, 1);
        System.out.println(booking1);
        System.out.println();

        bookingController.confirmBooking(user1, booking1);
        System.out.println(booking1);
        System.out.println();

        Booking booking2 = bookingController.reserve(user2, journey1, station1, station3, 1);
        System.out.println("Booking2 = " + booking2);
        System.out.println();

        bookingController.confirmBooking(user2, booking2);
        System.out.println(booking2);
    }

    private static void testIntervalBooking() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        TrainRepository trainRepository = new TrainRepository();
        TrainService trainService = new TrainService(trainRepository);
        TrainController trainController = new TrainController(trainService);
        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, new FirstAvailableSeatStrategy(), trainService);
        BookingController bookingController = new BookingController(bookingService);
    
        User admin = userController.createUser("admin", "admin@email", Role.ADMIN);
        User user1 = userController.createUser("user1", "user1@email", Role.CUSTOMER);
        User user2 = userController.createUser("user2", "user2@email", Role.CUSTOMER);

        Station station1 = new Station("S1", "CSMT");
        Station station2 = new Station("S2", "Dadar");
        Station station3 = new Station("S3", "Ghatkopar");
        Station station4 = new Station("S4", "Thane");
        Station station5 = new Station("S5", "Kasara");
        List<Station> stations = Arrays.asList(station1, station2, station3, station4, station5);
        String train1 = trainController.createTrain(admin, "Rajdhani", stations, Arrays.asList(new CoachTemplate("A1", 1)));
        String journey1 = trainController.createTrainJourney(admin, train1, LocalDateTime.now(), LocalDateTime.now());
        
        Booking booking1 = bookingController.reserve(user1, journey1, station1, station3, 1);
        System.out.println(booking1);
        System.out.println();

        bookingController.confirmBooking(user1, booking1);
        System.out.println(booking1);
        System.out.println();

        Booking booking2 = bookingController.reserve(user2, journey1, station4, station5, 1);
        System.out.println(booking2);
        System.out.println();

        bookingController.confirmBooking(user2, booking2);
        System.out.println(booking2);
    }

    private static void testIdempotency() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        TrainRepository trainRepository = new TrainRepository();
        TrainService trainService = new TrainService(trainRepository);
        TrainController trainController = new TrainController(trainService);
        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, new FirstAvailableSeatStrategy(), trainService);
        BookingController bookingController = new BookingController(bookingService);
    
        User admin = userController.createUser("admin", "admin@email", Role.ADMIN);
        User user1 = userController.createUser("user1", "user1@email", Role.CUSTOMER);
        User user2 = userController.createUser("user2", "user2@email", Role.CUSTOMER);

        Station station1 = new Station("S1", "CSMT");
        Station station2 = new Station("S2", "Dadar");
        Station station3 = new Station("S3", "Ghatkopar");
        Station station4 = new Station("S4", "Thane");
        Station station5 = new Station("S5", "Kasara");
        List<Station> stations = Arrays.asList(station1, station2, station3, station4, station5);
        String train1 = trainController.createTrain(admin, "Rajdhani", stations, Arrays.asList(new CoachTemplate("A1", 1)));
        String journey1 = trainController.createTrainJourney(admin, train1, LocalDateTime.now(), LocalDateTime.now());
        
        Booking booking = bookingController.reserve(user1, journey1, station1, station3, 1);
        System.out.println(booking);
        System.out.println();

        bookingController.confirmBooking(user1, booking);
        System.out.println(booking);
        System.out.println();
        
        bookingController.confirmBooking(user1, booking);
    }

    private static void testHappyFlow() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        TrainRepository trainRepository = new TrainRepository();
        TrainService trainService = new TrainService(trainRepository);
        TrainController trainController = new TrainController(trainService);
        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, new FirstAvailableSeatStrategy(), trainService);
        BookingController bookingController = new BookingController(bookingService);
    
        User admin = userController.createUser("admin", "admin@email", Role.ADMIN);
        User user1 = userController.createUser("user1", "user1@email", Role.CUSTOMER);
        User user2 = userController.createUser("user2", "user2@email", Role.CUSTOMER);

        Station station1 = new Station("S1", "CSMT");
        Station station2 = new Station("S2", "Dadar");
        Station station3 = new Station("S3", "Ghatkopar");
        Station station4 = new Station("S4", "Thane");
        Station station5 = new Station("S5", "Kasara");
        List<Station> stations = Arrays.asList(station1, station2, station3, station4, station5);
        String train1 = trainController.createTrain(admin, "Rajdhani", stations, Arrays.asList(new CoachTemplate("A1", 1)));
        String journey1 = trainController.createTrainJourney(admin, train1, LocalDateTime.now(), LocalDateTime.now());
        
        Booking booking = bookingController.reserve(user1, journey1, station1, station3, 1);
        System.out.println(booking);
        System.out.println();

        bookingController.confirmBooking(user1, booking);
        System.out.println(booking);
    }

    private static void testSameStationDeboardAndOnboard() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        TrainRepository trainRepository = new TrainRepository();
        TrainService trainService = new TrainService(trainRepository);
        TrainController trainController = new TrainController(trainService);
        BookingRepository bookingRepository = new BookingRepository();
        BookingService bookingService = new BookingService(bookingRepository, new FirstAvailableSeatStrategy(), trainService);
        BookingController bookingController = new BookingController(bookingService);
    
        User admin = userController.createUser("admin", "admin@email", Role.ADMIN);
        User user1 = userController.createUser("user1", "user1@email", Role.CUSTOMER);
        User user2 = userController.createUser("user2", "user2@email", Role.CUSTOMER);

        Station station1 = new Station("S1", "CSMT");
        Station station2 = new Station("S2", "Dadar");
        Station station3 = new Station("S3", "Ghatkopar");
        Station station4 = new Station("S4", "Thane");
        Station station5 = new Station("S5", "Kasara");
        List<Station> stations = Arrays.asList(station1, station2, station3, station4, station5);
        String train1 = trainController.createTrain(admin, "Rajdhani", stations, Arrays.asList(new CoachTemplate("A1", 1)));
        String journey1 = trainController.createTrainJourney(admin, train1, LocalDateTime.now(), LocalDateTime.now());
        
        Booking booking1 = bookingController.reserve(user1, journey1, station1, station3, 1);
        System.out.println("Booking1 = " + booking1);
        System.out.println();

        bookingController.confirmBooking(user1, booking1);
        System.out.println(booking1);
        System.out.println();

        Booking booking2 = bookingController.reserve(user2, journey1, station3, station5, 1);
        System.out.println("Booking2 = " + booking2);
        System.out.println();

        bookingController.confirmBooking(user2, booking2);
        System.out.println(booking2);
    }
}