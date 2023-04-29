package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionsTest {
    public static void main(String[] args) {

        Que q = new Que();
        PT producer = new PT("producer", q);
        CT consumer = new CT("consumer", q);

        try {
            producer.start();
            consumer.start();

            producer.join();
            consumer.join();
        } catch(Exception e) {

        }
    }
}

class Que {
    private List<Integer> list;
    private int capacity;
    private int val;

    private final java.util.concurrent.locks.Lock lock = new ReentrantLock();
    private final Condition fullCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();

    public Que() {
        this.list = new ArrayList<>();
        this.capacity = 1;
        this.val = 0;
    }

    public void produce() {
        while(true) {
            try {
                lock.lock();
                while (list.size() == capacity)
                    fullCondition.await();

                ++val;
                System.out.println("producing value: " + val + ", thread: " + Thread.currentThread());
                list.add(val);
                sleep();

                emptyCondition.signal();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void consume() {
        while(true) {
            try {
                lock.lock();
                while (list.size() == 0)
                    emptyCondition.await();

                int el = list.remove(list.size() - 1);
                System.out.println("consuming value: " + el + ", thread: " + Thread.currentThread());
                sleep();
                fullCondition.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }
}

class PT extends Thread {
    Que q;
    public PT(String name, Que q) {
        super(name);
        this.q=q;
    }

    @Override
    public void run() {
        q.produce();
    }
}
class CT extends Thread {
    Que q;
    public CT(String name, Que q) {
        super(name);
        this.q=q;
    }

    @Override
    public void run() {
        q.consume();
    }
}


