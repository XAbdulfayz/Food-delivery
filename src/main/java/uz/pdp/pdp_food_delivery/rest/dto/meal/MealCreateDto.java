package uz.pdp.pdp_food_delivery.rest.dto.meal;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;

import java.time.LocalDate;

@Getter
@Setter
public class MealCreateDto implements BaseDto {

    private MultipartFile picture;

    private String name;

    private LocalDate date;

    private Double price;

    private String photoId;



    public MealCreateDto(String photoId, String name) {
        this.photoId = photoId;
        this.name = name;
    }
}
