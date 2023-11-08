package graph;

import java.util.*;

public class TopoSortTest {
    public static void main(String[] args) {

        KahnAlgorithm algorithm=new KahnAlgorithm();
        List<List<Integer>> adjList=new ArrayList<>(
                List.of(
                        List.of(),
                        List.of(0),
                        List.of(1),
                        List.of(2),
                        List.of(0,1),
                        List.of(0,3)

                )
        );

        algorithm.sort(adjList, 6);
    }
}

class KahnAlgorithm {
    public void sort(List<List<Integer>> adjList, int v) { // BFS
        int[] indg=new int[v];

        //creating in-degree array
        for (List<Integer> integers : adjList) {
            for (Integer integer : integers) indg[integer]++;
        }
        Queue<Integer> q=new LinkedList<>();

        for(int i=0;i<indg.length;i++) {
            if(indg[i]==0)
                q.offer(i);
        }

        List<Integer> list=new ArrayList<>();
        while(!q.isEmpty()) {
            int node=q.poll();
            for(int child: adjList.get(node)) {
                indg[child]--;
                if(indg[child] == 0)
                    q.offer(child);
            }
            list.add(node);
        }

        System.out.println("topo list: "+list);
    }
}

class TopoSort {
    int[] vis;
    Stack<Integer> st;
    public void sort(int[][] edges, int v, int e) { // DFS

        st=new Stack<>();
        vis=new int[v];
        for(int i=0;i<v;i++) {
            if(vis[i]==0)
                dfs(i, edges);
        }
        int[] ans=new int[v];
        int k=0;
        while(!st.isEmpty()) {
            ans[k++]=st.pop();
        }
        System.out.println(Arrays.toString(ans));
    }

    public void dfs(int node, int[][] edges) {
        vis[node]=1;
        for(int child: edges[node]) {
            if(vis[child]==0)
                dfs(child, edges);
        }
        st.add(0,node);
    }
}
