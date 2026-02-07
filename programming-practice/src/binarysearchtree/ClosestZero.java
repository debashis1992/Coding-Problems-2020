package binarysearchtree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ClosestZero {
    public static void main(String[] args) {
        int[] a={2,-1,1};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        Solution sn=new Solution();
        int idx = sn.find(a);
        System.out.println("index: "+idx);

        Map<Integer, PriorityQueue<Integer>> map=new HashMap<>();

    }
}


class Solution {
    public int find(int[] a) {
        int idx=-1;
        int l=0,r=a.length-1;
        while(l<=r) {
            int mid=l+(r-l)/2;
            if(a[mid] <= 0) {
                idx=mid;
                l=mid+1;
            }
            else if(a[mid] > 0)
                r=mid-1;
        }
        return idx;
    }
}