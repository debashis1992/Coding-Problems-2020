package designPatterns.carrental;

import java.util.Date;
import java.util.List;

public class CarRentalTest {
}

enum YardStatus {
    OPERATIONAL, CLOSED
}

enum VehicleType {
    CAR, BIKE, TRUCK
}

enum VehicleAvailableStatus {
    AVAILABLE, NOT_AVAILABLE
}

interface Yard {
    boolean addItem(Item item);
    boolean removeItem(Item item);
    boolean close();
    boolean open();
}

class YardInfo implements Yard {
    int id;
    String location;
    YardStatus status;
    List<Item> itemList;

    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public boolean removeItem(Item item) {
        return false;
    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public boolean open() {
        return false;
    }
}

class Item {
    int id;
    VehicleType vehicleType;
    int yardId;
    String description;
    ItemDetails itemDetails;
    VehicleAvailableStatus status;
}

class ItemDetails {
    //some more details here
}

class Voucher {}

interface PricingEngine {
    Double getPrice(Item item);
    Double getPriceForBooking(Item item, Date startDate, Date endDate);
    Double update(Item item);
    Double getAndUpdatePriceOnVoucher(Item item, Voucher voucher);
}

interface Request {}
interface ReservationRequest extends Request {}
interface CancellationRequest extends Request {}
interface ReturnRequest extends Request {}

interface Reservation {
    Order reserve(Item item, ReservationRequest req);
    Order returnItem(Item item, ReturnRequest req);
    Order cancel(Item item, CancellationRequest req);
}

enum OrderType {
    RESERVE, CANCEL_RESERVE
}

class Order {
    int id;
    int itemId;
    OrderType orderType;
    OrderDetails details;
    Bill bill;
}

class OrderDetails {
    int orderId;
    Date bookingDate;
    Date bookingStartDate;
    Date bookingEndDate;
    Date cancellationDate;
    Date returnDate;
}

class Bill {
    int billId;
    int orderId;
    Double billAmount;
    Date billingDate;
    Payment paymentId;
    BillDetails billDetails;
}

class BillDetails {
    int billId;
    //bill breakdown
}

enum PaymentStatus {
    FULLY_PAID, PARTIAL_PAID, NOT_PAID
}

class Payment {
    int id;
}

class PaymentDetails {
    int paymentId;
    PaymentStatus paymentStatus;
    String paymentMode;
}

