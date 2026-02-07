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
//        t.solve(3, items, 2);
        t.solve();

        int idx = ('z' - 'a');
        System.out.println(idx);
        char c = (char)(idx + 'a');
        System.out.println(c);

    }

    public void solve() {
        int n=4;
        int[] d=new int[n+1];
        int lim = (int)1e5;
        Arrays.fill(d, lim);
        List<List<int[]>> adj=new ArrayList<>();
        for(int i=0;i<=n;i++)
            adj.add(new ArrayList<>());

        adj.get(1).add(new int[]{2,2});
        adj.get(1).add(new int[]{4,1});
        adj.get(2).add(new int[]{1,2});
        adj.get(2).add(new int[]{3,4});
        adj.get(3).add(new int[]{2,4});
        adj.get(3).add(new int[]{4,3});
        adj.get(4).add(new int[]{1,1});
        adj.get(4).add(new int[]{3,3});

        PriorityQueue<int[]> pq=new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.offer(new int[]{1,0});
        d[1]=0;

        while(!pq.isEmpty()) {
            int[] ar=pq.poll();
            int u=ar[0], cost=ar[1];
            if(d[u] < cost)
                continue;

            for(int[] edge: adj.get(u)) {
                int v=edge[0], wt=edge[1];

                if(d[u]!=lim && d[v] > d[u] + wt) {
                    d[v]=d[u]+wt;
                    pq.offer(new int[]{v, d[v]});
                }
            }
        }

        System.out.println(Arrays.toString(d));

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
