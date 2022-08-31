package threads;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ImplementSemaphoreTest {

    public static void main(String[] args) {
        Thread[] producers = new Thread[10];
        Thread[] consumers = new Thread[10];

        Semaphore semaphore = new Semaphore(20);
        for(int i=0;i<10;i++) {
            producers[i] = new Thread(() -> semaphore.acquire());
        }

        for(int i=0;i<10;i++) {
           consumers[i] = new Thread(() -> semaphore.release());
        }

        Arrays.stream(producers).forEach(Thread::start);
        Arrays.stream(consumers).forEach(Thread::start);
    }

}


class Semaphore {
    int max;
    int count;

    public Semaphore(int max) {
        this.max = max;
        this.count = 0;
    }

    public synchronized void acquire() {
        if(count == max) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ++count;
        System.out.println("Acquired: "+count+", Thread: "+Thread.currentThread().getName());
        notifyAll();
    }

    public synchronized void release() {
        if (count == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        --count;
        System.out.println("Released: "+count+", Thread: "+Thread.currentThread().getName());
        notifyAll();
    }
}