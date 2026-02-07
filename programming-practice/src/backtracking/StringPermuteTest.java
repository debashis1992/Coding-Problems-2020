package backtracking;

import java.util.ArrayList;
import java.util.List;

public class StringPermuteTest {
    public static void main(String[] args) {

        String s = "ABC";
        Solution2 sn=new Solution2();
        List<String> list = sn.permute(s);
        System.out.println(list);
    }
}


class Solution2 {
    List<String> result;
    public List<String> permute(String s) {
        result = new ArrayList<>();
        permute(0, s);
        return result;
    }
    private void permute(int id, String s) {
        if(id == s.length()) {
            result.add(s);
            return;
        }

        for(int i=id;i<s.length();i++) {
            s = swap(s,i,id);
            permute(id+1,s);
            s = swap(s,i,id);
        }
    }

    private String swap(String s, int i, int j) {
        char[] ch=s.toCharArray();
        char tmp = ch[i];
        ch[i] = ch[j];
        ch[j] = tmp;
        return String.valueOf(ch);
    }

}