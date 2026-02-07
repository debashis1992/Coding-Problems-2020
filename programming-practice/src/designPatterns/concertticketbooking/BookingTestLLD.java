package designPatterns.concertticketbooking;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookingTestLLD {
}


/**
 * Event
 * Seat
 *
 * Booking
 * Payment
 *
 * User
 *
 *
 */

class User {
    UUID userId;
}

enum EventType {
    CONCERT, MOVIE, SHOW
}

abstract class Event {
    UUID eventId;
    String name, description;
    Date startTs, endTs;
    AtomicLong maxCapacity;
    EventType eventType;

    public Event(String name, String description, Date startTs, Date endTs, AtomicLong maxCapacity) {
        this.name = name;
        this.description = description;
        this.startTs = startTs;
        this.endTs = endTs;
        this.maxCapacity = maxCapacity;
    }
}

interface EventRepo {
    Event create(String name, String description, Date start, Date end, AtomicLong capacity, EventType eventType);
    Set<Event> getEvents();
    Set<Event> searchEvents(String name, Date start, Date end, EventType eventType);
}

class ConcertEvent extends Event {
    public ConcertEvent(String name, String description, Date startTs, Date endTs, AtomicLong maxCapacity) {
        super(name, description, startTs, endTs, maxCapacity);
        eventType = EventType.CONCERT;
    }
}

class MovieEvent extends Event {
    public MovieEvent(String name, String description, Date startTs, Date endTs, AtomicLong maxCapacity) {
        super(name, description, startTs, endTs, maxCapacity);
        eventType = EventType.MOVIE;
    }
}

enum SeatStatus { BOOKED, PENDING, AVAILABLE }

class Seat {
    final UUID seatId = UUID.randomUUID();
    volatile SeatStatus status;
    final ReentrantLock lock = new ReentrantLock();

    public Seat(SeatStatus status) {
        this.status = status;
    }
}


interface SeatRepo {
    void initialize(UUID eventId, int capacity);
    Set<Seat> getAvailableSeats(UUID eventId);
    boolean bookSeat(UUID eventId, UUID seatId);
    boolean cancelSeat(UUID eventId, UUID seatId);
    boolean confirm(UUID eventId, UUID seatId);
}

class SeatRepository implements SeatRepo {
    private final Map<UUID, Set<Seat>> seatsDb = new ConcurrentHashMap<>();

    @Override
    public void initialize(UUID eventId, int capacity) {
        synchronized (eventId) {
            Set<Seat> newAllocation = ConcurrentHashMap.newKeySet(capacity);
            for(int i=0;i<capacity;i++) {
                newAllocation.add(new Seat(SeatStatus.AVAILABLE));
            }
            seatsDb.putIfAbsent(eventId, newAllocation);
        }
    }

    @Override
    public Set<Seat> getAvailableSeats(UUID eventId) {
        return seatsDb.getOrDefault(eventId, ConcurrentHashMap.newKeySet())
                .stream().filter(seat -> seat.status.equals(SeatStatus.AVAILABLE))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean bookSeat(UUID eventId, UUID seatId) {
        if(seatsDb.containsKey(eventId)) {
                Set<Seat> totalSeats = seatsDb.get(eventId);
                Optional<Seat> optionalSeat =
                        totalSeats.stream()
                                .filter(s ->s.seatId.equals(seatId))
                                .findAny();

                if(optionalSeat.isPresent()) {
                    Seat seat = optionalSeat.get();
                    try {
                        seat.lock.lock();
                        if(seat.status.equals(SeatStatus.AVAILABLE)) {
                            seat.status = SeatStatus.PENDING;
                            return true;
                        }
                    } finally {
                        seat.lock.unlock();
                    }

                }
        }
        return false;

    }

    @Override
    public boolean cancelSeat(UUID eventId, UUID seatId) {
        if(seatsDb.containsKey(eventId)) {
                Set<Seat> totalSeats = seatsDb.get(eventId);
                Optional<Seat> optionalSeat =
                        totalSeats.stream()
                                .filter(s ->s.seatId.equals(seatId))
                                .findAny();
                if(optionalSeat.isPresent()) {
                    Seat seat = optionalSeat.get();
                    try {
                        seat.lock.lock();
                        if(seat.status.equals(SeatStatus.PENDING) || seat.status.equals(SeatStatus.BOOKED)) {
                            seat.status = SeatStatus.AVAILABLE;
                            return true;
                        }
                    } finally {
                        seat.lock.unlock();
                    }

                }
        }
        return false;
    }

    @Override
    public boolean confirm(UUID eventId, UUID seatId) {
        if(seatsDb.containsKey(eventId)) {
                Set<Seat> totalSeats = seatsDb.get(eventId);
                Optional<Seat> optionalSeat =
                        totalSeats.stream()
                                .filter(s ->s.seatId.equals(seatId))
                                .findAny();
                if(optionalSeat.isPresent()) {
                    Seat seat = optionalSeat.get();
                    try {
                        seat.lock.lock();
                        if(seat.status.equals(SeatStatus.PENDING)) {
                            seat.status = SeatStatus.BOOKED;
                            return true;
                        }
                    } finally {
                        seat.lock.unlock();
                    }

                }
        }
        return false;
    }
}

enum BookingStatus { BOOKED, CANCELLED, PARTIAL_BOOKED }

class Booking {
    UUID bookingId;
    UUID userId;
    UUID eventId;
    Set<UUID> seatId;
    BookingStatus bookingStatus;

    public Booking(UUID userId, UUID eventId, Set<UUID> seatId, BookingStatus bookingStatus) {
        this.bookingId = UUID.randomUUID();
        this.userId = userId;
        this.eventId = eventId;
        this.seatId = seatId;
        this.bookingStatus = bookingStatus;
    }
}


interface BookingRepo {
    Booking create(UUID userId, UUID eventId, Set<UUID> seatId, BookingStatus bookingStatus);
    void confirm(UUID bookingId);
    Set<Booking> viewAllBookings();
    void cancel(UUID bookingId);
}

class BookingRepository implements BookingRepo {
    private final Map<UUID, Booking> bookingsDb;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;

    public BookingRepository(Map<UUID, Booking> bookingsDb, SeatRepository seatRepository, PaymentRepository paymentRepository) {
        this.bookingsDb = bookingsDb;
        this.seatRepository = seatRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Booking create(UUID userId, UUID eventId, Set<UUID> seatId, BookingStatus bookingStatus) {
        synchronized (userId) {
            for(UUID seat: seatId) {
                seatRepository.bookSeat(eventId, seat);
            }

            Booking booking = new Booking(userId, eventId, seatId, BookingStatus.PARTIAL_BOOKED);
            bookingsDb.put(booking.bookingId, booking);

            return booking;
        }
    }

    @Override
    public void confirm(UUID bookingId) {
        synchronized (bookingId) {
            // perform the payment operation

            if(bookingsDb.containsKey(bookingId)) {
                Booking booking = bookingsDb.get(bookingId);
                for(UUID seat: booking.seatId)
                    seatRepository.confirm(booking.eventId, seat);

                booking.bookingStatus = BookingStatus.BOOKED;
            }
        }
    }

    @Override
    public Set<Booking> viewAllBookings() {
//        return bookingsDb.values();
        return new HashSet<>();
    }

    @Override
    public void cancel(UUID bookingId) {
        synchronized (bookingId) {
            if(bookingsDb.containsKey(bookingId)) {
                Booking booking = bookingsDb.get(bookingId);
                for(UUID seat: booking.seatId) {
                    seatRepository.cancelSeat(booking.eventId, seat);
                }

                booking.bookingStatus = BookingStatus.CANCELLED;
            }
        }
    }

}

enum PaymentStatus { PAID, NOT_PAID }

class Payment {
    UUID paymentId;
    UUID bookingId;
    PaymentStatus paymentStatus;

    public Payment(UUID bookingId, PaymentStatus paymentStatus) {
        this.bookingId = bookingId;
        this.paymentStatus = paymentStatus;
    }
}

interface PaymentRepo {
    void pay(UUID bookingId);
    PaymentStatus checkPaymentStatus(UUID bookingId);
}

class PaymentRepository implements PaymentRepo {
    @Override
    public void pay(UUID bookingId) {

    }

    @Override
    public PaymentStatus checkPaymentStatus(UUID bookingId) {
        return null;
    }
}







