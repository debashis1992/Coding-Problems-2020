package threading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AugTest {
    public static void main(String[] args) {

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
                })
                .thenApplyAsync((s) -> s/2)
                .thenApplyAsync((s) -> s+100);

        CompletableFuture<Void> completableFuture1 = CompletableFuture.allOf(completableFuture);
        completableFuture1.join();

        completableFuture.thenAccept((s) -> System.out.println("result: "+s));

        try(ExecutorService service = Executors.newSingleThreadExecutor()) {
            service.submit(() -> System.out.println("running some task"));
        }





    }
}
