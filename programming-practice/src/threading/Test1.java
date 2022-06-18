package threading;

public class Test1 {

	public static void main(String[] args) {

		T1 t = new T1();
		Thread t1 = new Thread(t, "T1");
		Thread t2 = new Thread(t, "T2");
		Thread t3 = new Thread(t, "T3");
		Thread t4 = new Thread(t, "T4");

		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
//		MyRun run = () -> System.out.println("runnable running");
//		new Thread(run).start();
//
//		Runnable runnable = () -> System.out.println("lambda running");
//		new Thread(runnable).start();
	}
}

class T1 implements Runnable {

	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				System.out.println("Thread : " + Thread.currentThread().getName() + ", i : " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


interface MyRun extends Runnable {
	public void run();
	
}