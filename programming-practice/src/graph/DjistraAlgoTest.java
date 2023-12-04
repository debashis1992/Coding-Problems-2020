package graph;

import java.util.*;

public class DjistraAlgoTest {
    public static void main(String[] args) {
        DjistraAlgoTest t=new DjistraAlgoTest();

        List<List<List<Integer>>> items = List.of(
                List.of(
                        List.of(1,1), List.of(2,6)
                ),
                List.of(
                        List.of(0,1), List.of(2,3)
                ),
                List.of(
                        List.of(0,6), List.of(1,3)
                )
        );
        t.solve(3, items, 2);

    }

    public void solve(int v, List<List<List<Integer>>> adj, int src) {
        int lim=(int)1e9;
        int[] d=new int[v];
        Arrays.fill(d, lim);
        d[src]=0;

        PriorityQueue<int[]> pq=new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        pq.add(new int[]{0, src});

        while(!pq.isEmpty()) {
            int[] last=pq.poll();
            int dist=last[0], node=last[1];

            for(List<Integer> props: adj.get(node)) {
                int child=props.get(0), wt=props.get(1);
                if(dist + wt < d[child]) {
                    int newDistance = dist+wt;
                    d[child] = newDistance;
                    pq.add(new int[]{newDistance, child});
                }
            }
        }

        System.out.println(Arrays.toString(d));

    }
}
