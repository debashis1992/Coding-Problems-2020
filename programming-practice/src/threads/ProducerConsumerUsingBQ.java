package threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerUsingBQ {
    public static void main(String[] args) {

        BQ bq = new BQ();
        Thread p = new Thread(new Producer(bq), "producer-thread");
        Thread c = new Thread(new Consumer(bq), "consumer-thread");

        p.start();
        c.start();

        try {
            p.join();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable {
    BQ bq;
    public Producer(BQ bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
        bq.add();
    }

}

class Consumer implements Runnable {
    BQ bq;
    public Consumer(BQ bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
        bq.remove();
    }
}

class BQ {
    BlockingQueue<Integer> queue;
    int value;

    public BQ() {
        queue = new ArrayBlockingQueue<>(1);
        value = 0;
    }

    public void add() {
        while(true) {
            ++value;
            try {
                System.out.println("Adding element: " + value + ", Thread: " + Thread.currentThread());
                queue.put(value);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void remove() {
        while(true) {
            try {
                int element = queue.take();
                System.out.println("Removing element: " + element + ", Thread: " + Thread.currentThread());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}