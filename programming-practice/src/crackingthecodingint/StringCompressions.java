package crackingthecodingint;

public class StringCompressions {
    public static void main(String[] args) {

        String a = "aabcccccaaa";
        System.out.println(solve(a));

    }
    public static String solve(String a) {
        StringBuilder sb=new StringBuilder();
        if(a==null || a.isEmpty()) return a;

        char[] c=a.toCharArray();
        char last=c[0];
        int count=1;

        for(int i=1;i<c.length;i++) {
            if(last == c[i])
                count++;
            else {
                sb.append(last).append(count);
                last = c[i];
                count=1;
            }
        }
        sb.append(last).append(count);

        if(sb.length() >= a.length())
            return a;
        return sb.toString();

    }
}
