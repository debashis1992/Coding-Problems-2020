package divideAndConquer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DACTest {
    static class Items {
        int val;
        int index;
        public Items(int v,int i) {
            val = v;
            index = i;
        }
    }
    private static List<Integer> countSmaller(int[] a) {
        int n = a.length;
        Items[] items = new Items[n];
        for(int i=0;i<n;i++)
            items[i] = new Items(a[i], i);

        int[] counts = new int[n];
        mergeSort(counts, 0 ,n-1, items);
        List<Integer> result = new ArrayList<>();
        for(int i: counts)
            result.add(i);
        return result;
    }

    private static void mergeSort(int[] count,int lo,int hi,Items[] items) {
        if(lo >= hi) return;
        int middle = (lo+hi)/2;
        mergeSort(count,lo, middle, items);
        mergeSort(count, middle+1,hi, items);
        merge(count,lo, middle, middle+1, hi, items);
    }
    private static void merge(int[] counts,int loStart, int loEnd, int hiStart, int hiEnd, Items[] items) {
        int m = hiEnd-loStart+1;
        Items[] sorted = new Items[m];
        int loPtr = loStart, hiPtr = hiStart;
        int index=0, rightCounter=0;

        while (loPtr <= loEnd && hiPtr <= hiEnd) {
            if(items[loPtr].val > items[hiPtr].val) {
                rightCounter++;
                sorted[index++] = items[hiPtr++];
            } else {
                counts[items[loPtr].index] += rightCounter;
                sorted[index++] = items[loPtr++];
            }
        }
        while (loPtr <= loEnd) {
            counts[items[loPtr].index] += rightCounter;
            sorted[index++] = items[loPtr++];
        }

        while (hiPtr <= hiEnd) {
            sorted[index++] = items[hiPtr++];
        }

        System.arraycopy(sorted, 0, items, loStart, m);

    }

    public static void main(String[] args) {
//        System.out.println(findNthFibElement(14));
        int[] a = {5,2,6,1};
//        System.out.println(countSmaller(a));

        System.out.println(Math.floor(log(64,4)) == Math.ceil(log(64,4)));
    }
    private static double log(int a,int b) {
        return Math.log(a)/Math.log(b);
    }

    public static int findNthFibElement(int n) {
        int[] fib = new int[n+1];
        int mod = (int)1e8;
        if(n==0 || n==1)    return fib[n];
        fib[0]=0;
        fib[1]=1;
        for(int i=2;i<=n;i++)
            fib[i] = (fib[i-1]+fib[i-2])%mod;

        return fib[n]%10;

    }

    public static List<Integer> checkSmallerThanSelf(int[] a) {
        int n = a.length;
        int min = a[n-1], max = a[n-1];
        List<Integer> list = new ArrayList<>();
        list.add(0);
        for(int i=n-1;i-1>=0;i--) {
            check(a, i - 1, i, min, max, list);
            min = Math.min(min,a[i-1]);
            max = Math.max(max,a[i-1]);
            System.out.println("min: "+min+", max : "+max);
        }
        Collections.reverse(list);
        return list;
    }
    private static void check(int[] a,int i,int j,int min, int max, List<Integer> list) {
        if(a[i] > a[j]) {
            list.add(list.get(list.size() - 1) + 1);
            return;
        }
        if(a[i] > min) {
            if(a[i] < max)
                list.add(list.get(list.size()-1));
            else list.add(list.get(list.size() - 1) + 1);
        }
        else list.add(0);
    }
}
