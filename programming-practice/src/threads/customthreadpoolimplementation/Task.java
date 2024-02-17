package threads.customthreadpoolimplementation;

public class Task implements Runnable {
    int i;

    public Task(int i) {
        this.i=i;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Value of i: "+i+", Current thread: "+Thread.currentThread());
    }
}
