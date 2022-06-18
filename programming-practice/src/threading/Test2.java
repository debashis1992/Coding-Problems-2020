package threading;

/*
To print odd and even numbers using 2 different threads.
 */

public class Test2 {

	public static void main(String[] args) {
		
		T t = new T();
		OddThread oddThread = new OddThread(t);
		EvenThread evenThread = new EvenThread(t);
		
		Thread t1 = new Thread(oddThread, "odd thread");
		Thread t2 = new Thread(evenThread, "even thread");
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class T {
	private volatile int i;
	public T() {
		this.i = 0;
	}

	public void printOdd() {
		while (true) {
			synchronized (this) {
				while (i%2==0) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("i: "+i+", thread : "+Thread.currentThread());
				++i;
				notify();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void printEven() {
		while (true) {
			synchronized (this) {
				while (i%2!=0) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("i: "+i+", thread : "+Thread.currentThread());
				++i;
				notify();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class OddThread implements Runnable {
	T t;
	public OddThread(T t) {
		this.t = t;
	}
	@Override
	public void run() {
		t.printOdd();
	}

}

class EvenThread implements Runnable {
	T t;

	public EvenThread(T t) {
		this.t = t;
	}
	@Override
	public void run() {
		t.printEven();
	}

}