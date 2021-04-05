import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();
    //<Sabaji><04/04/2021><Part 3: Solution>
    private Restaurant selectedRestaurant = new Restaurant();

    private int subTotalForSelectedRestaurantItems;
    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException{
        //<Sabaji><04/04/2021><Part 2: Solution>  Iterate through restaurants ignoring cases throw exception otherwise
        // Assumption - the search result will give first result from the ArrayList since return type is not ArrayList.
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().compareToIgnoreCase(restaurantName) == 0){
                this.selectedRestaurant = restaurant;
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

    //<Sabaji><04/04/2021><Part 3: Solution> - this method can be used for Recalcalculation
    public int getSelectedItemPriceTotal(List<Item> selectedItems){
        int itemTotalPrice = 0;
        for (Item selectedItem: selectedItems) {
            itemTotalPrice += selectedItem.getPrice();
        }
        this.subTotalForSelectedRestaurantItems = itemTotalPrice;
        return this.subTotalForSelectedRestaurantItems;
    }

    //<Sabaji><04/04/2021><Part 3: Solution> - this method can be used to dynamically update subTotal
    // ACTION = ADD : Checkbox is checked by user otherwise Unchecked
    public int getSelectedItemPriceTotal(Item selectedItems, String action){
        if (action == "ADD"){
            this.subTotalForSelectedRestaurantItems += selectedItems.getPrice();
        } else{
            this.subTotalForSelectedRestaurantItems -= selectedItems.getPrice();
        }
        return this.subTotalForSelectedRestaurantItems;
    }
    public int getsubTotalForSelectedRestaurantItems(){
        return subTotalForSelectedRestaurantItems;
    }

    public void setSubTotalForSelectedRestaurantItems(int subTotalForSelectedRestaurantItems) {
        this.subTotalForSelectedRestaurantItems = subTotalForSelectedRestaurantItems;
    }

    public Restaurant getSelectedRestaurant(){
        return selectedRestaurant;
    }
}
