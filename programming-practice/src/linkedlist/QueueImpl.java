package linkedlist;

public class QueueImpl {
    public static void main(String[] args) {
        FrontMiddleBackQueue q = new FrontMiddleBackQueue();
        q.pushFront(1);
        q.pushBack(2);
        q.pushMiddle(3);
        q.pushMiddle(4);

        q.popFront();
        q.popMiddle();
        q.popMiddle();
        q.popBack();
        q.popFront();
    }
}

class FrontMiddleBackQueue {
    LinkedNode linkedNode;
    public FrontMiddleBackQueue() {
        linkedNode = new LinkedNode();
    }

    public void pushFront(int val) {
        linkedNode.pushFront(val);
    }

    public void pushMiddle(int val) {
        linkedNode.pushMiddle(val);
    }

    public void pushBack(int val) {
        linkedNode.pushBack(val);
    }

    public int popFront() {
        return linkedNode.popFront();
    }

    public int popMiddle() {
        return linkedNode.popMiddle();
    }

    public int popBack() {
        return linkedNode.popBack();
    }
}

/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such:
 * FrontMiddleBackQueue obj = new FrontMiddleBackQueue();
 * obj.pushFront(val);
 * obj.pushMiddle(val);
 * obj.pushBack(val);
 * int param_4 = obj.popFront();
 * int param_5 = obj.popMiddle();
 * int param_6 = obj.popBack();
 */

//class Node {
//    int val;
//    Node next;
//    public Node(int val) {
//        this.val=val;
//        this.next=next;
//    }
//}
class LinkedNode {
    Node head, tail;
    int len;
    public LinkedNode() {
        head=null;
        tail=null;
        len=0;
    }
    public void pushFront(int x) {
        Node node = new Node(x);
        if(head==null) {
            head=node;
            tail=node;
        } else {
            node.next=head;
            head=node;
        }
        ++len;
    }
    public void pushBack(int x) {
        Node node=new Node(x);
        tail.next=node;
        tail=node;
        ++len;
    }
    public int popFront() {
        if(head==null) return -1;
        int value=head.val;
        head=head.next;
        --len;
        return value;
    }
    public int popBack() {
        if(head==null) return -1;
        Node curr=head;
        while(curr.next!=null && curr.next.next!=null) {
            curr=curr.next;
        }
        curr.next = null;
        tail=curr;
        --len;
        return curr.val;
    }
    public void pushMiddle(int x) {
        Node node=new Node(x);
        int middle=len/2;
        Node curr=head;
        int i=0;
        while(i<middle) {
            curr=curr.next;
            ++i;
        }

        Node right=curr.next;
        curr.next=node;
        node.next=right;
        ++len;
    }
    public int popMiddle() {
        if(head==null) return -1;

        int middle=len/2;
        Node curr=head;
        int i=0;
        while(i<middle) {
            ++i;
            curr=curr.next;
        }
        int value=-1;
        if(curr==head) {
            value=curr.val;
            head = head.next;
        } else {
            value = curr.next.val;
            curr.next = curr.next.next;
        }
        --len;
        return value;
    }
}
