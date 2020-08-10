package arrays;

import java.util.HashMap;
import java.util.Map;

public class SubArrayEqualsK {
    public static void main(String[] args) {
        int[] a = {};
        int k = 5;

    }
    public static int findSubArraysEqualsK(int[] a,int k) {
        int count=0;
        int currSum=0;
        Map<Integer,Integer> prevSum = new HashMap<>();
        int n=a.length;
        for(int i=0;i<n;i++) {
            currSum+=a[i];

            if(currSum==k)
                count++;
            if(prevSum.containsKey(currSum - k)) {
                count+= prevSum.get(currSum - k);
            }

            Integer c = prevSum.get(currSum);
            if(c==null)
                prevSum.put(currSum,1);
            else prevSum.put(currSum, count+1);
        }
        return count;
    }
}
