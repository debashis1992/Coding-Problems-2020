package sorting;

import java.util.Arrays;

public class CountInversionsTest {

    public static void main(String[] args) {
        int[] a={8,4,2,1};
        System.out.println(mergeAndSort(a, 0, a.length-1));
    }

    public static int mergeAndSort(int[] a,int l,int h) {
        int count=0;
        if(l<h) {
            int mid = l+(h-l)/2;
            count+= mergeAndSort(a, l, mid);
            count+= mergeAndSort(a, mid+1, h);
            count+= mergeAndCount(a, l, mid, h);
        }
        return count;
    }

    public static int mergeAndCount(int[] a,int l,int mid, int h) {
        int[] left = Arrays.copyOfRange(a, l, mid+1);
        int[] right = Arrays.copyOfRange(a, mid+1, h+1);

        int i=0,j=0,k=l,swaps=0;
        while(i<left.length && j<right.length) {
            if(left[i] < right[j])
                a[k++] = left[i++];
            else {
                swaps+= (mid+1) - (l+i);
                a[k++] = right[j++];
            }
        }

        while(i<left.length)
            a[k++] = left[i++];
        while(j<right.length)
            a[k++] = right[j++];

        return swaps;
    }
}
