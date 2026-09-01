package test;

public class ProgramTest {
    public static void main(String[] args) {

        StringBuilder sb=new StringBuilder();
        sb.append('a').append(2);
        System.out.println(sb);

        String s = "aabbbbbccc";
        ProgramTest t=new ProgramTest();
        t.compress(s.toCharArray());
    }

    public int compress(char[] chars) {

        StringBuilder sb=new StringBuilder();
        char last = chars[0];
        int count=1;

        for(int i=1;i<chars.length;i++) {
            char current = chars[i];
            if(last != current) {
                if(count == 1)
                    sb.append(last);
                else sb.append(last).append(count);
                count=1;
                last=current;
            } else {
                count++;
            }
        }

        if(count == 1)
            sb.append(last);
        else sb.append(last).append(count);

        System.out.println(sb);
        return -1;

    }
}
