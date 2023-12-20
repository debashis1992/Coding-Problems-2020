package matrix;

import java.util.ArrayList;
import java.util.List;

public class SpiralTest {

    public void solve(int[][] a, int i, int j, int r, int c) {
        int n=i;
        for(int k=j;k<c;k++)
            list.add(a[n][k]);

        int m=c-1;
        for(int k=i+1;k<r;k++)
            list.add(a[k][m]);

        n=r-1;
        for(int k=c-2;k>=j;k--)
            list.add(a[n][k]);

        m=j;
        for(int k=r-2;k>i;k--)
            list.add(a[k][m]);

    }

    List<Integer> list;
    public List<Integer> spiralOrder() {
        int[][] a={{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        list=new ArrayList<>();


        int r=a.length, c=a[0].length;
        int i=0,j=0;
        while(i<r && j<c) {
            solve(a, i,j,r,c);
            i++;
            j++;
            r--;
            c--;
        }

        System.out.println(list);
        return list;
    }

    public static void main(String[] args) {
        SpiralTest sp=new SpiralTest();
        sp.spiralOrder();
    }

}

