package threads.customthreadpoolimplementation;

public class CustomExecutors {

    public static CustomExecutorService newFixedThreadPoolCustomExecutor(int capacity) {

        return new CustomExecutorService(capacity);

    }
}
