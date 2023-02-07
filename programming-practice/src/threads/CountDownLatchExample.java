package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) {

        int nThreads = 5;
        CountDownLatch countDownLatch = new CountDownLatch(nThreads);

        List<Thread> workerList = new ArrayList<>();
        for(int i=0;i<nThreads;i++) {
            new Thread(new Worker(4, countDownLatch), "thread"+(i+1)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main has started....");

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