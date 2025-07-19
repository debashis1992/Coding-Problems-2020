package threading.test;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class PoolTest2 {
    public static void main(String[] args) {

        int nThreads = 5;
        MyThreadPool threadPool = new MyThreadPool(nThreads);
        for(int i=0;i<1000;i++) {
            final int t = i;
            threadPool.submit(() -> 
            {
                System.out.println("submitting job with i +" + t + ", current thread: " + Thread.currentThread());
            });
        }

        threadPool.shutDown();
        
    }
}


class MyThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Worker[] workers;
    private volatile boolean shutdown = false;

    public MyThreadPool(int n) {
        taskQueue = new LinkedBlockingDeque<>();

        workers = new Worker[n];
        for(int i=0;i<n;i++) {
            workers[i] = new Worker("worker-i-"+i);
            workers[i].start();
        }
    }

    public void shutDown() {
        shutdown = true;
        for(Worker worker: workers) 
            worker.interrupt();
    }
    public void submit(Runnable runnable) {
        if(!shutdown) {
            try {
                taskQueue.put(runnable);
            } catch(InterruptedException e) {
                System.out.println("exception occurred: " + e.getMessage());
            }
        }
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while(!shutdown || !taskQueue.isEmpty()) {
                try {
                    Runnable runnable = taskQueue.take();
                    runnable.run();
                } catch(InterruptedException e) {
                    System.out.println("exception occurred: " + e.getMessage());
                }
            }
        }
    }
}
