package threads;
import java.util.ArrayList;
import java.util.List;

// WAP to program to demonstrate the producer-consumer solution

class PCTest {
    public static void main(String[] args) {
        Q q = new Q();
        ProducerThread pThread = new ProducerThread("producer-thread-1", q);
        ConsumerThread cThread = new ConsumerThread("consumer-thread-1", q);

        pThread.start();
        cThread.start();

        ProducerThread pThread2 = new ProducerThread("producer-thread-2", q);
        ConsumerThread cThread2 = new ConsumerThread("consumer-thread-2", q);

        pThread2.start();
        cThread2.start();
        try {
            pThread.join();
            cThread.join();
            pThread2.join();
            cThread2.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ProducerThread extends Thread {
    Q q;
    public ProducerThread(String name, Q q) {
        super(name);
        this.q = q;
    }

    @Override
    public void run() {
        this.q.produce();
    }
}

class ConsumerThread extends Thread {
    Q q;
    public ConsumerThread(String name, Q q) {
        super(name);
        this.q = q;
    }

    @Override
    public void run() {
        this.q.consume();
    }

}

class Q {
    int value;
    List<Integer> list;
    public Q() {
        this.list = new ArrayList<>(1);
        this.value = 1;

        //atmost 1 element should be present in the list.
    }

    public void produce() {
        while(true) {
            synchronized (this) {
                //if size is not 0, then we cannot add any new element, consumer needs to consumer first.
                if(list.size() > 0) {
                    try {
                        wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Adding value: "+value+", Thread: "+Thread.currentThread().getName());
                    list.add(value);
                    ++value;
                    notifyAll();
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {e.printStackTrace();}
                }
            }
        }
    }

    public void consume() {
        while(true) {
            synchronized (this) {
                if(list.size() == 0) {
                    try {
                        wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    int v = list.remove(0);
                    System.out.println("Removing value : "+v+", Thread: "+Thread.currentThread().getName());
                    notifyAll();
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {e.printStackTrace();}
                }
            }
        }
    }
}