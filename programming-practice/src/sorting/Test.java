package sorting;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        String[] foods = {"kimchi","miso","sushi","moussaka","ramen","bulgogi"};
        String[] cuisine = {"korean","japanese","japanese","greek","japanese","korean"};
        int[] ratings = {9,12,8,15,13,7};

        FoodRatings foodRatings = new FoodRatings(foods, cuisine, ratings);
        System.out.println(foodRatings.highestRated("korean"));

        foodRatings.changeRating("miso", 13);
        System.out.println(foodRatings.highestRated("japanese"));
    }
}




class Dish {
    String food;
    String cuisine;
    int rating;

    public Dish(String food, String cuisine, int rating) {
        this.food = food;
        this.cuisine = cuisine;
        this.rating = rating;
    }
}

class FoodRatings {

    List<Dish> listOfDishes;
    Map<String, Set<Dish>> mapOfCuisineDishList;
    Map<Dish, Integer> mapOfDishRating;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        List<Dish> dishes= new ArrayList<>();
        for(int i=0;i<foods.length;i++) {
            Dish dish = new Dish(foods[i], cuisines[i], ratings[i]);
            dishes.add(dish);
        }

        listOfDishes = new ArrayList<>(dishes);
        mapOfDishRating = listOfDishes.stream().collect(Collectors.toMap(d -> d, d -> d.rating));

        mapOfCuisineDishList = listOfDishes.stream().collect(Collectors.groupingBy(d -> d.cuisine,
                Collectors.toCollection(() -> new TreeSet<>(((o1, o2) -> {
                    if(o1.rating < o2.rating) {
                        return 1;
                    } else if(o1.rating > o2.rating) {
                        return -1;
                    }
                    else {
                        return o1.food.compareTo(o2.food);
                    }
                })))));
    }

    public void changeRating(String food, int newRating) {
        List<Dish> dishes = listOfDishes.stream().filter(d -> d.food.equals(food)).collect(Collectors.toList());
        Dish dish = dishes.get(0);
        mapOfDishRating.remove(dish);
        dish.rating = newRating;

        mapOfDishRating.put(dish, newRating);
        listOfDishes.stream().filter(d -> d.food.equals(food)).forEach(d -> d.rating = newRating);
    }

    public String highestRated(String cuisine) {
        Set<Dish> dishes = mapOfCuisineDishList.get(cuisine);
//        List<Dish> ans = dishes.stream().sorted((o1,o2) -> {
//            if(o1.rating < o2.rating) {
//                return 1;
//            } else if(o1.rating > o2.rating) {
//                return -1;
//            }
//            else {
//                return o1.food.compareTo(o2.food);
//            }
//        }).collect(Collectors.toList());
        List<Dish> ans = new ArrayList<>(dishes);
        return ans.get(ans.size()-1).food;
    }
}