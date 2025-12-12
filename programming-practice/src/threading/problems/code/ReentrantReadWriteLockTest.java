package threading.problems.code;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    public static void main(String[] args) {

//        SharedResource sharedResource = new SharedResource();
//        sharedResource.methodA();
//        sharedResource.methodB();

        BetterSampleClass betterSampleClass = new BetterSampleClass();
        int threadCount = 10;
        Thread[] readThreads= new Thread[threadCount];
        for(int i=0;i<threadCount;i++) {
            readThreads[i] = new Thread(betterSampleClass::read, "read-thread"+i);

        }


        Thread[] writeThreads= new Thread[threadCount];
        for(int i=0;i<threadCount;i++) {
            writeThreads[i] = new Thread(betterSampleClass::write, "write-thread"+i);

        }

        for(int i=0;i<threadCount;i++) {
            readThreads[i].start();
            writeThreads[i].start();
        }

        for(int i=0;i<threadCount;i++) {
            try {
                readThreads[i].join();
                writeThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

class BetterSampleClass {
    private final ReentrantReadWriteLock reentrantLock;
    private final Lock readLock;
    private final Lock writeLock;
    private String data;

    public BetterSampleClass() {
        reentrantLock = new ReentrantReadWriteLock();
        readLock = reentrantLock.readLock();
        writeLock = reentrantLock.writeLock();
        data = "";
    }

    public void read() {
        readLock.lock();
        try {
            System.out.println("reading data: "+data);
        } finally {
            readLock.unlock();
        }
    }

    public void write() {
        writeLock.lock();
        try {
            data+= "a";
            System.out.println("writing data: "+data);
        } finally {
            writeLock.unlock();
        }
    }
}


class SharedResource {
    private final ReentrantLock reentrantLock;

    public SharedResource() {
        this.reentrantLock = new ReentrantLock();
    }

    public void methodA() {
        reentrantLock.lock();
        try {
            System.out.println("executing methodA");
            //perform critical code here
        } finally {
            reentrantLock.unlock();
        }
    }

    public void methodB() {
        reentrantLock.lock();
        try {
            System.out.println("executing methodB");
            //perform critical code here
        } finally {
            reentrantLock.unlock();
        }
    }
}

//https://www.javamadesoeasy.com/2015/04/implementation-of-customown.html


class CustomReentrantReadWriteLock {

    private int readLockCount;
    private int writeLockCount;
    private final CustomReentrantReadWriteLock.ReadLock readLock;
    private final CustomReentrantReadWriteLock.WriteLock writeLock;

    public CustomReentrantReadWriteLock() {
        readLockCount =0;
        writeLockCount =0;
        readLock = new CustomReentrantReadWriteLock.ReadLock();
        writeLock = new CustomReentrantReadWriteLock.WriteLock();
    }

    public CustomReentrantReadWriteLock.ReadLock readLock() {
        return readLock;
    }

    public CustomReentrantReadWriteLock.WriteLock writeLock() {
        return writeLock;
    }

    public class ReadLock {
        public synchronized void lock() {
            if(writeLockCount == 0) {
                ++readLockCount;
            }
            else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public synchronized void unlock() {
            --readLockCount;
            if(readLockCount == 0) {
                notifyAll();
            }
        }

    }

    public class WriteLock {
        public synchronized void lock() {
            if(writeLockCount == 0 && readLockCount == 0) {
                ++writeLockCount;
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public synchronized void unlock() {
            --writeLockCount;
            if(writeLockCount == 0) {
                notifyAll();
            }
        }

    }
}


