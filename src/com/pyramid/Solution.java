package com.pyramid;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

///**
// * Company Y has employees that like to go out to lunch. Company Y is situated in an area that has many restaurants with different types of cuisine.  Whenever possible, the employees prefer to go out to lunch together – however, there is no guarantee that a single restaurant can satisfy all the employees.
// * <p>
// * Our goal is to design a system that keeps track of the restaurants in the area and their cuisine types, and can suggest a restaurant that will best satisfy a party of people.
// * <p>
// * Example Restaurants: (Feel free to add more)
// * •  Name:   Satisfactory Pita
// * o  Cuisine: Mediterranean
// * o  Price: $
// * •  Name:  Three Guys
// * o  Cuisine: Burgers & Fries
// * o  Price: $$
// * •  Name:   China Panda
// * o  Cuisine: Chinese
// * o  Price: $$$
// * <p>
// * Example Requests:
// * •  Name:   Keith Ward
// * o  Budget: $$
// * o  Cuisine: Chinese
// * •  Name:  Tony Jackson
// * o  Budget: $
// * o  Cuisine: Mediterranean
// * •  Name: Stephanie Lytton
// * o  Budget: $$$
// * o  Cuisine: Mediterranean, Burgers & Fries, Chinese
// */

/**
 * Cuisine enum lists all possible Restaurant cuisines.
 */
enum Cuisine {
    MEDITERRANEAN, BURGERS, CHINESE, MEXICAN, NOODLES
}

/**
 * Price enum list all possible values for Restaurant price and Employee budget. Integer values are assigned
 * to each element of the enum for comparison so that budget is inclusive of lower price values.
 */
enum Price {
    $(1), $$(2), $$$(3);

    private final int price;

    Price(int price) {
        this.price = price;
    }
}

/**
 * The Employee POJO. Employee members are immutable. It is assumed that Employee members do not change during the
 * execution of the program.
 */
class Employee {
    final String name;
    final List<Cuisine> cuisines;
    final Price budget;

    Employee(String name, Cuisine[] cuisines, Price budget) {
        this.name = name;
        this.cuisines = new ArrayList<>(Arrays.asList(cuisines));
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(cuisines, employee.cuisines) &&
                budget == employee.budget;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cuisines, budget);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", cuisines=" + cuisines +
                ", budget=" + budget +
                '}';
    }
}

/**
 * The Restaurant POJO. Restaurant members are immutable. It is assumed that Restaurant members do not change during the
 * execution of the program.
 */

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

    /**
     * Simplified toString() for use with streaming final output.
     *
     * @return Simplified Restaurant name for display in final output.
     */
    @Override
    public String toString() {
        return name;
    }
}

/**
 * Thrown when the Restaurant array is null or the Restaurant array contains a null.
 */
class InvalidRestaurantException extends Exception {
    public InvalidRestaurantException(String message) {
        super(message);
    }
}

/**
 * Thrown when the Employee array is null or the Employee array contains a null.
 */
class InvalidEmployeeException extends Exception {
    public InvalidEmployeeException(String message) {
        super(message);
    }
}

/**
 * Restaurant selector Solution implementation. This code assumes that Employee budget is inclusive, that is an Employee
 * budget indicates the maximum they are willing to spend on a Restaurant. This application creates two Arrays, one for
 * Employees and one for Restaurants. It then iterates over all the given Restaurants and adds a vote for each when it
 * matches an Employee's cuisine or is within an Employee's budget. As their are many cases when a tie in votes may
 * exist, this code displays an ordered list of the top five Restaurants, along with the number of votes tallied for
 * each Restaurant.
 */
public class Solution {

    private final Restaurant[] restaurants;
    private final Employee[] employees;

    /**
     * Default constructor requires an array of Restaurants and an array of Employees. Non null values are expected, but
     * handled. Null values for either array or contained in either array will cause immediate termination of the
     * application.
     *
     * @param restaurants An array of restaurants.
     * @param employees   An array of employees.
     */
    public Solution(Restaurant[] restaurants, Employee[] employees) {
        this.restaurants = restaurants;
        this.employees = employees;
    }

    /**
     * Execution method when run from the command line. Tallies the votes for each Restaurant and displays the top five
     * to standard out.
     *
     * @param args No command line arguments are used.
     */
    public static void main(String[] args) {

        Restaurant[] restaurants = new Restaurant[]{
                new Restaurant("Satisfactory Pita", Cuisine.MEDITERRANEAN, Price.$),
                new Restaurant("Three Guys", Cuisine.BURGERS, Price.$$),
                new Restaurant("Chinese Panda", Cuisine.CHINESE, Price.$$$),
                new Restaurant("Tasty Tacos", Cuisine.MEXICAN, Price.$$),
                new Restaurant("Bowl O' Noodles", Cuisine.NOODLES, Price.$),
                new Restaurant("Gyro Guy-o", Cuisine.MEDITERRANEAN, Price.$$)
        };

        Employee[] employees = new Employee[]{
                new Employee("Keith Ward", new Cuisine[]{Cuisine.CHINESE}, Price.$$),
                new Employee("Tony Jackson", new Cuisine[]{Cuisine.MEDITERRANEAN}, Price.$),
                new Employee("Stephanie Lytton", new Cuisine[]{Cuisine.MEDITERRANEAN, Cuisine.BURGERS, Cuisine.CHINESE}, Price.$$$)
        };

        try {
            Solution solution = new Solution(restaurants, employees);
            Map<Restaurant, Integer> sortedTopFive = solution.tallyVotes();

            System.out.println("Here are the top five (or less) options and their number of tallied votes, ranked in order of relevance. Enjoy!");
            sortedTopFive.entrySet().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Creates a Map of Restaurants to votes and initializes the vote value to zero.
     *
     * @param restaurants An array of Restaurants used to create the Map.
     * @return A Map, keyed by Restaurants with values for the number of votes.
     * @throws InvalidRestaurantException if the Restaurant array is null or it contains a null Restaurant.
     */
    Map<Restaurant, Integer> mapRestaurants(Restaurant[] restaurants) throws InvalidRestaurantException {
        if (restaurants == null || restaurants[0] == null) {
            throw new InvalidRestaurantException("No restaurants supplied.");
        }
        Map<Restaurant, Integer> returnMap = new HashMap<>();
        for (Restaurant restaurant : restaurants) {
            returnMap.put(restaurant, 0);
        }
        return returnMap;
    }

    /**
     * Tallies the number of votes for each restaurant in Restaurant array. A vote is added when the an Employee's
     * cuisine matches Restaurant's cuisine or the Employee's budget is less than or equal to the the Restaurant's price.
     *
     * @return A sorted Map, keyed by Restaurant, whose value is the number of votes.
     * @throws InvalidRestaurantException if the restaurant array is null or it contains a null restaurant.
     * @throws InvalidEmployeeException   if the employee array is null or it contains a null employee.
     */
    Map<Restaurant, Integer> tallyVotes() throws InvalidRestaurantException, InvalidEmployeeException {
        Map<Restaurant, Integer> restaurantMap = mapRestaurants(restaurants);
        if (employees == null || employees[0] == null) {
            throw new InvalidEmployeeException("No employees supplied.");
        }
        for (Restaurant restaurant : restaurants) {
            for (Employee employee : employees) {
                if (restaurant.price.compareTo(employee.budget) <= 0) {
                    restaurantMap.put(restaurant, restaurantMap.get(restaurant) + 1);
                }
                if (employee.cuisines.contains(restaurant.cuisine)) {
                    restaurantMap.put(restaurant, restaurantMap.get(restaurant) + 1);
                }
            }
        }
        return restaurantMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}

/**
 * Sample unit tests for the Solution class.
 */
class SolutionTest {

    Solution solution;
    Restaurant[] restaurants;
    Employee[] employees;

    /**
     * Check that an InvalidRestaurantException is thrown when the provided restaurant array is null.
     */
    @Test
    public void nullRestaurantTest() {
        employees = new Employee[]{new Employee("Test Name 1", new Cuisine[]{Cuisine.CHINESE}, Price.$)};
        solution = new Solution(null, employees);
        assertThrows(InvalidRestaurantException.class, () -> solution.tallyVotes());
    }

    /**
     * Check that an InvalidEmployeeException is thrown when the provided employee array is null.
     */
    @Test
    public void nullEmployeeTest() {
        restaurants = new Restaurant[]{new Restaurant("Test Restaurant 2", Cuisine.MEXICAN, Price.$)};
        solution = new Solution(restaurants, null);
        assertThrows(InvalidEmployeeException.class, () -> solution.tallyVotes());
    }

    /**
     * Check that two votes are tallied for the given restaurant, one for price and one for cuisine.
     */
    @Test
    public void oneRestaurantOneEmployeeTestTwoVotes() throws InvalidRestaurantException, InvalidEmployeeException {
        restaurants = new Restaurant[]{new Restaurant("Test Restaurant 3", Cuisine.MEXICAN, Price.$)};
        employees = new Employee[]{new Employee("Test Name 3", new Cuisine[]{Cuisine.MEXICAN}, Price.$)};
        solution = new Solution(restaurants, employees);
        Map<Restaurant, Integer> sortedTopFive = solution.tallyVotes();
        assertEquals(1, sortedTopFive.keySet().size());
        assertEquals(2, sortedTopFive.get(restaurants[0]));
    }

    /**
     * Check that zero votes are tallied for the given restaurant when there is no match between employee budget
     * or cuisine with restaurant price and cuisine.
     */
    @Test
    public void oneRestaurantOneEmployeeZeroVotes() throws InvalidRestaurantException, InvalidEmployeeException {
        restaurants = new Restaurant[]{new Restaurant("Test Restaurant 4", Cuisine.BURGERS, Price.$$)};
        employees = new Employee[]{new Employee("Test Name 4", new Cuisine[]{Cuisine.MEXICAN}, Price.$)};
        solution = new Solution(restaurants, employees);
        Map<Restaurant, Integer> sortedTopFive = solution.tallyVotes();
        assertEquals(1, sortedTopFive.keySet().size());
        assertEquals(0, sortedTopFive.get(restaurants[0]));
    }

    /**
     * Check that only the top five results are sorted when more than five restaurants and employees are given.
     */
    @Test
    public void manyRestaurantsManyEmployeesFiveResults() throws InvalidRestaurantException, InvalidEmployeeException {
        restaurants = new Restaurant[]{
                new Restaurant("Test Restaurant 5-1", Cuisine.BURGERS, Price.$),
                new Restaurant("Test Restaurant 5-2", Cuisine.MEXICAN, Price.$$),
                new Restaurant("Test Restaurant 5-3", Cuisine.MEDITERRANEAN, Price.$$$),
                new Restaurant("Test Restaurant 5-4", Cuisine.CHINESE, Price.$),
                new Restaurant("Test Restaurant 5-5", Cuisine.BURGERS, Price.$$),
                new Restaurant("Test Restaurant 5-6", Cuisine.BURGERS, Price.$$$)};
        employees = new Employee[]{
                new Employee("Test Name 5-1", new Cuisine[]{Cuisine.MEXICAN}, Price.$$),
                new Employee("Test Name 5-2", new Cuisine[]{Cuisine.BURGERS}, Price.$),
                new Employee("Test Name 5-3", new Cuisine[]{Cuisine.CHINESE}, Price.$$$),
                new Employee("Test Name 5-4", new Cuisine[]{Cuisine.MEDITERRANEAN}, Price.$$),
                new Employee("Test Name 5-5", new Cuisine[]{Cuisine.MEXICAN}, Price.$),
                new Employee("Test Name 5-6", new Cuisine[]{Cuisine.BURGERS}, Price.$$$),
                new Employee("Test Name 5-7", new Cuisine[]{Cuisine.CHINESE}, Price.$$),
        };
        solution = new Solution(restaurants, employees);
        Map<Restaurant, Integer> sortedTopFive = solution.tallyVotes();
        assertEquals(5, sortedTopFive.size());
    }

    /**
     * Checks that comparison of Prices return expected results.
     */
    @Test
    public void budgetComparisonTest() {
        assertTrue(Price.$.compareTo(Price.$$$) <= 0);
        assertTrue(Price.$$$.compareTo(Price.$$) >= 0);
    }
}