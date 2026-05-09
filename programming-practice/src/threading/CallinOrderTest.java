package threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class CallinOrderTest {
    public static void main(String[] args) {

        Foo2 foo = new Foo2();
        Runnable r1 = () -> {
            while(true) {
                foo.first();
            }
        };
        Runnable r2 = () -> {
            while(true) {
                foo.second();
            }
        };
        Runnable r3 = () -> {
            while(true) {
                foo.third();
            }
        };

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");
        Thread t3 = new Thread(r3, "t3");


        t1.start();
        t2.start();
        t3.start();


        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Foo {
    private final CountDownLatch firstDone = new CountDownLatch(1);
    private final CountDownLatch secondDone = new CountDownLatch(1);

    public void first() {
        try {
            System.out.println("first called from :" + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
          firstDone.countDown();
        }
    }
    public void second() {
        try {
            firstDone.await();
            System.out.println("second called from :" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            secondDone.countDown();
        }

    }
    public void third() {
        try {
            secondDone.await();
            System.out.println("third called from :" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


class Foo2 {
    //using wait/notify mechanism
    volatile int c=0;
    public void first() {
        synchronized (this) {
            try {
                if (c == 0) {
                    System.out.println("first called from : " + Thread.currentThread().getName());
                    c = 1;
                    Thread.sleep(1000);
                    notifyAll();
                } else wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void second() {
        synchronized (this) {
            try {
                if (c != 1) {
                    this.wait();
                } else {
                    System.out.println("second called from : " + Thread.currentThread().getName());
                    c = 2;
                    Thread.sleep(1000);
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void third() {
        synchronized (this) {
            try {
                if (c != 2) {
                    this.wait();
                } else {
                    System.out.println("third called from : " + Thread.currentThread().getName());
                    c = 0;
                    Thread.sleep(1000);
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}