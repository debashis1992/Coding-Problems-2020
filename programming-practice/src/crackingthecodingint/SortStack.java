package crackingthecodingint;

import java.util.Stack;

public class SortStack {
    public static void main(String[] args) {

        Stack<Integer> stack=new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(100);

        System.out.println(stack);
        Stack<Integer> result = sortStack(stack);
        System.out.println(result);

    }

    public static Stack<Integer> sortStack(Stack<Integer> stack) {

        if(stack == null || stack.isEmpty()) return stack;

        Stack<Integer> tmp = new Stack<>();
        while(!stack.isEmpty()) {
            int element = stack.pop();

            if(tmp.isEmpty() || tmp.peek() >= element) {
                tmp.push(element);
            } else {
                int count = 0;
                while(!tmp.isEmpty() && tmp.peek() < element) {
                    stack.push(tmp.pop());
                    count++;
                }

                tmp.push(element);
                while(count-->0 && !stack.isEmpty()) {
                    tmp.push(stack.pop());
                }
            }
        }

        return tmp;
    }
}
