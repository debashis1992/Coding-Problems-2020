package dp;

public class CoinChangeTest {
    public static void main(String[] args) {
        int[] a = {1,2,5};
        int v = 11;
        CoinChangeTest test = new CoinChangeTest();
        System.out.println(test.findMinCoinsIteratively(a, a.length , v));
    }

    //bottom-up approach
    // recursive
    public int findMinCoins(int[] coins, int v) {
        int res = findMinCoinsRecursively(coins, coins.length, v);
        return res==Integer.MAX_VALUE ? -1 : res;
    }
    private int findMinCoinsRecursively(int[] coins, int m, int v) {
       if(v==0) return 0;
       int res = Integer.MAX_VALUE;

       for(int i=0;i<m;i++) {
           if(coins[i] <= v) {
               int subResult = findMinCoinsRecursively(coins, m, v-coins[i]);
               if(subResult != Integer.MAX_VALUE && subResult+1 < res)
                   res = subResult+1;
           }
       }
       return res;
    }

    public int findMinCoinsIteratively(int[] coins, int m, int v) {
        int[] table = new int[v+1];
        table[0] = 0;
        for(int i=1;i<=v;i++)
            table[i] = Integer.MAX_VALUE;

        for(int i=1;i<=v;i++) {
            for(int j=0;j<m;j++) {
                if(coins[j] <= i) {
                    int subResult = table[i - coins[j]];
                    if(subResult != Integer.MAX_VALUE && subResult+1 < table[i])
                        table[i] = subResult+1;
                }
            }
        }
        return table[v];

    }
}
