package crackingthecodingint;

import java.util.Stack;

public class BasicCalculator2 {
    public static void main(String[] args) {

        String s="5/2+3";
        int res = calculate(s);
        System.out.println(res);

    }
    public static int calculate(String s) {
        Stack<Integer> st=new Stack<>();
        char[] c=s.toCharArray();
        int i=0 , n=0;
        char lastOp='+';
        while(i<c.length) {
            if(Character.isDigit(c[i])) {
                n = n*10 + (c[i] - '0');
            } else if((!Character.isDigit(c[i]) && c[i]!=' ') || i==c.length-1) {

                if(lastOp == '+')
                    st.push(n);
                else if(lastOp == '-')
                    st.push(-n);
                else if(lastOp == '/')
                    st.push((int)st.pop() / n);
                else if(lastOp == '*')
                    st.push((int)st.pop() * n);

                lastOp=c[i];
                n=0;
            }

            i++;
        }
        if(n>0) {
            st.push(n);
        }
        int sum=0;
        while(!st.isEmpty())
            sum+= st.pop();
        return sum;

    }
}
