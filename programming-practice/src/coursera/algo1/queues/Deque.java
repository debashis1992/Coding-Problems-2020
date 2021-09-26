package coursera.algo1.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final LinkedList<Item> linkedList;

    // construct an empty deque
    public Deque() {
        linkedList = new LinkedList<>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.linkedList.isEmpty();
    }

    // return the number of items on the deque
    public int size() {
        return this.linkedList.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("value cannot be null");

        Node<Item> newNode = new Node<>(item);
        this.linkedList.addNodeToHead(newNode);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("value cannot be null");

        Node<Item> newNode = new Node<>(item);
        this.linkedList.addNodeToLast(newNode);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        return this.linkedList.removeFirst().value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        return this.linkedList.removeLast().value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node<Item> headNode = linkedList.head;
            int i = 0;

            @Override
            public boolean hasNext() {
                return headNode.next != null;
            }

            @Override
            public Item next() {
                if (i == 0) {
                    ++i;
                    return headNode.value;
                }
                if (headNode == null) {
                    throw new NoSuchElementException("no elements present");
                }
                headNode = headNode.next;
                return headNode.value;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.isEmpty());
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);

//        System.out.println(deque.removeLast());
//        System.out.println(deque.removeFirst());

        deque.addFirst(100);
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " --> ");
        }
        System.out.print("NULL");
        System.out.println();

    }

}

class Node<Item> {
    Item value;
    Node<Item> next;
    Node<Item> prev;

    public Node(Item value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}

class LinkedList<Item> {
    Node<Item> head;
    Node<Item> tail;
    int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addNodeToHead(Node<Item> node) {
        if (head == null) {
            tail = node;
        } else {
            node.next = head;
            head.prev = node;

        }
        head = node;
        ++this.size;
    }

    public void addNodeToLast(Node<Item> node) {
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        ++this.size;
    }

    public Node<Item> removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("size is empty");

        Node<Item> headNode = this.head;
        if (this.size > 1) {
            head = head.next;
            head.prev = null;
        } else {
            head = null;
            tail = null;
        }
        --this.size;
        return headNode;
    }

    public Node<Item> removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("size is empty");

        Node<Item> tailNode = this.tail;
        if (this.size > 1) {
            tail = tail.prev;
        } else {
            head = null;
            tail = null;
        }
        --this.size;
        return tailNode;
    }
}