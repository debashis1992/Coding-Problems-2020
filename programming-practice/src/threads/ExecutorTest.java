package threads;

import java.util.concurrent.*;

public class ExecutorTest {
    public static void main(String[] args) {

        //different types of java executors.
        //1. SingleThreadExecutors
        ExecutorService executorService = Executors.newSingleThreadExecutor(); //single thread

        //2. FixedThreadPool of size(n)
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);

        //3. CachedThreadPool
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        //4. ScheduledExecutor
        ExecutorService executorService3 = Executors.newScheduledThreadPool(10);

        Future<String> future = executorService.submit(() -> "Running task");

        try {
            System.out.println(future.get(1000, TimeUnit.MILLISECONDS));
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
