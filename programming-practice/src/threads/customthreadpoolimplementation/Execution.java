package threads.customthreadpoolimplementation;

public class Execution implements Runnable {

    private final CustomExecutorService customExecutorService;

    public Execution(CustomExecutorService customExecutorService) {
        this.customExecutorService = customExecutorService;
    }

    @Override
    public void run() {
        while(true) {
            if(customExecutorService.queue.size()!=0)
                customExecutorService.queue.poll().run();
        }
    }

    public void executeTask() {
        if(customExecutorService.currentCapacity < customExecutorService.maxCapacity) {
            customExecutorService.currentCapacity++;

            Thread thread = new Thread(this);
            thread.start();
        }
    }
}
