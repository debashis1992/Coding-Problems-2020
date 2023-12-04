package threading;

public class SynchronizationTest {
    public static void main(String[] args) {
        SolutionTest solutionTest1=new SolutionTest("name1");
        SolutionTest solutionTest2=new SolutionTest("name1");

        ThreadTest t1=new ThreadTest( "t1", solutionTest1);
        ThreadTest t2=new ThreadTest( "t2", solutionTest2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}
    }
}

class SolutionTest {
    String message;
    public SolutionTest(String m) {
        message=m;
    }

    public void print() {
        try {
            System.out.println("Printing: " + message+", Thread: "+Thread.currentThread());
            Thread.sleep(1000);
        } catch(Exception e) { e.printStackTrace(); }
    }
}

class ThreadTest extends Thread {
    final SolutionTest test;
    public ThreadTest(String name, SolutionTest test) {
        super(name);
        this.test=test;
    }

    @Override
    public void run() {
        synchronized (ThreadTest.class) {
            test.print();
        }
    }
}
