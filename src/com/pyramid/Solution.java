package com.pyramid;

import java.util.*;

//TODO use generics?
enum Cuisine {
    MEDITERRANEAN("Mediterranean"),
    BURGERS("Burgers & Fries"),
    CHINESE("Chinese"),
    MEXICAN("Mexican");

    private final String name;

    Cuisine(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

//TODO use generics?
enum Price {
    $(1f), $$(2f), $$$(3f);

    private final float price;

    Price(float price) {
        this.price = price;
    }

    float getPrice() {
        return price;
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
        return "Restaurant{" +
                "name='" + name + '\'' +
                '}';
    }
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

    private final Map<Cuisine,Integer> cuisineMap;
    private final Map<Price, Integer> priceMap;
    private final Map<Restaurant, Integer> restaurantMap;

    public Solution() {
        cuisineMap = mapCuisines();
        priceMap = mapPrices();
        restaurantMap = mapRestaurants();
    }

    //TODO use generics?
    Map<Cuisine, Integer> mapCuisines() {
        Map<Cuisine, Integer> returnMap = new HashMap<>();
        for (Cuisine cuisine : Cuisine.values()) {
            returnMap.put(cuisine, 0);
        }
        return returnMap;
    }

    //TODO use generics?
    Map<Price, Integer> mapPrices() {
        Map<Price, Integer> returnMap = new HashMap<>();
        for(Price price : Price.values()) {
            returnMap.put(price, 0);
        }
        return returnMap;
    }

    Map<Restaurant, Integer> mapRestaurants() {
        Map<Restaurant, Integer> returnMap = new HashMap<>();
        for (Restaurant restaurant : restaurants) {
            returnMap.put(restaurant, 0);
        }
        return returnMap;
    }

    void addOccurrenceToCuisine(Cuisine cuisine) {
        cuisineMap.put(cuisine, cuisineMap.get(cuisine) + 1);
    }

    void addOccurrenceToPrice(Price price) {
        priceMap.put(price, priceMap.get(price) + 1);
    }

    private static final Restaurant[] restaurants = new Restaurant[] {
            new Restaurant("Satisfactory Pita", Cuisine.MEDITERRANEAN, Price.$),
            new Restaurant("Three Guys", Cuisine.BURGERS, Price.$$),
            new Restaurant("Chinese Panda", Cuisine.CHINESE, Price.$$$),
            new Restaurant("Tasty Tacos", Cuisine.MEXICAN, Price.$$)
    };

    private static final Employee[] employees = new Employee[] {
            new Employee("Keith Ward", new Cuisine[]{Cuisine.CHINESE}, Price.$$),
            new Employee("Tony Jackson", new Cuisine[]{Cuisine.MEDITERRANEAN}, Price.$),
            new Employee("Stephanie Lytton", new Cuisine[]{Cuisine.MEDITERRANEAN, Cuisine.BURGERS, Cuisine.CHINESE}, Price.$$$)
//            new Employee("John Smith", new Cuisine[] {Cuisine.BURGERS, Cuisine.CHINESE}, Price.$$)
    };

    public static void main(String[] args) {
        Solution solution = new Solution();
        List<Cuisine> cuisines = new ArrayList<>();
        List<Price> prices = new ArrayList<>();


        for (Restaurant restaurant : restaurants) {
            for (Employee employee : employees) {
                // TODO remove in favor of map?
                prices.add(employee.budget);
                solution.addOccurrenceToPrice(employee.budget);
                if (restaurant.price == employee.budget) {
                    solution.restaurantMap.put(restaurant, solution.restaurantMap.get(restaurant) + 1);
                }
                if (employee.cuisines.contains(restaurant.cuisine)) {
                    solution.restaurantMap.put(restaurant, solution.restaurantMap.get(restaurant) + 1);
                }
            }
        }



        System.out.println("^^^^^^^^^^^^Cuisine Enum^^^^^^^^^^^");
        for (Cuisine c : Cuisine.values()) {
            System.out.println(c);
        }

        System.out.println("*********Restaurants***********");

        System.out.println(restaurants[0].name);
        System.out.println(restaurants[1].name);
        System.out.println(restaurants[2].name);
        System.out.println(restaurants[3].name);

        System.out.println("----------------cuisine list-----------");
        for (Cuisine c : cuisines) {
            System.out.println(c);
            System.out.println(c.getName());
        }

        System.out.println("$$$$$$$$$$$$$$$$price list$$$$$$$$$$$$$$$$");
        for (Price p : prices) {
            System.out.println(p.getPrice());
        }

        System.out.println("%%%%%%%%%%%%%%%%Sorted Cuisine Map%%%%%%%%%%%%");
        solution.cuisineMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(System.out::println);

        System.out.println("$$$$$$$$$$$Sorted Price Map$$$$$$$$$$$$$$$$$$$$$$$$");
        solution.priceMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(System.out::println);

        System.out.println("!@#$!@#$ Sorted Restaurant Map !@#$!@#$");
        solution.restaurantMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(System.out::println);

        //TODO create hash map of all cuisine types to collect number of occurrences

        //TODO find the best cuisine type
        //TODO find the restaurant that matches cuisine type

        //TODO find the best price
        //TODO find the restaurant that matches the price


    }

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

