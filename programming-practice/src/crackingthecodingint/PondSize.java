package crackingthecodingint;

import java.util.ArrayList;
import java.util.List;

public class PondSize {
    public static int[][] points = {
            {0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}
    };
    public static void main(String[] args) {

        int[][] arr = {
                {0, 2, 1, 0},
                {0, 1, 0, 1},
                {1, 1, 0, 1},
                {0, 1, 0, 1}
        };

        solve(arr);
    }

    public static void solve(int[][] a) {
        int r=a.length, c=a[0].length;

        int[][] vis=new int[r][c];
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                if(a[i][j] == 0 && vis[i][j] == 0) {
                    int count = dfs(a, i, j, vis);
                    list.add(count);
                    vis[i][j] = 1;
                }
            }
        }

        System.out.println(list);

    }

    public static int dfs(int[][] a, int i, int j, int[][] vis) {
        vis[i][j]=1;

        int c=1;
        for(int[] point: points) {
            int x=i+point[0], y=j+point[1];
            if(x>=0 && x<a.length && y>=0 && y<a[0].length && a[x][y]==0 && vis[x][y]==0) {
                c+= dfs(a, x, y, vis);
            }
        }
        return c;
    }
}
