package threading;

public class CountDownLatchCustomTest {

    public static void main(String[] args) {

        CountDownLatchCustom c = new CountDownLatchCustom(2);
        Task1 task1 = new Task1(c);
        Task1 task2 = new Task1(c);
        Thread t1=new Thread(task1);
        Thread t2=new Thread(task2);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {

        }

        t1.start();
        t2.start();

        try {
            c.await();
        } catch (InterruptedException e) {

        }
        System.out.println("main resumed from here.....");
    }
}

class Task1 implements Runnable {
    CountDownLatchCustom c;
    public Task1(CountDownLatchCustom c) {
        this.c=c;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println("task done here...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            c.countDown();
        }
    }
}

class CountDownLatchCustom {
    private volatile int count;

    public CountDownLatchCustom(int count) {
        this.count=count;
    }

    public synchronized void countDown() {
        count--;
        if(count == 0) {
            this.notify();
        }
    }

    public synchronized void await() throws InterruptedException {
        if(count > 0)
            this.wait();
    }
}