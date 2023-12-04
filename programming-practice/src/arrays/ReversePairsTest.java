package arrays;

public class ReversePairsTest {

    public static void main(String[] args) {

        int[] nums={2,4,3,5,1};
        System.out.println(new Solution2().reversePairs(nums));
    }
}

class Solution2 {
    public int reversePairs(int[] nums) {

        return mergeSort(nums, 0, nums.length-1);
    }

    public int mergeSort(int[] nums, int l, int r) {
        int c=0;
        if(l<r) {
            int mid=l+(r-l)/2;
            c+=mergeSort(nums, l, mid);
            c+=mergeSort(nums, mid+1, r);
            c+=merge(nums, l, mid, r);
        }
        return c;
    }
    public int merge(int[] nums, int l, int mid, int r) {
        int n1=mid-l+1;
        int n2=r-mid;

        int[] left=new int[n1];
        int[] right=new int[n2];

        for(int i=0;i<n1;i++)
            left[i]=nums[l+i];

        for(int i=0;i<n2;i++)
            right[i]=nums[mid+1+i];

        int p1=0,p2=0,cnt=0;
        for(;p1<n1;p1++) {
            while(p2<n2 && left[p1] > 2*right[p2])
                p2++;
            cnt+= p2;
        }

        int i=0,j=0,k=l;
        while(i<n1 && j<n2) {
            if(left[i] <= right[j])
                nums[k++]=left[i++];
            else nums[k++]=right[j++];
        }

        while(i<n1)
            nums[k++]=left[i++];
        while(j<n2)
            nums[k++]=right[j++];

        return cnt;
    }
}
