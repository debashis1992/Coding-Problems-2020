package test;

import java.util.Stack;

public class TestClass {
    public static void main(String[] args) {
//      String s = "100101";
//      int count=0;
//      Stack<Character> stack = new Stack<>();
//      for(int i=0;i<s.length();i++) {
//          if(stack.isEmpty()) {
//              stack.add(s.charAt(i));
//              continue;
//          }
//          while(!stack.isEmpty() && stack.peek().equals(s.charAt(i))) {
//              stack.pop();
//              count++;
//          }
//          stack.add(s.charAt(i));
//      }
//      int res = stack.size()+count;
//        System.out.println(count);

        System.out.println(getText1("h8"));

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
