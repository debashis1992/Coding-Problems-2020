package leetcodeJuneChallenge;

public class MinimumPathSum {
    public static void main(String[] args) {
        int[][] grid = {
                {1,3,1},
                {1,5,1},
                {4,2,1}
        };

        System.out.println(findMinPathSum(grid));
    }
    public static int findMinPathSum(int[][] grid) {
        return findMinPathSumRec(grid, 0, 0);
    }
    public static int findMinPathSumRec(int[][] grid,int row,int col) {
        if(row == grid.length-1 && col == grid[0].length-1)
            return grid[row][col];
        if(row == grid.length-1)
            return grid[row][col] + findMinPathSumRec(grid, row, col+1);
        if(col == grid[0].length-1)
            return grid[row][col] + findMinPathSumRec(grid, row+1, col);

        return grid[row][col] + Math.min(findMinPathSumRec(grid, row, col+1),
                    findMinPathSumRec(grid, row+1, col));


    }
}
