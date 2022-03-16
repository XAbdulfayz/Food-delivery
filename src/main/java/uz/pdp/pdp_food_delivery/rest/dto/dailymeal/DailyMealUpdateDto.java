package uz.pdp.pdp_food_delivery.rest.dto.dailymeal;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

@Getter
@Setter
public class DailyMealUpdateDto extends GenericDto {
    private boolean done;
}
