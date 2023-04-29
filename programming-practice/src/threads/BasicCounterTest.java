package threads;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicCounterTest {
    public static void main(String[] args) {
        BasicCounter basicCounter = new BasicCounter();

        AtomicCounter atomicCounter = new AtomicCounter();
        SyncCounter syncCounter = new SyncCounter();
        LockBasedCounter lockBasedCounter = new LockBasedCounter();

        Thread t1 = new Thread(() -> {
            for(int i=0;i<3;i++)
                lockBasedCounter.increment();
        }, "t1");
        Thread t2 = new Thread(() -> {
            for(int i=0;i<3;i++)
                lockBasedCounter.increment();
        },"t2");


        System.out.println(lockBasedCounter.counter);
        try {
            t1.join();
            t2.join();

            t1.start();
            t2.start();
        } catch (InterruptedException e) {}
    }
}

class BasicCounter {
    int counter;
    public BasicCounter() { counter = 0; }
    public void increment() { ++counter;
        System.out.println("incremented counter: "+counter+", thread: "+Thread.currentThread());
    }
}

class AtomicCounter {
    AtomicInteger counter;
    public AtomicCounter() { counter = new AtomicInteger(0); }
    public void increment() {
        counter.getAndIncrement();
        System.out.println("incremented counter: "+counter.get()+", thread: "+Thread.currentThread());

    }
}

class SyncCounter {
    int counter;
    public SyncCounter() { counter = 0; }
    public void increment() {
        synchronized (this) {
            ++counter;
            System.out.println("incremented counter: "+counter+", thread: "+Thread.currentThread());
        }
    }
}

class LockBasedCounter {
    int counter;
    public final Lock lock = new ReentrantLock();
    public LockBasedCounter() {
        counter=0;
    }

    public void increment() {
        try {
            lock.lock();
            ++counter;
            System.out.println("incremented counter: "+counter+", thread: "+Thread.currentThread());

        } finally {
            lock.unlock();
        }

    }
}


class Ac {
    public void doSomething() {}
}
class Bc {

}
