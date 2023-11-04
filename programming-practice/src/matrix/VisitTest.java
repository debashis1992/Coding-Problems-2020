package matrix;

import java.util.Arrays;

public class VisitTest {

    int[][] dp;
    int getNumPaths(int rows, int columns) {
        dp=new int[rows][columns];

        for(int i=0;i<rows;i++)
            Arrays.fill(dp[i], -1);

        visit(0,0,rows,columns);
        return dp[0][0];

    }

    public int visit(int i, int j, int r,int c) {
        if(i>=r || j>=c)
            return 0;
        if(i==r-1 && j==c-1)
            dp[i][j]=1;

        if(dp[i][j]==-1)
            dp[i][j] = visit(i+1,j,r,c)
                    +
                    visit(i,j+1,r,c);


        return dp[i][j];
    }

    public static void main(String[] args) {
        VisitTest t=new VisitTest();
        System.out.println(t.getNumPaths(3,3));
    }
}
