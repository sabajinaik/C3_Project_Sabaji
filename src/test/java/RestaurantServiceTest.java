import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //<Sabaji><04/04/2021><Part 2: Solution> Refactor the code
    private void initialize_test_case_Data_With_Amelie_Restaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }
    private void initialize_restaurant_menu_for_a_restaurant(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        //<Sabaji><04/04/2021><Part 2: Solution> Implement Test Case
        //Arrange
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();
        String lookupRestaurantName = "Amelie's cafe";
        //Act
        Restaurant resultRestaurantTest = service.findRestaurantByName(lookupRestaurantName);
        //Assert
        assertEquals(lookupRestaurantName, resultRestaurantTest.getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        //<Sabaji><04/04/2021><Part 2: Solution> Implement Test Case
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();
        String lookupRestaurantName = "Bhaucha Vadaapaav";

        //Act and Assert
        restaurantNotFoundException testException = assertThrows(restaurantNotFoundException.class,()->{
            service.findRestaurantByName(lookupRestaurantName);
        });
        //Assert
        assertEquals("Error: Restaurant could not be found", testException.getMessage());
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<Sabaji><04/04/2021><Part 3: Failing test case> TDD Test Cases for calculating item subtotal
    //<<<<<<<<<<<<<<<<<<<<SUBTOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Assumption: User has received the list of items from the menu in the UI using Restaurant.getMenu() method
    // User selects the list of items from the grid using checkbox
    // 1. Selecting a checkbox should dynamically update the subTotal of the items in the cart
    // 2. Unchecking the chekbox should reduce the price of item in the cart
    // 3. Calculate sub total when the list selected of items is given by the user (in case of recalculation)
    // 3. Initially the subTotal should be zero

    @Test
    public void initialize_the_Cart_with_zero_subTotal() throws restaurantNotFoundException {
        //<Sabaji><04/04/2021><Part 3: Failing test case>
        //Arrange
        RestaurantService restaurantService = new RestaurantService();
        Restaurant selectedRestaurantTest = new Restaurant();
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();

        //Act -
        selectedRestaurantTest = restaurantService.findRestaurantByName("Amelie's cafe");

        //Assert
        assertEquals(0, restaurantService.getsubTotalForSelectedRestaurantItems());
    }

    @Test
    public void when_user_selects_a_Singleitem_the_cart_total_should_increment_by_the_item_price_in_the_list() throws restaurantNotFoundException{
        //<Sabaji><04/04/2021><Part 3: Failing test case>
        //Arrange
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();
        Item testItem = new Item("Sweet corn soup", 119);

        RestaurantService restaurantService = new RestaurantService();
        Restaurant selectedRestaurant = restaurantService.findRestaurantByName("Amelie's cafe");
        restaurantService.setSubTotalForSelectedRestaurantItems(200);
        //Act
        restaurantService.getSelectedItemPriceTotal(testItem, "ADD");
        //Assert - upon adding the item, the total should increase by 119
        assertEquals(319, restaurantService.getsubTotalForSelectedRestaurantItems());
    }

    @Test
    public void when_user_unchecks_an_item_the_cart_total_should_increment_by_the_item_price_in_the_list() throws restaurantNotFoundException{
        //<Sabaji><04/04/2021><Part 3: Failing test case>
        //Arrange
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();
        Item testItem = new Item("Sweet corn soup", 119);

        RestaurantService restaurantService = new RestaurantService();
        Restaurant selectedRestaurant = restaurantService.findRestaurantByName("Amelie's cafe");
        restaurantService.setSubTotalForSelectedRestaurantItems(200);

        //Act
        restaurantService.getSelectedItemPriceTotal(testItem, "REMOVE");
        //Assert - upon unchecking the item (remove action), the cart total should reduce to 81
        assertEquals(81, restaurantService.getsubTotalForSelectedRestaurantItems());
    }

    @Test
    public void when_user_provides_all_selected_item_list_of_the_cart_it_should_calculate_total_amount() throws restaurantNotFoundException{
        //<Sabaji><04/04/2021><Part 3: Failing test case>
        //Arrange
        initialize_test_case_Data_With_Amelie_Restaurant();
        initialize_restaurant_menu_for_a_restaurant();
        List<Item> selectedItemList = new ArrayList<>();
        selectedItemList.add(new Item("Vegetable lasagne", 269));
        selectedItemList.add(new Item("Sweet corn soup", 119));

        RestaurantService restaurantService = new RestaurantService();
        Restaurant selectedRestaurant = restaurantService.findRestaurantByName("Amelie's cafe");
        //Act
        restaurantService.getSelectedItemPriceTotal(selectedItemList);
        //Assert - upon unchecking the item (remove action), the cart total should reduce to 81
        assertEquals(388, restaurantService.getsubTotalForSelectedRestaurantItems());

    }
    //<<<<<<<<<<<<<<<<<<<<SUBTOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>
}