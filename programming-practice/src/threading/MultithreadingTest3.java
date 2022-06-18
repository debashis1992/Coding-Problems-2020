package threading;

public class MultithreadingTest3 {
	public static void main(String[] args) {
		Counter counter = new Counter();
		Thread t1 = new Thread(counter, "thread 1");
		Thread t2 = new Thread(counter, "thread 2");
		Thread t3 = new Thread(counter, "thread 3");

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t1.start();
		t2.start();
		t3.start();
	}

}

class Counter implements Runnable {
	private int c = 0;

	public void increment() {
		++c;
	}

	public void decrement() {
		--c;
	}

	@Override
	public void run() {
		synchronized (this) {
			// incrementing
			increment();
			System.out.println("Thread name : " + Thread.currentThread() + ", c: " + c);
			// decrementing
			decrement();
			System.out.println("Thread name : " + Thread.currentThread() + ", c: " + c);
		}
	}

}