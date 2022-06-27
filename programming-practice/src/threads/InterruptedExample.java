package threads;

public class InterruptedExample {
    public static void main(String[] args) {

        Thread sleepyThread = new Thread(() -> {
            try {
                System.out.println("Going for sleep for an hour...");
                Thread.sleep(1000 * 60 * 60);
            } catch (InterruptedException e) {
                System.out.println("Interrupted during my sleep...");
                System.out.println("is interrupted: "+Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("is interrupted: "+Thread.currentThread().isInterrupted());
            }
        });

        sleepyThread.start();
        System.out.println("about to wake up the sleepy thread...");
        sleepyThread.interrupt();
        System.out.println("woke up the sleepy thread...");
        try {
            sleepyThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
