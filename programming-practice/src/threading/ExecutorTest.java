package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

	public static void main(String[] args) {
		
		// get the count of the number of CPU cores
		int count = Runtime.getRuntime().availableProcessors();
		System.out.println("Current count : "+count);
		ExecutorService service = Executors.newFixedThreadPool(count);
		
		
		for(int i=0;i<500;i++) {
			MyTask task = new MyTask(10000L);
			
			// execute method returns a void, hence a Runnable task is being used
			service.execute(task);
		}
		
		// initiate a shutdown, no new threads can be added to the executor now.
		service.shutdown();
		
		try {
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finishing all threads");
	}
}

class MyTask implements Runnable {
	
	long lim;
	public MyTask(long lim) {
		this.lim = lim;
	}

	@Override
	public void run() {
		long sum=0;
		for(long i=0;i<lim;i++)
			sum+= i;
		
		System.out.println(sum);
	}
	
}