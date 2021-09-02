import org.junit.jupiter.api.BeforeEach;
import com.RestaurantFinder.Item;
import com.RestaurantFinder.Restaurant;
import com.RestaurantFinder.itemNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import java.util.ArrayList;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    List<Item> dummy = new ArrayList<>();

    public void restaurantCreate(){
        LocalTime OpeningTime = LocalTime.parse("10:30:00");
        LocalTime ClosingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",OpeningTime,ClosingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurantCreate();
        restaurant.setClosingTime(LocalTime.now().plusMinutes(10));
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurantCreate();
        restaurant.setClosingTime(LocalTime.now().minusMinutes(10));
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<<<Order value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void order_value_should_get_cumulative_total_when_collection_of_item_selected(){
        restaurantCreate();
        dummy = restaurant.getMenu();
        assertEquals(506,restaurant.getOrderValue(dummy));
    }

    @Test
    public void order_value_should_reduce_cumulative_total_when_an_item_removed(){
        restaurantCreate();
        dummy=restaurant.getMenu();
        int total = restaurant.getOrderValue(dummy);
        int afterTotal = dummy.get(1).getPrice();
        dummy.remove(1);
        assertEquals(total-afterTotal,restaurant.getOrderValue(dummy));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<Order value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurantCreate();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurantCreate();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurantCreate();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}