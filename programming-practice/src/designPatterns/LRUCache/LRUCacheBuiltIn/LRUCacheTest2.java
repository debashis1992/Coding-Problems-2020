package designPatterns.LRUCache.LRUCacheBuiltIn;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LRUCacheTest2 {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(4);
        cache.put(1,1);
        cache.put(2,2);
        cache.put(3,3);
        cache.put(4,4);
        cache.put(5,5);


        cache.view();
        cache.get(5);
        cache.put(100,100);
        cache.view();
    }
}

class LRUCache {
    private final int capacity;
    private final Map<Node,Integer> map;
    private final DoubleLinkedList doubleLinkedList;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.doubleLinkedList = new DoubleLinkedList();
    }

    public synchronized boolean put(Integer key, Integer value) {
        Node node = new Node(key);
        if(map.containsKey(node)) {
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addNodeToHead(node);
            map.put(node, value);
            return true;
        }

        if(capacity == map.size()) {
            Node tailNode = doubleLinkedList.removeFromTail();
            map.remove(tailNode);
        }
        doubleLinkedList.addNodeToHead(node);
        map.put(node, value);

        return true;
    }

    public synchronized int get(Integer key) {
        Node node = new Node(key);
        if(map.containsKey(node)) {
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addNodeToHead(node);
            return map.get(node);
        }
        return -1;
    }

    public void view() {
        System.out.println(map);
    }
}

class DoubleLinkedList {
    Node head;
    Node tail;

    public DoubleLinkedList() {
        head = null;
        tail = null;
    }

    public void addNodeToHead(Node newNode) {
        if(head == null && tail == null) {
            head=newNode;
            tail=newNode;
        }
        else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    public Node removeFromTail() {
        Node tailNode = tail;
        Node newTailNode = tail.prev;
        newTailNode.next = null;
        tail = newTailNode;
        return tailNode;
    }

    public void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;

        if(prevNode==null && nextNode==null) {
            head = null;
            tail = null;
        }

        if(prevNode!=null)
            prevNode.next = nextNode;
        if(nextNode!=null)
            nextNode.prev = prevNode;
    }

}

class Node {
    int val;
    Node prev, next;

    public Node(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return val == node.val;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }
}


