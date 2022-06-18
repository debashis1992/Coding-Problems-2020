package threading;

import java.util.LinkedList;

public class ProducerConsumerTest {
	public static void main(String[] args) {

		Q q = new Q();
		Thread t1 = new Thread(new Producer(q), "producer thread");
		Thread t2 = new Thread(new Consumer(q), "consumer thread");

		t1.start();	// start producer thread
		t2.start();	// start consumer thread
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

class Q {
	int capacity = 2;
	LinkedList<Integer> list;

	public Q() {
		list = new LinkedList<>();
	}

	public void add() {
		int v = 0;
		while (true) {
			synchronized (this) {
				
				while (list.size() == capacity) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Adding : "+v);
				list.add(v++);
				notify();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void remove() {
		while (true) {
			synchronized (this) {
				while (list.size() == 0) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				int i = list.removeFirst();
				System.out.println("Removing : " + i);
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

class Producer implements Runnable {
	Q q;

	public Producer(Q q) {
		this.q = q;
	}

	@Override
	public void run() {
		q.add();
	}
}

class Consumer implements Runnable {
	Q q;

	public Consumer(Q q) {
		this.q = q;
	}

	@Override
	public void run() {
		q.remove();
	}
}