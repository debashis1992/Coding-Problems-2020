package threading.problems.code;

import java.util.ArrayList;
import java.util.List;

public class BoundedBlockingQueueTest {

    public static void main(String[] args) {

        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(1);
        Runnable pRunnable = () -> {
            int val=1;
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                blockingQueue.enqueue(val++);
            }
        };


        Runnable cRunnable = () -> {
          while(true) {
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
              blockingQueue.dequeue();
          }
        };


        Thread tp = new Thread(pRunnable, "producer-thread");
        Thread tc = new Thread(cRunnable, "consumer-thread");

        tp.start();
        tc.start();

        try {
            tp.join();
            tc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

final class BlockingQueue<T> {
    private final List<T> t;
    private final int capacity;
    public BlockingQueue(int capacity) {
        t = new ArrayList<>();
        this.capacity = capacity;
    }

    public void enqueue(T element) {
        synchronized (this) {
            while(t.size() == capacity) {
                System.out.println("Queue already full, so cannot add element: "+element+", going on waiting state. Thread: "+Thread.currentThread().getName());
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Adding element: "+element+", with thread: "+Thread.currentThread().getName());
            t.add(element);
            notifyAll();

        }
    }

    public T dequeue() {
        synchronized (this) {
            while(t.isEmpty()) {
                System.out.println("No elements to remove. Going on waiting state. Thread: "+Thread.currentThread().getName());
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            T element = t.removeFirst();
            System.out.println("Removing element using thread: "+Thread.currentThread()
                    .getName());
            notifyAll();
            return element;
        }
    }

    public synchronized int getSize() {
        return t.size();
    }


}