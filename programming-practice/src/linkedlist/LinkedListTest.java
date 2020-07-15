package linkedlist;

public class LinkedListTest {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.addNode(4);
        list.addNode(3);
        list.addNode(2);
        list.addNode(1);

        // 4 - 3 - 2 - 1
        insertionSort(list);
        System.out.println(list.head);


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