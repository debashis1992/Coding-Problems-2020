package crackingthecodingint;

import java.util.Arrays;

public class EditsAway {
    static int[][] dp;
    public static void main(String[] args) {

        System.out.println(edits("pale","pales"));

    }

    public static boolean edits(String a, String b) {
//        dp=new int[a.length()+1][b.length()+1];
//        for(int[] row: dp)
//            Arrays.fill(row, -1);
//
//        int e=solve(a.toCharArray(),b.toCharArray(), 0, 0);
//        return (e<=1);

        if(Math.abs(a.length() - b.length()) > 1) return false;

        String x = a.length() <= b.length() ? a : b;
        String y = a.length() <= b.length() ? b : a;

        boolean flag=false;
        int i=0,j=0;
        while(i<x.length() && j<y.length()) {
            if(x.charAt(i) != y.charAt(j)) {

                if(flag) return false;

                flag = true;
                if(x.length() == y.length()) {
                    i++;
                    j++;
                }
                else j++; //move pointer for longer string

            } else {
                i++;
                j++;
            }
        }
        return true;
    }

    public static int solve(char[] a, char[] b, int i, int j) {
        if(i==a.length) return b.length-j;
        if(j==b.length) return a.length-i;

        if(dp[i][j]!=-1)
            return dp[i][j];

        if(a[i] == b[j])
            return dp[i][j] = solve(a, b, i+1, j+1);
        else {
            int insert = 1+solve(a,b,i,j+1);
            int remove = 1+solve(a,b,i+1, j);
            int replace = 1+solve(a,b,i+1,j+1);

            return dp[i][j] = Math.min(Math.min(insert, remove), replace);
        }

    }
}
