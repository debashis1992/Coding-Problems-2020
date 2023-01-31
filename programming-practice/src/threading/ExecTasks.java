package threading;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;



public class ExecTasks {
    public static long n=100;
    public static void sum() {
        long start=System.currentTimeMillis();
        long sum=0;
        for(int i=1;i<=n;i++)
            sum+=i;


        System.out.println("sum: "+sum);
        long end=System.currentTimeMillis();
        System.out.println("time taken: "+(end-start));
    }

    public static void sumUsingExec() {
        long startTime=System.currentTimeMillis();
        long start=1, end=n/10;

        List<Future<Long>> resultList = new ArrayList<>();
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++) {

            Callable callable = new SumTask(start, end);
            Future<Long> future = exec.submit(callable);
            resultList.add(future);
            start=end+1;
            end+=end;
        }

        long sum=0;
        for(Future<Long> future: resultList) {
            try {
                sum+= future.get();
            } catch(Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("sum: "+sum);
        long endTime=System.currentTimeMillis();
        System.out.println("time taken: "+(endTime-startTime));

        exec.shutdown();
    }


    public static void main(String[] args) {
        //sum();

        sumUsingExec();
    }

}


class SumTask implements Callable<Long> {

    long start;
    long end;
    public SumTask(long i,long j) {
        start=i;
        end=j;
    }

    @Override
    public Long call() throws Exception {
        long sum=0;
        for(long i=start;i<=end;i++)
            sum+= i;

        System.out.println("start: "+start+", end: "+end+", sum: "+sum);
        return sum;
    }
}

