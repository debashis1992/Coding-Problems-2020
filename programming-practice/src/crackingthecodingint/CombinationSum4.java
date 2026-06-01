package crackingthecodingint;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum4 {
    public static void main(String[] args) {

        Solution2 sn=new Solution2();
        int[] nums={4,2,1};
        int target=32;
        int count = sn.combinationSum4(nums, target);

        System.out.println(count);
    }
}
class Solution2 {
    public int combinationSum4(int[] nums, int target) {
        List<List<Integer>> list=new ArrayList<>();
        List<Integer> runningList=new ArrayList<>();

        backtrack(nums, 0, 0, target, list, runningList);
        System.out.println("list: "+list);
        return list.size();
    }

    public void backtrack(int[] nums, int idx, int rs, int target, List<List<Integer>> list,
                          List<Integer> runningList) {
        if(idx == nums.length || rs > target) return;

        if(rs == target) {
            list.add(new ArrayList<>(runningList));
            return;
        }

        for(int i=idx;i<nums.length;i++) {
            rs+= nums[i];
            runningList.add(nums[i]);
            backtrack(nums, 0, rs, target, list, runningList);
            rs-= nums[i];
            runningList.removeLast();
        }
    }
}