package sorting;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MergeSortTest {
    public static void main(String[] args) {

        MergeSortTest test = new MergeSortTest();
        int[] a= {1,3,2,3,1};
        int c = test.performMergeSort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(c);

//        Arrays.stream(a).boxed().collect(Collectors.toSet());

    }

    public int performMergeSort(int[] a) {
        return mergeSortBase(a, 0, a.length-1);
    }

    public int mergeSortBase(int[] a, int low, int high) {
        int c=0;
        if(low<high) {
            int mid = (low+high)/2;
            c+=mergeSortBase(a, low, mid);
            c+=mergeSortBase(a, mid+1, high);
            c+=merge(a, low, mid, high);
        }
        return c;
    }

    public int merge(int[] a, int low, int mid, int high) {
        int n1=mid-low+1, n2=high-mid;
        int[] left=new int[n1], right = new int[n2];

        //copy elements from left & right
        for(int i=0;i<n1;i++)
            left[i]=a[low + i];

        for(int j=0;j<n2;j++)
            right[j] = a[mid+1+j];


        int p1=0,p2=0,c=0;
        for(;p1<n1;p1++) {
            while(p2<n2 && (long)left[p1] > (long)2*right[p2])
                p2++;

            c+=p2;
        }

        int i=0,j=0,k=low;
        while(i<n1 && j<n2) {
            if(left[i] < right[j])
                a[k++] = left[i++];
            else
                a[k++] = right[j++];
        }

        while(i<n1)
            a[k++] = left[i++];
        while(j<n2)
            a[k++] = right[j++];

        return c;
    }
}
