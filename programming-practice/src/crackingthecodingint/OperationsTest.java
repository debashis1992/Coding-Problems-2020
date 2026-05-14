package crackingthecodingint;

public class OperationsTest {
    public static void main(String[] args) {
        Op o=new Op();
        System.out.println(o.multiply(2,-4));
        System.out.println(o.subtract(4, -2));
        System.out.println(o.divide(-2056, -10));
    }
}

class Op {
    public int multiply(int a, int b) {
        boolean anegative=false, bnegative=false;
        if(a<0) {
            a=negate(a);
            anegative=true;
        }
        if(b<0) {
            b=negate(b);
            bnegative=true;
        }

        int x = Math.max(a, b);
        int y = Math.min(a, b);
        int c=0;
        for(int i=1;i<=y;i++) //loop executes min times
            c+=x;

        if(anegative == bnegative)
            return c;
        return negate(c);
    }
    public int negate(int x) {
        int sum=0;
        int b = x > 0 ? -1 : 1;
        int abs = Math.abs(x);
        while(abs-- > 0) {
            sum+=b;
        }
        return sum;

    }
    public int subtract(int a, int b) {
        return a + negate(b);
    }
    public int divide(int a, int b) {
        if(b==0) {
            throw new ArithmeticException("divide by zero not possible!");
        }
        boolean anegative=false, bnegative=false;
        if(a<0) {
            a=negate(a);
            anegative=true;
        }
        if(b<0) {
            b=negate(b);
            bnegative=true;
        }

        int c=0;
        while(a>0 && a>=b) {
            a = subtract(a, b);
            c++;
        }

        if(anegative == bnegative)
            return c;
        return negate(c);
    }
}
