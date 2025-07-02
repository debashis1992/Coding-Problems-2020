package graph;

import java.util.*;

public class WordLadderTest {

    public static void main(String[] args) {
        WordLadderTest test=new WordLadderTest();
//        String begin = "aaaaa", end = "ggggg";
//        List<String> list = new ArrayList<>(Arrays.asList("aaaaa","caaaa","cbaaa","daaaa","dbaaa","eaaaa","ebaaa","faaaa","fbaaa","gaaaa","gbaaa","haaaa","hbaaa","iaaaa","ibaaa","jaaaa","jbaaa","kaaaa","kbaaa","laaaa","lbaaa","maaaa","mbaaa","naaaa","nbaaa","oaaaa","obaaa","paaaa","pbaaa","bbaaa","bbcaa","bbcba","bbdaa","bbdba","bbeaa","bbeba","bbfaa","bbfba","bbgaa","bbgba","bbhaa","bbhba","bbiaa","bbiba","bbjaa","bbjba","bbkaa","bbkba","bblaa","bblba","bbmaa","bbmba","bbnaa","bbnba","bboaa","bboba","bbpaa","bbpba","bbbba","abbba","acbba","dbbba","dcbba","ebbba","ecbba","fbbba","fcbba","gbbba","gcbba","hbbba","hcbba","ibbba","icbba","jbbba","jcbba","kbbba","kcbba","lbbba","lcbba","mbbba","mcbba","nbbba","ncbba","obbba","ocbba","pbbba","pcbba","ccbba","ccaba","ccaca","ccdba","ccdca","cceba","cceca","ccfba","ccfca","ccgba","ccgca","cchba","cchca","cciba","ccica","ccjba","ccjca","cckba","cckca","cclba","cclca","ccmba","ccmca","ccnba","ccnca","ccoba","ccoca","ccpba","ccpca","cccca","accca","adcca","bccca","bdcca","eccca","edcca","fccca","fdcca","gccca","gdcca","hccca","hdcca","iccca","idcca","jccca","jdcca","kccca","kdcca","lccca","ldcca","mccca","mdcca","nccca","ndcca","occca","odcca","pccca","pdcca","ddcca","ddaca","ddada","ddbca","ddbda","ddeca","ddeda","ddfca","ddfda","ddgca","ddgda","ddhca","ddhda","ddica","ddida","ddjca","ddjda","ddkca","ddkda","ddlca","ddlda","ddmca","ddmda","ddnca","ddnda","ddoca","ddoda","ddpca","ddpda","dddda","addda","aedda","bddda","bedda","cddda","cedda","fddda","fedda","gddda","gedda","hddda","hedda","iddda","iedda","jddda","jedda","kddda","kedda","lddda","ledda","mddda","medda","nddda","nedda","oddda","oedda","pddda","pedda","eedda","eeada","eeaea","eebda","eebea","eecda","eecea","eefda","eefea","eegda","eegea","eehda","eehea","eeida","eeiea","eejda","eejea","eekda","eekea","eelda","eelea","eemda","eemea","eenda","eenea","eeoda","eeoea","eepda","eepea","eeeea","ggggg","agggg","ahggg","bgggg","bhggg","cgggg","chggg","dgggg","dhggg","egggg","ehggg","fgggg","fhggg","igggg","ihggg","jgggg","jhggg","kgggg","khggg","lgggg","lhggg","mgggg","mhggg","ngggg","nhggg","ogggg","ohggg","pgggg","phggg","hhggg","hhagg","hhahg","hhbgg","hhbhg","hhcgg","hhchg","hhdgg","hhdhg","hhegg","hhehg","hhfgg","hhfhg","hhigg","hhihg","hhjgg","hhjhg","hhkgg","hhkhg","hhlgg","hhlhg","hhmgg","hhmhg","hhngg","hhnhg","hhogg","hhohg","hhpgg","hhphg","hhhhg","ahhhg","aihhg","bhhhg","bihhg","chhhg","cihhg","dhhhg","dihhg","ehhhg","eihhg","fhhhg","fihhg","ghhhg","gihhg","jhhhg","jihhg","khhhg","kihhg","lhhhg","lihhg","mhhhg","mihhg","nhhhg","nihhg","ohhhg","oihhg","phhhg","pihhg","iihhg","iiahg","iiaig","iibhg","iibig","iichg","iicig","iidhg","iidig","iiehg","iieig","iifhg","iifig","iighg","iigig","iijhg","iijig","iikhg","iikig","iilhg","iilig","iimhg","iimig","iinhg","iinig","iiohg","iioig","iiphg","iipig","iiiig","aiiig","ajiig","biiig","bjiig","ciiig","cjiig","diiig","djiig","eiiig","ejiig","fiiig","fjiig","giiig","gjiig","hiiig","hjiig","kiiig","kjiig","liiig","ljiig","miiig","mjiig","niiig","njiig","oiiig","ojiig","piiig","pjiig","jjiig","jjaig","jjajg","jjbig","jjbjg","jjcig","jjcjg","jjdig","jjdjg","jjeig","jjejg","jjfig","jjfjg","jjgig","jjgjg","jjhig","jjhjg","jjkig","jjkjg","jjlig","jjljg","jjmig","jjmjg","jjnig","jjnjg","jjoig","jjojg","jjpig","jjpjg","jjjjg","ajjjg","akjjg","bjjjg","bkjjg","cjjjg","ckjjg","djjjg","dkjjg","ejjjg","ekjjg","fjjjg","fkjjg","gjjjg","gkjjg","hjjjg","hkjjg","ijjjg","ikjjg","ljjjg","lkjjg","mjjjg","mkjjg","njjjg","nkjjg","ojjjg","okjjg","pjjjg","pkjjg","kkjjg","kkajg","kkakg","kkbjg","kkbkg","kkcjg","kkckg","kkdjg","kkdkg","kkejg","kkekg","kkfjg","kkfkg","kkgjg","kkgkg","kkhjg","kkhkg","kkijg","kkikg","kkljg","kklkg","kkmjg","kkmkg","kknjg","kknkg","kkojg","kkokg","kkpjg","kkpkg","kkkkg","ggggx","gggxx","ggxxx","gxxxx","xxxxx","xxxxy","xxxyy","xxyyy","xyyyy","yyyyy","yyyyw","yyyww","yywww","ywwww","wwwww","wwvww","wvvww","vvvww","vvvwz","avvwz","aavwz","aaawz","aaaaz"));

        String begin = "der", end = "dfs";
        List<String> list = new ArrayList<>(Arrays.asList("des","der","dfr","dgt","dfs"));


        List<List<String>> ans = test.findLadders(begin, end, list);
        System.out.println(ans);
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);

        Queue<AnotherPair> q = new LinkedList<>();
        q.offer(new AnotherPair(beginWord, 1, new ArrayList<>(Arrays.asList(beginWord))));
        words.remove(beginWord);

        List<List<String>> result = new ArrayList<>();
        int min = Integer.MAX_VALUE;

        while(!q.isEmpty()) {
            int s = q.size();
            List<String> seenWords=new ArrayList<>();
            for(int k=0;k<s;k++) {
                AnotherPair anotherPair = q.poll();
                String old = anotherPair.word;
                int currSeq = anotherPair.seq;
                List<String> currentPath = anotherPair.path;

                if(old.equals(endWord)) {
                    if(min > currSeq) {
                        min = currSeq;
                        result = new ArrayList<>();
                    }
                    result.add(new ArrayList<>(currentPath));
                }

                for(int i=0;i<old.length();i++) {
                    char[] c = old.toCharArray();
                    for(char j='a';j<='z';j++) {
                        c[i] = j;
                        String newWord = String.valueOf(c);
                        if(words.contains(newWord)) {
                            seenWords.add(newWord);
                            List<String> currentPathCopy = new ArrayList<>(currentPath);
                            currentPathCopy.add(newWord);
                            q.offer(new AnotherPair(newWord, currSeq+1, currentPathCopy));
                        }
                    }
                }
            }
            words.removeAll(seenWords);
        }

        return result;
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
    String word;
    int seq;
    List<String> path;

    public AnotherPair(String word, int seq) {
        this.word=word;
        this.seq=seq;
        this.path=new ArrayList<>();
    }

    public AnotherPair(String word, int seq, List<String> path) {
        this.word=word;
        this.seq=seq;
        this.path=path;
    }
}