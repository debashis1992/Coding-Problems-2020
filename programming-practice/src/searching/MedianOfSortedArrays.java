package searching;

import java.util.Arrays;

public class MedianOfSortedArrays {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,10};
        int[] b = {5,6, 7, 8};
        int[] result = merge(a,b);
        System.out.println(Arrays.toString(result));
        if(result.length%2!=0)
            System.out.println(result[result.length/2]);
        else {
            double median = ((double) result[result.length / 2 - 1] + (double) result[(result.length / 2)]) / 2.0;
            System.out.println(median);
        }
    }
    private static int[] merge(int [] a,int[] b) {
        int n1 = a.length;
        int n2 = b.length;
        int[] result = new int[n1+n2];
        int i=0,j=0,k=0;
        while(i < n1 && j < n2) {
            if(a[i]<b[j])
                result[k++] = a[i++];
            else result[k++] = b[j++];
        }
        while(i<n1)
            result[k++] = a[i++];
        while(j<n2)
            result[k++] = b[j++];

        return result;
    }
}
