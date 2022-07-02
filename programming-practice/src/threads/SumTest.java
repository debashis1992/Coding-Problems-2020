package threads;

import edu.princeton.cs.algs4.StdOut;

import java.util.concurrent.*;

public class SumTest {
    public static void main(String[] args) {
        SumTest test = new SumTest();
//        test.approach1();
//        test.approach2();
//        test.approach3();

//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            System.out.println("Running completable future block..."+", Thread: "+Thread.currentThread());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            finally {
//                System.out.println("Completed...");
//            }
//        });
//
//        try {
//            completableFuture.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s+" world!").thenAccept(s -> System.out.println(s));
        try {
            completableFuture.get(1, TimeUnit.MINUTES);
        } catch(InterruptedException | ExecutionException |TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void approach3() { //using 10 threads created using executor framework
        int nThreads = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        int begin = 0;
        int c = 1;
        int interval = 100000000/nThreads;
        int end = interval;
        while(c <= nThreads) {
            RunnableSum runnableSum = new RunnableSum("thread-"+(c+1), begin, end);
            begin = end+1;
            end = interval*(c+1);
            ++c;
            executorService.execute(runnableSum);
        }
        long s = System.currentTimeMillis();
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long e = System.currentTimeMillis();
        System.out.println("time taken: "+(e-s));
    }

    public void approach2() { //using multiple threads which are user created
        RunnableSum runnableSum = new RunnableSum("thread-1", 0, 20000000);
        RunnableSum runnableSum1 = new RunnableSum("thread-2", 20000001, 40000000);
        RunnableSum runnableSum2 = new RunnableSum("thread-3", 40000001, 60000000);
        RunnableSum runnableSum3 = new RunnableSum("thread-4", 60000001, 80000000);
        RunnableSum runnableSum4 = new RunnableSum("thread-5", 80000001, 100000000);

        long start = System.currentTimeMillis();
        runnableSum.start();
        runnableSum1.start();
        runnableSum2.start();
        runnableSum3.start();
        runnableSum4.start();

        try {
            runnableSum.join();
            runnableSum1.join();
            runnableSum2.join();
            runnableSum3.join();
            runnableSum4.join();
        } catch(InterruptedException e) {e.printStackTrace();}

        long end = System.currentTimeMillis();
        System.out.println("time taken: "+(end-start));
        System.out.println("final sum: "+(runnableSum.sum+runnableSum1.sum+runnableSum2.sum+runnableSum3.sum+runnableSum4.sum));
    }

    public void approach1() { //using a single thread
        RunnableSum runnableSum = new RunnableSum("all-sum-thread", 0, 100000000);
        runnableSum.start();
        long start = System.currentTimeMillis();
        try {
            runnableSum.join();
        } catch (InterruptedException e) {e.printStackTrace();}

        long end = System.currentTimeMillis();
        System.out.println("time taken: "+(end-start));
        System.out.println("final sum: "+runnableSum.sum);
    }
}

class RunnableSum extends Thread {
    int start,end;
    long sum;
    public RunnableSum(String name, int start,int end) {
        super(name);
        this.start=start;
        this.end = end;
        this.sum = 0;
    }

    @Override
    public void run() {
        long s = 0;
        for(int i=start;i<=end;i++)
            s+= i;
        sum = s;
        System.out.println("Task completed.");
    }
}
