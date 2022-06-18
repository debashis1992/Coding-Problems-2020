package threads;

import javax.xml.transform.Source;

public class ThreadCreationTest {
    public static void main(String[] args) {

        // main thread
//        for(int i=0;i<10;i++) {
//            System.out.println("value : "+i);
//            try {
//                Thread.sleep(2000);  // 1 sec
//            } catch(InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        MyThread thread1 = new MyThread("testing-thread1");
        MyThread thread2 = new MyThread("testing-thread2");
        MyThread thread3 = new MyThread("testing-thread3");
        MyThread thread4 = new MyThread("testing-thread4");
        MyThread thread5 = new MyThread("testing-thread5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        // thread creation -> init -> runnable -> start -> running -> shutdown

        // thread pool -> t1,t2,t3,t4 -- again managed by JVM

        // any child object can be stored into a parent class reference variable
        A a = new B();

        Runnable runnable = new AnotherThread();
        runnable.run();

        //latest java implementation
        Thread thread6 = new Thread(() -> {
            for(int i=0;i<5;i++)
                System.out.println(i);
            System.out.println("printing from new thread");
        });
        thread6.start();
    }
}

class A {

}

class B extends A {

}

class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("running myThread -- "+this.getName());
    }
}

class AnotherThread implements Runnable {
    @Override
    public void run() {
        System.out.println("printing from another thread");
    }
}
