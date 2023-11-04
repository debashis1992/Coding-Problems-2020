package searching;

import java.util.Arrays;

public class FindMaxTest {
    public static void main(String[] args) {

//        System.out.println(findMax(new int[]{4,5,6,7,0,1,2}));
//        int[][] a={{1,2,3},{3,3,4},{1,1,2}};
//        System.out.println(matrixMedian(a));

        int[] a={0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates(a));
    }

    public static int removeDuplicates(int[] nums) {

        for(int i=0;i+1<nums.length;i++) {
            if(nums[i] == nums[i+1]) {
                int j=i+2;
                if(j<nums.length) {
                    int index = find(nums, j, nums[i]);
                    nums[i+1] = nums[index];
                }
            }
            else while(nums[i] > nums[i+1])
                leftshift(nums, i+1);
        }
        System.out.println(Arrays.toString(nums));

        int c=0;
        for(int i=0;i+1<nums.length;i++) {
            if(nums[i] < nums[i+1])
                c++;
            else break;
        }
        return c+1;
    }

    public static void leftshift(int[] nums, int index) {
        int tmp=nums[index];
        for(int i=index;i+1<nums.length;i++)
            nums[i]=nums[i+1];

        nums[nums.length-1]=tmp;
    }

    public static int find(int[] nums, int l, int k) {
        int h=nums.length-1;
        while(l<h) {
            int mid=l+(h-l)/2;
            if(nums[mid] <= k)
                l=mid+1;
            else h=mid;
        }
        return l;
    }

    public static int matrixMedian(int[][] m) {

        int r=m.length, c=m[0].length;
        int l=0, h=10;
//                h=(int)1e6+1;
        while(l<=h) {
            int mid=l+(h-l)/2;
            int cnt=0;
            for(int i=0;i<r;i++)
                cnt+= find(m[i], mid);

            if(cnt<= (r*c)/2)
                l=mid+1;
            else h=mid-1;
        }
        return l;
    }

    static int find(int[] a,int k) {
        int l=0,h=a.length-1;
        while(l<=h) {
            int mid=l+(h-l)/2;
            if(a[mid] <= k)
                l=mid+1;
            else h=mid-1;
        }

        return l;
    }

    public static int findMax(int[] a) {
        int f=0,l=a.length-1;
        while(f<l) {
            int mid=f+(l-f)/2;
            if(a[f] <= a[mid] && mid+1 < a.length && a[mid] <= a[mid+1]) {
                f = mid + 1;
            } else l=mid;
        }
        return a[l];
    }
}
