package testing;

import java.util.Arrays;

public class AnotherTest {
    public static void main(String[] args) {
        int[] a={3,4,5,6,7,0,1,2};
        Solution2 sn=new Solution2();
        int idx = sn.search(a, 2);
        System.out.println(idx);

        Arrays.stream(a).map(x -> x).boxed().toList();
    }

    public static int findMax(int[] nums) {
        int l=0,h=nums.length-1;
        while(l<h) {
            int mid=l+(h-l)/2;
            if(nums[l] <= nums[mid] && nums[mid] < nums[mid+1]) {
                l=mid;
            }
            else h=mid;
        }

        return l;
    }
}

class Solution2 {
    public int search(int[] nums, int target) {
        int l = 0, h = nums.length - 1;

        while (l <= h) {
            int mid = l + (h - l) / 2;

            if (nums[mid] == target) return mid;

            // left half is sorted
            if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target < nums[mid]) {
                    h = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            // right half is sorted
            else {
                if (nums[mid] < target && target <= nums[h]) {
                    l = mid + 1;
                } else {
                    h = mid - 1;
                }
            }
        }
        return -1;
    }
}
