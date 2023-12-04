package graph;

import java.util.*;

public class ShortestPathUndirectedGraphTest {
    public static void main(String[] args) {
        SolutionTest1 t1=new SolutionTest1();
        int[][] edges={{0,1},{0,3},{3,4},{4 ,5},{5, 6},{1,2},{2,6},{6,7},{7,8},{6,8}};
        int[] res=t1.solve(edges, 9, 10);
        System.out.println(Arrays.toString(res));
    }
}

class SolutionTest1 {
    boolean[] vis;
    int[] d;
    public int[] solve(int[][] edges, int v, int e) {
        vis=new boolean[v];
        d=new int[v];
        List<List<Integer>> adj=new ArrayList<>();
        for(int i=0;i<v;i++)
            adj.add(new ArrayList<>());

        for(int i=0;i<edges.length;i++) {
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }

        Queue<int[]> q=new LinkedList<>();
        q.offer(new int[]{0,0});
        vis[0]=true;
        while(!q.isEmpty()) {
            int s=q.size();
            for(int i=0;i<s;i++) {
                int[] pair=q.poll();
                int node=pair[0], step=pair[1];
                d[node]=step;
                for(int child: adj.get(node)) {
                    if(!vis[child]) {
                        q.offer(new int[]{child, step + 1});
                        vis[child] = true;
                    }
                }
            }
        }

        for(int i=0;i<v;i++) {
            if(!vis[i]) d[i]=-1;
        }

        return d;
    }
}
