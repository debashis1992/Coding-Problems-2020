package coursera.algo1.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private final RandomLinkedList<Item> randomLinkedList;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.randomLinkedList = new RandomLinkedList<>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.randomLinkedList.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.randomLinkedList.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("value cannot be null");

        RandomNode<Item> node = new RandomNode<>(item);
        this.randomLinkedList.addNodeItemoLast(node);
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.randomLinkedList.isEmpty())
            throw new NoSuchElementException("no elements are present");

        RandomNode<Item> randomNode = this.randomLinkedList.removeRandomElement();
        return randomNode.value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.randomLinkedList.isEmpty())
            throw new NoSuchElementException("no elements are present");

        RandomNode<Item> randomNode = this.randomLinkedList.getRandomElement();
        return randomNode.value;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            RandomNode<Item> headNode = randomLinkedList.head;
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
        RandomizedQueue<String> r = new RandomizedQueue<>();
        r.enqueue("debashis");
        r.enqueue("walmart");
        r.enqueue("copart");
        System.out.println(r.dequeue());
        System.out.println(r.dequeue());
        System.out.println(r.dequeue());

    }

}

class RandomNode<Item> {
    Item value;
    RandomNode<Item> next;
    RandomNode<Item> prev;

    public RandomNode(Item value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}

class RandomLinkedList<Item> {
    RandomNode<Item> head;
    RandomNode<Item> tail;
    int size;

    public RandomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addNodeItemoLast(RandomNode<Item> node) {
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        ++this.size;
    }

    public RandomNode<Item> getRandomElement() {
        int randomIndex = getRandomNumber();
        int i = 0;
        RandomNode<Item> currNode = this.head;
        while (i++ != randomIndex)
            currNode = currNode.next;

        return currNode;
    }

    public RandomNode<Item> removeRandomElement() {
        int randomIndex = getRandomNumber();
        int i = 0;
        RandomNode<Item> currNode = this.head;
        RandomNode<Item> prevNode = null;
        while (i++ != randomIndex) {
            prevNode = currNode;
            currNode = currNode.next;
        }
        if (prevNode != null && prevNode.next != null) {
            prevNode.next = currNode.next;
        } else {
            this.head = currNode.next;
        }
        --this.size;
        return currNode;
    }

    private int getRandomNumber() {
        int min = 0;
        int max = this.size - 1;
        return StdRandom.uniform(min, max);
    }
}