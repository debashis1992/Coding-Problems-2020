package dp;

import java.util.Arrays;

public class LongestPalindromicSubseqTest {
    public static void main(String[] args) {


        Solution2 solution2=new Solution2();
//        String s = "zzazz";
//        solution2.longestPalindromeSubseq(s);

        String s1="babgbag";
        String s2="bag";

        solution2.distinctSubseq(s1,s2);
    }
}

class Solution2 {
    int[][] dp;
    public void longestPalindromeSubseq(String s) {

        int n=s.length();
        dp=new int[n][n];
        for(int[] row: dp)
            Arrays.fill(row, -1);
        String s2 = new StringBuilder(s).reverse().toString();

        int len=find(s,s2, n-1,n-1);
        System.out.println(len);


    }
    public int find(String s1, String s2, int i, int j) {
        if(i<0 || j<0) return 0;

        if(dp[i][j]!=-1)
            return dp[i][j];

        if(s1.charAt(i) == s2.charAt(j)) {
            dp[i][j] = 1+find(s1,s2,i-1,j-1);
        }
        else return dp[i][j] = Math.max(
                find(s1,s2,i-1,j),
                find(s1,s2,i,j-1)
        );

        return dp[i][j];
    }

    public void distinctSubseq(String s1, String s2) {
        int n=s1.length(), m=s2.length();

        dp=new int[n][m];
        for(int[] row: dp)
            Arrays.fill(row, -1);

        int len=findAnother(s1,s2,n-1,m-1);
//        for(int i=0;i<dp.length;i++) {
//            for(int j=0;j<dp[0].length;j++) {
//                System.out.print(dp[i][j]);
//            }
//            System.out.println();
//        }

        System.out.println(len);
    }


    public int findAnother(String s1, String s2, int i, int j) {
        if(j<0)
            return 1;
        else if(i<0)
            return 0;

        if(dp[i][j]!=-1)
            return dp[i][j];
        if(s1.charAt(i) == s2.charAt(j)) {
            dp[i][j]= findAnother(s1,s2,i-1,j-1) + findAnother(s1,s2,i-1,j);
        }
        else dp[i][j]=findAnother(s1,s2,i-1,j);
        return dp[i][j];
    }
}
