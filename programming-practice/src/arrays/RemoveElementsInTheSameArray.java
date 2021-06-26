package arrays;

import java.util.Arrays;

public class RemoveElementsInTheSameArray {
    public static void main (String[] args) {
        int[] a = {1,2,2,2,2,3,4,4,5,5,5};
        int s = change2(a, a.length);
        System.out.println(Arrays.toString(a));
        System.out.println(s);
    }
    public static int change(int[] a, int n) {
        int[] temp = new int[n];
        int j=0;
        for(int i=0;i+1<n;i++) {
            if(a[i]!=a[i+1]) {
                temp[j++]=a[i];
            }
        }
        temp[j++]=a[n-1];
        for(int i=0;i<n;i++) {
            a[i]=temp[i];
        }
        return j++;
    }

    public static int change2(int[] a, int n) {
        int j=0;
        for(int i=0;i+1<n;i++) {
            if(a[i]!=a[i+1]) {
                a[j++]=a[i];
            }
        }
        a[j++]=a[n-1];
        return j++;
    }
}
