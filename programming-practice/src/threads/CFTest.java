package threads;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class CFTest {

    public static void main(String[] args) throws Exception {

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 1)
                .thenApply(x -> x+1);

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> 10)
                        .thenCompose(x -> CompletableFuture.supplyAsync(() -> x+1));

        System.out.println(cf1.get());
        System.out.println(cf2.get());


        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "")
                .handle((s, t) -> {
           if(s.length() == 0)
               return "empty string";
           else return s;
        });

        System.out.println(cf3.get());

        CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() -> "done....");
//        Thread.sleep(5000);
        System.out.println(cf4.complete("done"));

        System.out.println(cf4.get());

        CompletableFuture<String> cf5 = CompletableFuture.supplyAsync(() -> "hello")
                .thenCompose((s1) -> CompletableFuture.supplyAsync(() -> s1+"world"));
        System.out.println("thenCompose result: "+cf5.get());

        CompletableFuture<String> cf6 = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> cf7 = CompletableFuture.supplyAsync(() -> "world");

        CompletableFuture<String> cf8 = cf6.thenCombine(cf7, (s1,s2) -> s1+s2);

        System.out.println("thenCombine result: "+cf8.get());
        System.out.println("cf8 completed?: "+cf8.complete("completed..."));

        CompletableFuture<Integer> cf9 = CompletableFuture.supplyAsync(() -> "1").thenApply(Integer::valueOf);

        Thread t1 = new Thread(() -> {
            System.out.println("daemon thread running....");
            while(true)
                System.out.println("still running....");
        });
        t1.setDaemon(true);
        t1.start();
    }
}

interface UserInfo {
    public CompletableFuture<String> getUserName();
    public CompletableFuture<Integer> getUserId();
}
