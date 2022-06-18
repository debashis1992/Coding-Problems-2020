package threading;

public class Test3 {

	public static void main(String[] args) {
		
		JustAThread th = new JustAThread();
		Thread jt1 = new Thread(th);
		Thread jt2 = new Thread(th);
		Thread jt3 = new Thread(th);
		
		jt1.start();
		jt2.start();
		jt3.start();
		
		try {
			jt1.join();
			jt2.join();
			jt3.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


class ThreadSafeCounter {
	public static class Counter {
		int count;
		public void inc() {
			synchronized (this) {
				count=count+1;
				System.out.println("Count: "+count+", current thread : "+Thread.currentThread());
			}
		}
		public int getCount() {
			return count;
		}
	}
}

class JustAThread implements Runnable {
	
	private ThreadSafeCounter.Counter counter;
	
	public JustAThread() {
		ThreadSafeCounter.Counter newCounter = new ThreadSafeCounter.Counter();
		this.counter = newCounter;
	}
	
	@Override
	public void run() {
		while(true) {
			this.counter.inc();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}