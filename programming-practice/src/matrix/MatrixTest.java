package matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrixTest {
    public int[][] findFarmland(int[][] land) {
        List<int[]> finalList = new ArrayList<>();
        int r=land.length;
        int c=land[0].length;

        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                if(land[i][j] == 1) {
                    int[] list = new int[4];
                    list[0]=i;
                    list[1]=j;
                    dfs(land, i,j,r,c,list);
                    finalList.add(list);
                }

            }
        }
        return finalList.toArray(new int[0][]);
    }

    public static int getMaximumGold(int[][] a) {
        int max=0;

        int r = a.length;
        int c = a[0].length;

        int[][] grid = new int[r][c];
        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                grid[i][j] = -1;
            }
        }

        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                if(a[i][j]!=0) {
                    grid[i][j] = getMaxSum(a, grid, i, j);
                    if(max < grid[i][j])
                        max = grid[i][j];
                }
            }
        }
        return max;
    }

    private static int getMaxSum(int[][] a,int[][] grid, int i,int j) {
        if(!isValid(a,i,j))
            return 0;
        else {
            int temp = a[i][j];
            a[i][j] = -1;

            grid[i][j] = temp + Math.max(Math.max(getMaxSum(a,grid,i-1,j), getMaxSum(a,grid,i,j-1)),
                    Math.max(getMaxSum(a,grid,i,j+1), getMaxSum(a,grid,i+1,j)));

            a[i][j] = temp;
            return grid[i][j];
        }
    }

    private static int getMaxSumDP(int[][] a,int[][] grid, int i,int j) {
        if(!isValid(a,i,j))
            return 0;
        else {
            if(grid[i][j] == -1) {
                int temp = a[i][j];
                a[i][j] = -1;

                grid[i][j] = temp + Math.max(Math.max(grid[i-1][j], grid[i][j-1]),
                        Math.max(grid[i][j+1], grid[i+1][j]));

                a[i][j] = temp;
            }
            return grid[i][j];
        }
    }

    private static boolean isValid(int[][] a,int i,int j) {
        return i>=0 && i<a.length &&
                j>=0 && j<a[0].length &&
                a[i][j]!=0 && a[i][j]!=-1;

    }

    private void dfs(int[][] land, int i,int j, int r,int c, int[] list) {
        if(!isValid(land, i,j,r,c))
            return;

        //once visited, mark it as 0
        land[i][j] = 0;

        dfs(land, i,j+1, r,c,list);
        dfs(land, i+1, j, r,c,list);

        list[2] = Math.max(list[2], i);
        list[3] = Math.max(list[3], j);
    }

    private boolean isValid(int[][] land, int i,int j,int r,int c) {
        return i>=0 && i<r && j>=0 && j<c && land[i][j] == 1;
    }

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
//        int[][] grid = {{2,1,3},{6,5,4},{7,8,9}};
//        System.out.println(new MatrixTest().minFallingPathSum(grid));

        int[][] a = {{0,6,0},{5,8,7},{0,9,0}};
        System.out.println(getMaximumGold(a));

    }
}
