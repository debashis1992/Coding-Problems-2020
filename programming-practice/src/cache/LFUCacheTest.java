package cache;

import java.util.*;
/*
    LFU - Least Frequency cache
 */
public class LFUCacheTest {
    public static void main(String[] args) {

        LFUCache cache = new LFUCache(2);
        cache.put(3,1);
        cache.put(2,1);
        cache.put(2,2);
        cache.put(4,4);
        cache.get(2);

    }
}

class LFUCache {

    int cap, minFreq;
    Map<Integer, Node> map;
    Map<Integer, DLL> freqMap;
    public LFUCache(int capacity) {
        cap=capacity;
        minFreq=1;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if(!map.containsKey(key)) return -1;

        Node node = map.get(key);
        int currFreq = node.freq;
        DLL dll = freqMap.get(currFreq);
        dll.deleteNode(node);

        if(dll.isEmpty()) {
            if(currFreq == minFreq)
                minFreq=currFreq+1;
        }
        node.freq++;
        DLL dll2 = freqMap.getOrDefault(node.freq, new DLL());
        dll2.insertAfterHead(node);

        freqMap.put(node.freq, dll2);
        return node.value;
    }

    public void put(int key, int value) {

        if(map.containsKey(key)) {
            Node node = map.get(key);
            int currFreq = node.freq;
            DLL dll = freqMap.get(currFreq);
            dll.deleteNode(node);

            if(dll.isEmpty()) {
                if(currFreq == minFreq)
                    minFreq=currFreq+1;
            }
            node.freq++;
            node.value = value;
            DLL dll2 = freqMap.getOrDefault(node.freq, new DLL());
            dll2.insertAfterHead(node);
            freqMap.put(node.freq, dll2);
        }
        else {
            if(map.size() == cap) {
                // limit reached
                DLL dll4 = freqMap.get(minFreq);
                Node node = dll4.deleteFromTail();
                // remove from actual map
                map.remove(node.key);
            }
            minFreq = 1;
            Node newNode = new Node(key,value);
            map.put(key, newNode);
            DLL dll3 = freqMap.getOrDefault(minFreq, new DLL());
            dll3.insertAfterHead(newNode);
            freqMap.put(minFreq, dll3);

        }


    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */



class Node {
    int key, value, freq;
    Node next, prev;

    public Node(int k,int v) {
        key=k;
        value=v;
        freq=1;
    }
}

class DLL {
    Node head, tail;
    public DLL() {
        head = new Node(-1,-1);
        tail = new Node(-1,-1);
        head.next = tail;
        tail.prev = head;
    }

    public boolean isEmpty() {
        return head.next == tail && tail.prev == head;
    }

    public Node deleteFromTail() {
        Node deletedNode = tail.prev;
        Node prevNode = deletedNode.prev;
        prevNode.next = tail;
        tail.prev = prevNode;
        return deletedNode;
    }

    public void insertAfterHead(Node node) {
        Node nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    public void deleteNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }
}
