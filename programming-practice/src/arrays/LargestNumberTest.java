package arrays;

import java.util.*;
import java.util.stream.Collectors;

public class LargestNumberTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while (t-- > 0) {
            int n = scanner.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = scanner.nextInt();
//            List<String> list = new ArrayList<>();
//            for(int i=0;i<n;i++) {
//                list.add(String.valueOf(a[i]));
//            }
//            System.out.println(manipulate(list));
            System.out.println(performMergeSort(a, 0, a.length-1));
        }
    }

    public static String manipulate(List<String> a) {
        Collections.sort(a, (x, y) -> {
            String xy = x + y;
            String yx = y + x;
            return xy.compareTo(yx) > 0 ? -1 : 1;
        });
        if (a.indexOf("0") == 0 && a.lastIndexOf("0") == a.size() - 1)
            return "0";
        return a.stream().collect(Collectors.joining(""));
    }

    /*
    This function will calculate the number of inversions in an array
     */
    public static int performMergeSort(int[] a, int l, int r) {
       int count = 0;
       if(l<r) {
           int mid = (l+r)/2;
           count+= performMergeSort(a,l,mid);
           count+= performMergeSort(a,mid+1,r);
           count+= sortAndMerge(a,l,mid,r);
       }
       return count;
    }

    private static int sortAndMerge(int[] a, int l, int m, int r) {
        int n1 = m-l+1;
        int n2 = r-m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for(int i=0;i<n1;i++)
            left[i] = a[l+i];
        for(int i=0;i<n2;i++)
            right[i] = a[m+1+i];

        int inv=0;
        int i=0,j=0,k=l;
        while(i<n1 && j<n2) {
            if(left[i] <= right[j])
                a[k++] = left[i++];
            else {
                a[k++] = right[j++];
                inv+= (m+1) - (l+i);
            }
        }
        while(i<n1)
            a[k++]=left[i++];
        while(j<n2)
            a[k++]=right[j++];

        return inv;
    }

}
