package subarrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TugOfWarTest {
    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        System.out.println(tugOfWar(list, list.size()));

    }

    static int[][] dp;
    public static int tugOfWar(ArrayList<Integer> arr, int n) {
        int totSum=0;
        for(int i: arr) {
            totSum+= i;
        }

        dp=new int[n][(n/2)+1];
        for(int[] row: dp)
            Arrays.fill(row, -1);

        return f(arr,0,0,0,totSum);
    }

    public static int f(ArrayList<Integer> a, int i, int cnt, int currentSum,
                        int totSum) {

        if(i==a.size()) return Integer.MAX_VALUE;
        if(dp[i][cnt]!=-1)
            return dp[i][cnt];

        if(cnt == a.size()/2) {
            //got the subset
            //take the difference

           return dp[i][cnt] = Math.abs(totSum - 2*currentSum);
        }

        //notpick
        int notpick = f(a, i+1, cnt, currentSum, totSum);
        //pick
        int pick = f(a, i+1, cnt+1, currentSum+a.get(i), totSum);
        dp[i][cnt] = Math.min(notpick, pick);

        return dp[i][cnt];
    }
}
