package designPatterns.elevator;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class ElevatorTest {
}

class Building {
    private int floors;
    private List<Elevator> elevatorList;

    public Building(int floors, List<Elevator> elevatorList) {
        this.floors = floors;
        this.elevatorList = elevatorList;
    }
}
enum Status {
    GOING_UP, GOING_DOWN, IDLE
}

class Elevator {
    private int id;
    private int currentWeight;
    private int maxWeight;
    private Queue<Request> upRequestQueue;
    private Queue<Request> downRequestQueue;
    private Status status;
    public static int count;
    static {
        count = 0;
    }

    public Elevator(int maxWeight) {
        this.maxWeight = maxWeight;
        this.status = Status.IDLE;
        this.currentWeight = 0;
        this.id = ++count;
        upRequestQueue = new ArrayDeque<>();
        downRequestQueue = new ArrayDeque<>();
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public Status getStatus() {
        return status;
    }

    public synchronized boolean addRequest(int floorId, DirectionType directionType) {
        if(directionType == DirectionType.DOWN)
            downRequestQueue.add(new Request(floorId));
        else upRequestQueue.add(new Request(floorId));

        return true;
    }

}

enum DirectionType {
    UP, DOWN
}
class Request {
    private final int floorId;
    private DirectionType directionType;
    private RequestStatus requestStatus;

    public Request(int floorId) {
        this.floorId = floorId;
        requestStatus = RequestStatus.OPEN;
    }

    public int getFloorId() {
        return floorId;
    }
}

enum RequestStatus {
    OPEN, CLOSED
}
