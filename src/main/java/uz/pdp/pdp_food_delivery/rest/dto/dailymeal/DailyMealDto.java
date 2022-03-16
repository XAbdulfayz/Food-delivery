package uz.pdp.pdp_food_delivery.rest.dto.dailymeal;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
public class DailyMealDto extends GenericDto {

    private String name;

    private LocalDate date;

    private String photoId;

}
