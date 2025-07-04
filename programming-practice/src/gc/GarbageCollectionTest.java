package gc;

public class GarbageCollectionTest {
    public static void main(String[] args) {

        String s = "some text";
        String s2 = new String("some text").intern();

        System.out.println(s == s2);
    }
}
