package threads;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadWriteLockTest {
    public static void main(String[] args) {

        Page page = new Page();
        Random random = new Random();

        int readerThreadCount = 20, writerThreadCount = 20;
        Thread[] readers = new Thread[readerThreadCount];
        Thread[] writers = new Thread[writerThreadCount];

        for(int i=0;i<readerThreadCount;i++)
            readers[i] = new Thread(() -> {
                while(true) {
                    page.read();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        for(int i=0;i<writerThreadCount;i++)
            writers[i] = new Thread(() -> {
                while(true) {
                    page.write(random.nextInt(100));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        Arrays.stream(writers).forEach(Thread::start);
        Arrays.stream(readers).forEach(Thread::start);


        for(int i=0;i<readerThreadCount;i++) {
            try {
                readers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<writerThreadCount;i++) {
            try {
                writers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Lock {
    boolean isWriteAcquired;
    public Lock() {
        isWriteAcquired = false;
    }
}

class Page {
    volatile AtomicInteger an;
    Lock lock;

    public Page() {
        an = new AtomicInteger(-1);
        lock = new Lock();
    }

    public synchronized void read() {
        while(lock.isWriteAcquired) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Reading Thread: "+Thread.currentThread().getName()+", Value: "+an.get());
        notifyAll();

    }

    public synchronized void write(int x) {
        System.out.println("Writing Thread: "+Thread.currentThread().getName()+", Value: "+an.get());
        while(lock.isWriteAcquired) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lock.isWriteAcquired = true;
        an.getAndSet(x);

        lock.isWriteAcquired = false;
        notifyAll();
    }
}
