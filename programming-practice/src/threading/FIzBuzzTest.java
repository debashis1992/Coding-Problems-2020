package threading;

public class FIzBuzzTest {
    public static void main(String[] args) {

        FizBuzz f=new FizBuzz(20);
        Runnable r1 = f::printNumber;
        Runnable r2 = f::printFizz;
        Runnable r3 = f::printBuzz;
        Runnable r4 = f::printFizBuzz;

        Thread t1 = new Thread(r1, "numbers");
        Thread t2 = new Thread(r2, "fizz");
        Thread t3 = new Thread(r3, "buzz");
        Thread t4 = new Thread(r4, "fizzbuzz");

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

class FizBuzz {
    private int n, cap;
    public FizBuzz(int n) {
        this.n=1;
        cap=n;
    }

    public synchronized void printNumber() {
        try {
            while(n<=cap) {
                if (n % 3 != 0 && n % 5 != 0) {
                    System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                    ++n;
                    notifyAll();
                } else wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void printFizz() {
        try {
            while(n<=cap) {
                if (n % 3 == 0 && n % 5 != 0) {
                    System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                    ++n;
                    notifyAll();
                } else wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void printBuzz() {
        try {
            while(n<=cap) {
                if (n % 3 != 0 && n % 5 == 0) {
                    System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                    ++n;
                    notifyAll();
                } else wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void printFizBuzz() {
        try {
            while(n<=cap) {
                if (n % 3 == 0 && n % 5 == 0) {
                    System.out.println("i: " + n + ", thread: " + Thread.currentThread().getName());
                    ++n;
                    notifyAll();
                } else wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}