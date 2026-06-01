package crackingthecodingint;

import java.util.*;

public class MaxSlidingWindowTest {
    public static void main(String[] args) {

        Solution3 sn=new Solution3();
        int[] nums={1,3,-1,-3,5,3,6,7};
        int k=3;

        int[] res = sn.maxSlidingWindow(nums, k);
        System.out.println(Arrays.toString(res));
    }
}

class Solution3 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq=new ArrayDeque<>();
        List<Integer> res=new ArrayList<>();

        for(int i=0;i<k;i++) {
            while(!dq.isEmpty() && nums[i] >= nums[dq.peekLast()])
                dq.pollLast();

            dq.offerLast(i);
        }
        if(!dq.isEmpty())
            res.add(nums[dq.peekFirst()]);

        for(int i=k;i<nums.length;i++) {
            while(!dq.isEmpty() && dq.peekFirst() <= i-k)
                dq.pollFirst();

            while(!dq.isEmpty() && nums[i] >= nums[dq.peekLast()])
                dq.pollLast();

            dq.offerLast(i);
            if(!dq.isEmpty())
                res.add(nums[dq.peekFirst()]);
        }

        System.out.println(res);
        return res.stream().mapToInt(x -> x).toArray();
    }
}
