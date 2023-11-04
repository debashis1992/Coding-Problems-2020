package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RatMazeProblem {
    String [][] dp;
    List<String> ans;
    public static void main(String[] args) {

        RatMazeProblem problem=new RatMazeProblem();
        int[][] path = {{1,0,0,0},{1,1,0,1},{1,1,0,0},{0,1,1,1}};
        problem.getPath(path);

    }

    public void getPath(int[][] m) {
        int r=m.length, c=m[0].length;

        dp=new String[r][c];
        ans=new ArrayList<>();
        for(int i=0;i<r;i++)
            Arrays.fill(dp[i], "");

        visit(m,0,0,r,c, "");
        System.out.println(ans);
    }

    public String visit(int[][] m,int i, int j,int r, int c, String tmp) {
       if(i<0 || j<0 || i>=r || j>=c)
           return "";

       else if(i==r-1 && j==c-1 && m[i][j]==1) {
           ans.add(tmp);
       }

       else if(m[i][j] == 0 || m[i][j] == -1)
           return "";

       int t=m[i][j];
       m[i][j]=-1;
       if(dp[i][j].isEmpty()) {

           int[][] points = {{-1,0},{1,0},{0,1},{0,-1}};
           for(int[] point: points) {
               int x=i+point[0];
               int y=j+point[1];
               if(x<0 || y<0 || x>=r || y>=c || m[x][y]==0 || m[x][y]==-1)
                   continue;

               dp[i][j]+= visit(m, x,y,r,c,tmp+"X");
           }
       }
       m[i][j] = t;

       return dp[i][j];

    }
}
