package strings;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StringProblemsTest {
    public static void main(String[] args) {
        String s = "ABC";
        Set<String> set = new LinkedHashSet<>();
        permute(s, 0, s.length()-1, set);
        System.out.println(set);
    }

    public static void permute(String s, int l, int r, Set<String> set) {
        if(l==r)
            set.add(s);
        else {
            for(int i=l;i<=r;i++) {
                s = swap(s, l, i);
                permute(s, l+1, r, set);
                s = swap(s, l, i);
            }
        }
    }


    private static String swap(String s, int i, int j) {
        char[] ch = s.toCharArray();
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
        return String.valueOf(ch);
    }
}
