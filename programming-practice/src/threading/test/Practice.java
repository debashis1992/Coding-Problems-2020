package threading.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Practice {
    public volatile int x=5;
    public static void main(String[] args) {
        Practice practice = new Practice();

        Thread t1 = new Thread(() -> {
           for(int i=0;i<10;i++) {
               try {
                   Thread.sleep(1000);
                   System.out.println("running thread: "+Thread.currentThread());
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });


//        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable myRunnable = () -> System.out.println("runnable got executed.");
        Thread t2 = new Thread(myRunnable);
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Runnable myRunnable2 = () -> {
            synchronized (practice) {
                System.out.println("value present: "+practice.x);
                practice.x++;
                System.out.println("value after: "+practice.x);
            }
        };

        Thread t4 = new Thread(myRunnable2);
        Thread t5 = new Thread(myRunnable2);

        t4.start();
        t5.start();
        try {
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("value of x: "+practice.x);

        // producer-consumer problem
        Resource resource = new Resource();
        Runnable producer = new Producer(resource);
        Runnable consumer = new Consumer(resource);

        Thread producerThread = new Thread(producer, "producer-thread");
        Thread consumerThread = new Thread(consumer, "consumer-thread");

//        producerThread.start();
//        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            System.out.println("exception occurred: " + e.getMessage());
        }

        CallableImpl<String> impl = new CallableImpl<>("some value");
        CallableImpl<Double> impl2 = new CallableImpl<>(112.343);
        try {
//            impl.call();
//            impl2.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CallableImpl<Integer> task = new CallableImpl<>(1000);
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<Integer> future = service.submit(task);
        try {
            Integer res = future.get();
            System.out.println("got result : " + res);
            service.shutdown();
            System.out.println(future.isCancelled());
            System.out.println(future.isDone());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("exception occurred : " + e.getMessage());
                    }

                    return "hello world!!";
                });

        completableFuture.join();
        completableFuture.thenAccept(System.out::println);

        CompletableFuture<Integer> completableFuture1 = CompletableFuture
                .supplyAsync(() -> 100)
                .thenApplyAsync(s -> s+2)
                .thenApplyAsync(s -> s/2);

        completableFuture1.join();
        completableFuture1.thenAccept(s -> System.out.println("final result : " + s));


        CompletableFuture<String> cp1 = CompletableFuture.supplyAsync(() -> "result1");
        CompletableFuture<String> cp2 = CompletableFuture.supplyAsync(() -> "result2");
        CompletableFuture<String> cp3 = CompletableFuture.supplyAsync(() -> "result3");

        CompletableFuture<Void> allCp = CompletableFuture.allOf(cp1, cp2, cp3);
        allCp.thenRun(() -> {
            String result1 = cp1.join();
            String result2 = cp2.join();
            String result3 = cp3.join();

            System.out.println("Final results : " + result1 + ", " + result2 + ", " + result3);
        });



    }
}

class CallableImpl<T> implements Callable<T> {
    T t;
    public CallableImpl(T t) {
        this.t=t;
    }

    @Override
    public T call() throws Exception {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("exception occurred: "+ e.getMessage());
        }
        System.out.println("calling value: " + t);
        return t;
    }
}

class Producer implements Runnable {
    private final Resource resource;
    public Producer(Resource resource) {
        this.resource=resource;
    }

    @Override
    public void run() {
        while(true) {
            resource.add();
        }
    }
}

class Consumer implements Runnable {
    private final Resource resource;
    public Consumer(Resource resource) {
        this.resource=resource;
    }

    @Override
    public void run() {
        while(true) {
            resource.remove();
        }
    }

}

class Resource {
    private int x;
    private final List<Integer> list;
    public Resource() {
        x=0;
        list=new ArrayList<>();
    }

    public synchronized void add() {
        try {
            while(!list.isEmpty()) {
                wait();
            }
            ++x;
            list.add(x);
            System.out.println("Added new element: " + list.get(0) +" , current thread: " + Thread.currentThread().getName());
            Thread.sleep(1000);
            notifyAll();

        } catch (InterruptedException e) {
            System.out.println("exception occurred while adding: "+e.getMessage());
        }
    }

    public synchronized void remove() {
        try {
            while(list.isEmpty())
                wait();

            int element = list.get(0);
            list.clear();
            System.out.println("Removing element: " + element + " , current thread: " + Thread.currentThread().getName());
            Thread.sleep(1000);
            notifyAll();
        } catch (InterruptedException e) {
            System.out.println("exception occurred while removing: " + e.getMessage());
        }
    }
}


