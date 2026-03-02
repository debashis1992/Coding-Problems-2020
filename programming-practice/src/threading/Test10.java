package threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Test10 {
    public static void main(String[] args) throws InterruptedException {

//        var executor = Executors.newVirtualThreadPerTaskExecutor();
//            long start = System.currentTimeMillis();
//            for(int i=0;i<100_00_00;i++) {
//                executor.submit(() -> {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            }
//            executor.shutdown();
//            long end = System.currentTimeMillis();
//        System.out.println("end - start: "+(end-start));
//
//
//        Thread.startVirtualThread(() -> {
//            try {
//                System.out.println("current thread: "+Thread.currentThread());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).join();


    }
}
