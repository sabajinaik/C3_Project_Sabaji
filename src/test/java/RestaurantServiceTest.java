import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


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
}