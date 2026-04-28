package crackingthecodingint;

public class PartitionList {
    public static void main(String[] args) {

        Node root=new Node(1);
        root.next=new Node(4);
        root.next.next=new Node(3);
        root.next.next.next=new Node(2);
        root.next.next.next.next=new Node(5);
        root.next.next.next.next.next=new Node(2);

        print(root);
        partitionList(root, 3);
        print(root);

    }
    public static void print(Node node) {
        Node c=node;
        while(c!=null) {
            System.out.print(c.val+ " -> ");
            c=c.next;
        }
        System.out.println("null");
    }


    public static Node partitionList(Node node, int t) {

        Node dummy=new Node(-1);
        dummy.next = node;

        Node high=node, prev=dummy;
        while(high!=null && high.val <= t) {
            prev=high;
            high=high.next;
        }

        Node higherLastNode = prev;

        // now check all the remaining nodes from higherNode to end
        // if found smaller, then just add it in the end of higherLastNode
        // and now that becomes our new higherLastNode

        Node curr = higherLastNode.next;
        while(curr!=null) {
            if(curr.val < t) {
                Node nextNode = curr.next;
                Node lessNode = new Node(curr.val);

                higherLastNode.next = lessNode;
                lessNode.next = high;

                curr = nextNode;
            } else {
                prev = curr;
                curr = curr.next;

            }
        }

        return dummy.next;


    }
}

