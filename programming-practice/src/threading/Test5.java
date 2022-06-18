package threading;

public class Test5 {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
		ConcurrentThread concurrentThread = new ConcurrentThread();

		ConcurrentThread.Th[] t = new ConcurrentThread.Th[10];
		int f = 1, l = 1000;
		for (int i = 0; i < t.length; i++) {
			t[i] = concurrentThread.new Th(f, l);
			f = l + 1;
			l = l + 1000;
			t[i].start();
		}
		try {
			for (int i = 0; i < t.length; i++)
				t[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		int max=0;
//		for(int i=0;i<t.length;i++) {
//			max = Math.max(max, t[i].max);
//		}
		long end = System.currentTimeMillis();
		System.out.println("time: " + (end - start));
		System.out.println("max : "+concurrentThread.maxV);
	}
}

class ConcurrentThread {
	public volatile int maxV;
	class Th extends Thread {

		int start;
		int end;
		int max;
		
		public Th(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			long s=System.currentTimeMillis();
			int[] c = new int[end + 1];
			for (int i = start; i <= end; i++) {
				c[i]=1;
				for (int j = 2; j <= Math.sqrt(i); j++) {
					if (i % j == 0)
						c[i]++;
				}
			}
			for (int i = start; i <= end; i++) {
				this.max = Math.max(this.max, c[i]);
			}
			maxV = Math.max(maxV, this.max);
			long e=System.currentTimeMillis();
			System.out.println("max found : " + this.max + " from thread : " + Thread.currentThread()+", time : "+(e-s));

		}
	}
}
