package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) {

        int nThreads = 6;
        CountDownLatch countDownLatch = new CountDownLatch(nThreads);

        for(int i=0;i<nThreads-1;i++) {
            new Thread(new Worker(4, countDownLatch), "thread"+(i+1)).start();
        }

//        new Thread(new AnotherWorker(countDownLatch), "another-thread").start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main has started....");

    }

}

class AnotherWorker implements  Runnable {
    CountDownLatch countDownLatch;
    public AnotherWorker(CountDownLatch count) {
        this.countDownLatch=count;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
//            int d=1/0;
        }
         catch (InterruptedException e) {
            e.printStackTrace();
         }
        finally {
            countDownLatch.countDown();
        }
    }
}
class Worker implements Runnable {

    int lim;
    CountDownLatch countDownLatch;
    public Worker(int lim, CountDownLatch countDownLatch) {
        this.lim=lim;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(lim*1000);
        } catch (Exception e) {

        }

        System.out.println("Thread: "+Thread.currentThread().getName()+" finished execution...");
        countDownLatch.countDown();
    }
}