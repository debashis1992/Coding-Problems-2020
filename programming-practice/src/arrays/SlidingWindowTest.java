package arrays;

import java.util.Arrays;
import java.util.Scanner;

public class SlidingWindowTest {
    public static void main(String[] args) {
//        int[] a = {1, 4, 2, 10, 23, 3, 1, 0, 20};
//        int k=4;

        int[] a = {100,2,3,4,5,6,7,8,9,10};
        int k = 3;
//        System.out.println(getWinner(a, k));
    }
    public static int findMaxConsecutiveSum(int[] a,int n,int k) {
        if (k > n)
            return -1;
        int initialSum = 0;
        for (int i = 0; i < k; i++) {
            initialSum += a[i];
        }
        int max = initialSum;
        for (int i = k; i < n; i++) {
            initialSum+= a[i] - a[i-k];
            if (initialSum > max)
                max = initialSum;
        }
        return max;
    }

    public static int getWinner(int[] a,int k) {
        if(k>=a.length) {
            return findMaxElement(a);
        } else {
            int max = -1, min=-1;
            // compare first two elements
            if(a[0] > a[1]) {
                max = a[0];
                min = a[1];
            } else {
                max = a[1];
                min = a[0];
            }
            // make greater and push lower element at the end
            a[0] = max;
            // copy elements
            for(int i=1;i+1<a.length;i++) {
                a[i] = a[i+1];
            }
            a[a.length-1] = min;
            // check for k times if its max
            for(int i=1;i<k;i++) {
                if (a[0] < a[i])
                    return getWinner(a, k);
            }
            return a[0];
        }
    }
    private static int findMaxElement(int[] a) {
        int max = a[0];
        for(int i=0;i<a.length;i++) {
            if (a[i] > max)
                max = a[i];
        }
        return max;
    }
}
