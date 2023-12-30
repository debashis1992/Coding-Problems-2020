package amazon;

import java.util.*;
import java.util.stream.Collectors;

public class SteppingNumbersTest {
    static List<Integer> set = new ArrayList<>();
    public static void main(String[] args) {
        int begin=8, end=15;
        List<Integer> list = getSteppingNumbers(begin, end);
        System.out.println(list);
    }

    public static List<Integer> getSteppingNumbers(int begin, int end) {
        Queue<Long> q= new LinkedList<>();
        for(int i=1;i<=9;i++) {
            q.offer((long)i);
        }
        if(begin==0)
            set.add(0);

        while(!q.isEmpty()) {
            long n=q.poll();
            if(n<end) {
                int r=(int)(n%10);
                if(r<9)
                    q.offer(n*10 + r+1);
                if(r>0)
                    q.offer(n*10 + r-1);

                if(n>=begin && n<=end)
                    set.add((int)n);
            }
        }

        return set;
    }

    public static void process(int n, int begin, int end) {
        if(n>=begin && n<=end)
            set.add(n);
        else return;
        int r=n%10;
        if(r==0) {
            int n1=n*10+(r+1);
            process(n1, begin, end);
        }
        else if(r==9) {
            int n1=n*10+(r-1);
            process(n1, begin, end);
        }
        else {
            int n1=n*10+(r+1);
            int n2=n*10+(r-1);
            process(n1, begin, end);
            process(n2, begin, end);
        }
    }
}
