package threads;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Testing2025 {

    public static void main(String[] args) {

//        try(ExecutorService executorService = Executors.newFixedThreadPool(5)) {
//            executorService.submit(() -> System.out.println("some task"));
//        }


        int[] ar = {1,2,3,4,5,6,7};
        int result = Arrays.stream(ar).filter(x -> x%2==0)
                .map(x -> x*2)
                .sum();

        System.out.println(result);

        String s = "eat";
        char[] c = s.toCharArray();
        Arrays.sort(c);
        System.out.println(String.valueOf(c));


        int val = 'c'-'a';
        System.out.println(val);
        System.out.println((char)(val + 'a'));

    }
}


class Singleton {
    private Singleton() {}
    private static Singleton instance;

    public static Singleton getInstance() {
        if(instance == null)
            instance = new Singleton();
        return instance;
    }
}

class Singleton2 {
    private Singleton2() {}
    private static volatile Singleton2 instance;

    public static Singleton2 getInstance() {
        if(instance == null) {
            synchronized (Singleton2.class) {
                if(instance == null)
                    instance = new Singleton2();
            }
        }
        return instance;
    }
}

class Singleton3 {
    private Singleton3() {}

    private static class HolderInstance  {
        private static final Singleton3 INSTANCE = new Singleton3();
    }

    public static Singleton3 getInstance() {
        return HolderInstance.INSTANCE;
    }
}


interface Ex1 {
    void execute();
}
interface Ex3 {}

interface Ex2 extends Ex1, Ex3 {

}


