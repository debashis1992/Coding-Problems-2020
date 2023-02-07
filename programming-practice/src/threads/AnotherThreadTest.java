package threads;

public class AnotherThreadTest {
    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> System.out.println("running..."), "t1");
        t1.start();

        t1.join();

        Runnable runnable = () -> System.out.println("running again...");
        Thread t2 = new Thread(runnable);

        t2.start();
        t2.join();

    }
}
