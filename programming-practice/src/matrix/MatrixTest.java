package matrix;

public class MatrixTest {
    private int minPath(int[][] a,int[][] dp, int i, int j,int r,int c) {
        if(i==r-1) {
            dp[i][j] = a[i][j];

        }
        else if(j==c-1) {
            dp[i][j] = a[i][j] + Math.min(minPath(a,dp,i+1,j-1,r,c),
                    minPath(a, dp, i+1,j,r,c));
        }
        else if(j==0) {
            dp[i][j] = a[i][j] + Math.min(minPath(a,dp,i+1,j,r,c),
                    minPath(a,dp,i+1,j+1,r,c));
        }
        else {
            dp[i][j] = a[i][j] + Math.min(
                    dp[i+1][j-1],
                    Math.min(dp[i+1][j],  dp[i+1][j+1])
            );
        }
        return dp[i][j];
    }
    public int minFallingPathSum(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;

        int[][] dp = new int[r][c];
        for (int i = 0; i < c; i++) {
            dp[r - 1][i] = matrix[r - 1][i];
        }

        // minPath(matrix, dp, 0,0,r,c);
        int ans = Integer.MAX_VALUE;
//        for(int i=0;i<c;i++) {
//            int s = minPath(matrix,dp,0,i,r,c);
//            System.out.println(s);
//            ans = Math.min(ans, s);
//        }
        for (int i = 0; i < r - 1; i++) {
            for (int j = 0; j < c; j++) {
                dp[i][j] = minPath(matrix, dp, i, j, r, c);
            }
        }
        for (int i = 0; i < c; i++) {
            int s = minPath(matrix, dp, 0, i, r, c);
            System.out.println(s);
            ans = Math.min(ans, s);
//        }
        }
        return ans;
    }
    public int countNegatives(int[][] grid) {
        int r=grid.length;
        int c=grid[0].length;

        int i=0, j=c-1;
        int ans=0;
        while(i<r && j>=0) {
            if(grid[i][j] < 0) {
                ans+= (r-i);
                j--;
            }
            else if(grid[i][j] > 0)
                i++;
        }

        return ans;
    }

    public static void main(String[] args) {
//        int[][] grid = {{5,4,3,2},{4,3,2,-1},{3,2,1,-1},{1,1,-1,-2},{-1,-1,-2,-3}};
//        new MatrixTest().countNegatives(grid);
        int[][] grid = {{2,1,3},{6,5,4},{7,8,9}};
        System.out.println(new MatrixTest().minFallingPathSum(grid));
    }
}
