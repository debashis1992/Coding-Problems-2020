package threading;

public class MultithreadingTest2 {

	public static void main(String[] args) {
		Callme target = new Callme();
		
		Caller c1 = new Caller(target, "caller1");
		Caller c2 = new Caller(target, "caller2");
		Caller c3 = new Caller(target, "caller3");
		
		try {
			c1.t.join();
			c2.t.join();
			c3.t.join();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}


class Callme {
	void call(String msg) {
		System.out.print("["+msg);
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print("]");
		System.out.println();
	}
}

class Caller implements Runnable {
	String msg;
	Thread t;
	Callme target;
	
	public Caller(Callme target, String msg) {
		this.target = target;
		this.msg = msg;
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		synchronized (target) {
			target.call(msg);
		}
	}
}