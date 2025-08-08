package test;

import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

public class TestClass {
    public static void main(String[] args) {

        TestClass t = new TestClass();

    }

    public boolean canJump(int[] nums) {

        // 2 3 1 1 4
        // i=0, 2
        return jump(nums, 0);
    }

    public boolean jump(int[] nums, int i) {
        if(i>=nums.length-1)
            return true;

        for(int j=1;j<=nums[i];j++) {
            if(jump(nums, i+j))
                return true;

        }

        return false;
    }

    public static String getText(String input) {
        if(input.length()>2)
            return "Error";

        int c = input.charAt(0);
        if(c<=97 && c>=104)
            return "Error";
        int n = input.charAt(1) - '0';

        boolean b;
        b = ((c % 2 == 1 && n % 2 == 1) || (c % 2 == 0 && n % 2 == 0));

        if (b)
            return "Black";
        else return "White";
    }

    public static String getText1(String input) {
        if(input.length()>2)
            return "Error";

        int c = input.charAt(0) - 65;
        int r = input.charAt(1);

        if(r%2!=0) {
            if(c%2==0)
                return "Black";
            else return "White";
        } else {
            if (c%2==0)
                return "White";
            else return "Black";
        }
    }

}
