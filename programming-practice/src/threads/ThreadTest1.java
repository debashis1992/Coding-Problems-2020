package threads;

/**
 * Counter program to print odd & even elements using 2 different threads
 */
public class ThreadTest1 {
    public static void main(String[] args) {

        Counter counter = new Counter();
        OddThread oddThread = new OddThread("odd-thread", counter);
        EvenThread evenThread = new EvenThread("even-thread", counter);

        oddThread.start();
        evenThread.start();

        try {
            //wait for all threads to complete
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Counter {
    int count;

    public Counter() {
        this.count = 0;
    }

    public void printOdd() {
        synchronized (this) {
            while (true) {
                if (count % 2 == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Element: " + count + ", by " + Thread.currentThread());
                    ++count;
                    notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void printEven() {
        synchronized (this) {
            while (true) {
                if (count % 2 != 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Element: " + count + ", by " + Thread.currentThread());
                    ++count;
                    notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


class EvenThread extends Thread {
    Counter counter;

    public EvenThread(String name, Counter counter) {
        super(name);
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.printEven();
    }
}

class OddThread extends Thread {
    Counter counter;

    public OddThread(String name, Counter counter) {
        super(name);
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.printOdd();
    }
}