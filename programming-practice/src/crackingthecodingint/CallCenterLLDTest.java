package crackingthecodingint;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenterLLDTest {
}

enum Status {
    AVAILABLE, BUSY
}

enum LevelType {
    RESPONDENT(1), MANAGER(2), DIRECTOR(3);
    LevelType(int level) {}
}

abstract class Employee {
    ReentrantLock lock;
    Status status;

    public Employee() {
        lock = new ReentrantLock();
        status = Status.AVAILABLE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

class Respondent extends Employee {
    LevelType levelType;

    public Respondent() {
        super();
        this.levelType = LevelType.RESPONDENT;
    }
}

class Manager extends Employee {
    LevelType levelType;

    public Manager() {
        super();
        this.levelType = LevelType.MANAGER;
    }
}

class Director extends Employee {
    LevelType levelType;

    public Director() {
        super();
        this.levelType = LevelType.DIRECTOR;
    }
}

interface Request {}

interface DispatchRequest {
    void dispatchCall(Request request);
    void performRequest(Employee employee, Request request) throws IllegalArgumentException;
}

interface EmployeeRepo {
    ConcurrentHashMap<LevelType, Set<Employee>> map =
        new ConcurrentHashMap<>();

    List<Employee> findAvailableEmployee(LevelType levelType);
}

class DispatchRequestImpl implements DispatchRequest {
    private EmployeeRepo employeeRepo;
    private final ExecutorService service = Executors.newFixedThreadPool(10);

    private final List<LevelType> dispatchOrder =
            List.of(LevelType.RESPONDENT, LevelType.MANAGER, LevelType.DIRECTOR);

    @Override
    public void dispatchCall(Request request) {

        boolean requestDispatched = false;
        for (LevelType levelType : dispatchOrder) {
            List<Employee> employees = employeeRepo.findAvailableEmployee(levelType);
            if (employees.isEmpty())
                continue;

            Random random = new Random();
            Employee employee = employees.get(random.nextInt(employees.size()));

            ReentrantLock lock = employee.lock;
            try {
                lock.lock();
                employee.setStatus(Status.BUSY);
                service.submit(() -> performRequest(employee, request));
                requestDispatched = true;
                break;
            } finally {
                lock.unlock();
            }
        }

        if(!requestDispatched) {
            throw new RuntimeException("sorry no employees is available to take this request!");
        }
    }

    @Override
    public void performRequest(Employee employee, Request request) throws IllegalArgumentException {
        if(employee == null) throw new IllegalArgumentException("employee cannot be null");

        ReentrantLock lock = employee.lock;
        //do activity
        try {
            lock.lock();
            employee.setStatus(Status.AVAILABLE);
        } finally {
            lock.unlock();
        }
    }
}

/*
Imagine you have a call center with three levels of employees: respondent, manager,
and director. An incoming telephone call must be first allocated to a respondent who is free. If the
respondent can't handle the call, he or she must escalate the call to a manager. If the manager is not
free or not able to handle it, then the call should be escalated to a director. Design the classes and
data structures for this problem. Implement a method dispatchCall() which assigns a call to
the first available employee.
*/

