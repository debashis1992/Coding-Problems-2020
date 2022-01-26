package linkedlist;

public class LinkedListTest {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.addNode(4);
        list.addNode(5);

        // 4 - 3 - 2 - 1
//        insertionSort(list);
//        System.out.println(list.head);
//          Node someHead = rotateList(list,1);
//        System.out.println(someHead);
        Node head = new Node(1);
        head.next=new Node(2);
        head.next.next=new Node(3);
        head.next.next.next=new Node(4);
        head.next.next.next.next=new Node(5);
        printList(reverseLinkedList(head));

    }

    public static boolean findCycle(Node head) {
        // maintain 2 pointers
        // fast moves 2 places, while slow moves a single place
        // if someone is running faster, in a closed loop they are bound to meet at some place

        Node fast = head, slow = head;
        while(fast!=null && fast.next!=null) {
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow)
                return true;
        }
        return false;
    }

    public static int findLength(Node head) {
        // to find the length of a closed loop(cycle), once the fast and slow pointers meet at some place
        // we can save that node, and again traverse by one place each time till the point
        // we get the same node again
        // that's the length of the cycle

        Node fast = head, slow = head;
        while(fast!=null && fast.next!=null) {
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow)
                return calculateLength(slow);
        }
        return 0;
    }

    public static int calculateLength(Node head) {
        int count = 0;
        Node curr = head;
        do {
            curr = curr.next;
            ++count;
        } while(curr!=head);

        return count;
    }

    public static Node findCycleStartingNode(Node head) {
        int length = findLength(head);
        Node p1 = head, p2 = head;
        // move p2 by K positions(length of the cycle)
        while(length-->0)
            p2 = p2.next;

        while(p1!=p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    public static Node findCycleStartingNode2(Node head) {
        Node fast = head, slow = head;
        while(fast!=null && fast.next!=null) {
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow)
                break;
        }

        slow = head;
        while(slow!=fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public static Node findMiddleNode(Node head) {
        Node fast = head, slow = head;
        while(fast!=null && fast.next!=null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public static boolean isPalindrome(Node head) {
        int n = findListLength(head);
        Node p1 = head, p2 = head;

        // move p2 by (n-1) positions foreward
        while(p2.next!=null)
            p2 = p2.next;

        // link both the nodes
        p2.next = p1;


        while(p1!=p2) {
            p1 = p1.next;
            int pos = n-1;
            while(pos-- > 0) {
                p2 = p2.next;
            }

            if(p1.val != p2.val)
                return false;
        }
        return true;

    }

    public static int findListLength(Node node) {
        int c=0;
        Node curr = node;
        while(curr!=null) {
            curr = curr.next;
            ++c;

        }

        return c;
    }

    public static Node reverseLinkedList(Node head) {
        Node currNode=head;
        Node nextNode=null,prevNode=null;
        while(currNode!=null) {
            nextNode=currNode.next;
            currNode.next=prevNode;
            prevNode=currNode;
            currNode=nextNode;
        }
        return prevNode;
    }
    public static void printList(Node head) {
        Node curr=head;
        while(curr!=null) {
            System.out.print(curr.val+" -> ");
            curr=curr.next;
        }
        System.out.print("null");
        System.out.println();
    }
    public static Node rotateList(MyLinkedList list, int k) {
        Node curr = list.head;
        int len=0;
        while(curr!=null) {
            ++len;
            curr=curr.next;
        }
        if(k>len) k=k%len;
        k = len-k;

        Node temp = list.head;
        int i=1;
        while(i<k) {
            temp = temp.next;
            ++i;
        }
        if(temp==null) return list.head;

        Node kthNode = temp.next;
        temp.next = null;

        Node tmp = kthNode;
        while(tmp.next!=null) {
            tmp = tmp.next;
        }
        tmp.next = list.head;
        Node head = kthNode;
        return head;
    }
    public static void sortedInsert(MyLinkedList list, Node node) {
        if(list.sorted == null || list.sorted.val > node.val) {
            node.next = list.sorted;
            list.sorted = node;
        } else {
            Node currNode = list.sorted;
            while(currNode!=null && currNode.val < node.val) {
                currNode = currNode.next;
            }
            node.next = currNode.next;
            currNode.next = node;
        }
    }

    public static Node insertionSort(MyLinkedList list) {

        Node currNode = list.head;
        while(currNode!=null) {
            sortedInsert(list, currNode);
            currNode = currNode.next;
        }
        list.head = list.sorted;
        return list.head;
    }
}

class Node {
    int val;
    Node next;
    public Node(int val) {
        this.val = val;
        this.next = null;
    }
}
class MyLinkedList {
    Node head;
    Node tail;
    Node sorted;
    public void addNode(int val) {
        Node node = new Node(val);
        if(head==null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }


}