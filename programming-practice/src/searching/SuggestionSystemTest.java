package searching;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SuggestionSystemTest {
    public static void main(String[] args) {
        String[] products = {"mobile","mouse","moneypot","monitor","mousepad"};
        String searchWord = "mouse";

        System.out.println(suggestedProducts(products, searchWord));
    }

    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> anotherList = new ArrayList<>();
        if(searchWord==null && searchWord.isEmpty()) {
            List<String> result = Arrays.asList(products);
            anotherList.add(result);
            return anotherList;
        }
        Arrays.sort(products);
        List<List<String>> finalList = new ArrayList<>();
        for(int i=1;i<searchWord.length()+1;i++) {
            String c = searchWord.substring(0,i);
//            System.out.println(c);
            List<String> list = Arrays.stream(products).filter(p -> p.startsWith(c))
                    .limit(3).collect(Collectors.toList());

            finalList.add(list);
        }

        return finalList;
    }
}
