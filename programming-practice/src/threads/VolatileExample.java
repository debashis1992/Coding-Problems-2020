package threads;

public class VolatileExample {

    private static volatile int MY_INT = 0;
    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();

    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {
            int localVariable = MY_INT;
            while(MY_INT < 5) {
                System.out.println("Incrementing value: "+(++localVariable));
                MY_INT = localVariable;

                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int localVariable = MY_INT;
            while(localVariable < 5) {
                if(localVariable != MY_INT) {
                    System.out.println("Got value : "+MY_INT);
                    localVariable = MY_INT;
                }
            }
        }
    }
}

class Condition {
     boolean flag = true;
     int count;
    public void revert() {
        flag = false;
    }
}


class DoingUsefulThread extends Thread {
    Condition condition;
    public DoingUsefulThread(String name, Condition condition) {
        super(name);
        this.condition = condition;
    }
    @Override
    public void run() {
        boolean flag = condition.flag;
        while(flag) {
            System.out.println("Doing something useful...");
            flag = condition.flag;
        }

        System.out.println("Stopping at "+condition.count+", Thread: "+Thread.currentThread().getName());
    }
}

class StoppingThread extends Thread {
    Condition condition;
    public StoppingThread(String name, Condition condition) {
        super(name);
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<100;i++)
            ++condition.count;

        System.out.println("Stopping at "+condition.count+", Thread: "+Thread.currentThread().getName());
        condition.revert();
    }
}

class MyCounter {
    int val;

    public MyCounter() {
        this.val = 0;
    }

    public void increment() {
        ++this.val;
    }
}

class ReadingThread extends Thread {
    MyCounter counter;

    public ReadingThread(String name, MyCounter counter) {
        super(name);
        this.counter = counter;

    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Reading value: " + counter.val + ", Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class WritingThread extends Thread {
    MyCounter counter;

    public WritingThread(String name, MyCounter counter) {
        super(name);
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            counter.increment();
            System.out.println("New value: " + counter.val + ", Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}