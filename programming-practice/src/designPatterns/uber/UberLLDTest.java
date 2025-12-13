package designPatterns.uber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicReference;

public class UberLLDTest {
}


interface Request {}
interface RideRequestOperations {
    void addRideRequest(Request request);
    void cancelRideRequest(Request request);
}
interface DriverOperations {
    void accept(Request request);
    void decline(Request request);
    void startTrip();
    void endTrip();
}

interface DriverManagerOperations {
    void add(Driver driver);
    void remove(Driver driver);
    List<Driver> findAvailableDrivers();
}

enum DriverType { NORMAL, PREMIUM }

class PricingEngine {
    public static double getCost(String pickup, String destination, String type) {
        return 0.10; // return price based on some logic
    }
}
class RideRequest implements Request {
    private final String rideId;
    private final String pickup, destination;
    private final DriverType type;
    private final double cost;

    public RideRequest(String pickup, String destination, DriverType type) {
        this.rideId = UUID.randomUUID().toString();
        this.pickup = pickup;
        this.destination = destination;
        this.type = type;
        this.cost = PricingEngine.getCost(pickup, destination, type.name());
    }

    public String getPickup() {
        return pickup;
    }

    public String getDestination() {
        return destination;
    }

    public DriverType getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RideRequest that = (RideRequest) o;
        return Objects.equals(rideId, that.rideId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rideId);
    }

    public String getRideId() {
        return rideId;
    }
}

enum DriverStatus { AVAILABLE, NOT_AVAILABLE }

class Trip {
    private final Request request;
    private final String driverId;

    public Trip(Request request, String driverId) {
        this.request = request;
        this.driverId = driverId;
    }

    public Request getRequest() {
        return request;
    }

    public String getDriverId() {
        return driverId;
    }
}


class Driver implements DriverOperations {
    private final String driverId;
    private final String location;
    private volatile DriverStatus driverStatus;
    private final AtomicReference<Trip> currentTrip;

    public Driver(String location, DriverStatus driverStatus) {
        this.driverId = UUID.randomUUID().toString();
        this.location = location;
        this.driverStatus = driverStatus;
        this.currentTrip = new AtomicReference<>(null);
    }

    @Override
    public void accept(Request request) {
        if(currentTrip.get() ==null && driverStatus.equals(DriverStatus.AVAILABLE)) {
            Trip trip = new Trip(request, driverId);
            currentTrip.compareAndSet(null, trip);
            driverStatus = DriverStatus.NOT_AVAILABLE;
            System.out.println("assigned trip to driver with Id: "+driverId);
        }
        else {
            System.out.println("driver not available to accept rides...");
        }
    }

    @Override
    public void decline(Request request) {
        System.out.println("declining the ride request");
    }

    @Override
    public void startTrip() {
        if(currentTrip.get()!=null) {
            System.out.println("starting current trip");
        }
    }

    @Override
    public void endTrip() {
        if(currentTrip.get()!=null) {
            System.out.println("ending current trip");
            currentTrip.getAndSet(null);
            driverStatus = DriverStatus.AVAILABLE;
        }

    }

    public String getDriverId() {
        return driverId;
    }

    public String getLocation() {
        return location;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public AtomicReference<Trip> getCurrentTrip() {
        return currentTrip;
    }
}

class RideRequestManager implements RideRequestOperations {
    private RideRequestManager() {
        rideRequests = new LinkedBlockingDeque<>();
    }
    private static volatile RideRequestManager instance;
    private final BlockingQueue<Request> rideRequests;

    public static RideRequestManager getInstance() {
        if(instance == null) {
            synchronized (RideRequestManager.class) {
                if(instance == null)
                    instance = new RideRequestManager();
            }
        }
        return instance;
    }

    @Override
    public void addRideRequest(Request request) {
        rideRequests.offer(request);
    }

    @Override
    public void cancelRideRequest(Request request) {
        rideRequests.remove(request);
    }
}


class DriverManager implements DriverManagerOperations {
    private final ConcurrentHashMap<String, Driver> drivers;
    private DriverManager() {
        drivers = new ConcurrentHashMap<>();
    }
    private static volatile DriverManager instance;

    public static DriverManager getInstance() {
        if(instance == null) {
            synchronized (DriverManager.class) {
                if(instance == null)
                    instance = new DriverManager();
            }
        }
        return instance;
    }

    @Override
    public void add(Driver driver) {
        drivers.put(driver.getDriverId(), driver);
    }

    @Override
    public void remove(Driver driver) {
        drivers.remove(driver.getDriverId());
    }

    @Override
    public List<Driver> findAvailableDrivers() {
        List<Driver> driverList = new ArrayList<>();
        for(Driver driver: drivers.values()) {
            if(driver.getDriverStatus().equals(DriverStatus.AVAILABLE))
                driverList.add(driver);
        }
        return driverList;
    }
}