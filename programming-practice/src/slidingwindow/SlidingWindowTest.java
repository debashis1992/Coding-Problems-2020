package slidingwindow;

import java.util.ArrayList;
import java.util.List;

public class SlidingWindowTest {
    public static void main(String[] args) {
//        int[] a = {1,3,2,6,-1,4,1,8,2};
//        findAvg(a, 5);
//        int[] a = {2,1,5,1,3,2};
//        int[] a = {2,3,4,1,5};
//        findMaxSubArraySum(a, 2);
        findMinLength(new int[]{3,4,1,1,6}, 8);
    }

    public static void findMinLengthUsingSlidingWindow(int[] a,int s) {
        int end=0;
        int start=0;
        int sum=0, min=Integer.MAX_VALUE;
        for(int i=0;i<a.length;i++) {
            sum+= a[end];

            while(sum >= s) {
                min = Math.min(min, end-start+1);
                sum-= a[start];
                start++;
            }
        }
        min = min == Integer.MAX_VALUE ? 0 : min;
        System.out.println(min);
    }

    public static void findMinLength(int[] a, int s) {
        int min = a.length;
        //single-pass
        for(int i: a) {
            if(i > s) {
                System.out.println(1);
                return;
            }
        }
        for(int i=0;i<a.length;i++) {
            int sum=0;
            for(int j=i;j<a.length;j++) {
                sum+= a[j];
                if(sum >= s) {
                    int length = j-i+1;
                    if(min > length)
                        min = length;
                    break;
                }
            }
        }
        System.out.println(min);

    }
    public static void findAvg(int[] a, int k) {
        List<Double> list = new ArrayList<>();
        if(k >= a.length) {
            System.out.println((a[0] + a[a.length - 1]) / k);
            return;
        }
        double sum=0.0;
        for(int i=0;i<k;i++)
            sum+= a[i];
        list.add(sum/k);

        for(int i=1;i-1+k<a.length;i++) {
            sum+= a[i-1+k] - a[i-1];
            list.add(sum/k);
        }
        list.forEach(System.out::println);
    }

    public static void findMaxSubArraySum(int[] a, int k) {
        int sum=0;
        int max=0;
        for(int i=0;i<k;i++)
            sum+= a[i];
        if(max < sum)
            max=sum;
        for(int i=1;i-1+k<a.length;i++) {
            sum+= a[i-1+k] - a[i-1];
            if(max<sum)
                max=sum;
        }
        System.out.println(max);
    }
}
