package threading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FizBuzzTest2 {
    public static void main(String[] args) {

        FizBuzz2 f2=new FizBuzz2(20);
        Runnable r1 = f2::printNumber;
        Runnable r2 = f2::printFizz;
        Runnable r3 = f2::printBuzz;
        Runnable r4 = f2::printFizBuzz;

        Thread t1 = new Thread(r1, "numbers");
        Thread t2 = new Thread(r2, "fizz");
        Thread t3 = new Thread(r3, "buzz");
        Thread t4 = new Thread(r4, "fizzBuzz");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



class FizBuzz2 {
    private int n;
    private final int cap;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition number = lock.newCondition();
    private final Condition fizz = lock.newCondition();
    private final Condition buzz = lock.newCondition();
    private final Condition fizzBuzz = lock.newCondition();

    public FizBuzz2(int n) {
        this.n=1;
        cap=n;
    }

    public void printNumber() {
        try {
            lock.lock();
            while(n<=cap) {
                while (n<=cap && (n % 3 == 0 || n % 5 == 0)) {
                    number.await();
                }
                System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                ++n;
                if(n<=cap)
                    signalNext();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    public void printFizz() {
        try {
            lock.lock();
            while(n<=cap) {
                while (n<=cap && (n % 3 != 0 || n % 5 == 0)) {
                    fizz.await();
                }
                System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                ++n;
                if(n<=cap)
                    signalNext();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printBuzz() {
        try {
            lock.lock();
            while(n<=cap) {
                while (n<=cap && (n % 5 != 0 || n % 3 == 0)) {
                    buzz.await();
                }
                System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                ++n;
                if(n<=cap)
                    signalNext();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printFizBuzz() {
        try {
            lock.lock();
            while(n<=cap) {
                while(n<=cap && (n % 3 != 0 || n % 5 != 0)) {
                    fizzBuzz.await();
                }
                System.out.println("i: "+n+", thread: "+Thread.currentThread().getName());
                ++n;
                if(n<=cap)
                    signalNext();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private void signalNext() {
        if(n%3==0 && n%5==0)
            fizzBuzz.signal();
        else if(n%3==0)
            fizz.signal();
        else if(n%5==0)
            buzz.signal();
        else number.signal();
    }
}