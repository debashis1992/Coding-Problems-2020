package testing;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TestCode {
    public static void main(String[] args) {

        ExecutorService executorService= Executors.newFixedThreadPool(10);

        int timeout = 500;
        ApiResponse errorApiResponse = new ApiResponse(500);

        long start=System.currentTimeMillis();

        CompletableFuture<ApiResponse> completableFuture1 =
                CompletableFuture.supplyAsync(() -> callProvider("A"), executorService)
                        .orTimeout(timeout, TimeUnit.MILLISECONDS)
                        .exceptionally(ex -> errorApiResponse);

        CompletableFuture<ApiResponse> completableFuture2 =
                CompletableFuture.supplyAsync(() -> callProvider("ABC"), executorService)
                        .orTimeout(timeout, TimeUnit.MILLISECONDS)
                        .exceptionally(ex -> errorApiResponse);

        CompletableFuture<ApiResponse> completableFuture3 =
                CompletableFuture.supplyAsync(() -> callProvider("XWDFWFW"), executorService)
                        .orTimeout(timeout, TimeUnit.MILLISECONDS)
                        .exceptionally(ex -> errorApiResponse);

        List<CompletableFuture<ApiResponse>> completableFutureList =
                Arrays.asList(completableFuture1, completableFuture2, completableFuture3);

        CompletableFuture<Void> all =
                CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));

        all.join();

        try {
            List<ApiResponse> apiResponses = completableFutureList.stream()
                    .map(CompletableFuture::join)
                    .filter(res -> res.statusCode == 200)
                    .toList();

        } finally {
            long end=System.currentTimeMillis();
            System.out.println("overall time: "+(end-start));
        }

        executorService.shutdown();

    }

    public static ApiResponse callProvider(String provider) {
        Random random=new Random();
        int time = provider.hashCode() > 0 ? provider.hashCode() : -1*provider.hashCode();
        int randomTime = random.nextInt(time);

        try {
            Thread.sleep(randomTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new ApiResponse(200);
    }
}


class ApiResponse {
    int statusCode;

    public ApiResponse(int statusCode) {
        this.statusCode=statusCode;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                '}';
    }
}

