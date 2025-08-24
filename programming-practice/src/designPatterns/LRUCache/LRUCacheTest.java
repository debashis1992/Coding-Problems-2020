package designPatterns.LRUCache;

import java.util.*;

public class LRUCacheTest {
    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(4);
        cache.put("one",1);
        cache.put("two",2);
        cache.put("three",3);
        cache.put("four",4);
        cache.put("five",5);

        cache.view();
        cache.put("hunded", 100);
        cache.view();

    }
}


class LRUCache<K,V> {
    private final int capacity;
    private final LinkedHashMap<K, V> linkedHashMap;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.linkedHashMap = new LinkedHashMap<>(capacity, 1, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<K,V> entry) {
                return this.size() > capacity;
            }
        };
    }

    public synchronized boolean put(K key, V value) {
       return linkedHashMap.put(key, value) == value;
    }

    public synchronized V get(K key) {
        return linkedHashMap.get(key);
    }

    public void view() {
        System.out.println(linkedHashMap);
    }
}