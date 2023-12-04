package graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimumEffortTest {
    public static void main(String[] args) {

        int[][] h={{1,2,2},{3,8,2},{5,3,5}};
        System.out.println(new MinimumEffortTest().minimumEffortPath(h));
    }

    public int minimumEffortPath(int[][] h) {

        int n=h.length;
        int min=Integer.MAX_VALUE;
        int[][] vis=new int[n][n];

        PriorityQueue<int[]> pq=new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);

        pq.offer(new int[]{0,0,h[0][0]});
        vis[0][0]=1;

        while(!pq.isEmpty()) {
            int[] a=pq.poll();
            int i=a[0], j=a[1], d=a[2];

            if(i==n-1 && j==n-1)
                return d;
            int[][] points = {{1,0},{-1,0},{0,1},{0,-1}};
            for(int[] point: points) {
                int x=i+point[0], y=j+point[1];

                if(x<0 || x>=n || y<0 || y>=n || vis[x][y]==1)
                    continue;

                int diff=Math.abs(h[x][y] - h[i][j]);

                if(x!=n-1 && y!=n-1)
                    vis[x][y]=1;
                pq.offer(new int[]{x,y,diff});
            }
            pq.forEach(z -> System.out.print(Arrays.toString(z)));
            System.out.println();
        }

        return -1;
    }
}
