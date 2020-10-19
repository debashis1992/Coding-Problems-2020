package stack;



import java.util.Arrays;
import java.util.Stack;

public class StackProblemsTest {
    public static void main(String[] args) {
        QueueUsingStacks q = new QueueUsingStacks();
        q.enQueue(1);
        q.enQueue(2);
        q.enQueue(3);
        q.enQueue(4);
        q.enQueue(5);

        System.out.println(q.deQueue());
        System.out.println(q.deQueue());
        q.enQueue(6);
        q.enQueue(7);
        System.out.println(q.deQueue());

        TwoStacksUsingArray ar = new TwoStacksUsingArray(5);
        ar.push1(1);
        ar.push1(2);

        ar.push2(3);
        ar.push2(4);
        ar.push1(5);
        System.out.println(ar);

        System.out.println(ar);

    }

}

class QueueUsingStacks {
    Stack<Integer> st1;
    Stack<Integer> st2;
    public QueueUsingStacks() {
        st1 = new Stack<>();
        st2 = new Stack<>();
    }

    public void enQueue(int data) {
        st1.add(data);
    }
    public int deQueue() {
        if(st1.isEmpty()) return -1;
        while(!st1.isEmpty()) {
            st2.add(st1.pop());
        }
        int data = st2.pop();
        while(!st2.isEmpty()) {
            st1.add(st2.pop());
        }
        return data;
    }
}


class TwoStacksUsingArray {
    int[] a;
    int len1,len2;
    public TwoStacksUsingArray(int size) {
        a = new int[2*size+1];
        len1=0;
        len2=size;
        initialize();
    }
    public void initialize() {
        for(int i=0;i<a.length;i++)
            a[i]=-1;
    }
    public void push1(int x) {
        if(len1>=a.length/2)
            System.out.println("Stack1 size is full. Cannot add value");
        a[len1++] = x;
    }
    public void push2(int x) {
        if(len2>=a.length)
            System.out.println("Stack2 size is full. Cannot add value");
        a[len2++] = x;
    }

    public int pop1() {
        return a[len1--];
    }
    public int pop2() {
        return a[len2--];
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<len1;i++) {
            if(a[i]!=-1)
                sb.append(a[i]+",");
        }
        sb.append("]");
        sb.append("[");
        for(int i=a.length/2;i<a.length;i++) {
            if(a[i]!=-1)
                sb.append(a[i]+",");
        }
        sb.append("]");
        return sb.toString();
    }
}