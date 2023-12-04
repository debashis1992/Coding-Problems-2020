package graph;

import java.util.*;

public class AlienDictionary {
    public static void main(String[] args) {

        SolutionTest test=new SolutionTest();
        String[] words={"ac","ab","zc","zb"};
        System.out.println(test.findOrder(words));
    }
}

class SolutionTest {
    public String findOrder(String[] words) {
        // create the adj list
        List<List<Integer>> adj=new ArrayList<>();
        int n=26;
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());

        int[] indg=new int[n];
        boolean[] present=new boolean[n];
        for(String word: words) {
            for(char ch: word.toCharArray()) {
                present[(ch - 'a')]=true;
            }
        }

        for(int i=0;i+1< words.length;i++) {
            String s1=words[i], s2=words[i+1];
            int len=Math.min(s1.length(), s2.length());

            // Check that word2 is not a prefix of word1.
            if (s1.length() > s2.length() && s1.startsWith(s2)) {
                return "";
            }
            for(int j=0;j<len;j++) {
                if(s1.charAt(j)!=s2.charAt(j)) {
                    adj.get(s1.charAt(j) - 'a').add(s2.charAt(j) - 'a');
                    indg[s2.charAt(j) - 'a']++;
                    break;
                }
            }
        }

        int distinct=0;
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<indg.length;i++) {
            if(present[i]) distinct++;
            if(present[i] && indg[i]==0) q.offer(i);
        }

        List<Integer> ans=new ArrayList<>();
        while(!q.isEmpty()) {
            int node=q.poll();
            for(int child: adj.get(node)) {
                indg[child]--;
                if(indg[child]==0) q.offer(child);
            }
            ans.add(node);
        }

        System.out.println(distinct);
        StringBuilder sb=new StringBuilder();
        for (Integer an : ans) {
            sb.append((char) (an + 'a'));
        }

        System.out.println(sb);
        if(sb.length() < distinct) return "";
        return sb.toString();
    }

    public String alienOrder(String[] words) {

        // Step 0: Create data structures and find all unique letters.
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                counts.put(c, 0);
                adjList.put(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
                    break;
                }
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : counts.keySet()) {
            if (counts.get(c).equals(0)) {
                queue.add(c);
            }
        }
        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                counts.put(next, counts.get(next) - 1);
                if (counts.get(next).equals(0)) {
                    queue.add(next);
                }
            }
        }

        if (sb.length() < counts.size()) {
            return "";
        }
        return sb.toString();
    }
}