package graph;

import java.util.*;

public class CycleTest {
    public static void main(String[] args) {


        List<String> list=Arrays.asList("hit","hot","dog");
        char[] ch = {'h','o','t'};
        String st = String.valueOf(ch);
        System.out.println(st);
        System.out.println(list.contains("hot"));

        List<List<String>> anotherList=  new ArrayList<>();

        Solution s=new Solution();
        int[][] edges = {{1,2},{2,3},{3,1}};
//        System.out.println(s.cycleDetection(edges, 3, 3));

        int[][] mat={{0,0,0},{0,1,0},{1,1,1}};
        int[][] res = s.updateMatrix(mat);

        print(res);
    }

    public static void print(int[][] ar) {
        for(int i=0;i<ar.length;i++)
            System.out.println(Arrays.toString(ar[i]));
    }

}

class Solution {

    boolean[][] v;
    public int[][] updateMatrix(int[][] mat) {
        int r=mat.length, c=mat[0].length;
        int[][] mod=new int[r][c];
        v=new boolean[r][c];
        for(int i=0;i<r;i++)
            Arrays.fill(v[i], false);

        Queue<int[]> q=new LinkedList<>();
        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                mod[i][j] = mat[i][j];
                if(mod[i][j] == 1)
                    q.offer(new int[]{i,j});
            }
        }

        while(!q.isEmpty()) {
            int[] pair=q.poll();
            mod[pair[0]][pair[1]] = findNearest0(pair[0], pair[1],r,c,0,mat);
        }

        return mod;
    }

    public int findNearest0(int i, int j, int r, int c, int level, int[][] mat) {
        if(i<0 || i>=mat.length || j<0 || j>=mat[0].length) return 0;
        if(mat[i][j] == 0) return level;

        else {
            int[][] points={{-1,0},{1,0},{0,1},{0,-1}};
            int min=Integer.MAX_VALUE;
            for(int[] point: points) {
                int x=point[0]+i;
                int y=point[1]+j;
//                min=Math.min(min, findNearest0(x,y,r,c,level+1,mat));
            }
            return min;
        }
    }

    boolean[] vis;
    public boolean cycleDetection(int[][] edges, int n, int m) {
        vis=new boolean[n+1];

        List<List<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n+1;i++)
            adj.add(new ArrayList<>());

        for(int i=0;i<edges.length;i++) {
            int[] p=edges[i];
            adj.get(p[0]).add(p[1]);
            adj.get(p[1]).add(p[0]);
        }

        for(int i=0;i<vis.length;i++) {
            if(!vis[i]) {
                if(dfs(i, -1, adj)) return true;
            }
        }
        return false;

    }
    public boolean dfs(int node, int parent, List<List<Integer>> edges) {
        vis[node]=true;

        for(int child: edges.get(node)) {
            if(!vis[child]) {
                if(dfs(child, node, edges)) return true;
            }
            else if(child!=parent) return true;
        }
        return false;
    }
}