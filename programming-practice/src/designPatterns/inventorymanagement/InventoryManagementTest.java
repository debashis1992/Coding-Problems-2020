package designPatterns.inventorymanagement;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryManagementTest {
}


class Item {
    int itemId;
    long total, reserved, booked;
    ReentrantLock lock;
}

interface ItemRepo {
    Item findByItemId(int itemId);
}

class ReservationRequest {
    int requestId;
    ReservationRequestStatus reservationRequestStatus;

    public ReservationRequest(int requestId, ReservationRequestStatus reservationRequestStatus) {
        this.requestId = requestId;
        this.reservationRequestStatus = reservationRequestStatus;
    }
}

class Reservation {
    int id;
    int itemId;
    long createdTs;
    long expiry;
    ReservationStatus reservationStatus;

    public Reservation(int id, int itemId) {
        this.id = id;
        this.itemId = itemId;
        createdTs = System.currentTimeMillis();
        expiry = createdTs + TimeUnit.MINUTES.toMinutes(5);
        reservationStatus = ReservationStatus.RESERVED;
    }
}

enum ReservationStatus {
    RESERVED(1), CANCELLED(0);
    ReservationStatus(int statusCode) {}
}

enum ReservationRequestStatus {
    PENDING(0), DONE(1);
    ReservationRequestStatus(int statusCode) {}
}

interface ReservationRequestsRepo {
    ReservationRequest findByRequestId(int requestId);
    void save(ReservationRequest reservationRequest);
}

interface ItemReservationService {
    void reserve(int itemId, int requestId) throws Exception;
    Reservation create(int itemId, int requestId);
    void cancelReservation(int itemId, int requestId) throws Exception;
    void reserve(int itemId);
    void cancelReserve(int itemId);
    void book(int itemId);
    void cancelBook(int itemId);
}

interface ReservationRepo {
    boolean findByRequestId(int id);
    void save(Reservation reservation);
    Reservation findByReservationId(int id);
    void updateStatus(int id, ReservationStatus reservationStatus);
}


class ItemReservationServiceImpl implements ItemReservationService {
    ItemRepo itemRepo;
    ReservationRepo reservationRepo;
    ReservationRequestsRepo reservationRequestsRepo;

    @Override
    public void reserve(int itemId, int requestId) throws Exception {
        validateDuplicateRequest(requestId);
        Item item = itemRepo.findByItemId(itemId);
        if(item==null) {
            throw new RuntimeException("item not found!");
        }
        try {
            item.lock.lock();
            if(item.total > (item.reserved + item.booked)) {
                item.reserved+=1;
                Reservation reservation = create(itemId, requestId);
                reservationRepo.save(reservation);
                reservationRequestsRepo.save(new ReservationRequest(requestId, ReservationRequestStatus.DONE));
            }
            else {
                reservationRequestsRepo.save(new ReservationRequest(requestId, ReservationRequestStatus.PENDING));
            }
        } finally {
            item.lock.unlock();
        }

    }

    @Override
    public Reservation create(int itemId, int requestId) {
        if(reservationRepo.findByRequestId(requestId)) {
            throw new RuntimeException("duplicate reservation req found");
        }
        return new Reservation(requestId, itemId);
    }

    @Override
    public void cancelReservation(int itemId, int requestId) throws Exception {
        validateDuplicateRequest(requestId);
        Item item = itemRepo.findByItemId(itemId);
        if(item==null) {
            throw new RuntimeException("item not found!");
        }

        try {
            item.lock.lock();
            Reservation reservation = reservationRepo.findByReservationId(requestId);
            if(reservation == null)
                throw new Exception("no reservation found. Invalid request");

            if(reservation.reservationStatus == ReservationStatus.CANCELLED) {
                System.out.println("reservation is already cancelled");
                return;
            }
            if(item.reserved <= 0)
                throw new Exception("no pending reservation for this item id");

            item.reserved-=1;

            reservationRepo.updateStatus(requestId, ReservationStatus.CANCELLED);
        } finally {
            item.lock.unlock();
        }
    }

    public void reserve(int itemId) {
        Item item = itemRepo.findByItemId(itemId);
        if(item==null) {
            throw new RuntimeException("item not found!");
        }
        try {
            item.lock.lock();
            if(item.total <= (item.reserved + item.booked)) {
                throw new RuntimeException("operation not possible");
            }
            item.reserved++;
        } finally {
            item.lock.unlock();
        }

    }

    public void cancelReserve(int itemId) {
        Item item = itemRepo.findByItemId(itemId);
        if(item==null) {
            throw new RuntimeException("item not found!");
        }
        try {
            item.lock.lock();
            if(item.reserved == 0) {
                throw new RuntimeException("cannot cancel reserve");
            }
            item.reserved--;
        } finally {
            item.lock.unlock();
        }
    }

    public void book(int itemId) {
        Item item = itemRepo.findByItemId(itemId);
        if(item==null) {
            throw new RuntimeException("item not found!");
        }
        try {
            item.lock.lock();
            if(item.total <= (item.reserved + item.booked)) {
                throw new RuntimeException("operation not possible");
            }
            item.booked++;
            item.reserved--;
        } finally {
            item.lock.unlock();
        }

    }

    public void cancelBook(int itemId) {
        Item item = itemRepo.findByItemId(itemId);
        if(item==null) {
            throw new RuntimeException("item not found!");
        }
        try {
            item.lock.lock();
            if(item.booked == 0) {
                throw new RuntimeException("operation not possible");
            }
            item.booked--;
            cancelReserve(itemId);
        } finally {
            item.lock.unlock();
        }
    }

    private void validateDuplicateRequest(int requestId) throws Exception {
        ReservationRequest reservationRequest = reservationRequestsRepo.findByRequestId(requestId);
        if(reservationRequest != null && reservationRequest.reservationRequestStatus == ReservationRequestStatus.DONE) {
            throw new Exception("duplicate request");
        }
    }
}

class PaymentRequest {
    int reservationId;
    int requestId;
    PaymentStatus paymentStatus;
    long createdTs;

    public PaymentRequest(int requestId, int reservationId, PaymentStatus paymentStatus) {
        this.requestId = requestId;
        this.reservationId = reservationId;
        this.paymentStatus = paymentStatus;
        this.createdTs = System.currentTimeMillis();

    }
}

enum PaymentStatus {
    PENDING(0), COMPLETED(1), FAILED(-1);
    PaymentStatus(int statusCode) {}
}
class Payment {
    int requestId;
    PaymentStatus paymentStatus;
    //other payment related details
    //payment method
    final ReentrantLock lock;

    public Payment(int requestId, PaymentStatus paymentStatus) {
        this.requestId = requestId;
        this.paymentStatus = paymentStatus;
        lock = new ReentrantLock();
    }
}
class Booking {
    int requestId;
    int reservationId;
    long createdTs;
    BookingStatus bookingStatus;

    public Booking(int requestId, int reservationId, BookingStatus bookingStatus) {
        this.requestId = requestId;
        this.reservationId = reservationId;
        this.createdTs = System.currentTimeMillis();
        this.bookingStatus = bookingStatus;
    }
}

enum BookingStatus {
    BOOKED(1), CANCELLED(0);
    BookingStatus(int statusCode) {}
}

interface BookingRepo {
    Booking findByRequestId(int requestId) throws Exception;
    void create(Booking booking);
    void updateStatus(int requestId, BookingStatus bookingStatus);
}

interface PaymentRequestRepo {
    PaymentRequest findByRequestId(int requestId);
    void create(PaymentRequest paymentRequest);
    void updateStatus(int requestId, PaymentStatus paymentStatus);
}

interface PaymentRepo {
    Payment findByRequestId(int requestId);
    void save(Payment payment);
    void updatePaymentStatus(int requestId, PaymentStatus paymentStatus);
}

interface PaymentService {
    void pay(int requestId);
    void reversePay(int requestId);
}

interface ItemBookingService {
    void initiatePayment(int reservationId, int requestId) throws Exception;
    void confirmBooking(int requestId, int reservationId) throws Exception;
}


class ItemBookingServiceImpl implements ItemBookingService {
    PaymentService paymentService;
    PaymentRequestRepo paymentRequestRepo;
    PaymentRepo paymentRepo;
    ReservationRepo reservationRepo;

    BookingRepo bookingRepo;
    ItemReservationService itemReservationService;

    @Override
    public void initiatePayment(int reservationId, int requestId) throws Exception {
        // check if the request is duplicate
        PaymentRequest paymentRequest = paymentRequestRepo.findByRequestId(requestId);
        if(paymentRequest!=null && (paymentRequest.paymentStatus == PaymentStatus.COMPLETED
                || paymentRequest.paymentStatus == PaymentStatus.FAILED)) {

            throw new Exception("duplicate request");
        }
        // if not, then create an entry in payment request
        if(paymentRequest == null) {
            paymentRequestRepo.create(new PaymentRequest(requestId, reservationId, PaymentStatus.PENDING));
        }
        // check if the reservation is not expired
        Reservation reservation = reservationRepo.findByReservationId(reservationId);
        int itemId = reservation.itemId;
        if(reservation == null) {
            throw new Exception("no reservation found");
        }
        if(reservation.expiry < System.currentTimeMillis()) {
            reservationRepo.updateStatus(reservationId, ReservationStatus.CANCELLED);
            itemReservationService.cancelReserve(itemId);
            throw new Exception("reservation already expired, please add items back and retry");
        }
        // if yes, then cancel the payment and cancel the reservation right away
        // if no, then proceed to pay

        Payment payment = paymentRepo.findByRequestId(requestId);
        try {
            try {
                payment.lock.lock();
                paymentService.pay(requestId);
            } finally {
                payment.lock.unlock();
            }
        } catch (RuntimeException e) {
            System.out.println("some exception occurred during payment: "+e.getMessage());
        }
        PaymentStatus paymentStatus = PaymentStatus.COMPLETED;
        if(reservation.expiry < System.currentTimeMillis()) {
            try {
                payment.lock.lock();
                paymentService.reversePay(requestId);
                itemReservationService.cancelReserve(itemId);
            } finally {
                payment.lock.unlock();
            }
            reservationRepo.updateStatus(reservationId, ReservationStatus.CANCELLED);
            paymentStatus = PaymentStatus.FAILED;
        }
        // if payment is confirmed
        // if reservation is expired now, then reverse the payment and cancel the reservation
        // if not then, mark the final payment as completed
        paymentRepo.updatePaymentStatus(requestId, paymentStatus);
    }

    @Override
    public void confirmBooking(int requestId, int reservationId) throws Exception {
        // check if the request is duplicate
        Booking booking = bookingRepo.findByRequestId(requestId);
        if(booking!=null && booking.bookingStatus == BookingStatus.BOOKED) {
            throw new Exception("booking request duplicate");
        }
        // check if an existing booking is present with this requestId
        if(booking == null) {
            booking = new Booking(requestId, reservationId, BookingStatus.BOOKED);
            bookingRepo.create(booking);
        }

        // if present, then ignore this request
        // else create a booking
        bookingRepo.updateStatus(requestId, BookingStatus.BOOKED);

        // decrease the reservation count in inventory by 1
        // increase the booking count in inventory by 1
        Reservation reservation = reservationRepo.findByReservationId(reservationId);
        if(reservation!=null) {
            int itemId = reservation.itemId;
            itemReservationService.book(itemId);
        }
    }
}


/*
Problem statement:

At its core, this system sits between “user intent to buy” and “final order placement.”
The goal is simple to state, but tricky to implement:
Allow users to temporarily reserve inventory during checkout, without overselling, even under heavy concurrency.


Functional:
1. able to buy a product as long as it's available in our inventory
2. should not be allowed for overselling, i.e. number of final orders <= inventory count of a product
3. when multiple users are trying to buy a single available item, there should be a fairness policy like a first come first serve basis
4. users should be able to reserve an item, complete the payment, then order is created. If payment is not confirmed, then the inventory
is rolled back.
4. if users try to attempt to buy an item, if payment not confirmed with a given TTL, it should be returned to our inventory
5. if someone is cancelling the order, then the item should be returned back to the inventory
6. when item not available, users cannot buy now this item

Non-functional:
1. low latency during search a product
2. consistency >> availability, system should be highly consistent when mutliple users are trying to buy the same item.
Maintain the number of remaining items always correctly. No overselling. Strong consistency required during inventory updates.
3.  Consistency & idempotency during order placement, no duplicate orders on retry, no duplicate payments, transactions. System
should be able to handle these scenarios
4. Durable storage, once a order is placed, it cannot be removed unless cancelled from user. No vanishing orders or payments
5. system should be able to handle race conditions during high concurrency


 */