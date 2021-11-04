package priorityqueue;

import java.util.*;

public class PriorityQueueExample {
    public static void main(String[] args) {
        String[] arr = {"ab","ac","ab","c","d","ac","ab"};
        int k = 2;

//        System.out.println(findTopKElements(arr,k));
//        PriorityQueue<Integer> pq = new PriorityQueue<>();
//        pq.offer(1);
//        pq.offer(2);
//        pq.offer(3);
//        pq.offer(4);
//        pq.offer(5);
//
//        while(!pq.isEmpty())
//            System.out.println(pq.poll());

        int[] ar = {3,2,1,5,6,4};
        System.out.println(findKthLargestElement(ar,2));

    }

    public static List<String> findTopKElements(String[] arr, int k) {
        Map<String,Integer> map = new HashMap<>();

        // create a map with each string frequency count
        for(String a: arr)
            map.put(a, map.getOrDefault(a,0)+1);

        // create a pq with the increasing order of frequency
        PriorityQueue<Map.Entry<String,Integer>> pq =
                new PriorityQueue<>(
                        (a,b) -> a.getValue() == b.getValue() ?
                                b.getKey().compareTo(a.getKey())
                                : a.getValue().compareTo(b.getValue())
                );

        for(Map.Entry<String,Integer> entry: map.entrySet()) {
            pq.offer(entry);
            // if size exceeds the limit of top K values, poll the lesser ones
            if(pq.size() > k)
                pq.poll();
        }

        List<String> list = new LinkedList<>();
        while(!pq.isEmpty()) {
            list.add(0, pq.poll().getKey());
        }
        return list;
    }

    public static int findKthLargestElement(int[] arr,int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int a: arr) {
            pq.offer(a);
            if(pq.size() > k)
                pq.poll();

        }
        return pq.poll();
    }
}
