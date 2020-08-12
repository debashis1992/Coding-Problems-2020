package divideAndConquer;

public class LongestCommonPrefixTest {
    public static void main(String[] args) {
        String[] str = {"geeksforgeeks", "geeks", "geek", "geezer"};

        System.out.println(findCommonPrefix("geeksforgeeks", "gee"));
        System.out.println(findCommonPrefix(str, 0 , str.length-1));
    }

    public static String findCommonPrefix(String[] s,int start,int end) {
        if(start < end) {
            int middle = (start+end)/2;
            String s1 = findCommonPrefix(s, start, middle);
            String s2 = findCommonPrefix(s, middle+1, end);

            return findCommonPrefix(s1,s2);
        }
        else return s[start];
    }
    private static String findCommonPrefix(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<Math.min(s1.length(), s2.length());i++) {
            if(s1.charAt(i)==s2.charAt(i))
                sb.append(s1.charAt(i));
            else break;
        }
        return sb.toString();
    }
}
