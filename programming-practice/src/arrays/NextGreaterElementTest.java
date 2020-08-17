package arrays;

import java.util.*;

public class NextGreaterElementTest {
    public static void main(String[] args) {
        int[] a = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(diffNextGreaterElement(a, a.length)));
    }

    public static int[] findNextGreaterElement(int[] a,int n) {
        int[] arr = new int[n];
        if(a==null || n==0)
            return new int[]{};

        Stack<Integer> stack = new Stack<>();
        for(int i=n-1;i>=0;i--) {
            while(!stack.isEmpty() && stack.peek() <= a[i])
                stack.pop();

            if(stack.isEmpty())
                arr[i] = 0;
            else arr[i] = stack.peek();
            stack.push(a[i]);
        }
        return arr;
    }

    public static int[] diffNextGreaterElement(int[] a,int n) {
        Map<Integer, Integer> temp = new HashMap<>();
        Map<Integer, Integer> days = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<n;i++)
            temp.put(i, a[i]);
        for(int i=0;i<n;i++) {
            while(!stack.isEmpty() && temp.get(stack.peek()) < a[i]) {
                days.put(stack.peek(), i - stack.pop());
            }
            stack.push(i);
        }

        for(int i=0;i<n;i++) {
            if(!days.containsKey(i))
                days.put(i, 0);
        }
        int[] res = new int[n];
        for(int i=0;i<n;i++) {
            res[i] = days.get(i);
        }

        return res;

    }
}
