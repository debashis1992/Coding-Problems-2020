package tekion;

import java.util.*;

public class RankTest {
    public static void main(String[] args) {

        String[] s={"WXYZ", "XYZW"};
        int n=s[0].length();

        Map<Character, int[]> map = new HashMap<>();

        for(int i=0;i<s.length;i++) {
            for(int j=0;j<s[i].length();j++) {
                char c=s[i].charAt(j);

                map.putIfAbsent(c, new int[n]);
                map.get(c)[j]++;
            }
        }

        List<Character> teams = new ArrayList<>(map.keySet());

        Collections.sort(teams, (a,b) -> {
            for(int i=0;i<n;i++) {
                if(map.get(a)[i] != map.get(b)[i]) {
                    return map.get(b)[i] - map.get(a)[i];
                }
            }
            return a-b;
        });

        StringBuilder sb=new StringBuilder();
        for(Character team: teams)
            sb.append(team);

        System.out.println(sb);
    }
}
