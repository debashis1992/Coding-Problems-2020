package threads;

public class QuestionsTest {
    public static void main(String[] args) {


        Thread t1 = new Thread(() -> {
           try {
               System.out.println("running");
               System.out.println("sleeping");
               Thread.sleep(5000);
           } catch(InterruptedException e) {
               System.out.println("i was interrupted");
               e.printStackTrace();
           }
        });

        t1.start();
        t1.interrupt();

    }
}
