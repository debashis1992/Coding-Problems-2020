package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeakyBucketTest {
    public static void main(String[] args) {
        LeakyBucket leakyBucket = new LeakyBucket(5);

        Thread producer = new Thread(leakyBucket::put);
        Thread consumer = new Thread(leakyBucket::get);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class LeakyBucket {
    int size;
    List<Integer> q;
    int low;
    public LeakyBucket(int size) {
        this.size=size;
        this.q = new ArrayList<>();
        this.low=0;
    }

    //input or put operations rate is 2req/sec
    //output or get operations rate is 1req/sec
    public void put() {

        while(true) {
            synchronized (this) {
                if(q.size() > size-2) {
                    try {
                        wait(); //let the output take 1 element away
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Random random = new Random();
                    int v1 = random.nextInt(10);
                    int v2 = v1+1;
                    System.out.println("Inserting element: "+v1);
                    q.add(v1);
                    System.out.println("Inserting element: "+v2);
                    q.add(v2);
                    notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void get() {
        while(true) {
            synchronized (this) {
                if(q.size() == 0) {
                    System.out.println("Queue is empty!!");
                    try {
//                        Thread.sleep(2000); //sleep now and wait for 2sec for the inflow
                        wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    int v1 = q.remove(low);
                    System.out.println("Got element : "+v1);
                    notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
