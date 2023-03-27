package sorting;

import java.util.Arrays;

public class InversionsTest {
    public static void main(String[] args) {

        int[] a = {5,4,3,2,1};
        System.out.println("final inversions: "+findInversions(a,0,a.length-1));
        System.out.println("Array: "+ Arrays.toString(a));

    }
    public static int findInversions(int[] a, int f,int l) {
        int inv=0;
        if(f<l) {
            int mid = (f+l)/2;
            inv= findInversions(a,f,mid);
            inv+= findInversions(a,mid+1,l);
            inv+= merge(a,f,mid,l);
        }
        return inv;
    }

    public static int merge(int[] a,int f,int mid,int l) {
        int n1=mid-f+1;
        int n2=l-mid;

        int[] first=new int[n1];
        int[] second=new int[n2];

        for(int i=0;i<n1;i++)
            first[i]=a[f+i];

        for(int i=0;i<n2;i++)
            second[i]=a[mid+1+i];

        int inv=0,i=0,j=0,k=f;
        while(i<n1 && j<n2) {
            if(first[i] > second[j]) {
                a[k++]=second[j++];
                inv+= (mid+1) - (f+i);
            } else {
                a[k++]=first[i++];
            }
        }

        while(i<n1)
            a[k++]=first[i++];

        while(j<n2) {
            a[k++]=second[j++];
        }
        System.out.println("inversions: "+inv);
        return inv;
    }
}
