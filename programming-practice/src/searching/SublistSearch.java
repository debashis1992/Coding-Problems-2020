package searching;

public class SublistSearch {
    public static void main(String[] args) {
        MyLinkedList list1 = new MyLinkedList();
        list1.addNode(1);
        list1.addNode(2);
        list1.addNode(3);
        list1.addNode(4);

        MyLinkedList list2 = new MyLinkedList();
        list2.addNode(1);
        list2.addNode(2);
        list2.addNode(1);
//        list2.addNode(2);
//        list2.addNode(3);
//        list2.addNode(4);

        System.out.println(performSublistSearch(list1, list2));
    }

    public static boolean performSublistSearch(MyLinkedList list1, MyLinkedList list2) {
        Node head1 = list1.headNode;
        Node head2 = list2.headNode;
        if(head2 == null)   return false;   //base case
        while(head2!=null && head1.value != head2.value) {
            head2 = head2.nextNode;
        }
        if(head2==null) return false;
        else {
            boolean isPresent = true;
            while (head1 != null && head2 != null) {
                if (head1.value == head2.value) {
                    isPresent = true;
                } else  {
                    head1 = list1.headNode;
                    isPresent = false;
                }
                head1 = head1.nextNode;
                head2 = head2.nextNode;
            }
            return isPresent;
        }

    }

}
class MyLinkedList {
    Node headNode;
    Node tailNode;
    int size;
    public MyLinkedList() {
        headNode = null;
        tailNode = null;
        size = 0;
    }
    public void addNode(int value) {
        Node node = new Node(value);
        if(headNode == null) {
            headNode = node;
            tailNode = node;
        }
        else {
            tailNode.nextNode = node;
            tailNode = node;
        }
        ++size;
    }
}

class Node {
    Node nextNode;
    int value;
    public Node(int value) {
        this.value = value;
        this.nextNode = null;
    }
}
