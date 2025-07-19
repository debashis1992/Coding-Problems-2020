package threads;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {
    public static void main(String[] args) {

        ForkJoinPool pool = ForkJoinPool.commonPool();
        SumTask task = new SumTask(1, 100);

        Long result = pool.invoke(task);
        System.out.println("Result: "+result);


        PrintTask task2 = new PrintTask(1, 10);
        pool.invoke(task2);
        System.out.println("completed..");

    }
}

class PrintTask extends RecursiveAction {
    private int low;
    private int high;
    private static final int THRESHOLD = 10;
    public PrintTask(int low, int high) {
        this.low=low;
        this.high=high;
    }
    @Override
    protected void compute() {
        if(high - low <= THRESHOLD) {
            for(int i=low;i<=high;i++)
                System.out.println("performing print...");
        }
        else {
            int mid = low + (high-low)/2;
            PrintTask leftTask = new PrintTask(low, mid);
            PrintTask rightTask = new PrintTask(mid+1, high);

            invokeAll(leftTask, rightTask);
        }
    }
}

class SumTask extends RecursiveTask<Long> {
    private int low;
    private int high;

    public SumTask(int low, int high) {
        this.low=low;
        this.high=high;
    }

    @Override
    protected Long compute() {
        if(high - low <= 10) {
            long sum=0;
            for(int i=low;i<=high;i++)
                sum+= i;
            System.out.println("Current thread: "+Thread.currentThread());
            return sum;
        } else {
            int mid = low + (high-low)/2;
            SumTask leftTask = new SumTask(low, mid);
            SumTask rightTask = new SumTask(mid+1, high);

            leftTask.fork();
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();

            return leftResult+rightResult;
        }
    }
}
