package linkedlist;

public class ReverseKNodes {
    public static void main(String[] args) {
        LL ll = new LL();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
//        ll.add(6);

        ll.print();
//        N node = ll.reverseKNodesInPlace(5);
//        ll.print(node);

        N node = ll.reverseKNodesIteratively(3);
        ll.print(node);
    }
}

class N {
    int v;
    N next;
    public N(int v) {
        this.v=v;
    }
}

class LL {
    N head, tail;
    public void add(int v) {
        N node = new N(v);
        if(head == null) {
            head=node;
            tail=head;
        } else {
            tail.next=node;
            tail=node;
        }
    }

    public N reverseKNodesInPlace(int k) {
        return reverseKNodes(head, k);
    }

    private N reverseKNodes(N node, int k) {
        if(node==null)
            return null;

        N curr=node, prev=null, nextNode=null;
            for(int i=0;i<k && curr!=null;i++) {
                nextNode=curr.next;
                curr.next=prev;
                prev=curr;
                curr=nextNode;
            }
            if(nextNode!=null)
                node.next = reverseKNodes(nextNode, k); // recursive call O(n/k) times
            return prev;


    }

    public N reverseKNodesIteratively(int k) {
        if(head == null)
            return null;

        int len=0;
        N c = head;
        while(c!=null) {
            ++len;
            c=c.next;
        }

        N dummy = new N(-1);
        dummy.next=head;
        N curr, prev=dummy, next;
        while(len >=k) {
            curr=prev.next;
            next=curr.next;
//            int loop = len>k ? k: len-1;
            for(int i=1;i<k;i++) {
                curr.next=next.next;
                next.next=prev.next;
                prev.next=next;
                next=curr.next;
            }
            prev=curr;
            len-=k;
        }

        return dummy.next;
    }

    public void print() {
        N curr=head;
        while(curr!=null) {
            System.out.print(curr.v+" -> ");
            curr=curr.next;
        }
        System.out.println();
    }

    public void print(N n) {
        N curr=n;
        while(curr!=null) {
            System.out.print(curr.v+" -> ");
            curr=curr.next;
        }
        System.out.println();
    }
}
