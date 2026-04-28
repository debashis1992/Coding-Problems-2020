package designPatterns.ratelimiting;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimitingTest {
    public static void main(String[] args) throws Exception {

        BucketRepo bucketRepo = new BucketRepoImpl();
        TokenBucketStrategy tokenBucket = new TokenBucketStrategy(bucketRepo);

        String userA = "userA";
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));

        try {
            System.out.println("going to sleep now.... for 50sec");
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            throw e;
        }

        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));

        try {
            System.out.println("going to sleep now.... for 10sec");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw e;
        }

        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));
        System.out.println(tokenBucket.validate(userA));

    }
}


interface RateLimiterStrategy {
    boolean validate(String userId) throws Exception;
}

class BaseBucketProperties {
    final int MAX_TOKEN_COUNT = 5;
    final long REFILL_TIME_RATE = 60000; // 1 minute in milliseconds
}

class Bucket extends BaseBucketProperties {
    final String userId;
    int tokens;
    long lastRefillTs;
    final ReentrantLock lock;

    public Bucket(String userId) {
        this.userId=userId;
        tokens = MAX_TOKEN_COUNT;
        lastRefillTs = System.currentTimeMillis();
        lock = new ReentrantLock();
    }
}

interface BucketRepo {
    Bucket findByUserId(String userId);
    Bucket create(String userId);
    void update(Bucket bucket);
}

class BucketRepoImpl implements BucketRepo {
    ConcurrentHashMap<String, Bucket> bucketMap = new ConcurrentHashMap<>();

    @Override
    public Bucket findByUserId(String userId) {
        return bucketMap.get(userId);
    }

    @Override
    public Bucket create(String userId) {
        bucketMap.computeIfAbsent(userId, k -> new Bucket(userId));
        return bucketMap.get(userId);

    }

    @Override
    public void update(Bucket bucket) {
        if(bucketMap.containsKey(bucket.userId))
            bucketMap.put(bucket.userId, bucket);
    }
}

class TokenBucketStrategy implements RateLimiterStrategy {

    private final BucketRepo bucketRepo;

    public TokenBucketStrategy(BucketRepo bucketRepo) {
        this.bucketRepo = bucketRepo;
    }

    @Override
    public boolean validate(String userId) throws Exception {
        if(userId == null) throw new IllegalArgumentException("userId cannot be null");

        Bucket bucket = bucketRepo.findByUserId(userId);
        if(bucket == null) {
            //new user, race condition here
            //use computeIfAbsent(atomic) in map
            bucket = bucketRepo.create(userId);

        }
        ReentrantLock lock = bucket.lock;
        boolean allow=true;
        try {
            lock.lock();
            long now = System.currentTimeMillis();
            if(now - bucket.lastRefillTs > bucket.REFILL_TIME_RATE) {
                bucket.tokens = Math.min(bucket.tokens + bucket.MAX_TOKEN_COUNT, bucket.MAX_TOKEN_COUNT);
                bucket.lastRefillTs = now;
            }
            if(bucket.tokens > 0)
                bucket.tokens--;

            else allow = false;
            bucketRepo.update(bucket);


        } finally {
            lock.unlock();
        }

        return allow;
    }
}

class SlidingWindowProperties {
    final int WINDOW_SIZE_IN_MILLISECONDS = 60000;
    final int MAX_TOKEN_COUNT = 5;
}

class SlidingWindowLog extends SlidingWindowProperties {
    List<Long> requestLog;
    final ReentrantLock lock;
    final String userId;

    public SlidingWindowLog(String userId) {
        this.requestLog = new ArrayList<>();
        lock = new ReentrantLock();
        this.userId = userId;
    }
}

interface SlidingWindowLogRepo {
    SlidingWindowLog findByUserId(String userId);
    SlidingWindowLog create(String userId);
}

class SlidingWindowLogRepoImpl implements SlidingWindowLogRepo {
    ConcurrentHashMap<String, SlidingWindowLog> slidingWindowLogConcurrentHashMap =
            new ConcurrentHashMap<>();

    @Override
    public SlidingWindowLog findByUserId(String userId) {
        return slidingWindowLogConcurrentHashMap.get(userId);
    }

    @Override
    public SlidingWindowLog create(String userId) {
        slidingWindowLogConcurrentHashMap.computeIfAbsent(userId,
                k -> new SlidingWindowLog(userId));

        return slidingWindowLogConcurrentHashMap.get(userId);
    }
}

class SlidingWindowCounterStrategy implements RateLimiterStrategy {

    private final SlidingWindowLogRepo slidingWindowLogRepo;

    public SlidingWindowCounterStrategy(SlidingWindowLogRepo slidingWindowLogRepo) {
        this.slidingWindowLogRepo = slidingWindowLogRepo;
    }

    @Override
    public boolean validate(String userId) throws Exception {

        if(userId == null) throw new IllegalArgumentException("userId cannot be null");

        SlidingWindowLog slidingWindowLog = slidingWindowLogRepo.findByUserId(userId);
        if(slidingWindowLog == null) {
            slidingWindowLog = slidingWindowLogRepo.create(userId);
        }

        ReentrantLock lock = slidingWindowLog.lock;
        try {
            lock.lock();

            long now = System.currentTimeMillis();
            List<Long> requestLogs = slidingWindowLog.requestLog;

            Iterator<Long> it = requestLogs.iterator();
            while(it.hasNext()) {
                if(now - it.next() > slidingWindowLog.WINDOW_SIZE_IN_MILLISECONDS) {
                    it.remove();
                } else break;
            }
            if(requestLogs.size() >= slidingWindowLog.MAX_TOKEN_COUNT) {
                return false;
            }
            requestLogs.add(now);

        } finally {
            lock.unlock();
        }

        return true;

    }
}