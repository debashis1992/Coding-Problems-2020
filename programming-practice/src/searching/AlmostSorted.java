package searching;

import java.util.Scanner;

public class AlmostSorted {

    public static void main(String[] args) {

//        int[] a = {10, 3, 40, 20, 50, 80, 70};
//        int term = 70;
//        System.out.println(findInAlmostSorted(a, term));?
        int[] a = {1,1,1,1,0,0,0};
        System.out.println(find1s(a));
    }

    public static int find1s(int[] a) {
        return find1s(a, 0 ,a.length-1);
    }

    public static int find1s(int[] a,int l,int h) {
        if(l<=h) {
            int mid = (l+h)/2;
            if(mid> 0 && a[mid]==0 && a[mid-1]==1)
                return mid;
            else if(a[mid]==0)
                return find1s(a,l,mid-1);
            else if(a[mid] > 0)
                return find1s(a, mid+1, h);
        }
        return 0;
    }

    public static int findInAlmostSorted(int[] a, int term) {
        return findInAlmostSorted(a, 0, a.length-1, term);
    }
    public static int findInAlmostSorted(int[] a,int l,int h, int term) {
        if(l<=h) {
            int mid = (l+h)/2;
            if(a[mid] == term)
                return mid;
            else if(mid > 0 && a[mid-1]==term)
                return mid-1;
            else if(mid < a.length-1 && a[mid+1] == term)
                return mid+1;

            if(a[mid] < term)
                return findInAlmostSorted(a, mid+2, h, term);
            else return findInAlmostSorted(a, l, mid-2, term);
        }

        return -1;
    }
}
