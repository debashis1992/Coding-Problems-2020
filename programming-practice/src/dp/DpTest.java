package dp;

import java.util.*;

public class DpTest {
    public static void main(String[] args) {
        Solution4 sn =new Solution4();
        sn.longestCommonSubsequence("rabbbit","rabbit");

//        Solution5 sn=new Solution5();
//        System.out.println(sn.shortestCommonSupersequence("abac","cab"));


//        String s = "rabbbit", t="rabbit";
//        int n=s.length();
//        int c=0;
//        for(int mask=0;mask < (1<<n);mask++) {
//            StringBuilder sb=new StringBuilder();
//            for(int i=0;i<n;i++) {
//                if((mask & (1<<i)) != 0) {
//                    sb.append(s.charAt(i));
//                }
//            }
//            String s2 = new String(sb);
//            if(s2.equals(t))
//                c++;
//        }
//
//        System.out.println("total: "+c);
    }
}

class Solution5 {
    String[][] dp;
    public String shortestCommonSupersequence(String s1, String s2) {
        int n=s1.length(), m=s2.length();
        dp=new String[n][m];

        String s=lcs(s1,s2,0,0);
        int i=0,j=0;
        StringBuilder ans= new StringBuilder();
        for(int k=0;k<s.length();k++) {
            char c = s.charAt(k);

            while(s1.charAt(i)!=c)
                ans.append(s1.charAt(i++));

            while(s2.charAt(j)!=c)
                ans.append(s2.charAt(j++));

            ans.append(c);
            i++;
            j++;
        }

        ans.append(s1.substring(i)).append(s2.substring(j));
        System.out.println("ans: "+ans);
        return ans.toString();

    }
    private String lcs(String s1, String s2, int i, int j) {
        if(i>=s1.length() || j>=s2.length()) return "";

        if(dp[i][j]!=null) return dp[i][j];
        if(s1.charAt(i) == s2.charAt(j))
            dp[i][j] = s1.charAt(i) + lcs(s1,s2,i+1,j+1);

        else {
            String a = lcs(s1,s2,i+1,j);
            String b = lcs(s1,s2,i,j+1);

            dp[i][j] = (a.length() > b.length()) ? a: b;
        }
        return dp[i][j];
    }
}

class Solution4 {
    int[][] dp;
    Map<String, Set<String>> memo;
    public String longestCommonSubsequence(String s1, String s2) {
        int n=s1.length(), m=s2.length();
        dp=new int[n][m];
        for(int[] row: dp)
            Arrays.fill(row, -1);
        memo = new HashMap<>();

        int count = solve(s1,s2,0,0);
        System.out.println("count: "+count);

        for(int[] row: dp)
            System.out.println(Arrays.toString(row));

        Set<String> set = build(s1,s2,0,0);
        System.out.println("set: "+set);
        return "";
    }

    public Set<String> build(String s1, String s2, int i, int j) {
        if(i>=s1.length() || j>=s2.length()) {
            Set<String> base=new HashSet<>();
            base.add("");
            return base;
        }
        String key=i+"|"+j;
        if(memo.containsKey(key)) return memo.get(key);
        Set<String> result = new HashSet<>();
        if(s1.charAt(i) == s2.charAt(j)) {
            for(String str: build(s1,s2,i+1,j+1))
                result.add(s1.charAt(i) + str);
        }
        else {
            if(j+1 < s2.length() && dp[i][j+1] == dp[i][j])
                result.addAll(build(s1,s2,i,j+1));
            if(i+1 < s1.length() && dp[i+1][j] == dp[i][j])
                result.addAll(build(s1,s2,i+1,j));
        }

        memo.put(key, result);
        return result;

    }



    private int solve(String s1, String s2, int i, int j) {
        if(i>=s1.length() || j>=s2.length()) return 0;

        if(dp[i][j]!=-1)
            return dp[i][j];

        if(s1.charAt(i) == s2.charAt(j))
            dp[i][j] = 1 + solve(s1,s2,i+1,j+1);
        else {
            dp[i][j] = Math.max(
                    solve(s1,s2,i+1,j),
                    solve(s1,s2,i,j+1)
            );
        }

        return dp[i][j];
    }
}
