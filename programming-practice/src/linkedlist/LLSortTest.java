package linkedlist;

public class LLSortTest {
    public static void main(String[] args) {

        ListNode head = new ListNode(4);
        head.next=new ListNode(2);
        head.next.next=new ListNode(1);
        head.next.next.next=new ListNode(3);

        LLSortTest ll=new LLSortTest();
        ListNode res = ll.sortList(head);
        ll.print(res);


    }
    public void print(ListNode head) {
        ListNode curr=head;
        while(curr!=null) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
        }

    }

    public ListNode sortList(ListNode head) {
        ListNode res = null;
        if(head!=null) {
            ListNode[] nodes = split(head);
            ListNode left = nodes[0]!=null && nodes[0].next!=null ? sortList(nodes[0]) : nodes[0];
            ListNode right = nodes[1]!=null && nodes[1].next!=null ? sortList(nodes[1]) : nodes[1];
            res = sortAndMerge(left, right);
        }
        return res;
    }

    public ListNode sortAndMerge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;
        while(left!=null && right!=null) {
            if(left.val <= right.val) {
                head.next = left;
                left = left.next;
            } else {
                head.next = right;
                right = right.next;
            }
            head = head.next;
        }
        if(left!=null)
            head.next = left;
        else if(right!=null)
            head.next = right;

        ListNode res = dummy.next;
        dummy.next = null;

        return res;
    }

    public ListNode[] split(ListNode head) {
        ListNode s=head, f=head;
        if(s!=null && s.next!=null && s.next.next==null) {
            ListNode right = s.next;
            s.next = null;
            return new ListNode[]{head, right};
        }
        while(s!=null && f!=null && f.next!=null) {
            s=s.next;
            f=f.next.next;
        }

        ListNode right = s.next;
        s.next = null;
        return new ListNode[]{head, right};
    }
}

