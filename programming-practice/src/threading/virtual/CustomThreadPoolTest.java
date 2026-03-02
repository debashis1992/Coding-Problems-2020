package threading.virtual;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPoolTest {
}

class CustomThreadPool {
    private final int nThreads;
    private final BlockingQueue<Runnable> blockingQueue;
    private final Worker[] workers;
    private volatile boolean isShutdown;
    private final static int MULTIPLIER = 10;

    public CustomThreadPool(int nThreads) {
        this.nThreads=nThreads;
        blockingQueue=new ArrayBlockingQueue<>(nThreads * MULTIPLIER);
        workers=new Worker[nThreads];
        isShutdown = false;

        for(int i=0;i<nThreads;i++) {
            workers[i] = new Worker("worker-"+i);
            workers[i].start();
        }
    }

    public void shutdown() {
        isShutdown = true;
        for(int i=0;i<nThreads;i++)
            workers[i].interrupt();
    }

    public void submit(Runnable runnable) {
        if(!isShutdown) {
            try {
                blockingQueue.put(runnable);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else throw new RuntimeException("thread pool is shutdown!!");
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Runnable runnable = blockingQueue.take();
                    runnable.run();
                } catch (InterruptedException e) {
                    //do not break immediately, break when only interrupted and no tasks are there
                    if(isShutdown && blockingQueue.isEmpty())
                        break;
                } catch (Throwable e) {
                    System.out.println("exception occurred: "+e.getMessage());
                }
            }
        }
    }
}
