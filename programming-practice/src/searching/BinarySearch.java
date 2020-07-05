package searching;

import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t-->0) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for(int i=0;i<n;i++)
                a[i] = sc.nextInt();
            int term = sc.nextInt();
//            System.out.println(performBinarySearch(a,term));
            System.out.println(performRecursiveBinarySearch(a, term));
        }
    }

    public static int performBinarySearch(int[] a,int term) {
        int low = 0, high = a.length - 1;
        while(low <= high) {
            int mid = (low+high)/2;
            if(term < a[mid])
                high = mid-1;
            else if(term > a[mid])
                low = mid+1;
            else return mid;
        }
        return -1;
    }

    public static int performRecursiveBinarySearch(int[] a, int term) {
        return callBinarySearch(a, 0 ,a.length-1, term);
    }
    public static int callBinarySearch(int[] a, int low, int high, int term) {
        while(low<=high) {
            int mid = (low+high)/2;
            if(term < a[mid])
                return callBinarySearch(a, low,mid-1,term);
            else if(term > a[mid])
                return callBinarySearch(a, mid+1, high, term);
            else return mid;
        }
        return -1;
    }
}
