package graph;

import java.util.Arrays;

public class SurroundRegions {
    public void solve(char[][] b) {
        int r=b.length, c=b[0].length;
        int[][] vis=new int[r][c];

        // check boundaries
        // check first. last row
        for(int i=0;i<c;i++) {
            if(vis[0][i] == 0 && b[0][i]=='O')
                dfs(0,i,r,c,vis,b);
            if(vis[r-1][i] == 0 && b[r-1][i] == 'O')
                dfs(r-1,i,r,c,vis,b);
        }

        // check first, last column
        for(int i=0;i<r;i++) {
            if(vis[i][0] == 0 && b[i][0] == 'O')
                dfs(i,0,r,c,vis,b);
            if(vis[i][c-1]==0 && b[i][c-1]=='O')
                dfs(i,c-1,r,c,vis,b);
        }


        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                if(vis[i][j]==0 && b[i][j]=='O') {
                    b[i][j] = 'X';
                    vis[i][j] = 1;
                }
            }
        }


        print(b);
    }

    public void dfs(int i, int j, int r, int c, int[][] vis, char[][] b) {
        vis[i][j]=1;
        int[][] points={{-1,0},{1,0},{0,1},{0,-1}};
        for(int[] point: points) {
            int x=point[0]+i;
            int y=point[1]+j;

            if(x>=0 && x<r && y>=0 && y<c && vis[x][y]==0 && b[x][y]=='O')
                dfs(x,y,r,c,vis,b);
        }
    }

    public static void print(char[][] ar) {
        for(int i=0;i<ar.length;i++)
            System.out.println(Arrays.toString(ar[i]));
    }

    public static void main(String[] args) {
        SurroundRegions regions=new SurroundRegions();
        char[][] b={{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        regions.solve(b);
    }
}
