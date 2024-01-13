package dp;

import java.util.Arrays;

public class LongestCommonSuperSeqTest {
    public static void main(String[] args) {

        String s1="brute";
        String s2="groot";
        Solution solution = new Solution();
        System.out.println(solution.shortestCommonSupersequence(s1,s2));
    }

}

class Solution {
    int[][]  dp;
    public String shortestCommonSupersequence(String s1, String s2) {

        int n1=s1.length(), n2=s2.length();
        dp=new int[n1+1][n2+1];

        for(int i=0;i<=n1;i++)
            dp[i][0]=0;
        for(int i=0;i<=n2;i++)
            dp[0][i]=0;

        for(int i=1;i<=n1;i++) {
            for(int j=1;j<=n2;j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1))
                    dp[i][j] = 1+dp[i-1][j-1];
                else dp[i][j] = Math.max(
                        dp[i-1][j],
                        dp[i][j-1]
                );
            }
        }

        int len = dp[n1][n2];
        System.out.println("lcs : "+len);

        int i=n1, j=n2;
        StringBuilder sb = new StringBuilder();
        while(i>0 && j>0) {
            if(s1.charAt(i-1) == s2.charAt(j-1)) {
                sb.append(s1.charAt(i-1));
                i--;
                j--;
            }
            else if(dp[i-1][j] > dp[i][j-1]) {
                sb.append(s1.charAt(i-1));
                i--;
            } else {
                sb.append(s2.charAt(j-1));
                j--;
            }
        }

        while(i>0) {
            sb.append(s1.charAt(i-1));
            i--;
        }

        while(j>0) {
            sb.append(s2.charAt(j-1));
            j--;
        }

        return sb.reverse().toString();


    }

    public int getLcs(String s1, String s2, int i, int j) {
        if(i<0 || j<0) return 0;

        if(dp[i][j]!=-1)
            return dp[i][j];
        if(s1.charAt(i) == s2.charAt(j))
            dp[i][j] = 1 + getLcs(s1,s2,i-1,j-1);
        else dp[i][j] = Math.max(
                getLcs(s1,s2,i-1,j),
                getLcs(s1,s2,i,j-1)
        );

        return dp[i][j];
    }
}
