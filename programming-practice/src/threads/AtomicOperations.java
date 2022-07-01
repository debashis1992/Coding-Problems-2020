package threads;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicOperations {
    volatile AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        //atomic classes examples
        AtomicInteger atomicFive = new AtomicInteger(5);
        AtomicInteger atomicAlsoFive = new AtomicInteger(5);

        System.out.println(atomicFive.equals(atomicAlsoFive));
        System.out.println(atomicFive == atomicAlsoFive);
        System.out.println(atomicFive.hashCode()+" "+atomicAlsoFive.hashCode());

        Integer i1 = new Integer(23235);
        Integer i2 = new Integer(23235);
        System.out.println(i1.equals(i2));
        System.out.println(i1 == i2);
        System.out.println(i1.hashCode()+" "+i2.hashCode());

        Thread t1 = new Thread(() -> {
            System.out.println("thread executing....");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread execution complete.");
            }
        });

        t1.start();
        try {
            t1.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

