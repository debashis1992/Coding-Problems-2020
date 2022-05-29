package slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class FruitsAndBaskets {
    public static void main(String[] args) {
//        int[] f = {0, 0, 1, 1, 0, 1, 2, 1, 2, 1, 2, 1, 0, 0, 1, 3, 4};
        int[] f = {0,1,1,1,6,6,4,4,6};
//        System.out.println(getMax(f));
        System.out.println(totalFruit(f));
    }

    public static int totalFruit(int[] fruits) {
        Map<Integer, Integer> freq = new HashMap<>();
        int maxFruits = 0;
        int start=0, end=0;

        while(end < fruits.length){
            int curFruit = fruits[end];

            if(freq.containsKey(curFruit)){
                freq.put(curFruit, freq.get(curFruit)+1);
            }else if(freq.size() < 2){
                freq.put(curFruit, 1);
            }
            else{
                int startFruit;
                while(freq.size() >= 2){
                    startFruit = fruits[start];
                    freq.put(startFruit, freq.get(startFruit)-1);
                    if(freq.get(startFruit) == 0){
                        freq.remove(startFruit);
                    }
                    start++;
                }
                freq.put(curFruit, 1);
            }
            maxFruits = Math.max(maxFruits, end-start+1);
            end++;
        }
        return maxFruits;
    }

    //near-to-close implementation
    public static int getMaxUsingSlidingWindow(int[] f) {
        if (f.length <= 2)
            return f.length;

        int max = 0;
        int start = 0, end = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(f[start], start);

        while (end + 1 < f.length) {
            if (map.containsKey(f[end + 1]))
                end++;
            else if (map.size() < 2 && !map.containsKey(f[end + 1])) {
                map.put(f[end + 1], end+1);
                end++;

            } else {
                max = Math.max(max, (end - start + 1));
                int index = map.get(f[end]);
                map = new HashMap<>();
                start=index;
                end = start;
                map.put(f[start], start);
            }
        }
        return Math.max(max, (end - start + 1));
    }

    public static int getMax(char[] f) {
        int max = 0;
        int count = 0;
        boolean basketFull = false;

        int n = f.length;
        for (int i = 0; i < n; i++) {
            Map<Character, Boolean> map = new HashMap<>();
            max = Math.max(max, count);
            count = 1;
            map.put(f[i], true);
            for (int j = i + 1; j < n; j++) {
                if (!map.containsKey(f[j]) && !basketFull) {
                    basketFull = true;
                    map.put(f[j], true);
                    count++;
                } else if (map.containsKey(f[j]))
                    count++;
                else {
                    max = Math.max(max, count);
                    basketFull = false;
                    break;
                }
            }
        }

        return Math.max(max, count);
    }
}
