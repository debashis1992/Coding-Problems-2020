package test;

public class Test2Class {
    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.add(1);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
    }
}
class Queue {
    LinkedList linkedList;
    public Queue() {
        linkedList = new LinkedList();
    }
    public void add(int data) {
        linkedList.addNode(data);
    }
    // display the head of the queue
    public int peek() {
        return linkedList.getHeadValue();
    }
    // display and remove the head of the queue
    public int poll() {
        return linkedList.removeHead();
    }
}

class LinkedList {
    private Node head;
    private Node tail;
    public LinkedList() {
        head=null;
        tail=null;
    }
    public void addNode(int data) {
        Node node = new Node(data);
        if(head==null || tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }
    public int getHeadValue() {
        return head!=null ? head.data : -1;
    }
    public int removeHead() {
        if(head==null) return -1;
        int headValue = head.data;
        Node nextHeadNode = head.next;
        head = nextHeadNode;
        return headValue;
    }
}
class Node {
    int data;
    Node next;
    public Node(int data) {
        this.data = data;
    }
}
