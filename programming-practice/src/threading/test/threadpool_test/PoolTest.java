package threading.test.threadpool_test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PoolTest {

    public static void main(String[] args) {

        int nThreads = 5;
        MyThreadPool pool = new MyThreadPool(nThreads);
        for(int i=0;i<20;i++) {
            final int t = i;
            pool.submit(() -> {
                System.out.println(t + "]] printing hello world!!" + ", current thread: " + Thread.currentThread());
            });
        }

        pool.shutDown();
    }
}



class MyThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Worker[] workers;
    private volatile boolean shutDown = false;

    public MyThreadPool(int nThreads) {
        taskQueue = new LinkedBlockingDeque<>();

        workers = new Worker[nThreads];
        for(int i=0;i<nThreads;i++) {
            workers[i] = new Worker("worker-"+i);
            workers[i].start();
        }
    }

    public void submit(Runnable runnable) {
        if(!shutDown) {
            try {
                taskQueue.put(runnable);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutDown() {
        shutDown = true;
        for(Worker worker: workers) {
            worker.interrupt();
        }
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while(!shutDown || !taskQueue.isEmpty()) {
                try {
                    Runnable runnable = taskQueue.take();
                    runnable.run();
                } catch (InterruptedException e) {
                    System.out.println("exception occurred: " + e.getMessage());
                }
            }
        }
    }
}