package stack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class BasicCalculatorTest {
    public static void main(String[] args) {
        Solution sn=new Solution();
        String s="(7-123)";

//        int ans = sn.calculate(s);
//        System.out.println(ans);

        System.out.println(Arrays.toString(sn.getCoordinates(36, 6,6)));

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

    }
}

class Solution {

    public int[] getCoordinates(int pos, int r, int c) {
        int idx=pos-1;

        int rowsFromBottom = idx/c;
        int row = r-1-rowsFromBottom;

        int col = idx%c;
        if(rowsFromBottom%2 == 1)
            col = c - 1 - col;

        return new int[]{row, col};
    }

    public int calculate(String s) {
        int operand=0;
        int n=0;
        Stack<Object> stack = new Stack<>();

        for(int i=s.length()-1;i>=0;i--) {
            char ch = s.charAt(i);

            if(Character.isDigit(ch)) {
                operand = (int)Math.pow(10,n) * (int)(ch-'0') + operand;
                n+=1;
            }
            else if(ch!=' ') {
                if(n!=0) {
                    stack.push(operand);
                    n=0;
                    operand=0;
                }
                if(ch=='(') {
                    int res = evaluateExpr(stack);
                    stack.pop();

                    stack.push(res);
                } else {
                    stack.push(ch);
                }
            }
        }

        if(n!=0) {
            stack.push(operand);
        }

        return evaluateExpr(stack);
    }

    private int evaluateExpr(Stack<Object> stack) {

        if(stack.isEmpty() || !(stack.peek() instanceof Integer))
            stack.push(0);

        int res=(int)stack.pop();

        while(!stack.isEmpty() && (char)stack.peek()!=')') {
            char sign = (char)stack.pop();

            if(sign == '+') {
                res+=(int)stack.pop();
            } else {
                res-=(int)stack.pop();
            }
        }

        return res;
    }
}
