package uz.pdp.pdp_food_delivery.rest.dto.mealorder;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;


@Getter
@Setter
public class MealOrderDto extends GenericDto {


    private AuthUserDto userDto;


    private MealDto mealDto;


    private boolean done;

}
