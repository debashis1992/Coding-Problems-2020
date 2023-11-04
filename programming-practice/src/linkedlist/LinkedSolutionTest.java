package linkedlist;

import java.util.List;

public class LinkedSolutionTest {
    public static void main(String[] args) {
        Solution solution=new Solution();
//        Solution.ListNode listNode=new ListNode(1);
//        listNode.next= new Solution.ListNode(5);
//        listNode.next.next=new ListNode(7);
//        listNode.next.next.next=new ListNode(13);
////        listNode.next.next.next.next=new ListNode(5);
//
////        ListNode res = solution.reverseBetween(listNode, 1,5);
////        solution.printList(res);
//
//        Solution.ListNode res = solution.reverseLinkedListRange(listNode, 1, 2);
//        solution.printList(res);

        Solution.ListNode head = new Solution.ListNode(1);
        head.down=new Solution.ListNode(3);
        head.down.down = new Solution.ListNode(8);

        head.next = new Solution.ListNode(5);
        head.next.down = new Solution.ListNode(8);

        Solution.ListNode res = solution.flatten(head);
        solution.printList(res);

    }
}

class Solution {
    static class ListNode {
        int data;
        ListNode next;
        ListNode down;

        ListNode(int data) {
            this.data = data;
            this.next = null;
            this.down = null;
        }
    }
    public void printList(ListNode head) {
        ListNode curr=head;
        while(curr!=null) {
            System.out.print(curr.data+" -> ");
            curr=curr.down;
        }
        System.out.println();

    }

    ListNode flattenTheLinkedList(ListNode root) {
        return flatten(root);
    }

    ListNode merge(ListNode root, ListNode l1, ListNode l2) {
        ListNode c=root;
        ListNode p1=l1, p2=l2;
        while(p1!=null && p2!=null) {
            if(p1.data < p2.data) {
                c.down = p1;
                p1=p1.down;
            }
            else {
                c.down = p2;
                p2=p2.down;
            }
            c = c.down;
        }
        if(p1!=null) {
            c.down = p1;
        }
        else if(p2!=null) {
            c.down=p2;
        }
        root.next = null;
        return root;
    }

    ListNode flatten(ListNode root) {

        if(root==null || root.next==null)
            return root;
        else {

            root.next = flatten(root.next);
            root = merge(root, root.down, root.next);
            return root;
        }
    }

    public ListNode reverseLinkedListRange(ListNode head, int left, int right) {
        ListNode dummy=new ListNode(-1);
        dummy.next = head;

        ListNode prev = dummy;
        int i=0;
        while(++i< left)
            prev=prev.next;

        ListNode curr=prev.next, nextNode=curr.next;
        int diff = (right-left);
        while(diff-->0) {
            curr.next = nextNode.next;
            nextNode.next = prev.next;
            prev.next = nextNode;
            nextNode = curr.next;
        }

        ListNode res = dummy.next;
        dummy.next = null;
        return res;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {

        ListNode dummy=new ListNode(-1);
        dummy.next=head;

        ListNode prev=dummy, curr=dummy.next;
        int i=0;
        while(i<left-1) {
            prev=curr;
            curr=curr.next;
            i++;
        }

        ListNode firstEnd = prev;
        ListNode list=prev.next;
        prev.next=null;

        ListNode pp=null, cc=list;
        while(i<right) {
            pp=cc;
            cc=cc.next;
            i++;
        }

        ListNode secondStart = pp.next;
        pp.next = null;

        ListNode rev = reverse(list);
        firstEnd.next = rev;

        while(rev.next!=null) {
            rev=rev.next;
        }
        rev.next = secondStart;

        ListNode res=dummy.next;
        dummy.next=null;
        return res;

    }

    public ListNode reverse(ListNode head) {
        ListNode prev=null, curr=head, next=head;

        while(curr!=null) {
            next=curr.next;
            curr.next=prev;
            prev=curr;
            curr=next;
        }
        return prev;
    }


}
