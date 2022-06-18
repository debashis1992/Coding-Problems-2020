package threading;

public class MultithreadingTest1 {

	public static void main(String[] args) {
//		Thread1 th1 = new Thread1("th1");
//		th1.start();
//		
//		MyRunnable myRunnable = new MyRunnable();
//		new Thread(myRunnable, "th2").start();
//		
//		System.out.println(Thread.currentThread());
//		
//		Runnable runnable = () -> {System.out.println("new runnable thread3 created");};
//		new Thread(runnable, "th3").start();
//		
//		new NewThread();
		
//		AnotherThread anotherThread = new AnotherThread();
//		Thread t1 = new Thread(anotherThread);
//		t1.start();
//		
//		
//		try {
//			t1.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Exiting main thread");
		
		Thread4 th4 = new Thread4("thread4");
		Thread4 th5 = new Thread4("thread5");
	
	}
}

class Thread4 extends Thread {
	String name;
	public Thread4(String name) {
		super(name);
		this.name = name;
		start();
	}
	
	@Override
	public void run() {
		for(int i=5;i>=0;i--) {
			System.out.println("Executing thread : "+this.name+", i : "+i);
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class AnotherThread implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			System.out.println("Exception occured...");
		} finally {
			System.out.println("Exiting another thread");
		}
		
	}
	
}
class NewThread extends Thread {
	Thread t;
	public NewThread() {
		super("Demo thread");
		t = new Thread(this, "demo thread");
		start();	//start a thread
	}
	
	@Override
	public void run() {
		try {
			for(int i=0;i<5;i++) {
				System.out.println(this.getName()+" executing: i = "+i);
				Thread.sleep(500);
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
			System.out.println(this.getName()+" thread interrupted.");
		}
	}
}

class Thread1 extends Thread {
	public Thread1(String name) {
		super(name);
		
	}
	@Override
	public void run() {
		System.out.println("Thread1 is running now");
	}
}

class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Thread2 is running now");
	}
	
}