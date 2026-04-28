package crackingthecodingint;

public class IntersectionList {
    public static void main(String[] args) {


    }

    public static Node intersection(Node a, Node b) {
        Node p1=a, p2=b;

        while(p1!=p2) {
            p1 = p1 == null ? p2 : p1.next;
            p2 = p2 == null ? p2 : p2.next;

        }
        return p1;
    }
}
