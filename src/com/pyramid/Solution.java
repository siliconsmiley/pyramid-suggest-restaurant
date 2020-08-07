package com.pyramid;

import java.util.*;

enum Cuisine {
    MEDITERRANEAN, BURGERS, CHINESE, MEXICAN
}

class Employee {
    final String name;
    final List<Cuisine> cuisines;
    final Price budget;

    Employee(String name, Cuisine[] cuisines, Price budget) {
        this.name = name;
        this.cuisines = new ArrayList<>(Arrays.asList(cuisines));
        this.budget = budget;
    }
}

enum Price {
    $, $$, $$$
}

/**
 * Company Y has employees that like to go out to lunch. Company Y is situated in an area that has many restaurants with different types of cuisine.  Whenever possible, the employees prefer to go out to lunch together – however, there is no guarantee that a single restaurant can satisfy all the employees.
 * <p>
 * Our goal is to design a system that keeps track of the restaurants in the area and their cuisine types, and can suggest a restaurant that will best satisfy a party of people.
 * <p>
 * Example Restaurants: (Feel free to add more)
 * •  Name:   Satisfactory Pita
 * o  Cuisine: Mediterranean
 * o  Price: $
 * •  Name:  Three Guys
 * o  Cuisine: Burgers & Fries
 * o  Price: $$
 * •  Name:   China Panda
 * o  Cuisine: Chinese
 * o  Price: $$$
 * <p>
 * Example Requests:
 * •  Name:   Keith Ward
 * o  Budget: $$
 * o  Cuisine: Chinese
 * •  Name:  Tony Jackson
 * o  Budget: $
 * o  Cuisine: Mediterranean
 * •  Name: Stephanie Lytton
 * o  Budget: $$$
 * o  Cuisine: Mediterranean, Burgers & Fries, Chinese
 **/

public class Solution {

    private final Map<Restaurant, Integer> restaurantMap;

    private static final Restaurant[] restaurants = new Restaurant[]{
            new Restaurant("Satisfactory Pita", Cuisine.MEDITERRANEAN, Price.$),
            new Restaurant("Three Guys", Cuisine.BURGERS, Price.$$),
            new Restaurant("Chinese Panda", Cuisine.CHINESE, Price.$$$),
            new Restaurant("Tasty Tacos", Cuisine.MEXICAN, Price.$$)
    };

    private static final Employee[] employees = new Employee[]{
            new Employee("Keith Ward", new Cuisine[]{Cuisine.CHINESE}, Price.$$),
            new Employee("Tony Jackson", new Cuisine[]{Cuisine.MEDITERRANEAN}, Price.$),
            new Employee("Stephanie Lytton", new Cuisine[]{Cuisine.MEDITERRANEAN, Cuisine.BURGERS, Cuisine.CHINESE}, Price.$$$)
//            new Employee("John Smith", new Cuisine[] {Cuisine.BURGERS, Cuisine.CHINESE}, Price.$$)
    };

    public Solution() {
        restaurantMap = mapRestaurants();
    }

    Map<Restaurant, Integer> mapRestaurants() {
        Map<Restaurant, Integer> returnMap = new HashMap<>();
        for (Restaurant restaurant : restaurants) {
            returnMap.put(restaurant, 0);
        }
        return returnMap;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        for (Restaurant restaurant : restaurants) {
            for (Employee employee : employees) {
                if (restaurant.price == employee.budget) {
                    solution.restaurantMap.put(restaurant, solution.restaurantMap.get(restaurant) + 1);
                }
                if (employee.cuisines.contains(restaurant.cuisine)) {
                    solution.restaurantMap.put(restaurant, solution.restaurantMap.get(restaurant) + 1);
                }
            }
        }
        System.out.println("!@#$!@#$ Sorted Restaurant Map !@#$!@#$");
        solution.restaurantMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(System.out::println);

//        Map<K,V> topTen =
//                map.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                        .limit(10)
//                        .collect(Collectors.toMap(
//                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}

class Restaurant {
    public final String name;
    public final Cuisine cuisine;
    final Price price;

    public Restaurant(String name, Cuisine cuisine, Price price) {
        this.name = name;
        this.cuisine = cuisine;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(name, that.name) &&
                cuisine == that.cuisine &&
                price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cuisine, price);
    }

    @Override
    public String toString() {
        return name;
    }
}