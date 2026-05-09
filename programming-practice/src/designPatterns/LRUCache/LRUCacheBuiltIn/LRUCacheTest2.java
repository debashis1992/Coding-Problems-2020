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
    int capacity;
    Map<Integer, Node> map;
    Dll dll;

    public LRUCache(int capacity) {
        this.capacity=capacity;
        map=new HashMap<>();
        dll=new Dll();
    }

    public int get(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            dll.removeNode(node);
            dll.addToHead(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            dll.removeNode(node);
            node.val = value;
            dll.addToHead(node);

        } else {
            if(map.size() == capacity) {
                Node node = dll.removeFromTail();
                map.remove(node.key);
            }

            Node newNode = new Node(key, value);
            dll.addToHead(newNode);
            map.put(key, newNode);
        }

    }

    public void view() {
        System.out.println(map);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

class Dll {
    Node head, tail;
    public Dll() {
        this.head = new Node(-1,-1);
        this.tail = new Node(-1,-1);
        head.next = tail;
        tail.prev = head;
    }

    public void addToHead(Node node) {
        if(node==null) return;

        Node nn=head.next;
        head.next=node;
        node.prev=head;

        node.next=nn;
        nn.prev=node;
    }

    public void removeNode(Node node) {
        if(node==null) return;

        Node pp = node.prev;
        Node nn = node.next;
        pp.next = nn;
        nn.prev = pp;
    }

    public Node removeFromTail() {
        if(tail.prev == head) return null;
        Node pp = tail.prev;
        removeNode(pp);
        return pp;
    }
}

class Node {
    int key,val;
    Node next, prev;
    public Node(int key, int val) {
        this.key=key;
        this.val=val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                ", key=" + key +
                '}';
    }
}

