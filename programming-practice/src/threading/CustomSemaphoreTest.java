package threading;

public class CustomSemaphoreTest {
}

class SemaphoreCustom {
    int permits;

    public SemaphoreCustom(int permits) {
        this.permits=permits;
    }

    public synchronized void acquire() throws InterruptedException {
        if(permits <= 0)
            this.wait();
        permits--;
    }

    public synchronized void release() {
        permits++;
        if(permits > 0)
            this.notifyAll();
    }
}
