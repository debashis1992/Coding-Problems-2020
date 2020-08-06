package linkedlist;

class DNode {
    int data;
    DNode next;
    DNode prev;
    public DNode(int data) {
        this.data = data;
    }
}

class MyDoublyLinkedList {
    DNode head;
    DNode tail;

    public void addNode(int data) {
        DNode newNode = new DNode(data);
        if(head==null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
    }

    public void traverseFwd(DNode node) {
        while(node!=null) {
            System.out.print(node.data + " -> ");
            node = node.next;
        }
        System.out.println();
    }

    public void traverseBwd(DNode node) {
        while(node!=null) {
            System.out.print(node.data + " -> ");
            node = node.prev;
        }
        System.out.println();
    }
}

public class DoublyLinkedListTest {

    public static void main(String[] args) {
        MyDoublyLinkedList list = new MyDoublyLinkedList();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.addNode(4);
        list.addNode(5);

        DNode headNode = list.head;
        DNode tailNode = list.tail;

        list.traverseFwd(headNode);
        list.traverseBwd(tailNode);
    }
}
