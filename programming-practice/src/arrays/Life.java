package arrays;

import java.util.ArrayList;
import java.util.List;

public class Life {
    public static void main(String[] args) {
//        int[][] a = {{0,0,0,1,0,0},{0,1,1,1,1,0},{0,1,1,1,1,0}};
//        Life life = new Life();
//
//        life.gameOfLife(a);
        System.out.println(4^1^2^1^2);

    }
    public void gameOfLife(int[][] a) {
        int row = a.length, col = a[0].length;
        int[][] res = new int[row][col];


        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                //for position a[i][j], find the live cell count
                int live = findLiveCellCount(a,i,j);

                if(a[i][j] == 1) {
                    if(live < 2)
                        res[i][j] = 0;
                    else if (live>=2 && live<=3)
                        res[i][j] = 1;
                    else if (live>3)
                        res[i][j] = 0;
                } else {
                    if(live == 3)
                        res[i][j] = 1;
                }
            }
        }
        print(res);
        copy(a, res);
    }
    public void print(int[][] res) {
        int row = res.length;
        int col = res[0].length;
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                System.out.print(res[i][j]+", ");
            }
            System.out.println();
        }
    }

    public void copy(int[][] a,int [][] res) {
        int row = a.length;
        int col = a[0].length;

        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                a[i][j] = res[i][j];
            }
        }
    }
    public int findLiveCellCount(int[][] a, int row, int col) {
        List<Integer> list = new ArrayList<>();
        if(row-1>=0)
            list.add(a[row-1][col]);
        if(row+1<a.length)
            list.add(a[row+1][col]);
        if(col-1>=0)
            list.add(a[row][col-1]);
        if(col+1<a[0].length)
            list.add(a[row][col+1]);
        if(row-1>=0 && col-1>=0)
            list.add(a[row-1][col-1]);
        if(row+1<a.length && col-1>=0)
            list.add(a[row+1][col-1]);
        if(row+1<a.length && col+1<a[0].length)
            list.add(a[row+1][col+1]);
        if(row-1>=0 && col+1<a[0].length)
            list.add(a[row-1][col+1]);

        int count=0;
        for(int val: list) {
            if(val == 1)
                count+=1;
        }

        return count;
    }
}
