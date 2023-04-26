package interviews;

import java.util.Stack;

public class TestBrackets {
    public static void main(String[] args) {

        String s = null;
        System.out.println(isValid(s));
    }

    public static boolean isValid(String s) {

        if(s==null || s.isEmpty()) return true;
        // {{]}
        // stack = {
        Stack<Character> stack = new Stack<>();
        char[] c = s.toCharArray();
        for(int i=0;i<c.length;i++) {
            switch(c[i]) {
                case '(':
                case '[':
                case '{':
                    stack.push(c[i]);
                    break;
                case ')':
                    if(!stack.isEmpty() && stack.peek() == '(')
                        stack.pop();
                    break;
                case '}':
                    if(!stack.isEmpty() && stack.peek() == '{')
                        stack.pop();
                    break;
                case ']':
                    if(!stack.isEmpty() && stack.peek() == '[')
                        stack.pop();
                    break;
                default:
                    break;
            }
        }

        return stack.isEmpty();

    }
}
