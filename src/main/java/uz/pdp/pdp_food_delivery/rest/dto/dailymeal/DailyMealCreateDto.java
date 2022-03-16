package uz.pdp.pdp_food_delivery.rest.dto.dailymeal;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
public class DailyMealCreateDto implements BaseDto {
    private String name;
    private LocalDate date;
    private String photoId;

    public DailyMealCreateDto(String name, LocalDate date, String photoId) {
        this.name = name;
        this.date = date;
        this.photoId = photoId;
    }
}
