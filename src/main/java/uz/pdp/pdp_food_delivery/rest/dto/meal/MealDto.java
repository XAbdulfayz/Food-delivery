package uz.pdp.pdp_food_delivery.rest.dto.meal;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

import java.time.LocalDate;

@Getter
@Setter
public class MealDto extends GenericDto {

    private String photoId;

    private String photoPath;

    private Double price;

    private String name;

    private LocalDate date;

}
