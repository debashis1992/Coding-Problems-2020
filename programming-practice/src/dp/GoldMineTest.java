package dp;

public class GoldMineTest {
    public static void main(String[] args) {
        int[][] m = {{10, 33, 13, 15},
                {22, 21, 04, 1},
                {5, 0, 2, 3},
                {0, 6, 14, 2}};
        int row = m.length;
        int col = m[0].length;
        int max = Integer.MIN_VALUE;
        for(int i=0;i<row;i++) {
            int res = findMax(row, col, m, i, 0);
            if(res > max)
                max = res;
        }
        System.out.println(max!=Integer.MIN_VALUE ? max : -1);
    }
    public static int findMax(int row, int col, int[][] m, int i,int j) {
        if(i < 0 || i >= row || j >= col)
            return 0;
        else return max(m[i][j] + findMax(row, col, m, i, j+1) ,
                m[i][j] + findMax(row, col, m, i-1, j+1) ,
        m[i][j] + findMax(row, col,m,i+1,j+1));
    }

    public static int max(int a,int b,int c) {
        return Math.max(Math.max(a,b),c);
    }
}
