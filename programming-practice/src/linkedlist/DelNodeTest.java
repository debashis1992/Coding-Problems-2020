package linkedlist;

public class DelNodeTest {

    public static void main(String[] args) {
//        ListNode node=new ListNode(4);
//        node.next=new ListNode(5);
//        node.next.next=new ListNode(1);
//        node.next.next.next=new ListNode(9);
//
//        delete(node.next);
//        print(node);

        ListNode node=new ListNode(1);
        node.next=new ListNode(5);
        node.next.next=new ListNode(7);
        node.next.next.next=new ListNode(13);

//        ListNode res = reverseKGroups(node, 3);
//        print(res);
    }


    public static ListNode mergeSortedList(ListNode a, ListNode b) {

        ListNode res = new ListNode(-1);
        ListNode temp = res;

        while(a!=null && b!=null) {
            if(a.val < b.val) {
                temp.bottom = a;
                a=a.bottom;
            }
            else {
                temp.bottom = b;
                b=b.bottom;
            }
            temp=temp.bottom;
        }
        if(a!=null)
            temp.bottom=a;
        else temp.bottom=b;

        return res.bottom;
    }

    public static ListNode flatten(ListNode root) {
        if(root==null || root.next==null)
            return root;

        root.next = flatten(root.next);

        root = mergeSortedList(root, root.next);
        return root;
    }

    public static ListNode reverseKGroups(ListNode head, int k) {

        if(k==1)
            return head;
        int len=0;
        ListNode c=head;
        while(c!=null) {
            c=c.next;
            ++len;
        }

        if(k==len)
            return reverseK(head, k);
        int count=len/k;
        ListNode curr=head, prev=null, actualPrev=null, start=curr;

        while(count-->0) {
            int i=0;
            while(i<k) {
                prev=curr;
                curr=curr.next;
                ++i;
            }

            prev.next = null;
            ListNode rev = reverseK(start, k);
            if(actualPrev!=null)
                actualPrev.next=rev;
            else {
                head = rev;
            }
            ListNode c1=rev;
            while(c1.next!=null) {
                c1=c1.next;
            }
            c1.next=curr;
            start=curr;
            actualPrev = c1;
        }
        return head;

    }

    public static ListNode reverseK(ListNode head, int k) {
        ListNode c=head, prev=null,nextNode=null;
        int i=0;
        while(c!=null && i<k) {
            nextNode=c.next;
            c.next=prev;
            prev=c;
            c=nextNode;
        }
        return prev;

//        ListNode curr=prev;
//        int i=0;
//        while(i<k) {
//            curr=curr.next;
//            i++;
//        }
//        curr.next=null;
//        return prev;
    }

    public static ListNode reverse(ListNode node) {
        ListNode curr=node, prev=null, nextNode=null;
        while(curr!=null) {
            nextNode=curr.next;
            curr.next=prev;
            prev=curr;
            curr=nextNode;
        }

        return prev;
    }
    public static void delete(ListNode node) {

        ListNode nodeCopy = new ListNode(node.val);
        nodeCopy.next = node.next;
        ListNode rev=reverse(nodeCopy);
        ListNode c=rev, prev=null;
        while(c.next!=null) {
            prev=c;
            c=c.next;
        }
        prev.next=null;

        ListNode rev2=reverse(rev);
        print(rev2);
        node = rev2;
    }

    public static void print(ListNode node) {
        ListNode c=node;
        while(c!=null) {
            System.out.print(c.val+" -> ");
            c=c.next;
        }
        System.out.println();

    }

}


class ListNode {
    int val;
    ListNode next;
    ListNode bottom;
    public ListNode(int val) {
        this.val=val;
    }
}


