package graph;

import java.util.*;

public class CourseScheduleTest {
    boolean[] vis;
    List<Integer> ans;
    public int[] findOrder(int numCourses, int[][] pre) {

        List<List<Integer>> adj=new ArrayList<>();
        for(int i=0;i<=pre.length;i++)
            adj.add(new ArrayList<>());

        for(int i=0;i<pre.length;i++) {
            int[] ar=pre[i];

            adj.get(ar[1]).add(ar[0]);

        }

        System.out.println(adj);
        vis=new boolean[pre.length+1];
        ans=new ArrayList<>();

        for(int i=0;i< adj.size();i++) {
            if(!vis[i] && adj.get(i).size() > 0)
                visit(i, adj);
        }

        System.out.println(ans);
        if(ans.size() >= numCourses)
            return ans.stream().mapToInt(x -> x).toArray();
        return new int[]{};
    }

    public void visit(int node, List<List<Integer>> adj) {
        Queue<Integer> q=new LinkedList<>();

        q.offer(node);
        while(!q.isEmpty()) {
            int n=q.poll();
            if(vis[n]) continue;
            vis[n]=true;
            ans.add(n);

            for(int child: adj.get(n)) {
                if(!vis[child])
                    q.offer(child);
            }
        }
    }

    public static void main(String[] args) {
        CourseScheduleTest test=new CourseScheduleTest();

        int[] ans = test.findOrder(1, new int[][]{{0,1}});
        System.out.println(Arrays.toString(ans));
    }
}


