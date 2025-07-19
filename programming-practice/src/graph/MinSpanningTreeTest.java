package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MinSpanningTreeTest {

    public static void main(String[] args) {


    }

    public void minSpanningTree() {
        //Prim's Algorithm

        int n=6;
        List<List<int[]>> adj=new ArrayList<>(6);
        for(int i=0;i<n;i++)
            adj.add(new ArrayList<>());

        PriorityQueue<int[]> pq=new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        pq.offer(new int[]{0,0});

        int[] vis = new int[n];
        int s=0;
        while(!pq.isEmpty()) {
            int[] x = pq.poll();
            int node = x[0], edgeWt = x[1];

            s+= edgeWt;
            if(vis[node] == 1)
                continue;

            vis[node] = 1;
            for(int[] childNodes: adj.get(node)) {

                int childNode = childNodes[0], childEdgeWt = childNodes[1];
                pq.offer(new int[]{childNode, childEdgeWt});
            }
        }

        System.out.println("minimum spanning tree : "+s);
    }
}
