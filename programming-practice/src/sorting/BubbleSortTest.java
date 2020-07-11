package sorting;

import java.util.Arrays;

public class BubbleSortTest {
    public static void main(String[] args) {
        int[] a = {64, 25, 12, 11, 6};
        //performBubbleSort(a);
        performBubbleSortRecursively(a);
    }
    public static void performBubbleSort(int[] a) {
        for(int i=0;i<a.length-1;i++) {
            boolean swapped = false;
            for(int j=0;j<a.length-i-1;j++) {
                if(a[j] > a[j+1]) {
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    swapped = true;
                }
            }
            System.out.println(Arrays.toString(a));
            if(!swapped)
                break;
        }
        System.out.println(Arrays.toString(a));
    }

    public static void performBubbleSortRecursively(int[] a) {
        performBubbleSortUsingRecursion(a, a.length);
        System.out.println(Arrays.toString(a));
    }

    private static void performBubbleSortUsingRecursion(int[] a,int n) {
        // base case
        if(n==1)
            return;

        for(int i=0;i<n-1;i++) {
            if(a[i] > a[i+1]) {
                int temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
            }
        }
        performBubbleSortUsingRecursion(a, n-1);

    }
}
