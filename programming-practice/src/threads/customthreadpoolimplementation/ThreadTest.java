package threads.customthreadpoolimplementation;

public class ThreadTest {
    public static void main(String[] args) {

        int threadPoolCount = 5;
        CustomExecutorService customExecutorService = CustomExecutors.newFixedThreadPoolExecutor(threadPoolCount);

        int tasksCount = 50;
        for(int i=0;i<tasksCount;i++)
            customExecutorService.submit(new Task(i));



    }
}
