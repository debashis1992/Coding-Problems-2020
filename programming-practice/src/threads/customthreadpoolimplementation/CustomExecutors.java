package threads.customthreadpoolimplementation;

public class CustomExecutors {

    public static CustomExecutorService newFixedThreadPoolExecutor(int capacity) {
        return new CustomExecutorService(capacity);
    }
}
