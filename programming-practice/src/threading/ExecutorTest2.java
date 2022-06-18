package threading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Creating and executing a Simple Executor
 */
public class ExecutorTest2 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		CallableTask task = new CallableTask("world");
		
		// submit method will return a valid result as Future object, hence a Callable task is being used.
		Future<String> future = executor.submit(task);
		
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		
	}
	
	
}


class CallableTask implements Callable<String> {
	
	String message;
	
	public CallableTask(String message) {
		this.message = message;
	}

	@Override
	public String call() throws Exception {
		return "Hello "+message+"!";
	}
	

	
}