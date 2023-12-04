package threading;

public class ThreadingTest {

    public static void main(String[] args) {

        MyRunnable2 runnable2 = new MyRunnable2();
        Thread t1=new Thread(runnable2, "t1");
        t1.start();
        t1.start();
    }
}

class MyRunnable2 implements Runnable {
    @Override
    public void run() {
        System.out.println("running...");
    }
}
