package graph;

import java.util.*;

public class WordLadderTest {

    public static void main(String[] args) {
        WordLadderTest test=new WordLadderTest();
        int length=  test.ladderLength("hit","cog",List.of("hot","dot","dog","lot","log","cog"));
        System.out.println(length);

        List<List<String>> list= test.findLadders("hit","cog",List.of("hot","dot","dog","lot","log","cog"));
//        List<List<String>> list= test.findLadders("hit","cog",List.of("ac","ad"));
        System.out.println(list);
    }


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        List<List<String>> finalList=new ArrayList<>();
        Queue<List<String>> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Set<String> wordSet = new HashSet<>(wordList);

        q.offer(List.of(beginWord));
        while(!q.isEmpty()) {
            Set<String> currLayerWords = new HashSet<>();

            int s=q.size();
            for(int i=0;i<s;i++) {
                List<String> list = q.poll();
                String lastWord = list.get(list.size()-1);
                if(lastWord.equals(endWord)) {
                    finalList.add(new ArrayList<>(list));
                }

                for(int j=0;j<lastWord.length();j++) {
                    for(char c='a';c<='z';c++) {
                        char[] ch=lastWord.toCharArray();
                        ch[j]=c;

                        String nextWord = String.valueOf(ch);
                        if(wordSet.contains(nextWord) && !visited.contains(nextWord)) {
                            List<String> anotherList = new ArrayList<>(list);
                            anotherList.add(nextWord);
                            q.offer(anotherList);
                            currLayerWords.add(nextWord);
                        }
                    }
                }

            }
            visited = currLayerWords;
        }

        return finalList;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {


        Set<String> words=new HashSet<>(wordList);
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(beginWord, 1));

        while(!q.isEmpty()) {
            Pair p=q.poll();
            String s=p.s;
            int seq=p.seq;

            if(s.equals(endWord))
                return seq;

            for(int i=0;i<s.length();i++) {
                for(char c='a'; c<='z';c++) {
                    char[] ch=s.toCharArray();
                    ch[i]=c;

                    String s2=String.valueOf(ch);
                    if(words.contains(s2)) {
//                        System.out.println(s2);
                        q.offer(new Pair(s2, seq+1));
                        words.remove(s2);
                    }
                }
            }
        }

        return 0;
    }


}

class Pair {
    String s;
    int seq;
    public Pair(String s,int seq) {
        this.s=s;
        this.seq=seq;
    }
}

class AnotherPair {
    String s;
    int seq;
    List<String> steps;
    Set<String> words;
    public AnotherPair(String s, int seq, List<String> steps, Set<String> words) {
        this.s=s;
        this.seq=seq;
        this.steps=steps;
        this.words=words;
    }
}