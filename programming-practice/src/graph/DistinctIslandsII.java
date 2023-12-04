package graph;

import sorting.Test;

import java.util.*;

public class DistinctIslandsII {
    public static void main(String[] args) {

        Testing testing=new Testing();
        int[][] grid={{1,1,0,0,0},
                {1,0,0,0,0,},
                {0,0,0,0,1},
                {0,0,0,1,1}};

        System.out.println(testing.numDistinctIslands2(grid));
    }
}

class Testing {

    public int numDistinctIslands2(int[][] grid) {

        Set<Map<Integer, Integer>> setMap = new HashSet<>();
        int r=grid.length, c=grid[0].length;
        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++) {
                if(grid[i][j] == 1) {

                    List<int[]> positions = new ArrayList<>();
                    dfs(grid, i, j, positions);
                    int n=positions.size();
                    Map<Integer, Integer> map=new HashMap<>();
                    for(int x=0;x<n;x++) {
                        for(int y=x+1;y<n;y++) {
                            int dist = (int)(Math.pow((positions.get(x)[0] - positions.get(y)[0]), 2) +
                                    Math.pow((positions.get(x)[1] - positions.get(y)[1]), 2));
                            map.put(dist, map.getOrDefault(dist, 0)+1);
                        }
                    }
                    setMap.add(map);
                }
            }
        }

        return setMap.size();
    }

    public void dfs(int[][] grid, int i, int j, List<int[]> positions) {
        positions.add(new int[]{i,j});
        grid[i][j]=0;
        int[][] points = {{-1,0},{1,0},{0,1},{0,-1}};

        for(int[] point: points) {
            int x=i+point[0], y=j+point[1];
            if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || grid[x][y]==0)
                continue;
            dfs(grid, x, y, positions);
        }
    }
}
