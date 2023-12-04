package graph;

import java.util.Arrays;
import java.util.List;

public class BellmanFordTest {


}

class SolutionTest2 {
    public int[] shortestDistance(int n, List<List<Integer>> edges, int src) {
        int limit=(int)1e8;
        int[] d=new int[n];
        Arrays.fill(d, limit);
        d[src]=0;

        for(int i=0;i<n-1;i++) {
            for(List<Integer> edge: edges) {
                int u=edge.get(0);
                int v=edge.get(1);
                int wt=edge.get(2);
                if(d[u]!=limit && d[u]+wt < d[v]) {
                    d[v]=d[u]+wt;
                }
            }
        }

        for(List<Integer> edge: edges) {
            int u=edge.get(0);
            int v=edge.get(1);
            int wt=edge.get(2);
            if(d[u]!=limit && d[u]+wt < d[v]) {
                return new int[]{-1};
            }
        }
        return d;
    }

}
