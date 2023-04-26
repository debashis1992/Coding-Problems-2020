package threads;

public class YieldTest {
    public static void main(String[] args) {

        Runnable yt = new YT();
        Thread t1=new Thread(yt, "t1");
        Thread t2=new Thread(yt, "t2");

        t1.setPriority(4);
        t2.setPriority(8);
        try {
            t1.join();
            t2.join();

            t1.start();
            t2.start();
        } catch(InterruptedException e) {}
    }
}


class YT implements Runnable {
    @Override
    public void run() {
        System.out.println("started thread: "+Thread.currentThread());
        Thread.yield();
        System.out.println("Ended thread: "+Thread.currentThread());
    }
}
