package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class Test6 {

	public static void main(String[] args) {
		
//		ExecutorService executorService = Executors.newFixedThreadPool(10);
//		executorService.submit(new Task());
		
		ThreadFactory factory = new MyThreadFactory(1, "factory");
		for(int i=0;i<10;i++) {
			Thread t = factory.newThread(new Task());
			t.start();
		}
	}
}

class MyThreadFactory implements ThreadFactory {
	int id;
	String name;
	
	public MyThreadFactory(int id, String name) {
		this.id=id;
		this.name=name;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r,name+"_thread_"+id);
		System.out.println("Creating with thread with name : "+name+"_thread_"+id);
		return t;
	}
	
}


class Task implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Simple task running...");
	}
}