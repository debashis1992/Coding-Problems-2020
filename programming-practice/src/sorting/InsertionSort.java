package sorting;

import java.lang.reflect.Array;
import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] a = {12, 11, 13,5,6};
//        performInsertionSort(a);
        performRecursiveInsertionSort(a, a.length);
        System.out.println(Arrays.toString(a));
    }


    public static void performInsertionSort(int[] a) {
        for(int i=0;i<a.length-1;i++) {
            boolean swapped = false;
            for(int j=i+1;j<a.length;j++) {
                if(a[i] > a[j]) {
                    //swap or shift remaining elements
                    swapped = true;
                    int temp = a[j];
                    for(int k=j;k-1>=0;k--) {
                        a[k] = a[k-1];
                    }
                    a[i] = temp;
                }
            }
            if(!swapped)
                break;
            System.out.println(Arrays.toString(a));
        }
    }
    public static void performRecursiveInsertionSort(int[] a, int n) {
        if(n<=1)
            return;

        performRecursiveInsertionSort(a, n-1);
        int last = a[n-1];
        int j = n-2;

        while(j>=0 && a[j] > last) {
            a[j+1] = a[j];
            j--;
        }
        a[j+1] = last;

    }
}
