package sorting;

import java.util.Arrays;

public class Merge2SortedArrays {

    public static void main(String[] args) {

        int[] a={1,5,9,10,15,20};
        int m = a.length;
        int[] b={2,3,8,13};
        int n = b.length;

        merge(a,m,b,n);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
    }

    public static void merge(int[] a,int m,int[] b,int n) {
        for(int i=n-1;i>=0;i--) {
            int j, last = a[m-1] ;
            for(j=m-2;j>=0 && a[j] > b[i];j--)
                a[j+1]=a[j];

            if(last > b[i]) {
                a[j+1] = b[i];
                b[i] = last;
            }
        }
    }


}
