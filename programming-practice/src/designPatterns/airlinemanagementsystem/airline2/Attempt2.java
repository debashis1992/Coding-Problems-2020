package designPatterns.airlinemanagementsystem.airline2;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Attempt2 {
}


enum FlightStatus {
    OPERATIONAL, NOT_AVAILABLE
}
class Flight {
    int id;
    int fromId, toId;
    long price;
    Set<Seat> seats;
    FlightStatus flightStatus;
}

interface FlightRepo {
    Set<Flight> findAvailableFlightsByFromAndTo(int fromId, int toId);
    Set<Flight> findByFlightId(int flightId);
    void updateStatus(int flightId, FlightStatus flightStatus);
}
class FlightService {
    FlightRepo flightRepo;

    public Set<Flight> searchFlights(int fromId, int toId) {
        return flightRepo.findAvailableFlightsByFromAndTo(fromId, toId);
    }
}

enum SeatStatus {
    AVAILABLE, HOLD, NOT_AVAILABLE
}
class Seat {
    int id;
    SeatStatus seatStatus;
    ReentrantLock lock = new ReentrantLock(true);
}

interface SeatRepo {
    Seat findByFlightIdAndSeatId(int flightId, int seatId);
    void updateStatus(int seatId, SeatStatus seatStatus);
}

class BookingException extends RuntimeException {
    public BookingException(String message) {
        super(message);
    }
}

interface FlightBooking {
    void selectSeats(int flightId, Set<Integer> seatIds);
    void makePayment(UUID bookingId, Payment payment);
    void confirmBooking(UUID bookingId);
    void cancelBooking(UUID bookingId);
}

enum BookingStatus {
    SEAT_SELECTION, CONFIRMED, FAILED
}

enum PaymentStatus {
    PAID, NOT_PAID, EXPIRED
}

class Booking {
    UUID bookingId = UUID.randomUUID();
    long createdTs, maxPaymentTs;
    int flightId;
    Set<Integer> seatIds;
    BookingStatus bookingStatus;
    PaymentStatus paymentStatus;

    public Booking(int flightId, Set<Integer> seatIds, BookingStatus bookingStatus,
                   PaymentStatus paymentStatus) {
        this.flightId = flightId;
        this.seatIds = seatIds;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        createdTs = System.currentTimeMillis();
        int PAYMENT_EXPIRY = 5 * 60 * 1000;
        maxPaymentTs = createdTs + PAYMENT_EXPIRY;
    }
}

class Payment {
    UUID idempotencyKey;
    Map<String, Object> paymentDetails;
    long amount;
    PaymentStatus paymentStatus;
    ReentrantLock lock = new ReentrantLock();
}

interface PaymentRepo {
    Payment findByKey(UUID paymentId);
    void updateStatus(UUID paymentId, PaymentStatus paymentStatus);
}

interface BookingRepo {
    void create(Booking booking);
    Booking findByBookingId(UUID bookingId);
    void updateStatus(UUID bookingId, BookingStatus bookingStatus);
    void updatePaymentStatus(UUID bookingId, PaymentStatus paymentStatus);
}

interface PaymentService {
    void pay(Payment payment);
}

class FlightBookingService implements FlightBooking {

    FlightRepo flightRepo;
    SeatRepo seatRepo;
    BookingRepo bookingRepo;
    PaymentRepo paymentRepo;
    PaymentService paymentService;

    @Override
    public void selectSeats(int flightId, Set<Integer> seatIds) {
        int seatCount = seatIds.size();

        // check all seats are available
        List<Seat> seats = new ArrayList<>();
        int available = 0;
        for(int seatId: seatIds) {
            Seat seat = seatRepo.findByFlightIdAndSeatId(flightId, seatId);
            if(seat == null)
                throw new BookingException("seat cannot be null. seat not found");

            seats.add(seat);
        }

        seats.sort(Comparator.comparingInt(s -> s.id));
        for(Seat seat: seats) seat.lock.lock();

        try {
            //now select all seats to create a booking
            for (Seat seat : seats) {
                if (seat.seatStatus == SeatStatus.AVAILABLE)
                    available++;
            }
            if (available != seatCount) {
                throw new BookingException("not all seats are available for booking, please select " +
                        "seats again");
            }
            for (Seat seat : seats) {
                seatRepo.updateStatus(seat.id, SeatStatus.HOLD);
            }
            Booking booking = new Booking(flightId, seatIds, BookingStatus.SEAT_SELECTION,
                    PaymentStatus.NOT_PAID);
            bookingRepo.create(booking);
        } catch (RuntimeException e) {
            for(Seat seat: seats) {
                seatRepo.updateStatus(seat.id, SeatStatus.AVAILABLE);
            }
        }

        finally {
            for (Seat seat : seats) seat.lock.unlock();
        }
    }


    @Override
    public void makePayment(UUID bookingId, Payment payment) {
        //idempotency check
        if(payment==null || payment.idempotencyKey==null)
            throw new BookingException("payment uuid cannot be null");

        Payment payment1 = paymentRepo.findByKey(payment.idempotencyKey);
        if(payment1!=null) {
            if(payment1.paymentStatus == PaymentStatus.EXPIRED) {
                // cancel booking
                CompletableFuture.runAsync(() -> cancelBooking(bookingId));
                throw new BookingException("payment link is already expired");
            }
            if(payment1.paymentStatus == PaymentStatus.PAID) {
                System.out.println("payment already made, duplicate request!");
                return;
            }
        }

        Booking booking = bookingRepo.findByBookingId(bookingId);
        long now = System.currentTimeMillis();
        if(now > booking.maxPaymentTs) {
            System.out.println("payment time is expired");
            paymentRepo.updateStatus(payment.idempotencyKey, PaymentStatus.EXPIRED);
            bookingRepo.updatePaymentStatus(bookingId, PaymentStatus.EXPIRED);
            // now cancel booking
            CompletableFuture.runAsync(() -> cancelBooking(bookingId));
            return;
        }

        try {
            //make payment
            payment.lock.lock();
            paymentService.pay(payment);
            paymentRepo.updateStatus(payment.idempotencyKey, PaymentStatus.PAID);
            confirmBooking(bookingId);

        } catch (RuntimeException e) {
            throw new BookingException(e.getMessage());
        } finally {
            payment.lock.unlock();
        }

    }

    @Override
    public void confirmBooking(UUID bookingId) {
        Booking booking = bookingRepo.findByBookingId(bookingId);
        if(booking.bookingStatus == BookingStatus.CONFIRMED) {
            System.out.println("already confirmed");
            return;
        }
        bookingRepo.updatePaymentStatus(bookingId, PaymentStatus.PAID);
        bookingRepo.updateStatus(bookingId, BookingStatus.CONFIRMED);
    }

    @Override
    public void cancelBooking(UUID bookingId) {
        // check if booking is already cancel
        Booking booking = bookingRepo.findByBookingId(bookingId);
        if(booking == null)
            throw new BookingException("bookingId not found");

        if(booking.bookingStatus == BookingStatus.FAILED) {
            System.out.println("booking is already in failed status");
            return;
        }
        // reverse if any payment is made
        if(booking.paymentStatus == PaymentStatus.PAID) {
            //reverse the payment
        }

        // release all seats associated with this booking
        int flightId = booking.flightId;
        for(int seatId: booking.seatIds) {
            Seat seat = seatRepo.findByFlightIdAndSeatId(flightId, seatId);
            try {
                seat.lock.lock();
                seatRepo.updateStatus(seatId, SeatStatus.AVAILABLE);
            } finally {
                seat.lock.unlock();
            }
        }

        // update the status correctly for booking
        bookingRepo.updateStatus(bookingId, BookingStatus.FAILED);
    }
}










