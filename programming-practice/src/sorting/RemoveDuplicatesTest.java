package sorting;

import java.util.Arrays;

public class RemoveDuplicatesTest {
    public static void main(String[] args) {
        int[] a = {0,0,1,1,1,1,2,3,3};
        removeDuplicates(a);
    }
    public static int removeDuplicates(int[] nums) {
        int front = 0, back = 0, n = nums.length;
        while(front < n) {
            int number = nums[front];
            int count = 0;
            while(front < n && nums[front] == number) {
                count++;
                front++;
            }
            count = count >=2 ? 2 : count;
            while(count >0) {
                nums[back] = number;
                count--;
                back++;
            }
        }
//        System.out.println(Arrays.stream(nums).limit(back).toArray());
        return back;
    }
}
