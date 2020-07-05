package searching;

import java.util.Scanner;

public class RecursiveSubstringSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        int t = scanner.nextInt();
        while(t-->0) {
            String s1 = scanner.next();
            String s2 = scanner.next();
            System.out.println(contains(s1, s2));
        }
    }

    public static boolean contains(String s1, String s2) {
        if(s2.length() < s1.length())
            return false;
        return contains(s1.toCharArray(), 0, s2.toCharArray(), 0);
    }
    public static boolean contains(char[] c1, int i, char[] c2, int j) {
        if(j > c2.length - 1)
            return false;
        if(i==c1.length-1 && c1[i]==c2[j])
            return true;
        if(c1[i] == c2[j])
            return contains(c1, ++i , c2, ++j);
        if(c1[i] != c2[j])
            return contains(c1, i, c2, ++j);
        return false;
    }
}
