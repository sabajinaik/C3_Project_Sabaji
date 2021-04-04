import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException{
        //<Sabaji><04/04/2021><Part 2: Solution>  Iterate through restaurants ignoring cases throw exception otherwise
        // Assumption - the search result will give first result from the ArrayList since return type is not ArrayList.
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().compareToIgnoreCase(restaurantName) == 0){
                return restaurant;
            }
        }
        throw new restaurantNotFoundException("Error: Restaurant could not be found");
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
