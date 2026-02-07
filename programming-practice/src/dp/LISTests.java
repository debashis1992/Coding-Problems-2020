package dp;

import java.util.*;

public class LISTests {
    public static void main(String[] args) {
        Solution10 sn  = new Solution10();

        int[][] pairs = {{1,2},{2,3},{3,4}};
        Arrays.sort(pairs, (o1,o2) -> Integer.compare(o1[1], o2[1]));

        for(int[] row: pairs)
            System.out.println(Arrays.toString(row));


        int[] a={100,90,80,70,60,50,60,70,80,90,100};
//        System.out.println(sn.getLIS2(a));
//        System.out.println(sn.largestDivisibleSubset(a));

        String[] s = {"abcd","dbqca"};
        //sort by increasing order of str len
//        PriorityQueue<Word> pq = new PriorityQueue<>((o1,o2) -> o1.len.compareTo(o2.len));
//        for(String str: s) {
//            pq.offer(new Word(str, str.length()));
//        }
//
//        while(!pq.isEmpty())
//            System.out.println(pq.poll());

//        System.out.println(sn.longestStrChain(s));

//        System.out.println(sn.findNumberOfLIS(a));
        int[][] nums = {{1,1,1,0},
                {0,1,1,1},
                {0,1,1,1},
                {0,1,1,1}
        };

        System.out.println(sn.solve(nums));
    }
}
class Word {
    String s;
    Integer len;
    public Word(String s, int len) {
        this.s=s;
        this.len=len;
    }

    @Override
    public String toString() {
        return "Word{" +
                "s='" + s + '\'' +
                ", len=" + len +
                '}';
    }
}


class Solution10 {

    public int solve(int[][] a) {
        int r=a.length, c=a[0].length;
        int[][] dp=new int[r][c];

        for(int i=0;i<r;i++) {
            if(a[i][0]==1)
                dp[i][0]=1;
        }

        for(int i=0;i<c;i++) {
            if(a[0][i]==1)
                dp[0][i]=1;
        }

        for(int i=1;i<r;i++) {
            for(int j=1;j<c;j++) {
                if(a[i][j]==1) {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                }
            }
        }

        for(int[] row: dp)
            System.out.println(Arrays.toString(row));

        int ct=0;
        for(int i=0;i<r;i++) {
            for(int j=0;j<c;j++)
                ct+=dp[i][j];
        }

        return ct;

    }

    public int findNumberOfLIS(int[] nums) {
        int n=nums.length;
        int[] dp=new int[n];
        Arrays.fill(dp, 1);

        int[] ct=new int[n];
        Arrays.fill(ct, 1);
        int maxlen=0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<i;j++) {
                if(nums[j] < nums[i] && dp[j]+1 > dp[i]) {
                    dp[i] = dp[j]+1;
                    ct[i]=ct[j];
                }
                else if(nums[j] < nums[i] && dp[j]+1 == dp[i]) {
                    ct[i] = ct[i]+ct[j];
                }
            }

            if(dp[i] > maxlen)
                maxlen = dp[i];
        }

        System.out.println("dp: "+Arrays.toString(dp));
        System.out.println("ct: "+Arrays.toString(ct));

        int c=0;
        for(int i=0;i<n;i++) {
            if(maxlen == dp[i])
                c+= ct[i];
        }

        return c;
    }



    public int longestStrChain(String[] words) {
        PriorityQueue<Word> pq = new PriorityQueue<>((o1,o2) -> o1.len.compareTo(o2.len));
        for(String str: words) {
            pq.offer(new Word(str, str.length()));
        }

        List<Word> list = new ArrayList<>();
        while(!pq.isEmpty())
            list.add(pq.poll());

        int n=list.size();
//        System.out.println(list);
        int[] dp=new int[n];
        Arrays.fill(dp, 1);

        int maxlen=0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<i;j++) {
                String wordA = list.get(j).s;
                String wordB = list.get(i).s;

                if(wordA.length()+1 == wordB.length()) {
                    int x=0, y=0;
                    while(x<wordA.length() && y<wordB.length()) {
                        if(wordA.charAt(x) == wordB.charAt(y)) {
                            x++;
                        }
                        y++;
                    }
                    if(x == wordA.length() && dp[j]+1 > dp[i])
                        dp[i] = dp[j]+1;
                }
            }
            if(dp[i] > maxlen)
                maxlen = dp[i];
        }

        return maxlen;
    }

    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n=nums.length;
        Arrays.sort(nums);

        int[] dp=new int[n];
        int[] parent=new int[n];
        Arrays.fill(dp, 1);

        int maxlen=0, lastIdx=0;

        for(int i=0;i<n;i++) {
            parent[i]=i;
            for(int j=0;j<i;j++) {
                if(nums[i]%nums[j]==0 && dp[j]+1 > dp[i]) {
                    dp[i] = dp[j]+1;
                    parent[i]=j;
                }
                if(dp[i] > maxlen) {
                    maxlen = dp[i];
                    lastIdx = i;
                }
            }
        }
        List<Integer> list=new ArrayList<>();
        while(parent[lastIdx]!=lastIdx) {
            list.add(nums[lastIdx]);
            lastIdx=parent[lastIdx];
        }
        list.add(nums[lastIdx]);

        Collections.reverse(list);
        return list;
    }

    public List<Integer> getLIS2(int[] a) {
        int n=a.length;
        int[] dp=new int[n];
        int[] parent=new int[n];
        Arrays.fill(dp, 1);

        int maxLen=1, lastIndex=0;
        for(int i=0;i<n;i++) {
            parent[i]=i;
            for(int j=0;j<i;j++) {
                if(a[j] < a[i] && dp[j]+1 > dp[i]) {
                    dp[i] = dp[j]+1;
                    parent[i] = j;
                }
                if(dp[i] > maxLen) {
                    maxLen = dp[i];
                    lastIndex = i;
                }
            }
        }

        List<Integer> list=new ArrayList<>();
        while(parent[lastIndex]!=lastIndex) {
            list.add(a[lastIndex]);
            lastIndex = parent[lastIndex];
        }
        list.add(a[lastIndex]);

        Collections.reverse(list);

        return list;
    }
    public List<Integer> getLIS(int[] a) {

        int n=a.length;
        int[] tails=new int[n];
        int[] parent=new int[n];

        int size=0;

        for(int i=0;i<n;i++) {
            int l=0, r=size;
            while(l<r) {
                int mid = l+(r-l)/2;
                if(a[tails[mid]] < a[i])
                    l=mid+1;
                else r=mid;
            }
            if(l>0)
                parent[i]=tails[l-1];
            else parent[i]=i;

            tails[l]=i;
            if(l==size) size++;

        }

        System.out.println(Arrays.toString(tails));
        System.out.println(Arrays.toString(parent));

        List<Integer> list=new ArrayList<>();
        int k=tails[size-1];
        while(parent[k]!=k) {
            list.add(a[k]);
            k=parent[k];
        }
        list.add(a[k]);
        Collections.reverse(list);

        System.out.println("lis: "+list);
        return list;
    }
}