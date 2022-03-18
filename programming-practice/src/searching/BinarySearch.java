package searching;

import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        int t = sc.nextInt();
//        while(t-->0) {
//            int n = sc.nextInt();
//            int[] a = new int[n];
//            for(int i=0;i<n;i++)
//                a[i] = sc.nextInt();
//            int term = sc.nextInt();
////            System.out.println(performBinarySearch(a,term));
//            System.out.println(performRecursiveBinarySearch(a, term));
//
//
//        }
        int[] a = {4,6,10};
//        int key = 17;
//        System.out.println(ceil(a, key));
//
//        char[] c = {'a','c','f','h'};
//        System.out.println(nextGreater(c, 'a'));
        a = new int[]{1,3,8,12,4,2};
//        System.out.println(findMax(a, 0, a.length-1));
//        System.out.println(searchBit(a, 0, a.length-1, 10));
        a = new int[]{3,3,7,3};
        System.out.println(findMaxInRotated(a, 0, a.length-1));
    }

    public static int findMaxInRotated(int[] a,int f, int l) {
        while(f<l) {
            int mid = f+(l-f)/2;
            if(a[f] == a[mid] && a[mid] == a[l]) {
                ++f;
                --l;
            }
            if(mid == 0) {
                if(a[mid] < a[mid+1])
                    return mid+1;
                else return mid;
            }
            if(a[f] > a[mid])
                l=mid-1;
            else f=mid+1;
        }
        return f;
    }

    public static int findMax(int[] a, int f, int l) {
        while(f<l) {
            int mid = f + (l-f)/2;
            if(a[mid] < a[mid+1])
                f = mid+1;
            else l=mid;
        }
        return f;
    }

    public static int searchInRotated(int[] a, int f, int l, int key) {
       while(f<=l) {
           int mid = f+(l-f)/2;
           if(a[mid] == key)
               return mid;

           if(a[f] <= a[mid]) { //left part is sorted
               if(a[f] <= key && key < a[mid])
                   l=mid-1;
               else f=mid+1;

           } else { // right part is sorted
                if(a[mid] < key && key<=a[l])
                    f=mid+1;
                else l=mid-1;
           }
       }
        return -1;
    }

    public static int callBinarySearchOther(int[] a, int f, int l, int key) {
        while(f<=l) {
            int mid = (f+l)/2;
            if(a[mid] == key)
                return mid;

            if(a[f] < a[l]) {
                if(a[mid] > key)
                    l=mid-1;
                else f=mid+1;

            } else {
                if(a[mid] > key)
                    f=mid+1;
                else l=mid-1;
            }
        }
        return -1;

    }
    public static int searchBit(int[] a, int f, int l, int key) {
        if(f<=l) {
            int mid = (f+l)/2;
            if(key == a[mid])
                return mid;

            int i1 = searchBit(a, f, mid-1, key);
            int i2 = searchBit(a, mid+1, l, key);
            if(i1!=-1)
                return i1;
            if(i2!=-1)
                return i2;

            return -1;
        }
        return -1;
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

    public static int ceil(int[] a, int key) {
        return ceil(a, 0, a.length-1, key);
    }

    private static int ceil(int[] a, int start, int end, int key) {
        while(start <= end) {
            int mid = (start+end)/2;
            if(a[mid] == key)
                return mid;
            else if (a[mid] > key)
                end = mid-1;
            else start = mid+1;
        }
        return start;
    }

    public static int floor(int[] a, int key) {
        return floor(a, 0, a.length-1, key);
    }

    private static int floor(int[] a, int start, int end, int key) {
        if(start <= end) {
            int mid = start + (end - start)/2;
            if(a[mid] == key)
                return mid;
            else if(a[mid] > key)
                return floor(a, start, mid-1, key);
            else return floor(a, mid+1, end, key);
        }
        else return end;
    }

    public static char nextGreater(char[] c, char key) {
        int index = findNextGreater(c, 0, c.length-1, key);
        if(index == c.length-1)
            return c[0];
        else return c[index+1];
    }

    private static int findNextGreater(char[] c, int start, int end, char key) {
        while(start<=end) {
            int mid = (start+end)/2;
            if(c[mid] == key)
                return mid;
            else if(c[mid] > key)
                end = mid-1;
            else start = mid+1;
        }
        return end;
    }
}
