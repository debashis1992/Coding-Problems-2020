package dp;

public class PartitionWithMinAbsoluteDifferenceTest {
    public static void main(String[] args) {

        Solution3 s=new Solution3();
        int[] a={1,2,3,4};
        System.out.println(s.minimumDifference(a));
    }
}


class Solution3 {
    Boolean[][] dp;
    public int minimumDifference(int[] nums) {
        int s=0;
        int n=nums.length;
        for(int i:nums)
            s+=i;

        dp=new Boolean[n][s+1];
        // Initialize the DP table for the first row
        for (int i = 0; i <= s; i++) {
            dp[0][i] = (i == nums[0]);
        }

        f(nums, n-1, s);

        // Fill in the DP table using bottom-up dynamic programming
//        for (int ind = 1; ind < n; ind++) {
//            for (int target = 0; target <= s; target++) {
//                // Calculate if the current element is not taken
//                boolean notTaken = dp[ind - 1][target];
//
//                // Calculate if the current element is taken
//                boolean taken = false;
//                if (nums[ind] <= target) {
//                    taken = dp[ind - 1][target - nums[ind]];
//                }
//
//                // Update the DP table for the current element and target sum
//                dp[ind][target] = notTaken || taken;
//            }
//        }
        int min=Integer.MAX_VALUE;
        for(int i=0;i<=s;i++) {
            if(dp[n-1][i]) {
                int diff = Math.abs(i - (s-i));
                min=Math.min(min, diff);
            }
        }
        return min;

    }

    public boolean f(int[] a, int i, int t) {
        if(t==0) {
            return dp[i][t]=true;
        }
        if(i==0) {
            if(t==a[i])
                dp[i][t]=true;
            else dp[i][t]=false;

            return dp[i][t];
        }


        if(dp[i][t]!=null)
            return dp[i][t];

        boolean notpick = f(a,i-1,t);
        boolean pick=false;
        if(a[i]<=t)
            pick = f(a,i-1,t-a[i]);

        return dp[i][t] = (notpick || pick);
    }
}

