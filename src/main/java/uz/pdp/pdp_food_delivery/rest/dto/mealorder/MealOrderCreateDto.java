package uz.pdp.pdp_food_delivery.rest.dto.mealorder;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;

@Getter
@Setter
public class MealOrderCreateDto implements BaseDto {
    private AuthUserDto userDto;
    private MealDto mealDto;
    private boolean done;

    public MealOrderCreateDto(AuthUserDto userDto, MealDto mealDto) {
        this.userDto = userDto;
        this.mealDto = mealDto;
        this.done = false;
    }
}
