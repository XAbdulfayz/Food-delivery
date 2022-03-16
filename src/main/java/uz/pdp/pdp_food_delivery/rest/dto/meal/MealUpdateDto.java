package uz.pdp.pdp_food_delivery.rest.dto.meal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class MealUpdateDto extends GenericDto {

    private MultipartFile picture;

    private String name;

    private Double price;

    private LocalDate date;

}
