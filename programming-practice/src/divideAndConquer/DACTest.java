package divideAndConquer;

public class DACTest {
    public static void main(String[] args) {
        System.out.println(findNthFibElement(14));

    }
    public static int findNthFibElement(int n) {
        int[] fib = new int[n+1];
        int mod = (int)1e8;
        if(n==0 || n==1)    return fib[n];
        fib[0]=0;
        fib[1]=1;
        for(int i=2;i<=n;i++)
            fib[i] = (fib[i-1]+fib[i-2])%mod;

        return fib[n]%10;

    }
}
