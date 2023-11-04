package sorting;

public class InvTest {
    public static void main(String[] args) {
        Solution solution=new Solution();
        solution.mergeSort();
    }


}

class Solution {
    void mergeSort () {
        int[] arr={2,4,1,3,5};
        int cnt= mergeAndSort(arr, 0, arr.length-1);
        System.out.println(cnt);
    }

    public int mergeAndSort(int[] a,int f, int l) {
        int cnt=0;
        if(f<l) {
            int mid=f+(l-f)/2;
            cnt+= mergeAndSort(a,f,mid);
            cnt+= mergeAndSort(a,mid+1,l);
            cnt+= merge(a,f,mid,l);
        }
        return cnt;
    }

    public int merge(int[] a,int f, int mid, int l) {
        int n1=mid-f+1;
        int n2=l-mid;

        int[] left=new int[n1];
        int[] right=new int[n2];

        for(int i=0;i<n1;i++)
            left[i]=a[f+i];

        for(int i=0;i<n2;i++)
            right[i]=a[mid+1+i];

        int i=0,j=0,k=f;
        int cnt=0;
        while(i<n1 && j<n2) {
            if(left[i] < right[j])
                a[k++]=left[i++];
            else {
                cnt+= (mid+1) - (f+i);
                a[k++]=right[j++];
            }
        }

        while(i<n1) {
            a[k++]=left[i++];
        }
        while(j<n2) {
            a[k++]=right[j++];
        }
        return cnt;
    }


}