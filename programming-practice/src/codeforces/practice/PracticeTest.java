package codeforces.practice;

import java.util.Scanner;

public class PracticeTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t-->0) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for(int i=0;i<n;i++)
                a[i]=sc.nextInt();
            System.out.println(findMinDiff(a,a.length-1));
        }

    }
    public static int findMinDiff(int[] a,int n) {
        //sort the array
        performMergeSort(a,0,n);

        //find the min diff using a binary search algorithm
        int minDiff = findMin(a,n);
        //return the result
        return minDiff;
    }
    private static int findMin(int[] a,int n) {
        int minDiff = Integer.MAX_VALUE;
        int diff = 0;
        for(int i=0;i+1<=n;i++) {
            diff = Math.abs(a[i]-a[i+1]);
            minDiff=Math.min(diff,minDiff);
        }
        return minDiff;
    }
    private static int findMin(int[] a, int start, int end) {
        int diff = 0;
        int minDiff = Integer.MAX_VALUE;
        while(start<end) {
            int mid = (start+end)/2;
            if(mid+1 <= end) {
                diff = Math.abs(a[mid+1] - a[mid]);
            }
            if(mid-1 >=start) {
                diff = Math.abs(a[mid-1] - a[mid]);
            }
            minDiff = Math.min(diff, minDiff);
            return Math.min(minDiff, Math.min(findMin(a, start+1, end), findMin(a, start,end-1)));
        }
        return minDiff;
    }
    private static void performMergeSort(int[] a,int f,int l) {
        if(f<l) {
            int middle = (f + l) / 2;
            performMergeSort(a, f, middle);
            performMergeSort(a, middle + 1, l);
            merge(a, f, middle, l);
        }
    }
    private static void merge(int[] a,int f,int mid, int l) {
        int n1=mid-f+1;
        int n2=l-mid;

        int[] left=new int[n1];
        int[] right=new int[n2];

        for(int i=0;i<n1;i++)
            left[i]=a[f+i];
        for(int i=0;i<n2;i++)
            right[i]=a[mid+1+i];

        int i=0,j=0,k=f;
        while(i<n1 && j<n2) {
            if (left[i] < right[j])
                a[k++] = left[i++];
            else a[k++] = right[j++];
        }
        while(i<n1){
            a[k++]=left[i++];
        }
        while(j<n2) {
            a[k++]=right[j++];
        }
    }
}
