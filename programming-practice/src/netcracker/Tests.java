package netcracker;

import java.util.*;
import java.util.concurrent.*;

public class Tests {
    public static void main(String[] args) {

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(new Task());
//
//        executorService.shutdown();

//        Ex ex = () -> System.out.println("running");
//        ex.execute();
//
//        Runnable runnable = new Running();
//        Executor executor = Runnable::run;
//        executor.execute(runnable);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        Random random = new Random();
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i=0;i<100;i++) {
            futures.add(executorService.submit(() -> random.nextInt(5)));
        }

        Map<Integer,Integer> map = new HashMap<>();
        futures.forEach(future -> {
            try {
                Integer res = future.get(3, TimeUnit.SECONDS);
                map.put(res, map.getOrDefault(res, 0)+1);
            } catch (Exception e) {
                map.put(-1, map.getOrDefault(-1, 0)+1);
            }
        });

        System.out.println(map);

        executorService.shutdown();
    }
}

class Running implements Runnable {
    @Override
    public void run() {
        System.out.println("runnable");
    }
}

interface Ex {
    void execute();
}

class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("running....");
    }
}

