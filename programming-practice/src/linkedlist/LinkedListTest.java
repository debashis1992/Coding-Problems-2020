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