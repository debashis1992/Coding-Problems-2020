package jpmc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        CircuitBreaker circuitBreaker = new CircuitBreaker(10, 1000,
                3000);

        for(int i=0;i<20;i++) {
            try {
                circuitBreaker.execute(() -> {
                    throw new RuntimeException("manual error trigger");

                });
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(circuitBreaker.getErrorCount());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(int i=0;i<10;i++) {
            try {
                System.out.println(circuitBreaker.execute(() -> "result"));

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }


    }
}


/**
 Requirements:-

 1) Count errors within a given time period, if the number of errors in the period
 exceeds or reaches a configurable level then the circuit breaker should open.
 2) When the circuit breaker is open, this means that all executions should fail fast.
 3) After a configurable period of time, the circuit breaker should close.
 This means that executions will no longer fail fast.
 **/

class CircuitBreaker {

    private int threshold;
    private Deque<Long> errorQueue;
    private long thresholdTimePeriodInMillis;
    private long circuitBreakerCloseTimeInMillis;

    public CircuitBreaker(int threshold, long thresholdTimePeriodInMillis, long circuitBreakerCloseTimeInMillis) {
        this.threshold = threshold;
        this.thresholdTimePeriodInMillis = thresholdTimePeriodInMillis;
        this.circuitBreakerCloseTimeInMillis = circuitBreakerCloseTimeInMillis;
        this.errorQueue = new LinkedList<>();
    }

    public <T> T execute(Supplier<T> supplier) {
        long now = System.currentTimeMillis();
        if(errorQueue.size() >= threshold && (!errorQueue.isEmpty()
                && now < (circuitBreakerCloseTimeInMillis + errorQueue.peekLast()))) {
            executeFallBackPlan();
        }

        // 10, 20, 30, 40, 70 -> 60

        try {
            return supplier.get();
        } catch (Throwable e) {
            System.out.println("exception occurred: "+e.getMessage() + ", at time: "+System.currentTimeMillis());

            while(!errorQueue.isEmpty() && (now - errorQueue.peek())  >= thresholdTimePeriodInMillis) {
                errorQueue.poll();
            }
            if(errorQueue.size() == threshold) {
                executeFallBackPlan();
            }
            else {
                errorQueue.add(now);
            }
            throw e;
        }
    }

    public int getErrorCount() {
        return errorQueue.size();
    }

    public Exception executeFallBackPlan() throws RuntimeException {
        throw new RuntimeException("circuit breaker open. This is fallback plan. Time: "+System.currentTimeMillis());
    }




}
