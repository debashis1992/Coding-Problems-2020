package threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample {

    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("barrier tripped"));
    public static void main(String[] args) {
        BarrierWorker barrierWorker = new BarrierWorker();
        Thread t1 = new Thread(barrierWorker, "t1");
        Thread t2 = new Thread(barrierWorker, "t2");
        Thread t3 = new Thread(barrierWorker, "t3");

        try {
            t1.join();
            t2.join();
            t3.join();

            t1.start();
            t2.start();
            t3.start();
        } catch (Exception e) {}

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println("waiting: "+cyclicBarrier.getNumberWaiting());
//        RunnableWorker runnableWorker = new RunnableWorker();
//        Executor executor = (runnable) -> new Thread(runnable).start();
//        executor.execute(runnableWorker);
    }
}

class RunnableWorker implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("running....");
        } catch (Exception e) {}
    }
}


class BarrierWorker implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Thread: "+Thread.currentThread()+" finished... Number of waiting: "+CyclicBarrierExample.cyclicBarrier.getNumberWaiting());
            CyclicBarrierExample.cyclicBarrier.await();
        } catch (Exception e) {

        }
    }
}