package leetcodeJuneChallenge;

import java.util.Stack;

public class ValidStringTest {
    public static void main(String[] args) {
        System.out.println(isValid("(((((((*))))"));
    }

    public static boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        char[] c = s.toCharArray();
        int count = 0;
        for(int i=0;i<c.length;i++) {
            if(c[i]=='*')
                count++;
            if(c[i]=='(')
                st.push(c[i]);
            if(c[i]==')') {
                if(!st.isEmpty() && st.peek()=='(')
                    st.pop();
                else if(st.isEmpty() && count==0)
                    return false;
                else if(st.size()<=count)
                    return true;
            }
        }
        return true;
    }
}
