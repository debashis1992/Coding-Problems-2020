package linkedlist;

public class Test {
    public static void main(String[] args) {

        LLNode root=new LLNode(1);
        root.next = new LLNode(1);
        root.next.next = new LLNode(1);
        root.next.next.next = new LLNode(2);
        root.next.next.next.next = new LLNode(3);
//        root.next.next.next.next.next = new LLNode(4);
//        root.next.next.next.next.next.next = new LLNode(4);
//        root.next.next.next.next.next.next.next = new LLNode(5);


        removeDuplicates(root);
    }

    public static void removeDuplicates(LLNode root) {

        LLNode dummy = new LLNode(-1);
        dummy.next = root;

        LLNode prev=dummy, c=dummy.next;
        while(c!=null) {
            if(c.next!=null && c.val == c.next.val) {
                while(c.next!=null && c.val == c.next.val)
                    c=c.next;

                prev.next = c.next;
            } else {
                prev=c;

            }
            c=c.next;
        }
        print(dummy.next);

    }

    public static void print(LLNode root) {
        LLNode node=root;
        while(node!=null) {
            System.out.print(node.val + " -> ");
            node = node.next;
        }
    }
}

class LLNode {
    int val;
    LLNode next;
    public LLNode(int val) {
        this.val=val;
    }
}
