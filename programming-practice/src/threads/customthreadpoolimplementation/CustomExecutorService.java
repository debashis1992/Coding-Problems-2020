package threads.customthreadpoolimplementation;

import java.util.concurrent.LinkedBlockingDeque;

public class CustomExecutorService implements CustomExecutor {

    int currentCapacity;
    final int maxCapacity;
    LinkedBlockingDeque<Runnable> queue;
    Execution execution;

    public CustomExecutorService(int maxCapacity) {

        this.maxCapacity = maxCapacity;
        this.currentCapacity = 0;
        queue = new LinkedBlockingDeque<>();
        execution = new Execution(this);
    }

    @Override
    public void submit(Runnable runnable) {

        queue.add(runnable);
        //call execution to execute
        execution.executeTask();
    }
}
