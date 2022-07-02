package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AnotherExecutorFrameworkTest {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int size = 10;
        List<Future<String>> futureList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            futureList.add(executorService.submit(new StringTask()));
        }

        List<String> list = new ArrayList<>();
        for(Future<String> future: futureList) {
            try {
                list.add(future.get());
            } catch(InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(list);
        executorService.shutdown();
    }
}

class StringTask implements Callable<String> {
    @Override
    public String call() {
        //long operations

        return "call";
    }
}
