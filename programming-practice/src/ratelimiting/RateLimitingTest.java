package ratelimiting;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimitingTest {
    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(4);
        String user1 = "u1";

        Thread[] threads = new Thread[10];
        for(int i=0;i< threads.length;i++) {
            threads[i] = new Thread(() -> {
                tokenBucket.requestToken(user1);
                try {
                    Thread.sleep(new Random().nextInt(100) * 1000);
                } catch (Exception e) {
                }
                tokenBucket.allocateToken(user1);
            }, "thread[" + i + "]");

            threads[i].start();
        }

    }
}

class TokenBucket extends TimerTask {
    Map<String, Integer> token;
    int size;
    Timer timer;
    public TokenBucket(int size) {
        this.size=size;
        this.token = new HashMap<>();
        this.timer = new Timer();
        this.timer.schedule(this, 60*1000, 60*1000);
    }

    public void run() {
        System.out.println("refreshing token bucket...at: "+LocalDateTime.now());
        token.keySet().forEach(key -> token.put(key, size));
    }
    public synchronized boolean requestToken(String userId) {
        if(!token.containsKey(userId)) {
            token.put(userId, size-1);
            System.out.println("got token for userId: "+userId+". Thread: "+Thread.currentThread());
            return true;
        } else {
            int existingToken = token.get(userId);
            if(existingToken > 0) {
                System.out.println("token fetch successful for userId: "+userId
                        +". Available tokens: "+existingToken
                +". Thread: "+Thread.currentThread());
                token.put(userId, existingToken - 1);
                return true;
            }
            System.out.println("no available tokens for userId: "+userId+". Thread: "+Thread.currentThread());
            return false;
        }
    }

    public synchronized boolean allocateToken(String userId) {
        int sizeNow = token.getOrDefault(userId, size);
        sizeNow = Math.min(sizeNow+1, size);
        token.put(userId, sizeNow);
        System.out.println("allocated token for userId: "+userId+". " +
                "Available tokens: "+token.get(userId)
                +" .Thread: "+Thread.currentThread());
        return true;
    }

}



