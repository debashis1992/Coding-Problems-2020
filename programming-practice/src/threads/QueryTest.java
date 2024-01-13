package threads;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class QueryTest {

    public static void main(String[] args) {
        Solve solve=new Solve();
        Runnable runnable = () -> solve.test();
        Thread t1 = new Thread(runnable, "some-thread");
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {}

        CallableTest<String> callableTest = new CallableTest<>("some-test");
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> f = service.submit(() -> callableTest.call());

        try {
            String res = f.get(1000, TimeUnit.MILLISECONDS);
            System.out.println(res);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }

        MyTask myTask=new MyTask();


    }
}

class Solve {
    private final static Object t = new Object();
    public static void test() {
        synchronized (t) {
            System.out.println("do something");
        }
    }
}


class CallableTest<T> implements Callable<T> {
    T t;
    public CallableTest(T t) {
        this.t=t;
    }
    @Override
    public T call() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        return t;
    }
}


class MyTask extends TimerTask {
    Timer timer;
    public MyTask() {
        timer = new Timer();
        timer.schedule(this, 1000, 5000);
    }
    @Override
    public void run() {
        System.out.println("---Thread:"+Thread.currentThread()+"---");
        System.out.println("running some task");
        System.out.println("done");
        System.out.println("--------------");
    }
}