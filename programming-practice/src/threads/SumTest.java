package threads;

public class SumTest {
    public static void main(String[] args) {
        SumTest test = new SumTest();
        test.approach2();
//        test.approach2();
    }

    public void approach2() {
        RunnableSum runnableSum = new RunnableSum("thread-1", 0, 20000000);
        RunnableSum runnableSum1 = new RunnableSum("thread-2", 20000001, 40000000);
        RunnableSum runnableSum2 = new RunnableSum("thread-3", 40000001, 60000000);
        RunnableSum runnableSum3 = new RunnableSum("thread-4", 60000001, 80000000);
        RunnableSum runnableSum4 = new RunnableSum("thread-5", 80000001, 100000000);

        long start = System.currentTimeMillis();
        runnableSum.start();
        runnableSum1.start();
        runnableSum2.start();
        runnableSum3.start();
        runnableSum4.start();

        try {
            runnableSum.join();
            runnableSum1.join();
            runnableSum2.join();
            runnableSum3.join();
            runnableSum4.join();
        } catch(InterruptedException e) {e.printStackTrace();}

        long end = System.currentTimeMillis();
        System.out.println("time taken: "+(end-start));
        System.out.println("final sum: "+(runnableSum.sum+runnableSum1.sum+runnableSum2.sum+runnableSum3.sum+runnableSum4.sum));
    }

    public void approach1() {
        RunnableSum runnableSum = new RunnableSum("all-sum-thread", 0, 100000000);
        runnableSum.start();
        long start = System.currentTimeMillis();
        try {
            runnableSum.join();
        } catch (InterruptedException e) {e.printStackTrace();}

        long end = System.currentTimeMillis();
        System.out.println("time taken: "+(end-start));
        System.out.println("final sum: "+runnableSum.sum);
    }
}

class RunnableSum extends Thread {
    int start,end;
    long sum;
    public RunnableSum(String name, int start,int end) {
        super(name);
        this.start=start;
        this.end = end;
        this.sum = 0;
    }

    @Override
    public void run() {
        long s = 0;
        for(int i=start;i<=end;i++)
            s+= i;
        sum = s;
        System.out.println("Task completed.");
    }
}
