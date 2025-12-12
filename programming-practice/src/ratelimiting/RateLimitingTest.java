package ratelimiting;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class RateLimitingTest {
    public static void main(String[] args) throws InterruptedException {

//        long timestamp = Instant.now().toEpochMilli();
//        System.out.println(timestamp);

//        TokenBucket tokenBucket = new TokenBucket(6);
//        String user1 = "u1";
//
//        Thread[] threads = new Thread[10];
//        for(int i=0;i< threads.length;i++) {
//            threads[i] = new Thread(() -> {
//                tokenBucket.requestToken(user1);
//                try {
//                    Thread.sleep(new Random().nextInt(100) * 1000);
//                } catch (Exception e) {
//                    System.out.println("exception occurred: "+e.getMessage());
//                }
////                tokenBucket.allocateToken(user1);
//            }, "thread[" + i + "]");
//
//            threads[i].start();
//        }


//        LeakyBucket leakyBucket = new LeakyBucket(5);
//        Thread[] t1 = new Thread[10];
//        for(int i=0;i<t1.length;i++) {
//            t1[i] = new Thread(() -> {
//                try {
//                    Thread.sleep(1000);
//                    leakyBucket.requestToken();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }, "t1"+i);
//            t1[i].start();
//            try {
//                t1[i].join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }


        SlidingWindowLog slidingWindowLog = new SlidingWindowLog(5, 1);
        Thread[] threads = new Thread[10];
        for(int i=0;i<threads.length;i++) {
            threads[i] = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    slidingWindowLog.requestToken(Instant.now().toEpochMilli());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "t"+i);
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


class LeakyBucket extends TimerTask {
    Queue<Date> queue;
    int size;
    Timer timer;

    public LeakyBucket(int size) {
        this.queue = new LinkedList<>();
        this.size = size;
        this.timer = new Timer();
        this.timer.schedule(this, 1000, 1000);
    }

    @Override
    public void run() {
        if(!queue.isEmpty()) {
            queue.poll();
        }
    }

    public synchronized boolean requestToken() {
        System.out.println("Current thread: "+Thread.currentThread());
        if (queue.size() == size) {
            System.out.println("Already queue full.");
            return false;
        } else {
            System.out.println("got token at time: "+new Date().getTime());
            queue.offer(new Date());
            return true;
        }

    }
}

class SlidingWindowLog {
    List<Long> timestamps;
    int size; //5 req
    int windowSeconds; //per 1 minute window
    public SlidingWindowLog(int size, int windowSeconds) {
        this.size=size;
        timestamps=new ArrayList<>();
        this.windowSeconds = windowSeconds;
    }

    public synchronized boolean requestToken(long timestamp) {
        System.out.println("current thread: "+Thread.currentThread());
        // min start time for the current time
        long windowStartTime = timestamp - (windowSeconds * 1000L);
        Iterator<Long> iterator = timestamps.iterator();
        while(iterator.hasNext()) {
            Long start = iterator.next();
            if(start < windowStartTime)
                iterator.remove();
        }

        if(timestamps.size() == size) {
            System.out.println("no token available, hence rate limiting...");
            return false;
        } else {
            timestamps.add(timestamp);
            System.out.println("got token successfully....");
            return true;
        }
    }
}

class SlidingWindowLog2 {
    private final int limit; // count of tokens allowed
    private final long windowSizeInMillis; //window for max tokens
    private final Deque<Long> timestampLog = new ArrayDeque<>();

    public SlidingWindowLog2(int limit, long windowSizeInMillis) {
        this.limit = limit;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        while(!timestampLog.isEmpty() && now - timestampLog.peekFirst() > windowSizeInMillis) {
            timestampLog.pollFirst();
        }

        if(timestampLog.size() < limit) {
            timestampLog.addLast(now);
            return true;
        }
        else return false;
    }

    // 3 tokens in 60sec
    // (1.00), (1.15), (1.40)

}


