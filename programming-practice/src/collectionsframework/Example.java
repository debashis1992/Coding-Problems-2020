package collectionsframework;

import java.util.*;
import java.util.stream.Collectors;

public class Example {
    public static void main(String[] args) {

        int[] arr = {100, 21, 2, 14, 1, 4, 54, 62, 72, 86, 9, 10};

        Set<Integer> set = new TreeSet<>(Comparator.reverseOrder());
        for (int i : arr)
            set.add(i);

        System.out.println(set);


        String word = "alphabets";
        Map<Character, Integer> map = new TreeMap<>();
        char[] ch = word.toCharArray();
        for (char c : ch) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        System.out.println(map);


        //creating another set
        Set<Integer> set2 = new HashSet<>();
        for (int i : arr)
            set2.add(i);

        System.out.println(set2);
        System.out.println(set2.stream().sorted().collect(Collectors.toList()));


        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("Stack: "+stack);
        System.out.println("peek: "+stack.peek());
        int p1 = stack.pop();
        System.out.println("pop: "+p1+", stack: "+stack);

        System.out.println("position: "+stack.search(1));

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        System.out.println("queue: "+queue);

        System.out.println("peek: "+queue.peek());
        queue.poll();
        System.out.println(queue);

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Set<Integer> set3 = new HashSet<>();
        set3.addAll(list);
        System.out.println(set3);

        for(int i: set3)
            System.out.println("set value: "+i);

        Iterator<Integer> iterator = set3.iterator();
        while(iterator.hasNext()) {
            System.out.println("set value using iterator: "+iterator.next());
        }

        for(Map.Entry<Character, Integer> entry: map.entrySet()) {
            System.out.println("key: "+entry.getKey()+", value: "+entry.getValue());
        }

        map.forEach((key, value) -> System.out.println("key: "+key+", value :"+value));

    }
}

