package priorityqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class KSmallestSumPairs {

    public static void main(String[] args) {
        int[] nums1 = {1,1,2};
        int[] nums2 = {1,2,3};

        int k=3;
        Solution sn=new Solution();
        List<List<Integer>> result=sn.kSmallestPairs(nums1, nums2, k);
        System.out.println(result);
    }
}

class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq=new PriorityQueue<>((o1,o2) -> (o1[0]+o2[0]) - (o1[1]+o2[1]));

        for(int i=0;i<Math.min(k, nums1.length);i++) {
            pq.offer(new int[]{nums1[i], nums2[0], 0});
        }

        List<List<Integer>> result=new ArrayList<>();
        while(!pq.isEmpty() && k-->0) {
            int[] t=pq.poll();
            result.add(List.of(t[0], t[1]));
            int idx=t[2];

            if(idx+1 < nums2.length)
                pq.offer(new int[]{t[0], nums2[idx+1], idx+1});
        }


        return result;
    }
}
