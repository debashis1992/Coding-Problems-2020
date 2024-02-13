package threads.customthreadpoolimplementation;

import java.util.concurrent.LinkedBlockingDeque;

public class CustomExecutorService implements CustomExecutor {
    int maxCapacity;
    int currentCapacity;

    LinkedBlockingDeque<Runnable> queue;

    public CustomExecutorService(int maxCapacity) {
        this.maxCapacity=maxCapacity;
        this.currentCapacity=0;
        queue = new LinkedBlockingDeque();
    }


    @Override
    public void submit(Runnable runnable) {
        queue.add(runnable);
    }
}
