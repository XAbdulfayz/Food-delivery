package uz.pdp.pdp_food_delivery.rest.dto.mealorder;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;
@Getter
@Setter
public class MealOrderUpdateDto extends GenericDto {
    private boolean done;
    private String chatId;
}
