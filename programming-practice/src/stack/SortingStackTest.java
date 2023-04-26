package stack;

import java.util.Stack;

public class SortingStackTest {
    public static void main(String[] args) {
        Stack<Integer> st=new Stack<>();
        st.push(1);
        st.push(2);
        st.push(1);
        st.push(2);
        st.push(3);

        Stack<Integer> t=new Stack<>();
        while(!st.isEmpty()) {
            int item=st.pop();
            while(!t.isEmpty() && t.peek() < item) st.push(t.pop());
            t.push(item);
        }

        System.out.println("sorted stack: "+t);
    }
}
