package backtracking;

public class PermutationsTest {
    public static void main(String[] args) {
        String s = "ABCD";
        permute(s, 0, s.length() - 1);
    }

    public static void permute(String s, int l, int r) {
        if (l == r)
            System.out.println(s);
        else {
            for (int i = l; i <= r; i++) {
                s = swap(s, l, i);
                permute(s, l + 1, r);
                s = swap(s, l, i);
            }
        }
    }

    public static String swap(String s, int i, int j) {
        char[] c = s.toCharArray();
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
        return String.valueOf(c);
    }
}
