package crackingthecodingint;

public class RemoveDups {
    public static void main(String[] args) {


        Node root=new Node(1);
        root.next=new Node(2);
        root.next.next=new Node(1);
        root.next.next.next=new Node(2);
        root.next.next.next.next=new Node(2);
        root.next.next.next.next.next=new Node(3);
        root.next.next.next.next.next.next=new Node(4);
        root.next.next.next.next.next.next.next=new Node(3);

        print(root);
        Node distinct = removeDuplicate(root);
        print(distinct);
    }

    public static void print(Node node) {
        Node c=node;
        while(c!=null) {
            System.out.print(c.val+ " -> ");
            c=c.next;
        }
        System.out.println("null");
    }

    public static Node removeDuplicate(Node node) {
        if(node==null || node.next==null) return node;

        Node mergedNode = mergeSort(node);
        Node dummy=new Node(-1);
        dummy.next = mergedNode;

        Node prev = dummy;
        Node curr = prev.next;

        while(curr!=null) {
            while(curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
            }
            prev.next = curr;
            prev = curr;
            curr = curr.next;
        }

        Node result = dummy.next;
        dummy.next=null;
        return result;
    }

    public static Node mergeSort(Node node) {
        if(node==null || node.next==null) return node;

        Node f=node, s=node;
        Node prev=s;

        while(s!=null && f!=null && f.next!=null) {
            prev=s;
            s=s.next;
            f=f.next.next;
        }

        Node second = prev.next;
        prev.next = null;

        Node first = node;

        Node firstHalf = mergeSort(first);
        Node secondHalf = mergeSort(second);
        return merge(firstHalf, secondHalf);
    }

    public static Node merge(Node f, Node l) {
        Node dummy=new Node(-1);
        Node curr = dummy;

        Node c1=f, c2=l;
        while(c1!=null && c2!=null) {
            if(c1.val < c2.val) {
                curr.next = c1;
                c1 = c1.next;
            } else {
                curr.next = c2;
                c2 = c2.next;
            }
            curr=curr.next;
        }

        if(c1==null)
            curr.next = c2;
        else
            curr.next = c1;

        Node result = dummy.next;
        dummy.next = null;
        return result;

    }
}


class Node {
    int val;
    Node next;
    public Node(int val) {
        this.val=val;
    }
}