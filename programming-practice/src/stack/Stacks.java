package stack;

public class Stacks {
    private Node top;
    private Node prevTop;
    private int length;
    public void addNode(int data) {
        Node newNode = new Node(data);
        if(top!=null) {
            prevTop = top;
            newNode.prev = prevTop;
        }
        top = newNode;
        ++length;
    }

    public int peek() {
        int data = top.data;
        return data;
    }
    public int poll() {
        int data = top.data;
        --length;
        top = prevTop;
        prevTop = prevTop.prev;
        return data;
    }
    public boolean isEmpty() {
        return length==0;
    }

    @Override
    public String toString() {
        if(top==null)
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node currNode = top;
        while(currNode!=null) {
            sb.append(currNode.data).append(",");
            currNode = currNode.prev;
        }
        sb.append("]");
        return sb.toString();
    }
}

class Node {
    int data;
    Node next;
    Node prev;
    public Node(int data) {
        this.data = data;
    }
}