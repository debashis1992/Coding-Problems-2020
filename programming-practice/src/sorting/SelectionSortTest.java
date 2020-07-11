package sorting;

import java.util.Arrays;

public class SelectionSortTest {
    public static void main(String[] args) {
        int[] a = {64, 25, 12, 22, 11};
        performSelectionSort(a);
    }
    public static void performSelectionSort(int[] a) {
        /*
        Select the minimum element present over a given range
        range starts from 0 .... n, 1...n, 2..n , n-1...n
         */
        for(int i=0;i<a.length;i++) {
            int min_index = -1;
            int min = a[i];
            for(int j=i+1;j<a.length;j++) {
                if(a[j] < min) {
                    min = a[j];
                    min_index = j;
                }
            }
            if(min_index!=-1) {
                int temp = a[i];
                a[i] = min;
                a[min_index] = temp;
            }
        }
        System.out.println(Arrays.toString(a));
    }
}
