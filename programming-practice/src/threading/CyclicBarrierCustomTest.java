package threading;

public class CyclicBarrierCustomTest {
    public static void main(String[] args) {

        CyclicBarrierCustom cyclicBarrierCustom = new CyclicBarrierCustom(3);
        Thread[] threads = new Thread[3];
        for(int i=0;i<3;i++) {
            threads[i] = new Thread(new GenericThread(cyclicBarrierCustom), "thread-"+i);
        }

        for(int i=0;i<3;i++) {
            threads[i].start();
        }

        try {
            for (int i = 0; i < 3; i++) {
                threads[i].join();
            }
        } catch(InterruptedException e) {}

    }
}

class GenericThread implements Runnable {
    CyclicBarrierCustom cyclicBarrierCustom;
    public GenericThread(CyclicBarrierCustom cyclicBarrierCustom) {
        this.cyclicBarrierCustom=cyclicBarrierCustom;
    }
    @Override
    public void run() {
        System.out.println("trying to reach the barrier: "+Thread.currentThread());
        try {
            System.out.println("waiting at the barrier: "+Thread.currentThread());
            cyclicBarrierCustom.await();
        } catch(InterruptedException e) {

        }
    }
}

class CyclicBarrierCustom {
    int initialParties, waitingParties;
    CyclicBarrierRun cyclicBarrierRun;

    public CyclicBarrierCustom(int initialParties) {
        this.initialParties=initialParties;
        this.waitingParties=0;
        this.cyclicBarrierRun=new CyclicBarrierRun();
    }


    public synchronized void await() throws InterruptedException {
        // if initial parties == waiting parties, then notify all waiting parties
        waitingParties++;
        if(initialParties == waitingParties) {
            waitingParties = 0;
            this.notifyAll();
            cyclicBarrierRun.run();
        } else {
            this.wait();
        }
    }
}

class CyclicBarrierRun {
    public void run() {
        System.out.println("all waiting threads have reached the barrier point, tripping the barrier now...");
    }
}