package test1;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitingTest extends TimerTask  {
//    Design a rate limiter per userId
//
//    N tokens -> userId
//    T seconds refresh
//
//
//    10 tokens/minute
//    Refresh time = 1 minute

    // token bucket
    // leaky bucket
    // fixed window
    // sliding window

    // token bucket
    ConcurrentHashMap<String, Integer> tokenBucket;
    int capacity;
    int refreshRate;
    Timer timer;

    public RateLimitingTest(int capacity, int refreshRate) {
        this.capacity = capacity;
        this.refreshRate = refreshRate;
        tokenBucket = new ConcurrentHashMap<>();
        this.timer = new Timer();
        this.timer.schedule(this, 1000, refreshRate * 1000);
    }

    public boolean validateRequest(String userId) {
        synchronized (userId) {
            if (tokenBucket.containsKey(userId)) {
                int currentTokenCount = tokenBucket.get(userId);
                if (currentTokenCount == 0) {
                    System.out.println("no tokens available for this userId: " + userId);
                    return false;
                }
                else tokenBucket.put(userId, currentTokenCount - 1);
                return true;
            } else {
                System.out.println("new userId received: " + userId);
                tokenBucket.put(userId, capacity - 1);
                return true;
            }
        }
    }
    // 1 minute = refresh

    // tokenbucket = { u1: 10 , u2: 9 }
    // userId u1 -> 5
    // userId u1 -> 4

    @Override
    public void run() {
        for(Map.Entry<String, Integer> entry: tokenBucket.entrySet()) {
            tokenBucket.put(entry.getKey(), capacity);
        }
    }


    public static void main(String[] args) {

        RateLimitingTest test = new RateLimitingTest(10, 1);
        for(int i=0;i<100;i++) {
            System.out.println(test.validateRequest("u1"));
        }

        for(int i=0;i<5;i++) {
            System.out.println(test.validateRequest("u1"));
        }




    }


}


