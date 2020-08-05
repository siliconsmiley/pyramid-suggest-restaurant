package com.pyramid;

enum Cuisine {
    MEDITERRANEAN("Mediterranean"),
    BURGERS("Burgers & Fries"),
    CHINESE("Chinese"),
    MEXICAN("Mexican");

    private final String name;

    Cuisine(String name) {
        this.name = name;
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

    public static void main(String[] args) {
        Solution solution = new Solution();
        Restaurant[] restaurants = new Restaurant[]{
                new Restaurant("Satisfactory Pita", Cuisine.MEDITERRANEAN, Price.$),
                new Restaurant("Three Guys", Cuisine.BURGERS, Price.$$),
                new Restaurant("Chinese Panda", Cuisine.CHINESE, Price.$$$),
                new Restaurant("Tasty Tacos", Cuisine.MEXICAN, Price.$$)};

        Employee[] employees = new Employee[]{
                new Employee("Keith Ward", new Cuisine[]{Cuisine.CHINESE}, Price.$$),
                new Employee("Tony Jackson", new Cuisine[]{Cuisine.MEDITERRANEAN}, Price.$),
                new Employee("Stephanie Lytton", new Cuisine[]{Cuisine.MEDITERRANEAN, Cuisine.BURGERS, Cuisine.CHINESE}, Price.$$$)
        };


        System.out.println("hello coderpad");
        System.out.println(restaurants[0].name);
        System.out.println(restaurants[1].name);
        System.out.println(restaurants[2].name);
        System.out.println(restaurants[3].name);
    }

}

class Restaurant {
    final String name;
    final Cuisine cuisine;
    final Price price;

    public Restaurant(String name, Cuisine cuisine, Price price) {
        this.name = name;
        this.cuisine = cuisine;
        this.price = price;
    }
}

class Employee {
    final String name;
    final Cuisine[] cuisines;
    final Price budget;

    Employee(String name, Cuisine[] cuisines, Price budget) {
        this.name = name;
        this.cuisines = cuisines;
        this.budget = budget;
    }
}

