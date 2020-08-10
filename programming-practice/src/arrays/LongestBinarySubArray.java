package arrays;

import java.util.HashMap;
import java.util.Map;

public class LongestBinarySubArray {
    public static void main(String[] args) {
        int[] a = {0,1,1,0,1,1,1,0};
        System.out.println(findLongestBinSubArray(a));
    }

    public static int findLongestBinSubArray(int[] ar) {
        int count = 0, maxLen=0;
        int n = ar.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<n;i++) {
            count+= (ar[i]==0 ? 1 : -1);
            if(count==0)
                maxLen = Math.max(maxLen, i+1);
            if(!map.containsKey(count))
                map.put(count, i);
            else maxLen = Math.max(maxLen, i - map.get(count));
        }
        return maxLen;
    }
}

