package backtracking;

import java.util.*;
import java.util.stream.Collectors;

public class PermutationListTest {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        List<List<Integer>> list = new PermutationListTest().permute(nums);

    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> listOfLists = new ArrayList<>();
        int n = nums.length;

        permute(nums, 0, n-1, listOfLists);
        return new ArrayList<>(listOfLists);
    }

    public void permute(int[] nums, int l,int r, List<List<Integer>> list) {
        if(l==r)
            list.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        else {
            for(int i=l;i<=r;i++) {
                nums = swap(nums, l, i);
                permute(nums, l+1, r, list);
                nums = swap(nums, l, i);
            }
        }

    }

    public int[] swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
        return nums;
    }
}
