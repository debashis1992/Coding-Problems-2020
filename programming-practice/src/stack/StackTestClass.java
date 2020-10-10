package stack;

public class StackTestClass {
    public static void main(String[] args) {
        Stacks stacks = new Stacks();
        stacks.addNode(1);
        stacks.addNode(2);
        stacks.addNode(3);
        System.out.println(stacks);
        System.out.println(stacks.peek());
        System.out.println(stacks.isEmpty());
        System.out.println(stacks.poll());
        stacks.addNode(4);
        System.out.println(stacks);
        stacks.addNode(5);
        System.out.println(stacks);
        System.out.println(stacks.poll());
        System.out.println(stacks.poll());
        System.out.println(stacks.poll());
    }
}


