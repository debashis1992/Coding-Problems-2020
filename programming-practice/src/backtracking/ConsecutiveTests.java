package backtracking;

import java.util.ArrayList;
import java.util.List;

public class ConsecutiveTests {
    public static void main(String[] args) {

        Sol s=new Sol();
        s.splitString("");
    }
}

class Sol {
    public boolean splitString(String s) {

        backtrack("54320",0,new ArrayList<>());
        return false;
    }

    public void backtrack(String s, int i, List<Long> list) {
        if(i==s.length() && list.size()>=2) {
            System.out.println(list);
        }

        long num=0;
        for(int j=i;j<s.length();j++) {
            num=num*10 + (s.charAt(j)-'0');
            if(list.isEmpty() || list.get(list.size()-1) - num ==1) {
                list.add(num);
                backtrack(s, i + 1, list);
                list.remove(list.size() - 1);
            }
        }
    }
}
