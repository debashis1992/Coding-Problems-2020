package threading.virtual;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
//        TestRun testRun=new TestRun();
//        Thread t1=Thread.ofPlatform().name("test-run").unstarted(testRun);
//
//        ThreadFactory threadFactory=Thread.ofVirtual().factory();
//
//
//        t1.start();
//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        int[] arr=new int[2000000];
        Random random=new Random();
        for(int i=0;i<arr.length;i++)
            arr[i]=random.nextInt(40000);

        SumTask task = new SumTask(arr, 0, arr.length);
        long sum1 = task.invoke();

        System.out.println(sum1);

        long sum2 = Arrays.stream(arr).mapToLong(x -> x).sum();
        System.out.println(sum2);

        ExecutorService service = Executors.newFixedThreadPool(1000_000_000);
        Runnable[] runnable = new Runnable[1000];
        for(int i=0;i< runnable.length;i++) {
            runnable[i] = () -> System.out.println("some task performing...");
            service.submit(runnable[i]);
        }

        service.shutdown();
    }
}

class TestRun implements Runnable {
    @Override
    public void run() {
        System.out.println("running code.. [::"+Thread.currentThread());
    }
}


class SumTask extends RecursiveTask<Long> {
    private int[] arr;
    private int start, end;
    private static int THRESHOLD = 100;

    public SumTask(int[] arr, int start, int end) {
        this.arr=arr;
        this.start=start;
        this.end=end;
    }


    @Override
    public Long compute() {
        if(end - start <= THRESHOLD) {
            long sum=0;
            for(int i=start;i<end;i++)
                sum+=arr[i];
            return sum;
        }
        else {
            int mid=start+(end-start)/2;
            SumTask left=new SumTask(arr,start,mid);
            SumTask right=new SumTask(arr,mid,end);

            left.fork(); //asynchronous

            //get the right task in the current thread
            Long rightResult = right.compute();

            //join the left task (wait if not done)
            Long leftResult = left.join();

            //finally, return the result
            return rightResult + leftResult;
        }
    }
}