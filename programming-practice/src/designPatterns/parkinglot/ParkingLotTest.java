package designPatterns.parkinglot;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ParkingLotTest {
    public static void main(String[] args) {

    }
}

class Building {
    int id;
    String location;
    List<Level> levels;
}

class Level {
    int id;
    Building building;
    LevelStatus status;
    List<ParkingSpot> parkingSpots;

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}

interface ParkingSpot {
    boolean isEmpty();
    boolean assignVehicle(Vehicle vehicle) throws RuntimeException;
    boolean removeVehicle();
    VehicleType getVehicle();
}

class Spot implements ParkingSpot, Comparable<Spot> {
    int id;
    Level level;
    SpotStatus status;
    VehicleType vehicleType;

    public int getId() {
        return id;
    }

    @Override
    public synchronized boolean assignVehicle(Vehicle vehicle) throws RuntimeException {
        if(null == vehicle)
            throw new IllegalArgumentException("vehicle is not initialized. Cannot assign vehicle here!!");

        if(status == SpotStatus.AVAILABLE
            && vehicle.getVehicleType() == vehicleType) {
            status = SpotStatus.NOT_AVAILABLE;
            vehicle.setParkingSpot(this);
            return true;
        }
        else return false;
    }

    @Override
    public synchronized boolean removeVehicle() {
        if(status == SpotStatus.NOT_AVAILABLE) {
            status = SpotStatus.AVAILABLE;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return status == SpotStatus.AVAILABLE;
    }

    @Override
    public VehicleType getVehicle() {
        return vehicleType;
    }


    @Override
    public int compareTo(Spot o) {
        return Integer.compare(this.getId(), o.getId());
    }
}

enum LevelStatus {
    OPEN, CLOSED
}
enum SpotStatus {
    AVAILABLE, NOT_AVAILABLE
}
enum VehicleType {
    CAR, TRUCK, BIKE
}

class Ticket {
    int id;
    Vehicle vehicle;
    Date start, end;
    double totalPrice;
}

class PaymentReceipt {
    int id;
    Payment payment;
    Ticket ticket;
}

class Payment {
    int id;
    //more payment related fields here
}

class Vehicle {
    int id;
    String regNumber;
    Date in, out;
    VehicleType vehicleType;
    Spot parkingSpot;

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Spot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(Spot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}

interface ParkingStrategy {
    Optional<ParkingSpot> findNextParkingSpot(Level level);
}

class LinearAvailableParkingStrategy implements ParkingStrategy {
    @Override
    public synchronized Optional<ParkingSpot> findNextParkingSpot(Level level) {
        List<ParkingSpot> parkingSpots = level.getParkingSpots();
        return parkingSpots.stream().filter(ParkingSpot::isEmpty)
                .sorted()
                .findFirst();
    }
}
class LinearOppositeAvailableParkingStrategy implements ParkingStrategy {
    @Override
    public synchronized Optional<ParkingSpot> findNextParkingSpot(Level level) {
        List<ParkingSpot> parkingSpots = level.getParkingSpots();
        return parkingSpots.stream().filter(ParkingSpot::isEmpty)
//                .sorted(Comparator.reverseOrder())
                .findFirst();
    }
}