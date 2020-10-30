package dp;

public class KnapsackProblemTest {
    public int[][] t;
    public KnapsackProblemTest() {}
    public KnapsackProblemTest(int W,int n) {
        t = new int[n+1][W+1];
        for(int i=0;i<t.length;i++) {
            for(int j=0;j<t[0].length;j++)
                t[i][j] = -1;
        }
    }
    public static void main(String[] args) {
        int[] wt = {1,3,4,5};
        int[] val = {1,4,5,7};
        int W = 7;
        System.out.println(new KnapsackProblemTest().knapsack(wt,val,W,wt.length-1));

        System.out.println(new KnapsackProblemTest().knapsackUsingTopDown(wt,val,W,wt.length-1));
    }

    public int knapsack(int[] wt,int[] val,int W,int n) {
        if(n==0 || W==0) return 0;
        if(wt[n-1] > W)
            return knapsack(wt,val,W,n-1);
        else return Math.max(val[n-1] + knapsack(wt,val,W-wt[n-1], n-1),
                knapsack(wt,val,W,n-1));
    }

    public int knapsackUsingMemoization(int[] wt,int[] val,int W,int n) {
        if(n==0 || W==0) return 0;
        if(t[n][W]!=-1)
            return t[n][W];
        if(wt[n-1] > W)
            t[n][W] = knapsackUsingMemoization(wt,val,W,n-1);
        else t[n][W] = Math.max(val[n-1] + knapsackUsingMemoization(wt,val,W-wt[n-1],n-1),
                knapsack(wt,val,W,n-1));

        return t[n][W];
    }

    /*

    When converting recursive code to top-down approach.
    1. look for the changing values in recursive calls and only those ones to be included while array creation.
    2. use base cases for array initialization.
    3. replace all the recursive calls to array indexes.
    4. return the last value in the 2D matrix
    */

    public int knapsackUsingTopDown(int[] wt,int[] val, int W,int n) {
        initialize(n,W);
        for(int i=1;i<=n;i++) {
            for(int j=1;j<=W;j++) {
                if(wt[i-1] > j)
                    t[i][j] = t[i-1][j];
                else t[i][j] = Math.max(val[i-1]+t[i-1][j-wt[i-1]], t[i-1][j]);
            }
        }
        return t[n][W];
    }
    private void initialize(int n,int W) {
        t = new int[n+1][W+1];
        for(int i=0;i<t.length;i++) {
            for(int j=0;j<t[0].length;j++) {
                if(i==0 || j==0)
                    t[i][j]=0;
            }
        }
    }
}
