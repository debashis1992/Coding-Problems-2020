package designPatterns.airlinemanagementsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AirlineManagementSystemTest {
    public static void main(String[] args) {
        FlightBookingAdmin admin = new FlightBookingAdmin();

        FlightBookingState state = admin.getFlightBookingState();

        UUID flightId = UUID.randomUUID(), userId = UUID.randomUUID();
        Booking booking = state.initiateBooking(flightId, userId, admin.getBookings());

        admin.next();
        Seat seat = new Seat(flightId);
//        state.initiateBooking(flightId, userId, admin.getBookings()); //duplicate booking request

        admin.getFlightBookingState().seatSelection(booking, seat, admin.getBookings());
        admin.getFlightBookingState().seatSelection(booking, seat, admin.getBookings()); //duplicate seat booking here



    }
}

enum FlightStatus {
    AVAILABLE, CANCELLED, NOT_AVAILABLE
}

class Flight {
    private final UUID id;
    private final String source, destination;
    private final LocalDateTime dateTime;
    private final AtomicInteger availableSeats;
    private final List<Seat> seatList;
    private final Double price;
    private FlightStatus status;

    public Flight(String source, String destination, LocalDateTime dateTime, AtomicInteger availableSeats, List<Seat> seatList, Double price) {
        id = UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.dateTime = dateTime;
        this.availableSeats = availableSeats;
        this.seatList = seatList;
        this.price = price;
        this.status = FlightStatus.AVAILABLE;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AtomicInteger getAvailableSeats() {
        return availableSeats;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public Double getPrice() {
        return price;
    }

    public FlightStatus getStatus() {
        return status;
    }
}

enum SeatStatus {
    BOOKED, HOLD, AVAILABLE
}

class Seat {
    private final UUID id;
    private final UUID flightId;
    private SeatStatus status;

    public Seat(UUID flightId) {
        id = UUID.randomUUID();
        this.flightId = flightId;
        this.status = SeatStatus.AVAILABLE;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFlightId() {
        return flightId;
    }

    public SeatStatus getStatus() {
        return status;
    }
}


class User {
    private final UUID userId;
    private String firstName, lastName, email;

    public User(String firstName, String lastName, String email) {
        userId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

enum BookingStatus {
    SUCCESS, PENDING, FAILED
}
enum PaymentStatus {
    PAID, NOT_PAID
}

interface FlightBookingState {
    Booking initiateBooking(UUID flightId, UUID userId, List<Booking> bookings);
    Booking seatSelection(Booking booking, Seat seat, List<Booking> bookings);
    Booking processPayment(Booking booking, List<Booking> bookings);
}
class InitiateBookingState implements FlightBookingState {

    @Override
    public Booking initiateBooking(UUID flightId, UUID userId, List<Booking> bookings) {
        Optional<Booking> optionalBooking = bookings.stream().filter(booking -> booking.getFlightId().equals(flightId) && booking.getUserId().equals(userId)
                && booking.getStatus().equals(BookingStatus.PENDING))
                .findAny();
        if(optionalBooking.isPresent())
            throw new RuntimeException("already an existing booking is present, cannot initiate another booking");

        Booking booking = new Booking(flightId, userId);
        bookings.add(booking);
        return booking;
    }

    @Override
    public Booking seatSelection(Booking booking, Seat seat, List<Booking> bookings) {
        throw new RuntimeException("cannot select seats at this state");
    }

    @Override
    public Booking processPayment(Booking booking, List<Booking> bookings) {
        throw new RuntimeException("cannot process payment at this state");
    }
}
class SeatSelectionState implements FlightBookingState {

    @Override
    public Booking initiateBooking(UUID flightId, UUID userId, List<Booking> bookings) {
        throw new RuntimeException("already booking is initiated");
    }

    @Override
    public Booking seatSelection(Booking booking, Seat seat, List<Booking> bookings) {
        Optional<Booking> optionalBooking = bookings.stream().filter(booking1 -> booking1.getId().equals(booking.getId()))
                .findAny();
        if(optionalBooking.isPresent()) {
            synchronized (seat) {
                UUID seatId = seat.getId();
                Booking alreadyExistingBooking = optionalBooking.get();
                int index = bookings.indexOf(alreadyExistingBooking);
                if (alreadyExistingBooking.getSeatId() != null)
                    throw new RuntimeException("seat is already selected for this booking");
                else alreadyExistingBooking.setSeatId(seatId);

                bookings.set(index, alreadyExistingBooking);
                return alreadyExistingBooking;
            }
        }
        throw new RuntimeException("no booking found for this request");
    }

    @Override
    public Booking processPayment(Booking booking, List<Booking> bookings) {
        throw new RuntimeException("cannot process payment at this state");
    }
}
class PaymentState implements FlightBookingState {
    @Override
    public Booking initiateBooking(UUID flightId, UUID userId, List<Booking> bookings) {
        throw new RuntimeException("cannot initiate booking at this state");
    }

    @Override
    public Booking seatSelection(Booking booking, Seat seat, List<Booking> bookings) {
        throw new RuntimeException("seats should have been already selected at this state.");
    }

    @Override
    public Booking processPayment(Booking booking, List<Booking> bookings) {
        Optional<Booking> bookingOptional = bookings.stream().filter(booking1 -> booking1.getId().equals(booking.getId()))
                .findAny();
        if(bookingOptional.isPresent()) {
            Booking alreadyExistingBooking = bookingOptional.get();
            int index = bookings.indexOf(alreadyExistingBooking);
            try {
                //perform payment operation
                alreadyExistingBooking.setPaymentStatus(PaymentStatus.PAID);
                alreadyExistingBooking.setStatus(BookingStatus.SUCCESS);
            } catch (RuntimeException e) {
                System.out.println("exception occurred during payment: "+e.getMessage());
                alreadyExistingBooking.setPaymentStatus(PaymentStatus.NOT_PAID);
                alreadyExistingBooking.setStatus(BookingStatus.FAILED);
            }
            finally {
                bookings.set(index, alreadyExistingBooking);
            }
        }
        throw new RuntimeException("booking not found");
     }
}

class Booking {
    private final UUID id;
    private final UUID flightId, userId;
    private UUID seatId;
    private BookingStatus status;
    private PaymentStatus paymentStatus;

    public Booking(UUID flightId, UUID userId) {
        id = UUID.randomUUID();
        this.flightId = flightId;
        this.userId = userId;
        this.status = BookingStatus.PENDING;
        this.paymentStatus = PaymentStatus.NOT_PAID;
    }

    public void setSeatId(UUID seatId) {
        this.seatId = seatId;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public UUID getFlightId() {
        return flightId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getSeatId() {
        return seatId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public UUID getId() {
        return id;
    }
}


class FlightBookingAdmin {
    private FlightBookingState flightBookingState;
    private List<Booking> bookings;
    private FlightBookingState[] states;
    public FlightBookingAdmin() {
        states = new FlightBookingState[]{new InitiateBookingState(), new SeatSelectionState(), new PaymentState()};
        flightBookingState = states[0];
        bookings = new ArrayList<>();
    }

    public void setFlightBookingState(FlightBookingState flightBookingState) {
        this.flightBookingState = flightBookingState;
    }

    public FlightBookingState getFlightBookingState() {
        return flightBookingState;
    }

    public void next() {
        if(flightBookingState!=null) {
            int idx = -1;
            for(int i=0;i< states.length;i++) {
                if(flightBookingState == states[i]) {
                    idx=i;
                    break;
                }
            }
            if(idx == states.length - 1)
                flightBookingState = states[0];
            else flightBookingState = states[idx+1];
        }
    }

    public void previous() {
        if(flightBookingState!=null) {
            int idx = -1;
            for(int i=0;i<states.length;i++) {
                if(flightBookingState == states[i]) {
                    idx = i;
                    break;
                }
            }
            if(idx > 0)
                flightBookingState = states[idx-1];
        }
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}