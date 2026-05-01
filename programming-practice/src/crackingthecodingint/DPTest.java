package crackingthecodingint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DPTest {
    public static void main(String[] args) {

        Subsets subsets = new Subsets();
        int[] a={1,2,3};
        subsets.solve(a);

        StringSets stringSets = new StringSets();
        stringSets.solve("ABC");

        Parenthesis parenthesis = new Parenthesis();
        parenthesis.generate(3);
    }
}

class CoinTest {

    int[][] dp;
    void solve(int n) {
        int[] coins = {25,10,5,1};
        dp=new int[coins.length][n+1];
        for(int[] row: dp)
            Arrays.fill(row, -1);

        int ans = solve(n, 0, coins);
        System.out.println(ans);
    }

    private int solve(int amount, int i, int[] coins) {
        if(i==coins.length && amount>0) return 0;

        if(amount == 0) return 1;

        if(dp[i][amount]!=-1) return dp[i][amount];

        int nopick = solve(amount, i+1, coins);
        int pick = 0;
        if(coins[i] <= amount)
            pick = solve(amount - coins[i], i, coins);

        return dp[i][amount] = nopick + pick;
    }
}

class Parenthesis {
    void generate(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        backtrack(n, n, stringBuilder, result);
        System.out.println(result);
    }

    private void backtrack(int open, int closed, StringBuilder sb, List<String> result) {
        if(open==0 && closed==0) {
            result.add(new String(sb));
            return;
        }

        if(open > 0) {
            sb.append("(");
            backtrack(open-1, closed, sb, result);
            sb.deleteCharAt(sb.length()-1);
        }

        if(closed > open) {
            sb.append(")");
            backtrack(open, closed-1, sb, result);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}

class StringSets {
    List<String> result;
    public void solve(String s) {
        result=new ArrayList<>();
        char[] c=s.toCharArray();

        backtrack(c, 0);
        System.out.println(result);
    }

    private void backtrack(char[] c, int idx) {
        if(idx == c.length) {
            result.add(new String(c));
            return;
        }

        for(int i=idx;i < c.length;i++) {
            swap(c, i, idx);
            backtrack(c, idx+1);
            swap(c, i, idx);
        }
    }

    private void swap(char[] c, int i, int j) {
        char t = c[i];
        c[i] = c[j];
        c[j] = t;
    }
}

class Subsets {
    List<List<Integer>> result;
    public void solve(int[] a) {
        result = new ArrayList<>();
        Arrays.sort(a);
        backtrack(a, 0, new ArrayList<>());

        System.out.println(result);
    }

    private void backtrack(int[] a, int i, List<Integer> temp) {
        result.add(new ArrayList<>(temp));

        if(i == a.length) return;

        for(int idx = i;idx < a.length;idx++) {
            if(idx > i && a[idx] == a[idx-1])
                continue;

            temp.add(a[idx]);
            backtrack(a, idx+1, temp);
            temp.remove(temp.size()-1);
        }
    }
}

class MagicIndex {

    public void solve(int[] a) { //sorted, distinct
        int ans = solve(a, 0, a.length-1);
        System.out.println(ans);
    }
    private int solve(int[] a, int low, int high) {
        if(low > high) return -1;

        while(low <= high) {
            int mid = (low+high) >> 1;
            if(a[mid] == mid) return mid;
            if(a[mid] > mid) high=mid-1;
            else low=mid+1;
        }
        return -1;
    }

    public void solve2(int[] a) { //sorted but with duplicates
        solve2(a, 0, a.length-1);
    }

    private int solve2(int[] a, int low, int high) {
        if(low > high) return -1;

        int mid = (low+high) >> 1;
        if(a[mid] == mid) return mid;

        //search left
        int leftIdx = Math.min(mid-1, a[mid]);
        int left = solve2(a, low, leftIdx);
        if(left!=-1) return left;

        //search right
        int rightIdx = Math.max(mid+1, a[mid]);
        int right = solve2(a, rightIdx, high);
        return right;
    }
}

class DP2 {
    Boolean[][] dp;
    public void solve(int[][] a) {
        int r=a.length, c=a[0].length;
        dp=new Boolean[r][c];
        boolean ans = visit(0,0,a);

        System.out.println("path exists: "+ans);
    }

    public boolean visit(int i, int j, int[][] a) {
        if(i>= a.length || j>=a[0].length || a[i][j]==1) return false;

        if(i==a.length-1 && j==a[0].length-1) return true;

        if(dp[i][j]!=null) return dp[i][j];

        boolean right = visit(i,j+1,a);
        boolean down = visit(i+1, j, a);

        return dp[i][j] = (right || down);
    }
}

class DP {
    int[] dp;
    public int solve(int i, int n) {
        dp=new int[n+1];
        Arrays.fill(dp, -1);
        return climb(0, n);
    }
    public int climb(int i, int n) {
        if(i>n) return 0;
        if(i==n) return 1;

        if(dp[i]!=-1)
            return dp[i];

        return dp[i] = climb(i+1,n) + climb(i+2, n) + climb(i+3, n);
    }
}
