package threads;

import java.util.ArrayList;
import java.util.List;

public class TokenBucketFilterTest {
    public static void main(String[] args) {

        TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(5);
        List<Thread> threadList = new ArrayList<>();
        for(int i=0;i<10;i++) {
            String name = "t"+i;
            Thread t1 = new Thread(() -> tokenBucketFilter.getToken(name));
            threadList.add(t1);
        }

        //starting threads
        threadList.forEach(Thread::start);

        //joining threads
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}


class TokenBucketFilter {
    int N;
    int possibleToken;
    long lastRequestTime;

    public TokenBucketFilter(int N) {
        this.N=N;
        this.possibleToken=0;
        this.lastRequestTime=System.currentTimeMillis();
    }

    public synchronized void getToken(String threadName) {
        possibleToken+= (System.currentTimeMillis() - lastRequestTime)/1000;
        if(possibleToken > N)
            possibleToken=N;

        if(possibleToken==0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        possibleToken--;
        lastRequestTime = System.currentTimeMillis();
        System.out.println("Granted token at : "+System.currentTimeMillis()+",by thread: "+threadName);
    }
}

class Rate {
    int req;
    int seconds;
    int actualRate;
    public Rate(int req,int seconds) {
        this.req=req;
        this.seconds=seconds;
        this.actualRate=req/seconds;
    }
}