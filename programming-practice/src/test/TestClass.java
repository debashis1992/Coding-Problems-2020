package test;

import java.util.Stack;

public class TestClass {
    public static void main(String[] args) {
      String s = "100101";
      int count=0;
      Stack<Character> stack = new Stack<>();
      for(int i=0;i<s.length();i++) {
          if(stack.isEmpty()) {
              stack.add(s.charAt(i));
              continue;
          }
          while(!stack.isEmpty() && stack.peek().equals(s.charAt(i))) {
              stack.pop();
              count++;
          }
          stack.add(s.charAt(i));
      }
      int res = stack.size()+count;
        System.out.println(count);

    }


}
