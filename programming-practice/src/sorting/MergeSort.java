package sorting;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] a = {12,11,13,5,6};
        performMergeSort(a);
        System.out.println(Arrays.toString(a));
    }
    public static void performMergeSort(int[] a) {
        mergeSort(a, 0, a.length-1);
    }

    private static void mergeSort(int[] a,int l,int r) {
        if(r > l) {
            int middle = (l+r)/2;
            mergeSort(a, l, middle);
            mergeSort(a, middle+1, r);
            merge(a, l, middle, r);
        }
    }

    private static void merge(int [] a,int l, int m,int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for(int i=0;i<n1;i++)
            left[i] = a[l+i];
        for(int j=0;j<n2;j++)
            right[j] = a[m+1+j];

        int i=0,j=0,k=l;
        while(i < n1 && j<n2) {
            if(left[i] <= right[j])
                a[k++] = left[i++];
            else a[k++] = right[j++];
        }


        while(i<n1)
            a[k++] = left[i++];
        while(j<n2)
            a[k++] = right[j++];
    }

    public static void performRecursiveMergeSort(int[] a) {
        performRecursiveMergeSort(a, 0, a.length);
        System.out.println(Arrays.toString(a));
    }
    private static void performRecursiveMergeSort(int[] a,int l,int r) {
        if(a.length<=1)
            return;

        int middle = (l+r)/2;
        int[] left = new int[middle];
        int[] right = new int[r - middle];

        for(int i=0;i<middle;i++)
            left[i] = a[i];
        for(int i=middle;i<r;i++)
            right[i - middle] = a[i];

        performRecursiveMergeSort(left, 0, left.length);
        performRecursiveMergeSort(right, 0, right.length);

        int n1 = left.length, n2 = right.length;
        int i=0,j=0,k=0;
        while(i<n1 && j<n2) {
            if(left[i] <= right[j]) {
                a[k++] = left[i++];
            } else a[k++] = right[j++];
        }

        while(i<n1)
            a[k++] = left[i++];
        while(j<n2)
            a[k++] = right[j++];

    }
}
