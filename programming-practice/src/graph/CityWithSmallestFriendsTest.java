package graph;

import java.util.*;

public class CityWithSmallestFriendsTest {
    public static void main(String[] args) {
        CityWithSmallestFriendsTest c=new CityWithSmallestFriendsTest();
        int[][] edges = {{0,1,10},{0,2,1},{2,3,1},{1,3,1},{1,4,1},{4,5,10}};
        int ans = c.findTheCity(6, edges, 20);

        System.out.println(ans);
    }

    int[] city;
    boolean[] vis;
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        city=new int[n];
        vis=new boolean[n];

        List<List<int[]>> adj=new ArrayList<>();
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());

        for(int i=0;i<edges.length;i++) {
            int[] p=edges[i];
            adj.get(p[0]).add(new int[]{p[1], p[2]});
            adj.get(p[1]).add(new int[]{p[0], p[2]});
        }

        int min=Integer.MAX_VALUE;
        int ans=-1;
        for(int i=0;i<n;i++) {
            int nCities = bfs(i, adj, distanceThreshold);
            city[i]=nCities;
            if(min >= nCities) {
                ans=i;
                min = nCities;
            }
        }
        System.out.println("cities: "+ Arrays.toString(city));
        return ans;
    }
    public int bfs(int node, List<List<int[]>> adj, int threshold) {
        vis[node]=true;
        Queue<int[]> q=new LinkedList<>();
        q.offer(new int[]{node, 0});
        int nCities = 0;
        List<Integer> visitedChild = new ArrayList<>();
        while(!q.isEmpty()) {
            int[] polled=q.poll();
            int n=polled[0], step=polled[1];

            for(int[] childProps: adj.get(n)) {
                int child=childProps[0], distance=childProps[1];
                if(!vis[child] && ((step + distance) <= threshold)) {
                    vis[child]=true;
                    q.offer(new int[]{child, step+distance});
                    visitedChild.add(child);
                    nCities++;
                }
            }
        }
        vis[node]=false;
        for(int child: visitedChild)
            vis[child]=false;
        return nCities;
    }
}
