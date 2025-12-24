package dp;

import java.util.ArrayList;
import java.util.List;

public class BitMaskingTest {
    public static void main(String[] args) {

        int[] nums = {1,2,3,4};
        int n=nums.length;
        for(int mask = 0; mask < (1<<n); mask++) {
            List<Integer> subsets = new ArrayList<>();
            for(int i=0;i<n;i++) {
                if((mask & (1<<i)) != 0)
                    subsets.add(nums[i]);
            }
            System.out.println(subsets);
        }
    }
}
