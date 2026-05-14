package crackingthecodingint;

import java.util.Arrays;

public class SmallestDiff {
    public static void main(String[] args) {

        int[] a={-10, -5, 0, 5};
        int[] b={-6, -2, 3, 9};

        Arrays.sort(a);
        Arrays.sort(b);

        int i=0,j=0;
        long min = Long.MAX_VALUE;

        int[] res=new int[2];

        while(i<a.length && j<b.length) {
            long diff = Math.abs((long)a[i] - b[j]);
            if(min > diff) {
                res[0]=a[i];
                res[1]=b[j];
                min = diff;
            }

            if(a[i] < b[j])
                i++;
            else j++;
        }
        System.out.println("min: "+min);
        System.out.println(Arrays.toString(res));

    }

}


